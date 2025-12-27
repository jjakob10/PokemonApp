package com.example.pokemonapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityLoginBinding
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }

    override fun onClick(view: View) {

        if(view.id == R.id.button_login) {
            if(binding.editEmail.text.toString() == "" || binding.editPassword.text.toString() == ""){
                Toast.makeText(this, R.string.valid_login, Toast.LENGTH_SHORT).show()
            }else{
                loginVM.login()
            }
        }else if(view.id == R.id.not_registered) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}