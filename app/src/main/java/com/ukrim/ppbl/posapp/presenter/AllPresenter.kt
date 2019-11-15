package com.ukrim.ppbl.posapp.presenter

import android.content.Context
import com.ukrim.ppbl.posapp.model.ProdukData
import com.ukrim.ppbl.posapp.networking.RetrofitFactory
import com.ukrim.ppbl.posapp.view.CommonView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AllPresenter(
    private val view: CommonView,
    private val context: Context
) {
    fun getToken(auth: String) {
        view.showLoading()
        val api = RetrofitFactory.create()
        val request = api.getToken(auth)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = request.await()
                view.success(response)
            } catch (e: Exception) {
                view.error(e)
            }
            view.hideLoading()
        }
    }
    fun getAllProduk() {
        view.showLoading()
        val api = RetrofitFactory.create(context)
        val request = api.getAllProduk()
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = request.await()
                view.success(response)
            } catch (e: Exception) {
                view.error(e)
            }
            view.hideLoading()
        }
    }
    fun createProduk(produk : ProdukData){
        view.showLoading()
        val api = RetrofitFactory.create(context)
        val request = api.createProduk(
            produk.namaProduk,
            produk.hargaProduk,
            produk.stockProduk,
            produk.gambarProduk
        )
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = request.await()
                view.success(response)
            } catch (e: Exception) {
                view.error(e)
            }
            view.hideLoading()
        }
    }

}