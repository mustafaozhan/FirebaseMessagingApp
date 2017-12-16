package com.ozhan.mustafa.howl.ui.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import com.ozhan.mustafa.howl.R
import com.ozhan.mustafa.howl.ui.fragments.RegisterFragment

class RegisterActivity : AppCompatActivity() {
    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bindViews()
        init()
    }

    private fun bindViews() {
        mToolbar = findViewById(R.id.toolbar) as Toolbar
    }

    private fun init() {
        // set the toolbar
        setSupportActionBar(mToolbar)

        // set the register screen fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_content_register,
                RegisterFragment.newInstance(),
                RegisterFragment::class.java.simpleName)
        fragmentTransaction.commit()
    }

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}
