package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import net.sf.javaml.tools.InstanceTools.array


class CosRanking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cos_ranking)

        val checkedId = intent.getIntExtra("EXTRA_DATA", -1)
        Log.d("cosranking", "${checkedId}")
        val cal = Calculator()
        val cosArray = mutableListOf<Int>()
        val vector1 = Data.productMapList.get(checkedId).values.toDoubleArray()
        for(i in 0..10){
            if(i == checkedId){
                cosArray.add(0)
            } else {
                val vector2 = Data.productMapList.get(i).values.toDoubleArray()
                cosArray.add((cal.calCosSimilarity(vector1, vector2) * Data.NORM).toInt())
            }
        }
        var linerLayout = findViewById<View>(R.id.linerLayout) as LinearLayout
        for(i in 0 until cosArray.size){
            val textView = TextView(this)
            // ラジオボタンのテキストに商品名を入れる
            textView.text = cosArray.get(i).toString()
            // ラジオボタンのidを動的に生成
            textView.id = i
            linerLayout.addView(textView)
        }
    }
}
