package com.ukrim.ppbl.posapp.ui.product


import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.bottomsheets.BaseBottomSheet
import com.arthurivanets.bottomsheets.ktx.actionPickerConfig
import com.arthurivanets.bottomsheets.ktx.showActionPickerBottomSheet
import com.arthurivanets.bottomsheets.sheets.listeners.OnItemSelectedListener

import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.model.ProductsResponse
import com.ukrim.ppbl.posapp.model.ProdukData
import com.ukrim.ppbl.posapp.presenter.AllPresenter
import com.ukrim.ppbl.posapp.util.bs.ActionProviders
import com.ukrim.ppbl.posapp.util.bs.ConfirmProvider
import com.ukrim.ppbl.posapp.view.CommonView
import kotlinx.android.synthetic.main.fragment_product.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(), CommonView, ProductAdapter.Listener {

    lateinit var presenter: AllPresenter
    var products: MutableList<ProdukData> = mutableListOf()
    lateinit var adapter: ProductAdapter
    private var bottomSheet: BaseBottomSheet? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_product, container,
            false
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllProduk()
    }

    companion object {
        fun newInstance(): ProductFragment = ProductFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settingRV();

        btn_add_product.setOnClickListener {
            val intent = Intent(activity?.applicationContext, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun settingRV() {
        rv_product.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        rv_product.layoutManager = layoutManager as RecyclerView.LayoutManager?
        rv_product.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(8), true))
        rv_product.itemAnimator = DefaultItemAnimator()
        adapter = ProductAdapter(products, this)
        rv_product.adapter = adapter
        presenter = AllPresenter(this, activity!!.applicationContext)
        presenter.getAllProduk()
    }

    override fun showLoading() {
    }

    override fun error(error: Throwable) {
    }

    override fun success(anyResponse: Any) {
        val mProductsResponse = anyResponse as ProductsResponse
        products.clear()
        products.addAll(mProductsResponse.produkData)
        adapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
    }

    override fun onItemClick(product: ProdukData) {

    }

    private fun dismissBottomSheet(animate: Boolean = true) {
        bottomSheet?.dismiss(animate)
    }

    override fun onItemLongClick(product: ProdukData) {
        dismissBottomSheet()

        bottomSheet = showActionPickerBottomSheet(
            options = ActionProviders.getActionOptions(
                activity!!.applicationContext
            ),
            config = actionPickerConfig {
                title(product.namaProduk)
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    ActionProviders.Id.DELETE -> {
                        settingConfirmDelete(product)
                    }
                    ActionProviders.Id.EDIT -> {
                        Toast.makeText(
                            activity?.applicationContext,
                            "Edit Click",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        )

    }

    private fun settingConfirmDelete(product: ProdukData) {
        dismissBottomSheet()

        bottomSheet = showActionPickerBottomSheet(
            options = ConfirmProvider.getConfirmActionOptions(
                activity!!.applicationContext
            ),
            config = actionPickerConfig {
                title("Anda Yakin Hapus Produk ini?")
            },
            onItemSelectedListener = OnItemSelectedListener {
                when (it.id) {
                    ConfirmProvider.Id.CONFIRM -> {
                        Toast.makeText(
                            activity?.applicationContext,
                            "Proses Delete",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        )
    }

    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left =
                    column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics
            )
        )
    }

}
