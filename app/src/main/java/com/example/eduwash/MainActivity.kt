package com.example.eduwash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import com.example.eduwash.config.AppPrefs
import com.example.eduwash.view.OnBoardingActivity
import com.example.eduwash.view.PracticeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppPrefs(this).isFirstTimeLaunch()) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_main)

        buttonPractice.setOnClickListener {
            startActivity(Intent(this, PracticeActivity::class.java))
            finish()
        }

        val imageButton = findViewById<ImageButton>(R.id.imgbutton)
        imageButton?.setOnClickListener{
            startActivity(Intent(this, LatihanActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.about_developer -> {
                //val moveIntent = Intent(this@MainActivity, AboutMe::class.java)
                //startActivity(moveIntent)
            }

            R.id.change_language -> {

            }
        }
    }
}