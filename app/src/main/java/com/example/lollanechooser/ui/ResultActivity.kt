package com.example.lollanechooser.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.lollanechooser.R
import com.example.lollanechooser.common.Config.mPlayerList
import com.example.lollanechooser.databinding.ActivityResultBinding
import com.example.lollanechooser.model.Laner
import com.orhanobut.logger.Logger
import java.util.*
import kotlin.collections.ArrayList


class ResultActivity : AppCompatActivity() {
    private lateinit var mContext: Context


    private lateinit var mBinding: ActivityResultBinding

    private var mResultList = ArrayList<Laner>()     // 라인에 들어가는 사람 배열
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView<ActivityResultBinding?>(this, R.layout.activity_result)
                .apply {
                    lifecycleOwner = this@ResultActivity
                }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mContext = this


        val tmp = Laner()
        search(tmp)
        Logger.d("LeeJm : size : ${mResultList.size}")

        initSetting()
    }

    private fun initSetting() {
        val lanerRandom = Random().nextInt(mResultList.size - 1)

        Logger.d("LeeJm : random $lanerRandom")
        with(mBinding) {

            topValue.text = mResultList[lanerRandom].topLaner
            jglValue.text = mResultList[lanerRandom].jglLaner
            midValue.text = mResultList[lanerRandom].midLaner
            botValue.text = mResultList[lanerRandom].botLaner
            sptValue.text = mResultList[lanerRandom].sptLaner


        }
    }

    private fun search(tmpLaner: Laner) {
        val tmp = tmpLaner.copy()

        for (i in 0 until mPlayerList.size) {
            if (tmp.topLaner.isEmpty()) {
                if (mPlayerList[i].top) {
                    val tmpTop = tmp.copy()
                    tmpTop.topLaner = mPlayerList[i].name
                    search(tmpTop)
                }
            } else {
                if (tmp.jglLaner.isEmpty()) {
                    if (tmp.topLaner == mPlayerList[i].name) {
                        continue
                    } else {
                        if (mPlayerList[i].jgl) {
                            val tmpJgl = tmp.copy()
                            tmpJgl.jglLaner = mPlayerList[i].name
                            search(tmpJgl)
                        }
                    }

                } else {
                    if (tmp.midLaner.isEmpty()) {
                        if (tmp.topLaner == mPlayerList[i].name) {
                            continue
                        } else if (tmp.jglLaner == mPlayerList[i].name) {
                            continue
                        } else {
                            if (mPlayerList[i].mid) {
                                val tmpMid = tmp.copy()
                                tmpMid.midLaner = mPlayerList[i].name
                                search(tmpMid)
                            }
                        }
                    } else {
                        if (tmp.botLaner.isEmpty()) {
                            if (tmp.topLaner == mPlayerList[i].name) {
                                continue
                            } else if (tmp.jglLaner == mPlayerList[i].name) {
                                continue
                            } else if (tmp.midLaner == mPlayerList[i].name) {
                                continue
                            } else {
                                if (mPlayerList[i].bot) {
                                    val tmpBot = tmp.copy()
                                    tmpBot.botLaner = mPlayerList[i].name
                                    search(tmpBot)
                                }
                            }
                        } else {
                            if (tmp.sptLaner.isEmpty()) {
                                if (tmp.topLaner == mPlayerList[i].name) {
                                    continue
                                } else if (tmp.jglLaner == mPlayerList[i].name) {
                                    continue
                                } else if (tmp.midLaner == mPlayerList[i].name) {
                                    continue
                                } else if (tmp.botLaner == mPlayerList[i].name) {
                                    continue
                                } else {
                                    if (mPlayerList[i].spt) {
                                        val tmpSpt = tmp.copy()
                                        tmpSpt.sptLaner = mPlayerList[i].name
                                        mResultList.add(tmpSpt)
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

    }

}