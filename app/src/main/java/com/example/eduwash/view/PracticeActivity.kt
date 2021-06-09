package com.example.eduwash.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.eduwash.MainActivity
import com.example.eduwash.R
import com.example.eduwash.config.AppPrefs
import com.example.eduwash.databinding.ActivityPracticeBinding
import com.example.eduwash.hide
import com.example.eduwash.show
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.practice_slide1.*
import kotlinx.android.synthetic.main.practice_slide2.*
import kotlinx.android.synthetic.main.practice_slide3.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File

class PracticeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPracticeBinding

    private lateinit var sliderAdapter: SliderAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private val sliderChangeListener = object: ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            if (position == layouts.size.minus(1)) {
                binding.nextBtn.hide()
                binding.prevBtn.hide()
                binding.homeBtn.show()
            } else {
                binding.nextBtn.show()
                binding.prevBtn.show()
                binding.homeBtn.hide()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            //do nothing
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_practice)


        retrieveGifAsset()
        init()
        dataSet()
        interaction()
    }

    private fun retrieveGifAsset() {

        val gifImage = FirebaseStorage.getInstance().reference.child("gif/step1.gif")
        val gifImage2 = FirebaseStorage.getInstance().reference.child("gif/step2_left.gif")
        val gifImage2Right = FirebaseStorage.getInstance().reference.child("gif/step2_right.gif")
        val localFile = File.createTempFile("tempGif", "gif")
        val localFile2 = File.createTempFile("tempGif2", "gif")
        val localFile2Right = File.createTempFile("tempGif2Right", "gif")

        gifImage.getFile(localFile).addOnSuccessListener {
            val gifFromPath = GifDrawable(localFile.absolutePath)
            img.setImageDrawable(gifFromPath)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve the image", Toast.LENGTH_SHORT).show()
        }

        gifImage2.getFile(localFile2).addOnSuccessListener {
            val gif2FromPath = GifDrawable(localFile2.absolutePath)
            img2.setImageDrawable(gif2FromPath)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve the image", Toast.LENGTH_SHORT).show()
        }

        /*
        gifImage2Right.getFile(localFile2Right).addOnSuccessListener {
            val gif2RightFromPath = GifDrawable(localFile2Right.absolutePath)
            img2Right.setImageDrawable(gif2RightFromPath)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve the image", Toast.LENGTH_SHORT).show()
        }

         */
    }

    private fun init() {
        layouts = arrayOf(
            R.layout.practice_slide1,
            R.layout.practice_slide2,
            R.layout.practice_slide3,
                R.layout.practice_slide4,
                R.layout.practice_slide5,
                R.layout.practice_slide6,
                R.layout.practice_slide7,
                R.layout.practice_slide8,
                R.layout.practice_slide9,
                R.layout.practice_slide10,
                R.layout.practice_slide11,
                R.layout.practice_slide12
        )
        sliderAdapter = SliderAdapter(this, layouts)
    }

    private fun dataSet() {
        addBottomDots(0)
        binding.sliderPractice.apply {
            adapter = sliderAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)

        binding.dotsPracticeLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.colorGrey))
            binding.dotsPracticeLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.colorIndigo))
        }
    }

    private fun interaction() {
        binding.prevBtn.setOnClickListener {
            val current = getCurrentScreen(-1)
            if (current < layouts.size) {
                binding.sliderPractice.currentItem = current
            } else {
                navigateToHome()
            }
        }
        binding.homeBtn.setOnClickListener {
            // Launch login screen
            navigateToHome()
        }
        binding.nextBtn.setOnClickListener {
            val current = getCurrentScreen(+1)
            if (current < layouts.size) {
                binding.sliderPractice.currentItem = current
            } else {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        AppPrefs(this).setFirstTimeLaunch(false)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun getCurrentScreen(i: Int): Int = binding.sliderPractice.currentItem.plus(i)
}