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
        Log.d("size", componentList.size.toString())
        Log.d("size", unifiedList.size.toString())
//        for(component in componentList){
//            Log.d("component", component.toString())
//        }
//        for(i in 0..unifiedList.size - 1){
//            Log.d("unified", unifiedList.get(i).toString())
//        }
        Log.d("unified", unifiedList.get(124).toString())
        Log.d("unified", unifiedList.get(125).toString())
        Log.d("unified", unifiedList.get(126).toString())

        val testList: MutableList<String> = mutableListOf()
        testList.add("Android")
        testList.add("Android")
        testList.add("Android")
        testList.add("Android")
        testList.add("Android")
        for(test in testList){
            Log.d("debug", test.toString())
        }
    }
}
