package com.example.testcrud1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.testcrud1.R
import com.example.testcrud1.data.AppDatabase
import com.example.testcrud1.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullname: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnSave: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        //Inisialisasi
        fullname = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.nophone)
        btnSave = findViewById(R.id.btnSave)

        database = AppDatabase.getInstance(applicationContext)
        var intent = intent.extras
        if (intent!= null){
            var user = database.userDao().get(intent.getInt("id"))

            fullname.setText(user.fullname)
            email.setText(user.email)
            phone.setText(user.phone)
        }
        btnSave.setOnClickListener {
            if (fullname.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {

                if (intent != null){
                    //coding edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id", 0),
                            fullname.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                } else {
                    database.userDao().insertAll(
                        User(
                            null,
                            fullname.text.toString(),
                            email.text.toString(),
                            phone.text.toString()
                        )
                    )
                }

                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Isi Semua Data dengan benar !!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}