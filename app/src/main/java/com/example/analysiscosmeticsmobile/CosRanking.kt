package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.dom4j.Node

class CosRanking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cos_ranking)

        val checkedId = intent.getIntExtra("CHECKED_ID", -1)
        Log.d("cosranking", "${checkedId}")
        val cal = Calculator()
        val cosArray = mutableListOf<Int>()
        val vector1 = Data.productMapList.get(checkedId).values.toDoubleArray()
        for(i in 0 until Data.productMapList.size){
            if(i == checkedId){
                cosArray.add(0)
            } else {
                val vector2 = Data.productMapList.get(i).values.toDoubleArray()
                cosArray.add((cal.calCosSimilarity(vector1, vector2) * Data.NORM).toInt())
            }
        }
        var cosLinerLayout = findViewById<View>(R.id.cosList) as LinearLayout
        var productLinerLayout = findViewById<View>(R.id.productNameList) as LinearLayout
//        cosArray.sortDescending()
        // ソート用の変数へ代入。.mapでディープコピーをし、MutableList型へ再変換している。
        var pNameList = Data.productNameList.map{ it } as MutableList
        Log.d("instance=","${pNameList === Data.productNameList}")
        sortDescending(cosArray, pNameList)
        for(i in 0 until 10){
            // コサイン類似度を動的に生成したTextViewに入れる
            val cosTextView = TextView(this)
            // TextViewにコサイン類似度を入れる
            cosTextView.text = "${cosArray.get(i).toString()}"
            // TextViewのidを動的に生成
            cosTextView.id = i
            cosLinerLayout.addView(cosTextView)

            // 商品名を動的に生成したTextViewに入れる
            val productTextView = TextView(this)
            // TextViewにコサイン類似度を入れる
            productTextView.text = "${i + 1}位：${pNameList.get(i).text}"
            // TextViewのidを動的に生成
            productTextView.id = i
            // 商品名が長い場合、改行されるのを防ぐ。コサイン類似度と高さがずれるのを防ぐために必要。
            productTextView.canScrollHorizontally(0)
            productLinerLayout.addView(productTextView)
        }
    }
    fun sortDescending(n: MutableList<Int>, pNameList: MutableList<Node>) {
        var cosTmp: Int
        var minIndex: Int
        var nameTmp: Node
        for(i in n.indices.filter({ it < n.size - 1 })) {
            minIndex = i
            n.indices.filter({ it >= i + 1 }).forEach({ j -> if(n[j] > n[minIndex]) minIndex = j })
            if(minIndex != i) {
                // コサイン類似度のソート
                cosTmp = n[i]
                n[i] = n[minIndex]
                n[minIndex] = cosTmp

                // コサイン類似度の尺度で商品名をソート
                nameTmp = pNameList[i]
                pNameList[i] = pNameList[minIndex]
                pNameList[minIndex] = nameTmp
            }
        }
    }
}
