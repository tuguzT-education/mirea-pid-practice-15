package io.github.tuguzt.oauth_vk

data class VkUser(
    var id: Int, var first_name: String, var last_name: String,
    var bdate: String, var status: String, var is_friend: Int,
) {
    constructor() : this(
        0, "", "", "", "", -1
    )

    override fun toString() =
        mutableListOf(
            "Информация о пользователе:",
            "ID = $id",
            "Имя = $first_name",
            "Фамилия = $last_name",
        ).apply {
            if (bdate.isNotBlank()) add("День рождения = $bdate")
            if (status.isNotBlank()) add("Статус = $status")

            add(when (is_friend) {
                0 -> "Не является вашим другом"
                1 -> "Является вашим другом"
                else -> ""
            })
        }.joinToString("\n\n")
}
