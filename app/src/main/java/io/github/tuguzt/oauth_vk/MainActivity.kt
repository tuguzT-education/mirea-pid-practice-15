package io.github.tuguzt.oauth_vk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import io.github.tuguzt.oauth_vk.databinding.ActivityMainBinding
import io.github.tuguzt.oauth_vk.BuildConfig.VK_AUTH_KEY

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object VkServer {
        private const val API_SERVER_URL = "https://api.vk.com/"

        fun api(): Retrofit =
            Retrofit.Builder().baseUrl(API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = api().create(VkService::class.java)

        binding.read.setOnClickListener {
            val fields = mutableListOf<String>().apply {
                if (binding.birthday.isChecked) add("bdate")
                if (binding.status.isChecked) add("status")
                if (binding.isFriend.isChecked) add("is_friend")
            }.joinToString(",")

            service.getUsersProfiles(
                binding.id.text.toString(), fields, VK_AUTH_KEY
            ).enqueue(callback<VkUsersResponse> { list ->
                try {
                    binding.result.text =
                        list.response!!.joinToString("\n________\n") { it.toString() }
                } catch (e: Exception) {
                    binding.result.text = listOf(
                        "Произошла ошибка.",
                        "Проверьте, что у вас включен Интернет и указан ID пользователя."
                    ).joinToString("\n\n")
                }
            })
        }
    }

    private fun <T> callback(onSuccess: (response: T) -> Unit) =
        object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {  }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                Log.d("IDF", response.body().toString() )
                response.body()?.let { onSuccess(it) }
            }
        }
}
