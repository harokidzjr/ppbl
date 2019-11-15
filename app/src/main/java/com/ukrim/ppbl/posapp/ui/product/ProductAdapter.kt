package com.ukrim.ppbl.posapp.ui.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.ProdukData


class ProductAdapter(
    private val products: MutableList<ProdukData>,
    private val listener: Listener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(products.get(position), listener)
    }

    interface Listener {
        fun onItemClick(product: ProdukData)
        fun onItemLongClick(product: ProdukData)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaProduct: TextView = itemView.findViewById(R.id.tv_nama_product)
        val tvHargaProduct: TextView = itemView.findViewById(R.id.tv_harga_product)
        val tvStockProduct: TextView = itemView.findViewById(R.id.tv_stock_product)
        val ivImageProduct: ImageView = itemView.findViewById(R.id.iv_product)
        fun bindModel(product : ProdukData, listener: Listener) {
            tvNamaProduct.text = product.namaProduk
            tvHargaProduct.text = product.hargaProduk
            tvStockProduct.text = product.stockProduk
            Glide.with(itemView.context).load(product.imageUrl).into(ivImageProduct)
            itemView.setOnClickListener {
                listener.onItemClick(product)
            }
            itemView.setOnLongClickListener {
                listener.onItemLongClick(product)
                true
            }
        }
    }
}