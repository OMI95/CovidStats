package com.omer.covidstats.view.dashboard

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.omer.covidstats.R
import com.omer.covidstats.utils.HelperSnippets


class SettingsFragment : Fragment() {

    private lateinit var tvTheme: TextView
    private lateinit var tvCurrentTheme: TextView
    var selectedItem = 0
    val styles = arrayOf("Light", "Dark", "Follow system", "Auto battery")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getThemeMode()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        tvTheme = view.findViewById(R.id.settingTheme)
        tvTheme.setOnClickListener { chooseTheme() }
        tvCurrentTheme = view.findViewById(R.id.currentTheme)
        return  view
    }

    /**
     * The following function
     */
    private fun chooseTheme() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.choose_theme_title))
        val checkedItem = selectedItem

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, selectedOption ->

            when (selectedOption) {
                0 -> {
                    updateThemeState(selectedOption)
                    HelperSnippets.getInstance().setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    dialog.dismiss()
                }
                1 -> {
                    updateThemeState(selectedOption)
                    HelperSnippets.getInstance().setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    dialog.dismiss()
                }
                2 -> {

                    updateThemeState(selectedOption)
                    HelperSnippets.getInstance().setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    dialog.dismiss()
                }
                3 -> {
                    updateThemeState(selectedOption)
                    HelperSnippets.getInstance().setTheme(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    dialog.dismiss()
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateThemeState(mode: Int) {
        selectedItem = mode

        val pref = this.activity?.getSharedPreferences("ThemeMode", MODE_PRIVATE) ?: return
        with(pref.edit()) {
            putInt("mode", mode)
            apply()
        }
    }

    private fun getThemeMode() {
        val pref = this.activity?.getSharedPreferences("ThemeMode", MODE_PRIVATE)
        if (pref != null) {
            selectedItem = pref.getInt("mode",2)
        }
    }

    private fun setThemeTitle() {
        tvCurrentTheme.text = styles[selectedItem]
    }
}