package com.ukrim.ppbl.posapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ukrim.ppbl.posapp.R
import com.ukrim.ppbl.posapp.ui.dashboard.DashboardFragment
import com.ukrim.ppbl.posapp.ui.product.ProductFragment
import com.ukrim.ppbl.posapp.util.Preferences

class HomeActivity : AppCompatActivity() {

    private lateinit var dl: DrawerLayout
    private lateinit var t: ActionBarDrawerToggle
    private lateinit var nv: NavigationView
    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        dl = findViewById(R.id.activity_home)
        t = ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close)
        dl.addDrawerListener(t)
        t.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nv = findViewById(R.id.nv)
        nv.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            dl.closeDrawer(Gravity.LEFT)
            when (id) {
                R.id.home -> {
                    val fragment = DashboardFragment.newInstance()
                    addFragment(fragment)
                }
                R.id.product -> {
                    val fragment = ProductFragment.newInstance()
                    addFragment(fragment)
                }
                R.id.logout -> {
                    Preferences.saveToken("", this)
                    Preferences.savePassword("", this)
                    Preferences.saveUsername("", this)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        val defaultFragment = DashboardFragment.newInstance()
        addFragment(defaultFragment)
    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (t.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }
}
