package com.ukrim.ppbl.posapp.ui.product

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tfb.fbtoast.FBToast
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.ProdukData
import com.ukrim.ppbl.posapp.presenter.AllPresenter
import com.ukrim.ppbl.posapp.view.CommonView
import kotlinx.android.synthetic.main.activity_add_product.*
import java.io.ByteArrayOutputStream

class AddProductActivity : AppCompatActivity(), CommonView {
    lateinit var presenter: AllPresenter
    private var imageBase64: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        settingTombolSave();
    }

    private fun validate(): Boolean {
        var result = true
        if (et_name.text.toString() == "" ||
            et_harga.text.toString() == "" ||
            et_harga.text.toString() == "0" ||
            et_stock.text.toString() == "" ||
            et_stock.text.toString() == "0" ||
            imageBase64 == ""
        ) {
            result = false
        }
        return result
    }

    private fun settingTombolSave() {
        btn_save_product.setOnClickListener {
            val produkData = ProdukData(
                "0",
                et_name.text.toString(),
                et_harga.text.toString(),
                et_stock.text.toString(),
                imageBase64,
                "",
                "",
                ""
            );
            if(validate()) {
                btn_save_product.isEnabled = false
                presenter = AllPresenter(this, this)
                presenter.createProduk(produkData)
            }else{
                FBToast.errorToast(this,"Ada data yang masih salah",FBToast.LENGTH_SHORT)
            }
        }
        iv_produk.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 12)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap
            val byteArrayOS = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS)
            imageBase64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
            Glide.with(this).load(bitmap).into(iv_produk);
        }
    }

    override fun success(anyResponse: Any) {
        FBToast.successToast(this, "Data berhasil disimpan", FBToast.LENGTH_SHORT)
        et_name.setText("")
        et_harga.setText("")
        et_stock.setText("")
        btn_save_product.isEnabled = true;
        imageBase64 = ""
    }

    override fun error(error: Throwable) {
        btn_save_product.isEnabled = true;
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}
