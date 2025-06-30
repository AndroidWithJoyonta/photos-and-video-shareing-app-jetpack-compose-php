package com.sharing.monio.data.api

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val user: User? = null
)

data class User(
    val id: Int,
    val email: String
)

