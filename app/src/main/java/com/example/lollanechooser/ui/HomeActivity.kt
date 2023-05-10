package com.example.lollanechooser.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lollanechooser.R
import com.example.lollanechooser.common.Config
import com.example.lollanechooser.common.Config.mPlayerList
import com.example.lollanechooser.databinding.ActivityHomeBinding
import com.example.lollanechooser.model.Laner
import com.example.lollanechooser.model.Player
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import java.util.*

/**
 * HomeActivity
 * Created by 2023/05/10
 *
 * Description:
 */
class HomeActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private lateinit var mBinding: ActivityHomeBinding

    private lateinit var mPrefs: SharedPreferences
    private lateinit var mEdit: SharedPreferences.Editor

    private var mResultList = ArrayList<Laner>()     // 라인에 들어가는 사람 배열

    private lateinit var mPlayer1: Player
    private lateinit var mPlayer2: Player
    private lateinit var mPlayer3: Player
    private lateinit var mPlayer4: Player
    private lateinit var mPlayer5: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView<ActivityHomeBinding?>(this, R.layout.activity_home)
                .apply {
                    lifecycleOwner = this@HomeActivity
                }
        mContext = this

        mPrefs = mContext.getSharedPreferences(
            mContext.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        mEdit = mPrefs.edit()

        getPlayer()

        initSetting()
    }

    private fun initSetting() {

        mPlayerList = ArrayList<Player>().apply {
            add(mPlayer1)
            add(mPlayer2)
            add(mPlayer3)
            add(mPlayer4)
            add(mPlayer5)
        }

        with(mBinding) {
            playerList.adapter = PlayerAdapter(mPlayerList)
            playerList.layoutManager = LinearLayoutManager(mContext)
            val decoration = DividerItemDecoration(mContext, VERTICAL)
            playerList.addItemDecoration(decoration)

            btGetLane.setOnClickListener {
                startClick()
            }
        }
    }

    /**
     * 버튼 클릭시 실행
     */
    private fun startClick() {
        val tmpPlayerList = mPlayerList.clone() as ArrayList<Player>

        var playerAllNull = false   //한 명이 다 안누른사람이 있다면 거르기위한 값
        for (i in tmpPlayerList) {
            if (!i.top && !i.jgl && !i.mid && !i.bot && !i.spt) {
                playerAllNull = true
                break
            }
        }

        if (playerAllNull) {
            Toast.makeText(
                mContext,
                mContext.getString(R.string.all_null_player),
                Toast.LENGTH_LONG
            ).show()
            return
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
        if (laneAllNull) {
            Toast.makeText(
                mContext,
                mContext.getText(R.string.all_null_laner),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        // 입력이 다 있을 경우 실행
        savePlayer()
        mResultList = ArrayList()
        val tmp = Laner()
        search(tmp)

        if (mResultList.size == 0) {
            Toast.makeText(
                mContext,
                mContext.getText(R.string.can_not_fount_dfs),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val lanerRandom = Random().nextInt(mResultList.size - 1)

            val intent = Intent(mContext, ResultActivity::class.java)

            intent.putExtra("laner", mResultList[lanerRandom])
            mContext.startActivity(intent)
        }
    }

    /**
     * 모든 경우의 수 탐색
     */
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

    /**
     * 사용자 정보 가져오기
     */
    private fun getPlayer() {
        val tmpPlayer1 = mPrefs.getString(Config.PLAYER_1, "")
        val tmpPlayer2 = mPrefs.getString(Config.PLAYER_2, "")
        val tmpPlayer3 = mPrefs.getString(Config.PLAYER_3, "")
        val tmpPlayer4 = mPrefs.getString(Config.PLAYER_4, "")
        val tmpPlayer5 = mPrefs.getString(Config.PLAYER_5, "")

        mPlayer1 = if (tmpPlayer1 == null || tmpPlayer1.isEmpty()) {
            Player("Player1")
        } else {
            Gson().fromJson(tmpPlayer1, Player::class.java)
        }

        Logger.d("LeeJm : $mPlayer1")

        mPlayer2 = if (tmpPlayer2 == null || tmpPlayer2.isEmpty()) {
            Player("Player2")
        } else {
            Gson().fromJson(tmpPlayer2, Player::class.java)
        }

        mPlayer3 = if (tmpPlayer3 == null || tmpPlayer3.isEmpty()) {
            Player("Player3")
        } else {
            Gson().fromJson(tmpPlayer3, Player::class.java)
        }

        mPlayer4 = if (tmpPlayer4 == null || tmpPlayer4.isEmpty()) {
            Player("Player4")
        } else {
            Gson().fromJson(tmpPlayer4, Player::class.java)
        }

        mPlayer5 = if (tmpPlayer5 == null || tmpPlayer5.isEmpty()) {
            Player("Player5")
        } else {
            Gson().fromJson(tmpPlayer5, Player::class.java)
        }

    }

    /**
     * 사용자 정보 저장하기
     */
    private fun savePlayer() {
        var tmpNameIsNullFlag = false
        for (i in 0 until mPlayerList.size) {
            if (mPlayerList[i].name.isEmpty()) {
                mPlayerList[i].name = "Player${i + 1}"
                tmpNameIsNullFlag = true
            }
        }

        if (tmpNameIsNullFlag)
            Toast.makeText(mContext, mContext.getText(R.string.name_is_empty), Toast.LENGTH_LONG)
                .show()

        val tmpPlayer1Json = Gson().toJson(mPlayerList[0])
        val tmpPlayer2Json = Gson().toJson(mPlayerList[1])
        val tmpPlayer3Json = Gson().toJson(mPlayerList[2])
        val tmpPlayer4Json = Gson().toJson(mPlayerList[3])
        val tmpPlayer5Json = Gson().toJson(mPlayerList[4])

        mEdit.putString(Config.PLAYER_1, tmpPlayer1Json)
        mEdit.putString(Config.PLAYER_2, tmpPlayer2Json)
        mEdit.putString(Config.PLAYER_3, tmpPlayer3Json)
        mEdit.putString(Config.PLAYER_4, tmpPlayer4Json)
        mEdit.putString(Config.PLAYER_5, tmpPlayer5Json)

        mEdit.apply()
    }
}