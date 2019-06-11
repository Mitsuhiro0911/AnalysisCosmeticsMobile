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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // オブジェクト生成
        val reader = SAXReader()
        val cosmeProductCorpas = reader.read(assets.open("cosme_product.xml"))
        val cosmeComponentDictionary = reader.read(assets.open("cosme_component_dictionary.xml"))
        val cal = Calculator()
        val write = Write()

        // コーパスの商品数を計算
        val productNum = cosmeProductCorpas.selectNodes("//product").count()
        // 全成分情報を抽出
        val componentList: MutableList<String> = Parser().extractAllComponent(cosmeProductCorpas)
        // 抽出した全成分情報に同義語統一処理を行う
        val unifiedList: MutableList<String> = PreProcessing().unitySynonym(componentList, cosmeComponentDictionary)
        // 成分を含有する商品数の尺度でIDF値を計算
        val idfMap: LinkedHashMap<String, Double> = cal.calIDF(productNum, unifiedList)
        // 商品ごとの素性ベクトルを計算
        val productMapList = cal.calFeatureVector(productNum, unifiedList, idfMap, cosmeProductCorpas, cosmeComponentDictionary)
        // 二次元配列
        val cosArray = Array(productMapList.size, { arrayOfNulls<Int>(productMapList.size) })
        // 商品の全組合せのコサイン類似度を計算
        for (i in 0 until productMapList.size) {
            val vector1 = productMapList.get(i).values.toDoubleArray()
            for (j in 0 until productMapList.size) {
                val vector2 = productMapList.get(j).values.toDoubleArray()
                if (i == j) {
                    cosArray[i][j] = 0
                    break
                }
                if (cosArray[i][j] == null) {
                    cosArray[i][j] = (cal.calCosSimilarity(vector1, vector2) * Constants.NORM).toInt()
                    cosArray[j][i] = cosArray[i][j]
                }
            }
        }
        val productNameList: List<Node> = cosmeProductCorpas.selectNodes("//product//name")
        var radioGroup = findViewById<View>(R.id.radioGroup) as RadioGroup
        for(i in 0 until productNameList.size){
            val radio = RadioButton(this)
            radio.text = productNameList.get(i).text
            radioGroup.addView(radio)
        }

        val executeButton = findViewById<View>(R.id.executeButton)
        executeButton.setOnClickListener {
            Log.d("radio", "タップ成功")
        }

    }
}
