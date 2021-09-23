package com.omer.covidstats.view.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.omer.covidstats.R

class AboutFragment : Fragment() {

    private lateinit var webView: WebView
    private val pullRefreshVM: PullRefreshViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        webView = view.findViewById<WebView>(R.id.webView)
        createHtml()
        return view
    }

    override fun onResume() {
        super.onResume()
        Log.i("Log Resume", "Resumed")
        pullRefreshVM.isEnabled.value = false
    }

    override fun onPause() {
        super.onPause()
        Log.i("Log Pause", "Paused")
        pullRefreshVM.isEnabled.value = true
    }

    fun createHtml() {
        val html = StringBuilder()
        html.append(
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t<style>\n" +
                    "\t\tbody {\n" +
                    "\t\t\tfont-family: Arial;\n" +
                    "\t\t}\n" +
                    "\t</style>\n" +
                    "</head>\n" +
                    " <body>\n" +
                    "  <h1>COVID-19</h1>\n" +
                    "  <p>\n" +
                    "  \tCoronavirus disease (COVID-19) is an infectious disease caused by a newly discovered coronavirus.\n" +
                    "  </p>\n" +
                    "  <p>Most people infected with the COVID-19 virus will experience mild to moderate respiratory illness and recover without requiring special treatment. Older people, and those with underlying medical problems like cardiovascular disease, diabetes, chronic respiratory disease, and cancer are more likely to develop serious illness.</p>\n" +
                    "  <h2>Symptoms</h2>\n" +
                    "  <h3>Common</h3>\n" +
                    "  <ul>\n" +
                    "  \t<li>Fever</li>\n" +
                    "    <li>Dry Cough</li>\n" +
                    "    <li>Tiredness</li>\n" +
                    "  </ul>\n" +
                    "  <h3>Less Common</h3>\n" +
                    "  <ul>\n" +
                    "  \t<li>Sore Throat</li>\n" +
                    "    <li>Diarrhoea</li>\n" +
                    "    <li>Aches and Pains</li>\n" +
                    "    <li>Headache</li>\n" +
                    "  </ul>\n" +
                    "  <h2>Prevention</h2>\n" +
                    "  <ul>\n" +
                    "  \t<li>Wash your hands regularly with soap and water, or clean them with alcohol-based hand rub.</li>\n" +
                    "    <li>Maintain at least 1 metre distance between you and people coughing or sneezing.</li>\n" +
                    "    <li>Avoid touching your face. </li>\n" +
                    "    <li>Cover your mouth and nose when coughing or sneezing.</li>\n" +
                    "    <li>Stay home if you feel unwell.</li>\n" +
                    "  </ul>\n" +
                    "\n" +
                    " </body>\n" +
                    "</html>"
        )


        webView.loadDataWithBaseURL(null, html.toString(), "text/HTML", "UTF-8", null)
    }
}