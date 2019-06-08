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

    }
}
