package com.example.analysiscosmeticsmobile

import org.dom4j.Document
import org.dom4j.Node
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class Write {

    /**
     * 各商品同士のコサイン類似度をMDS計算用のCSVファイルに出力する
     *
     * @param [cosArray] 各商品同士のコサイン類似度
     */
    fun writeCosineSimilarity(cosArray: Array<Array<Int?>>, cosmeProductCorpas: Document): Unit{
        val productNameList: List<Node> = cosmeProductCorpas.selectNodes("//product//name")
        val fw = FileWriter("cosine_similarity.csv")
        val pw = PrintWriter(BufferedWriter(fw))
        for (i in 0 until cosArray.size) {
            pw.print("${productNameList.get(i).text}")
            for (j in 0 until cosArray.size) {
                pw.print(",${cosArray[i][j]}")
            }
            pw.println()
        }
        pw.close()
    }
}