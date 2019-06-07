package com.example.analysiscosmeticsmobile

import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.analysiscosmeticsmobile.Constants.Companion.reader
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cal = Calculator()
        val write = Write()
        // コーパスの商品数を計算

//        val assetManager: AssetManager = getResources().getAssets()
//        var xml = assetManager.openXmlResourceParser("cosme_product.xml");
//        val productNum = cal.calProductNum()
//        Log.d("MainActivity",productNum.toString())
    }
}
