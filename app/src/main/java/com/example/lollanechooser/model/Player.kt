package com.example.lollanechooser.model

/**
 * Player
 * Created by 2023/05/10
 *
 * Description: 사용자 정보
 */
data class Player(

    var name:String,
    var top:Boolean = true,
    var jgl:Boolean = true,
    var mid:Boolean = true,
    var bot:Boolean = true,
    var spt:Boolean = true
)
