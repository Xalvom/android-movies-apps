package com.himman.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.time.Instant

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername : String
    lateinit var sPassword : String
    lateinit var sNama : String
    lateinit var sEmail : String

    private lateinit var mDatabasePreference : DatabaseReference
    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabasePreference = mFirebaseInstance.getReference("User")

        btn_sign_up.setOnClickListener {
            sUsername = edtUsername.text.toString()
            sPassword = edtPassword.text.toString()
            sNama = edtNama.text.toString()
            sEmail = edtEmail.text.toString()

            if(sUsername.isEmpty()){
                edtUsername.setError("Silahkan Masukan Username")
                edtUsername.requestFocus()
            }else if(sPassword.isEmpty()){
                edtPassword.setError("Silahkan Masukan Password")
                edtPassword.requestFocus()
            }else if(sNama.isEmpty()){
                edtNama.setError("Silahkan Masukan Nama")
                edtNama.requestFocus()
            }else if(sEmail.isEmpty()){
                edtEmail.setError("Silahkan Masukan Email Address")
                edtEmail.requestFocus()
            }else{
                saveSignUP(sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveSignUP(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        var user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword

        if(sUsername != null){
            checkingDouble(sUsername, user)
        }
    }

    private fun checkingDouble(sUsername: String, data: User) {
        mDatabasePreference.child(sUsername).addValueEventListener( object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if(user == null){
                    mDatabasePreference.child(sUsername).setValue(data)

                    var goPhotoScreen = Intent(this@SignUpActivity, PhotoScreenActivity::class.java)
                        .putExtra("nama", data?.nama)
                    startActivity(goPhotoScreen)
                }else{
                    Toast.makeText(this@SignUpActivity, "Username sudah di pakai", Toast.LENGTH_LONG).show()
                }
            }

        })
    }
}
