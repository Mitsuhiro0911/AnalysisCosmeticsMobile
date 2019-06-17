package com.example.analysiscosmeticsmobile.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.analysiscosmeticsmobile.CosRanking
import com.example.analysiscosmeticsmobile.Data
import com.example.analysiscosmeticsmobile.R

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments?.getInt(ARG_SECTION_NUMBER) == 2) {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })
        // TODO:タブごとの画面XMLを作り、ARG_SECTION_NUMBERで表示先を分岐させる
        // 2つ目のタブの画面生成時
            var radioGroup = root.findViewById<View>(R.id.radioGroup) as RadioGroup
            // ラジオボタンを商品数分だけ動的に生成
            for (i in 0 until Data.productNameList.size) {
                val radio = RadioButton(context)
                // ラジオボタンのテキストに商品名を入れる
                radio.text = Data.productNameList.get(i).text
                // ラジオボタンのidを動的に生成
                radio.id = i
                radioGroup.addView(radio)
            }

            val executeButton = root.findViewById<View>(R.id.executeButton)
            executeButton.setOnClickListener {
                // 選択されたラジオボタンのidを取得
                val checkedId = radioGroup.getCheckedRadioButtonId()
                Log.d("checked", "${checkedId}")
                val intent = Intent(context, CosRanking::class.java)
                intent.putExtra("CHECKED_ID", checkedId);
                startActivity(intent)
            }
        return root
        }
        return null
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}