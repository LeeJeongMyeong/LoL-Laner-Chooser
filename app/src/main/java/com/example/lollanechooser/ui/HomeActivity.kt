package com.example.lollanechooser.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lollanechooser.R
import com.example.lollanechooser.common.Config.mPlayerList
import com.example.lollanechooser.databinding.ActivityHomeBinding
import com.example.lollanechooser.model.Laner
import com.example.lollanechooser.model.Player
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private lateinit var mBinding: ActivityHomeBinding

    private var mResultList = ArrayList<Laner>()     // 라인에 들어가는 사람 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView<ActivityHomeBinding?>(this, R.layout.activity_home)
                .apply {
                    lifecycleOwner = this@HomeActivity
                }

        mContext = this

        initSetting()
    }

    private fun initSetting() {

        mPlayerList = ArrayList()

        mPlayerList.add(Player("김상일"))
        mPlayerList.add(Player("이동연"))
        mPlayerList.add(Player("이승열"))
        mPlayerList.add(Player("이정명"))
        mPlayerList.add(Player("이현재"))

        with(mBinding) {
            playerList.adapter = PlayerAdapter(mPlayerList)
            playerList.layoutManager = LinearLayoutManager(mContext)
            val decoration = DividerItemDecoration(mContext, VERTICAL)
            playerList.addItemDecoration(decoration)


            val tmpPlayerList = mPlayerList.clone() as ArrayList<Player>

            btGetLine.setOnClickListener {

                var playerAllNull = false   //한 명이 다 안누른사람이 있다면 거르기위한 값
                for (i in tmpPlayerList) {
                    if (!i.top && !i.jgl && !i.mid && !i.bot && !i.spt) {
                        playerAllNull = true
                        break
                    }
                }

                val tmpPlayer = Player("", false, false, false, false, false)
                var laneAllNull = true     // 한 라인이 다 안눌렸다면 거르기위한 값
                for (i in tmpPlayerList) {
                    if (i.top) tmpPlayer.top = true
                    if (i.jgl) tmpPlayer.jgl = true
                    if (i.mid) tmpPlayer.mid = true
                    if (i.bot) tmpPlayer.bot = true
                    if (i.spt) tmpPlayer.spt = true

                    if (tmpPlayer.top && tmpPlayer.jgl && tmpPlayer.mid && tmpPlayer.bot && tmpPlayer.spt) {
                        laneAllNull = false
                        break
                    }
                }

                if (playerAllNull) {
                    Toast.makeText(mContext, "그래도 하나는 누르셔야죠....", Toast.LENGTH_LONG).show()
                } else if (laneAllNull) {
                    Toast.makeText(mContext, "그래도 라인은 가셔야죠....", Toast.LENGTH_LONG).show()
                } else {

                    mResultList = ArrayList()
                    val tmp = Laner()
                    search(tmp)

                    if (mResultList.size == 0) {
                        Toast.makeText(mContext, "가능한 경우의수가 없습니다.", Toast.LENGTH_LONG).show()
                    } else {
                        val lanerRandom = Random().nextInt(mResultList.size - 1)

                        val intent = Intent(mContext, ResultActivity::class.java)

                        intent.putExtra("laner", mResultList[lanerRandom])
                        mContext.startActivity(intent)
                    }
                }
            }
        }
    }

    private fun search(tmpLaner: Laner) {
        val tmp = tmpLaner.copy()

        for (i in 0 until mPlayerList.size) {
            if (tmp.topLaner!!.isEmpty()) {
                if (mPlayerList[i].top) {
                    val tmpTop = tmp.copy()
                    tmpTop.topLaner = mPlayerList[i].name
                    search(tmpTop)
                }
            } else {
                if (tmp.jglLaner!!.isEmpty()) {
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
                    if (tmp.midLaner!!.isEmpty()) {
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
                        if (tmp.botLaner!!.isEmpty()) {
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
                            if (tmp.sptLaner!!.isEmpty()) {
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