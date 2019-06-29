package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import org.dom4j.Node
import android.widget.TableLayout
import android.widget.TableRow
import kotlinx.android.synthetic.main.table_row.view.*


class CosRanking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cos_ranking)

        val checkedId = intent.getIntExtra("CHECKED_ID", -1)
        Log.d("cosranking", "${checkedId}")

        // 説明文の情報をセット
        val descriptionText = findViewById<View>(R.id.descriptionText) as TextView
        descriptionText.text = "${Data.productNameList.get(checkedId).text}と成分が似た商品"

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
        val tableLayout = findViewById<View>(R.id.tableLayout) as TableLayout

//        cosArray.sortDescending()
        // ソート用の変数へ代入。.mapでディープコピーをし、MutableList型へ再変換している。
        var pNameList = Data.productNameList.map{ it } as MutableList
        Log.d("instance=","${pNameList === Data.productNameList}")
        sortDescending(cosArray, pNameList)
        for(i in 0 until 10){
            val tableRow = layoutInflater.inflate(R.layout.table_row, null) as TableRow
            // 順位をセット
            tableRow.getVirtualChildAt(0).rowtext1.text = "${i + 1}位"
            tableRow.getVirtualChildAt(0).rowtext1.height = 100
            // 商品名をセット
            tableRow.getVirtualChildAt(1).rowtext2.text = "${pNameList.get(i).text}"
            tableRow.getVirtualChildAt(1).rowtext2.height = 100
            // 商品名が長い場合、改行されるのを防ぐ。コサイン類似度と高さがずれるのを防ぐために必要。
            tableRow.getVirtualChildAt(1).canScrollHorizontally(0)
            // 類似度をセット
            tableRow.getVirtualChildAt(2).rowtext3.text = "${cosArray.get(i).toString()}"
            tableRow.getVirtualChildAt(2).rowtext3.height = 100

            if((i + 1) % 2 == 0){
                tableRow.getVirtualChildAt(0).rowtext1.setBackgroundColor(1)
                tableRow.getVirtualChildAt(1).rowtext2.setBackgroundColor(1)
                tableRow.getVirtualChildAt(2).rowtext3.setBackgroundColor(1)
            }
            tableLayout.addView(tableRow)
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
