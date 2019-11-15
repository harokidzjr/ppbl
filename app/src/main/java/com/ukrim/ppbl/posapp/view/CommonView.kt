package com.ukrim.ppbl.posapp.view

interface CommonView {
    fun showLoading()
    fun error(error : Throwable)
    fun success(anyResponse : Any)
    fun hideLoading()
}