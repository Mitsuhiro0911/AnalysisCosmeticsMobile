package com.example.analysiscosmeticsmobile

import org.dom4j.Document
import org.dom4j.Node

class Data{

    companion object {
        // cos類似度をDouble型からInt型にキャストした際に値が全て0に丸められるのを防ぐための正規化で用いる
        val NORM = 100000000
        // コーパスの商品数
        var productNum = 0
        // 全成分情報
        var componentList: MutableList<String> = mutableListOf()
        // 同義語統一処理をした全成分情報
        var unifiedList: MutableList<String> = mutableListOf()
        // 商品とIDF値のマップ(成分を含有する商品数の尺度で計算)
        var idfMap: LinkedHashMap<String, Double> = linkedMapOf()
        // 商品ごとの素性ベクトル
        var productMapList: MutableList<LinkedHashMap<String, Double>> = mutableListOf()
        // 全商品の名前のリスト
        var productNameList: MutableList<Node> = mutableListOf()
    }

    fun setData(cosmeProductCorpas: Document, cosmeComponentDictionary: Document){
        val cal = Calculator()
        val write = Write()

        // コーパスの商品数を計算
        productNum = cosmeProductCorpas.selectNodes("//product").count()
        // 全成分情報を抽出
        componentList = Parser().extractAllComponent(cosmeProductCorpas)
        // 抽出した全成分情報に同義語統一処理を行う
        unifiedList = PreProcessing().unitySynonym(componentList, cosmeComponentDictionary)
        // 成分を含有する商品数の尺度でIDF値を計算
        idfMap = cal.calIDF(productNum, unifiedList)
        // 商品ごとの素性ベクトルを計算
        productMapList = cal.calFeatureVector(productNum, unifiedList, idfMap, cosmeProductCorpas, cosmeComponentDictionary)
        // 全商品の名前のリストを取得
        productNameList = cosmeProductCorpas.selectNodes("//product//name")
    }
}