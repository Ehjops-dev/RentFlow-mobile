package com.rentflow.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.R
import com.rentflow.MainActivity
import com.rentflow.databinding.ActivityLoginBinding
import com.rentflow.util.SessionManager
import com.rentflow.viewmodel.LoginUiState
import com.rentflow.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isRememberMeChecked = true
    private var isPasswordVisible = false
    
    private val viewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(SessionManager(applicationContext)) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Force status bar to match the exact background color of the web blueprint (#F9F9F9) and use dark icons
        window.statusBarColor = Color.parseColor("#F9F9F9")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize state to selected/checked state by default to match mockup design
        binding.customCheckboxImage.isSelected = isRememberMeChecked

        // Set initial password toggle icon to match Material style (Pic 3) instead of legacy Android eye
        binding.passwordToggle.setImageResource(R.drawable.design_ic_visibility)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text?.toString()?.trim() ?: ""
            val password = binding.passwordEditText.text?.toString() ?: ""

            if (email.isEmpty()) {
                Toast.makeText(this, "Email address is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Temporary Bypass for Local Development/UI Testing
            // Directly launch MainActivity to see the Home screen layout
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding.passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.passwordToggle.setImageResource(R.drawable.design_ic_visibility_off)
            } else {
                binding.passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.passwordToggle.setImageResource(R.drawable.design_ic_visibility)
            }
            binding.passwordToggle.setColorFilter(Color.parseColor("#4B5563"))
            binding.passwordEditText.setSelection(binding.passwordEditText.text.length)
        }

        binding.rememberMeRow.setOnClickListener {
            isRememberMeChecked = !isRememberMeChecked
            binding.customCheckboxImage.isSelected = isRememberMeChecked
        }

        binding.forgotPasswordButton.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }

        binding.helpButton.setOnClickListener {
            Toast.makeText(this, "Support contact: caretaker@rentflow.com", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is LoginUiState.Idle -> {
                        setLoading(false)
                    }
                    is LoginUiState.Loading -> {
                        setLoading(true)
                    }
                    is LoginUiState.Success -> {
                        setLoading(false)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    is LoginUiState.Error -> {
                        setLoading(false)
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
        binding.emailEditText.isEnabled = !isLoading
        binding.passwordEditText.isEnabled = !isLoading
    }
}
