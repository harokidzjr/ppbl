package com.ukrim.ppbl.posapp.networking

import com.ukrim.ppbl.posapp.model.LoginResponse
import com.ukrim.ppbl.posapp.model.ProductsResponse
import com.ukrim.ppbl.posapp.model.SuccessResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface RetrofitService {
    @POST("auth")
    fun getToken(
        @Header("Authorization") basic: String
    ): Deferred<LoginResponse>

    @GET("produk")
    fun getAllProduk(): Deferred<ProductsResponse>

    @FormUrlEncoded
    @POST("produk")
    fun createProduk(
        @Field("nama_produk") namaProduk : String,
        @Field("harga_produk") hargaProduk : String,
        @Field("stock_produk") stockProduk : String,
        @Field("gambar_produk") gambarProduk : String
    ): Deferred<SuccessResponse>
}