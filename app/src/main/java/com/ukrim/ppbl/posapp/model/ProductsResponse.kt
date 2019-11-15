package com.ukrim.ppbl.posapp.model
import com.google.gson.annotations.SerializedName


data class ProductsResponse(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("produk_data")
    val produkData: MutableList<ProdukData>
)

data class ProdukData(
    @SerializedName("id_produk")
    var idProduk: String,
    @SerializedName("nama_produk")
    var namaProduk: String,
    @SerializedName("harga_produk")
    var hargaProduk: String,
    @SerializedName("stock_produk")
    var stockProduk: String,
    @SerializedName("gambar_produk")
    var gambarProduk: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("updated_at")
    var updatedAt: String
)