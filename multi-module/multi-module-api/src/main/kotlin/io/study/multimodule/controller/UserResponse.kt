package io.study.multimodule.controller

import io.study.multimodule.domain.user.User

data class UserResponse(
    val name: String
) {
    constructor(user: User) : this(user.name)
}