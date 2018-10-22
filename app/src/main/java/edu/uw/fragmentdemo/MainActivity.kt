package edu.uw.fragmentdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

class MainActivity : AppCompatActivity() {

    private var adapter: ArrayAdapter<Movie>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //respond to search button clicking
    fun handleSearchClick(v: View) {
        val text = findViewById<View>(R.id.txt_search) as EditText
        val searchTerm = text.text.toString()

        downloadMovieData(searchTerm)
    }

    //download media information from iTunes
    private fun downloadMovieData(searchTerm: String) {

        var urlString = ""
        try {
            urlString = "https://itunes.apple.com/search?term=" + URLEncoder.encode(searchTerm, "UTF-8") + "&media=movie&entity=movie&limit=25"
            //Log.v(TAG, urlString);
        } catch (uee: UnsupportedEncodingException) {
            Log.e(TAG, uee.toString())
            return
        }

        val request = JsonObjectRequest(Request.Method.GET, urlString, null,
                Response.Listener { response ->
                    val movies = ArrayList<Movie>()

                    try {
                        //parse the JSON results
                        val results = response.getJSONArray("results") //get array from "search" key
                        for (i in 0 until results.length()) {
                            val track = results.getJSONObject(i)
                            if (track.getString("wrapperType") != "track")
                            //skip non-track results
                                continue
                            val title = track.getString("trackName")
                            val year = track.getString("releaseDate")
                            val description = track.getString("longDescription")
                            val url = track.getString("trackViewUrl")
                            val movie = Movie(title, year, description, url)
                            movies.add(movie)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    adapter!!.clear()
                    for (movie in movies) {
                        adapter!!.add(movie)
                    }
                }, Response.ErrorListener { error -> Log.e(TAG, error.toString()) })

        RequestSingleton.getInstance(this).add(request)
    }

    companion object {

        private val TAG = "MainActivity"
    }
}
