package com.himman.myapplication.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.himman.myapplication.R
import com.himman.myapplication.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class OnboardingThreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btn_next.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnboardingThreeActivity, SignInActivity::class.java))
        }
    }
}
