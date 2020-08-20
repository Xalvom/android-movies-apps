package com.himman.myapplication.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.himman.myapplication.R
import com.himman.myapplication.SignInActivity
import com.himman.myapplication.utils.Preference
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference: Preference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference= Preference(this)
        if(preference.getValues("onboarding").equals("1")){
            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent);
        }
        btn_next.setOnClickListener {
            var intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        btn_skip.setOnClickListener {
            finishAffinity()
            preference.setValues("onboarding", "1")
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent);
        }
    }
}
