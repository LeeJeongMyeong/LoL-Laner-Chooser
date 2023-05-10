package com.example.lollanechooser.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.lollanechooser.R
import com.example.lollanechooser.databinding.ActivityResultBinding
import com.example.lollanechooser.model.Laner
import java.util.*


/**
 * ResultActivity
 * Created by 2023/05/10
 *
 * Description: 결과화면
 */
class ResultActivity : AppCompatActivity() {
    private lateinit var mContext: Context


    private lateinit var mBinding: ActivityResultBinding

    private lateinit var mLaner: Laner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView<ActivityResultBinding?>(this, R.layout.activity_result)
                .apply {
                    lifecycleOwner = this@ResultActivity
                }

        mContext = this

        mLaner = intent.getParcelableExtra("laner")!!

        initSetting()
    }

    private fun initSetting() {

        with(mBinding) {

            topValue.text = mLaner.topLaner
            jglValue.text = mLaner.jglLaner
            midValue.text = mLaner.midLaner
            botValue.text = mLaner.botLaner
            sptValue.text = mLaner.sptLaner


        }
    }
}