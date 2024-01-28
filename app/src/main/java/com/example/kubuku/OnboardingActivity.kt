package com.example.kubuku

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.kubuku.adapter.SliderAdapter
import com.example.kubuku.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: SliderAdapter
    private val sliderImageList = arrayListOf<Int>(
        R.drawable.slider_image1,
        R.drawable.slider_image2,
        R.drawable.slider_image3,
    )
    private val sliderTitleList = arrayListOf<String>(
        "Baca buku fisik memang lebih enak dan nyaman",
        "Ngga perlu mahal-mahal beli, cukup sewa aja",
        "Yuk buka jendela dunia bersama Kubuku"
    )
    //Dots Indicator
    private lateinit var dots: ArrayList<TextView>
    private var currentPage = 0
    private val slideInterval = 3000L // 3 seconds
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SliderAdapter(sliderImageList, sliderTitleList)
        with(binding) {
            // Set ViewPager Adapter and Indicator Dots
            vpSlider.adapter = adapter
            dots = ArrayList()
            setIndicator()

            //Terms and Condition Textview
            val spannableString = SpannableString(tvTermsAndCondition.text)

            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                3, 13,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                18, 22,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                48, tvTermsAndCondition.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvTermsAndCondition.text = spannableString

            // Auto slide logic
            runnable = Runnable {
                if (currentPage == sliderImageList.size) {
                    currentPage = 0
                }
                vpSlider.setCurrentItem(currentPage++, true)
                handler.postDelayed(runnable, slideInterval)
            }
            handler.postDelayed(runnable, slideInterval)

            vpSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    selectedDot(position)
                    super.onPageSelected(position)
                }
            })
        }
    }

    //FUNCTION

    //Dots Indicator
    private fun selectedDot(position: Int) {
        for (i in 0 until sliderImageList.size) {
            dots[i].setTextColor(
                ContextCompat.getColor(
                    this,
                    if (i == position) R.color.black else R.color.bg_primary
                )
            )
        }
    }

    private fun setIndicator() {
        for (i in 0 until sliderImageList.size) {
            val dot = TextView(this)
            dot.text = Html.fromHtml("&#9679;", Html.FROM_HTML_MODE_LEGACY).toString()
            dot.textSize = 20f
            dots.add(dot)
            binding.dotsIndicator.addView(dot)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}
