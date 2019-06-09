package com.example.analysiscosmeticsmobile


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // 動作テスト用
        Log.d("size", idfMap.size.toString())
        for(idf in idfMap){
            Log.d("idf", idf.toString())
        }

    }
}
