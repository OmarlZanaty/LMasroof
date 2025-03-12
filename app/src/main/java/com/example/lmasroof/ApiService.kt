package com.example.lmasroof


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Data classes
data class LoginRequest(val email: String, val password: String)
data class SignupRequest(val name: String, val email: String, val password: String)
data class ApiResponse(val success: Boolean, val message: String)

// API Interface
interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<ApiResponse>

    @POST("signup")
    fun signUp(@Body request: SignupRequest): Call<ApiResponse>
}
