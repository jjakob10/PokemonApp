package com.example.pokemonapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityRegisterBinding
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var registerVM: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerVM = ViewModelProvider(this).get(RegisterViewModel::class.java)
        setObserver()

        binding.buttonRegister.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
    }

    private fun setObserver() {

        registerVM.getRegister().observe(this, Observer {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

        registerVM.getErrorMsg().observe(this, Observer {
            when (it) {
                Constants.FIREBASE.WEAK_PASSWORD -> {
                    Toast.makeText(this, R.string.weak_password, Toast.LENGTH_SHORT).show()
                }
                Constants.FIREBASE.INVALID_CREDENTIAL -> {
                    Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show()
                }
                Constants.FIREBASE.USER_COLISION -> {
                    Toast.makeText(this, R.string.user_collision, Toast.LENGTH_SHORT).show()
                }
                Constants.FIREBASE.FAIL -> {
                    Toast.makeText(this, R.string.register_fail, Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button_register) {

            if(binding.editName.text.toString() == "" || binding.editEmail.text.toString() == ""
                || binding.editPassword.text.toString() == "" || binding.editPasswordConfirm.text.toString() == ""){
                Toast.makeText(this, R.string.valid_register, Toast.LENGTH_SHORT).show()
            }else if(binding.editPassword.text.toString() != binding.editPasswordConfirm.text.toString()){
                Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT).show()
            }else {
                registerVM.register(
                    name = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                    password = binding.editPassword.text.toString()
                )
            }
        } else if (v?.id == R.id.img_back) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}