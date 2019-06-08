package com.example.analysiscosmeticsmobile

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.analysiscosmeticsmobile.Constants.Companion.reader
import java.io.InputStream
import android.content.res.XmlResourceParser
import android.R.xml
import org.dom4j.io.SAXReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cal = Calculator()
        val write = Write()
        // コーパスの商品数を計算

        val reader = SAXReader()
        val xml: InputStream = assets.open("cosme_product.xml")
        val document = reader.read(xml)
        Log.d("MainActivity", document.selectNodes("//product").count().toString())

//        val assetManager: AssetManager = getResources().getAssets()
//        var xml = assetManager.openXmlResourceParser("cosme_product.xml");
//        val productNum = cal.calProductNum()
//        Log.d("MainActivity",productNum.toString())
    }
}
