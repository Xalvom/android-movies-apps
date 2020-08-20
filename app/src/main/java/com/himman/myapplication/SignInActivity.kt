package com.himman.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.himman.myapplication.home.HomeActivity
import com.himman.myapplication.utils.Preference
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String
    
    lateinit var mDatabase : DatabaseReference
    lateinit var prefernce : Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        prefernce = Preference(this)

        prefernce.setValues("onboarding", "1")
        if(prefernce.getValues("status").equals("1")){
            finishAffinity()

            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }
        btn_sign_in.setOnClickListener { 
            iUsername = edtUsername.text.toString()
            iPassword = edtPassword.text.toString()
            
            if(iUsername.isEmpty()){
                edtUsername.setError("Silahkan tulis Username Anda")
                edtUsername.requestFocus()
            }else if(iPassword.isEmpty()){
                edtPassword.setError("Silahkan tulis Password Anda")
                edtPassword.requestFocus()
            }else{
                pushLogin(iUsername, iPassword)
            }
        }

        btn_sign_up.setOnClickListener {
            var signUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(signUp)
        }
        
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener( object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity, "Username tidak ditemukan", Toast.LENGTH_LONG).show()
                }else{
                    if(user.password.equals(iPassword)){
                        finishAffinity()
                        prefernce.setValues("nama", user.nama.toString())
                        prefernce.setValues("user", user.username.toString())
                        prefernce.setValues("url", user.url.toString())
                        prefernce.setValues("email", user.email.toString())
                        prefernce.setValues("saldo", user.saldo.toString())
                        prefernce.setValues("status", "1")
                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@SignInActivity, "Password tidak sesuai", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
