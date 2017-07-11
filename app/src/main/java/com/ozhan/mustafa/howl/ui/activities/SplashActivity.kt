package com.ozhan.mustafa.howl.ui.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.ozhan.mustafa.howl.R

class SplashActivity : AppCompatActivity() {
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        mHandler = Handler()

        mRunnable = Runnable {
            // check if user is already logged in or not
            if (FirebaseAuth.getInstance().currentUser != null) {
                // if logged in redirect the user to user listing activity
                MainActivity.startActivity(this@SplashActivity)
            } else {
                // otherwise redirect the user to login activity
                LoginActivity.startIntent(this@SplashActivity)
            }
            finish()
        }

        mHandler!!.postDelayed(mRunnable, SPLASH_TIME_MS.toLong())
    }

    companion object {
        private val SPLASH_TIME_MS = 1000
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, SPLASH_TIME_MS);
    }*/
}
