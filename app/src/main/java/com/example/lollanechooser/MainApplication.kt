package com.example.lollanechooser

import android.app.Application
import com.example.lollanechooser.common.Config

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * MainApplication
 * Created by 2023/05/09
 *
 * Description:
 */
class MainApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        initLogger()
    }


    /**
     * 로그 정보 초기화
     */
    private fun initLogger() {
        val formatStrategy =
            PrettyFormatStrategy.newBuilder()
                .methodCount(Config.LOGGER_STACK_COUNT).build()

        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })
    }
}