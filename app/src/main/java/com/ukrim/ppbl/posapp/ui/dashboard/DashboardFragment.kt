package com.ukrim.ppbl.posapp.ui.dashboard


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.ui.product.ProductFragment

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
    companion object {
        fun newInstance(): DashboardFragment = DashboardFragment()
    }

}
