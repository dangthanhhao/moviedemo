package com.example.moviedemo.screen.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviedemo.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val rad1=radioButton1
        val rad2=radioButton2

        rad1.setOnClickListener{
            if (rad2.isChecked){
                rad2.isChecked=false
            }
            rad1.isChecked=true
        }

        rad2.setOnClickListener {
            if (rad1.isChecked){
                rad1.isChecked=false
            }
            rad2.isChecked=true
        }


    }


}