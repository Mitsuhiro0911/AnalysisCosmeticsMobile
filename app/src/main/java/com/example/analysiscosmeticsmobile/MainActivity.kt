package com.example.analysiscosmeticsmobile

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.analysiscosmeticsmobile.ui.main.SectionsPagerAdapter
import org.dom4j.io.SAXReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val reader = SAXReader()
        val cosmeProductCorpas = reader.read(assets.open("cosme_product.xml"))
        val cosmeComponentDictionary = reader.read(assets.open("cosme_component_dictionary.xml"))
        Data().setData(cosmeProductCorpas, cosmeComponentDictionary)
    }
}