package com.ukrim.ppbl.posapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.LoginResponse
import com.ukrim.ppbl.posapp.presenter.AllPresenter
import com.ukrim.ppbl.posapp.util.Preferences
import com.ukrim.ppbl.posapp.view.CommonView

class SplashScreenActivity : AppCompatActivity(), CommonView {

    private lateinit var presenter: AllPresenter
    private var belumAutoLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        presenter = AllPresenter(this, this)
        presenter.getAllProduk()
    }

    override fun showLoading() {
        Log.d("TAGX", "Loading")
    }

    override fun error(error: Throwable) {
        if(belumAutoLogin) {
            belumAutoLogin = false
            val username = Preferences.getUsername(this)
            val password = Preferences.getPassword(this)
            val strAuth = username + ":" + password
            val auth = "Basic " + Base64.encodeToString(strAuth.toByteArray(), Base64.NO_WRAP)
            presenter.getToken(auth)
            return
        }
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun success(anyResponse: Any) {
        if(!belumAutoLogin){
            val loginResponse = anyResponse as LoginResponse
            val token = loginResponse.token
            Preferences.saveToken(token,this)
        }
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun hideLoading() {
        Log.d("TAGX", "Selesai")
    }
}
