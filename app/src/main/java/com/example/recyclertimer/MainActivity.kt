package com.example.recyclertimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclertimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)

    val fragmentManager = supportFragmentManager

    fragmentManager.beginTransaction().replace(R.id.container, MainFragment()).commit()
  }
}