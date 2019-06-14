package com.example.analysiscosmeticsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.util.Log


class CosRanking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cos_ranking)

        val checkedId = intent.getIntExtra("EXTRA_DATA", -1)
        Log.d("cosranking", "${checkedId}")
        for(i in 0..10){
        }
    }
}
