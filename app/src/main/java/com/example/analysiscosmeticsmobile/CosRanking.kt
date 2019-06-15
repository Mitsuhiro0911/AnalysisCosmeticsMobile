package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.util.Log
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
        for(cos in cosArray){
            Log.d("cosarray", "${cos}")
        }
    }
}
