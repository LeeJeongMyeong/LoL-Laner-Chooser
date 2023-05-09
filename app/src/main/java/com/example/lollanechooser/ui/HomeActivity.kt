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
import com.example.lollanechooser.model.Player
import com.orhanobut.logger.Logger

class HomeActivity : AppCompatActivity() {
    private lateinit var mContext: Context

    private lateinit var mBinding: ActivityHomeBinding
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


        mPlayerList.add(Player("김상일"))
        mPlayerList.add(Player("이동연"))
        mPlayerList.add(Player("이승열"))
        mPlayerList.add(Player("이정명"))
        mPlayerList.add(Player("Unknown"))



        with(mBinding) {
            playerList.adapter = PlayerAdapter(mPlayerList)
            playerList.layoutManager = LinearLayoutManager(mContext)
            val decoration = DividerItemDecoration(mContext, VERTICAL)
            playerList.addItemDecoration(decoration)


            val tmpPlayerList = mPlayerList.clone() as ArrayList<Player>

            btGetLine.setOnClickListener {

                Logger.d("LeeJm : $mPlayerList")

                var allNull = false
                for (i in tmpPlayerList) {
                    if (!i.top && !i.jgl && !i.mid && !i.bot && !i.spt) {
                        allNull = true
                        break
                    }
                }

                if (!allNull) {
                    val intent = Intent(mContext, ResultActivity::class.java)
                    mContext.startActivity(intent)

                } else {
                    Toast.makeText(mContext, "그래도 하나는 누르셔야죠....", Toast.LENGTH_LONG).show()
                }


            }
        }
    }

}