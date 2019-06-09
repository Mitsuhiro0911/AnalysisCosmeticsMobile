package com.example.analysiscosmeticsmobile

import android.util.Log
import org.dom4j.Document
import org.dom4j.Node

class PreProcessing{
    /**
     * 同義語辞書を用いて、表記揺れを解消する
     *
     * @param [componentList] 同義語の統一処理が行われる前のリスト
     * @return [unifiedList] 同義語の統一処理が行われたリスト
     */
    fun unitySynonym(componentList: MutableList<String>, cosmeComponentDictionary: Document): MutableList<String> {
        val unifiedList: MutableList<String> = mutableListOf()
        for (i in 0..componentList.size - 1) {
            val test: List<Node> =
                cosmeComponentDictionary.selectNodes("//component[text()='" + componentList.get(i).replace("（ＳＥ）", "(SE)") + "']/ancestor-or-self::*/representation//component")
            unifiedList.add(test.get(0).text)
            Log.d("test", test.get(0).text)
        }
        return unifiedList
    }
}