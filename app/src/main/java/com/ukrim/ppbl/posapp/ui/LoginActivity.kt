package com.ukrim.ppbl.posapp.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.LoginResponse
import com.ukrim.ppbl.posapp.presenter.AllPresenter
import com.ukrim.ppbl.posapp.util.Preferences
import com.ukrim.ppbl.posapp.view.CommonView
import kotlinx.android.synthetic.main.activity_login.*
import java.io.ByteArrayOutputStream


class LoginActivity : AppCompatActivity(), CommonView {

    private lateinit var presenter: AllPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = AllPresenter(this, this)
        settingTombolLogin()
        checkAndRequestPermissions()
    }

    private fun settingTombolLogin() {
        bt_login.setOnClickListener {

            val username = et_login_username.text.toString()
            val password = et_login_password.text.toString()
            if (cb_remeber.isChecked) {
                Preferences.saveUsername(username, this)
                Preferences.savePassword(password, this)
            }
            val strAuth = username + ":" + password
            val auth = "Basic " + Base64.encodeToString(strAuth.toByteArray(), Base64.NO_WRAP)
            presenter.getToken(auth)
//            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(intent, 12)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap
            val byteArrayOS = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOS)
            val base64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
            Log.d("TAG", base64.toString())
        }
    }

    override fun showLoading() {

    }

    override fun error(error: Throwable) {
        Log.d("TAGX", error.localizedMessage)
        Toast.makeText(this, "Username dan Password tidak ditemukan", Toast.LENGTH_LONG).show()
    }

    override fun success(anyResponse: Any) {
        Log.d("TAGX", "Sukses Login")
        //ketika username dan password benar
        val loginResponse = anyResponse as LoginResponse
        val token = loginResponse.token
        Preferences.saveToken(token, this)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun hideLoading() {
    }


    private fun checkAndRequestPermissions(): Boolean {
//        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val readExternalPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeExternalPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded = arrayListOf<String>()
//        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (readExternalPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writeExternalPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 400)
            return false
        }
        return true
    }
}
