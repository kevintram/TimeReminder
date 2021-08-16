package com.kiwicorp.timereminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch: SwitchMaterial = findViewById(R.id._switch)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Log.d("MainActivity", "YOOOOOOOO")
            }
        }
    }
}