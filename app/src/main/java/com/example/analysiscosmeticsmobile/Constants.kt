package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import org.dom4j.io.SAXReader

class Constants{
    companion object {
        // 何回前の実行のログまで残すかを決める
        val LOG_NUM = 5
        // cos類似度をDouble型からInt型にキャストした際に値が全て0に丸められるのを防ぐための正規化で用いる
        val NORM = 100000000
        val reader = SAXReader()
    }
}