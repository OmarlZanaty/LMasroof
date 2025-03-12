package com.example.lmasroof

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lmasroof.SignupRequest
import com.example.lmasroof.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.URL

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        testServerConnection()  // 🔹 Call test function


        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            val request = SignupRequest(name, email, password)
            RetrofitClient.apiService.signUp(request).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            Toast.makeText(this@SignUpActivity, "Response: ${responseBody.message}", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@SignUpActivity, "Response body is null!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, "Server Error: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
    private fun testServerConnection() {
        val testUrl = "http://13.212.79.5:3000/"  // 🔹 Make sure this is your actual API base URL
        try {
            val url = URL(testUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val responseCode = connection.responseCode
            if (responseCode == 200) {
                Log.d("SERVER_TEST", "✅ Server is reachable!")
            } else {
                Log.e("SERVER_TEST", "❌ Server returned error: $responseCode")
            }
        } catch (e: Exception) {
            Log.e("SERVER_TEST", "❌ Cannot reach server: ${e.message}")
        }
    }
}
