package com.example.pokemonapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityLoginBinding
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginVM: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginVM = ViewModelProvider(this).get(LoginViewModel::class.java)
        setObserver()

        binding.buttonLogin.setOnClickListener(this)
        binding.notRegistered.setOnClickListener(this)

    }

    private fun setObserver() {


        loginVM.getLogin().observe(this, Observer {
            binding.editEmail.setText("")
            binding.editPassword.setText("")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        loginVM.getErrorMsg().observe(this, Observer {
            when (it) {
                Constants.FIREBASE.INVALID_USER -> {
                    Toast.makeText(this, R.string.invalid_user, Toast.LENGTH_SHORT).show()
                }
                Constants.FIREBASE.WRONG_PASSWORD -> {
                    Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    override fun onClick(view: View) {

        if(view.id == R.id.button_login) {
            if(binding.editEmail.text.toString() == "" || binding.editPassword.text.toString() == ""){
                Toast.makeText(this, R.string.valid_login, Toast.LENGTH_SHORT).show()
            }else{
                loginVM.login(binding.editEmail.text.toString(), binding.editPassword.text.toString())
            }
        }else if(view.id == R.id.not_registered) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}