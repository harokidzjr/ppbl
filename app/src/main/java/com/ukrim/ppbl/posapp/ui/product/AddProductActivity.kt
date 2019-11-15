package com.ukrim.ppbl.posapp.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.ProdukData
import com.ukrim.ppbl.posapp.presenter.AllPresenter
import com.ukrim.ppbl.posapp.view.CommonView
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity(),CommonView {
    lateinit var presenter: AllPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        settingTombolSave();
    }
    private fun settingTombolSave() {
        btn_save_product.setOnClickListener {
            var produkData = ProdukData(
                "0",
                et_name.text.toString(),
                et_harga.text.toString(),
                et_stock.text.toString(),
                "-",
                "",
                "",
                ""
            );
            presenter = AllPresenter(this,this)
            presenter.createProduk(produkData)
        }
    }
    override fun success(anyResponse: Any) {
        Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();
        et_name.setText("")
        et_harga.setText("")
        et_stock.setText("")
    }
    override fun error(error: Throwable) {
    }
    override fun showLoading() {
    }





    override fun hideLoading() {
    }
}
