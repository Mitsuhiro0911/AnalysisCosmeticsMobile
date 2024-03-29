package com.example.analysiscosmeticsmobile


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*
import org.dom4j.Node
import org.dom4j.io.SAXReader
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // オブジェクト生成
        val reader = SAXReader()
        val cosmeProductCorpas = reader.read(assets.open("cosme_product.xml"))
        val cosmeComponentDictionary = reader.read(assets.open("cosme_component_dictionary.xml"))
        Data().setData(cosmeProductCorpas, cosmeComponentDictionary)

//        // 二次元配列
//        val cosArray = Array(productMapList.size, { arrayOfNulls<Int>(productMapList.size) })
//        // 商品の全組合せのコサイン類似度を計算
//        for (i in 0 until productMapList.size) {
//            val vector1 = productMapList.get(i).values.toDoubleArray()
//            for (j in 0 until productMapList.size) {
//                val vector2 = productMapList.get(j).values.toDoubleArray()
//                if (i == j) {
//                    cosArray[i][j] = 0
//                    break
//                }
//                if (cosArray[i][j] == null) {
//                    cosArray[i][j] = (cal.calCosSimilarity(vector1, vector2) * Constants.NORM).toInt()
//                    cosArray[j][i] = cosArray[i][j]
//                }
//            }
//        }
        var radioGroup = findViewById<View>(R.id.radioGroup) as RadioGroup
        // ラジオボタンを商品数分だけ動的に生成
        for(i in 0 until Data.productNameList.size){
            val radio = RadioButton(this)
            // ラジオボタンのテキストに商品名を入れる
            radio.text = Data.productNameList.get(i).text
            // ラジオボタンのidを動的に生成
            radio.id = i
            radioGroup.addView(radio)
        }

        val executeButton = findViewById<View>(R.id.executeButton)
        executeButton.setOnClickListener {
            // 選択されたラジオボタンのidを取得
            val checkedId = radioGroup.getCheckedRadioButtonId()
            Log.d("checked", "${checkedId}")
            val intent = Intent(this, CosRanking::class.java)
            intent.putExtra("CHECKED_ID", checkedId);
            startActivity(intent)
        }
    }
}
