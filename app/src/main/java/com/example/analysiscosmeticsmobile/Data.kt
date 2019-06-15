package com.example.analysiscosmeticsmobile

import org.dom4j.Document

class Data{

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

    fun setData(cosmeProductCorpas: Document, cosmeComponentDictionary: Document){
        val cal = Calculator()
        val write = Write()

        // コーパスの商品数を計算
        this.productNum = cosmeProductCorpas.selectNodes("//product").count()
        // 全成分情報を抽出
        this.componentList = Parser().extractAllComponent(cosmeProductCorpas)
        // 抽出した全成分情報に同義語統一処理を行う
        this.unifiedList = PreProcessing().unitySynonym(componentList, cosmeComponentDictionary)
        // 成分を含有する商品数の尺度でIDF値を計算
        this.idfMap = cal.calIDF(productNum, unifiedList)
        // 商品ごとの素性ベクトルを計算
        this.productMapList = cal.calFeatureVector(productNum, unifiedList, idfMap, cosmeProductCorpas, cosmeComponentDictionary)
    }
}