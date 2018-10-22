package edu.uw.fragmentdemo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieListFragment : Fragment() {

    private val TAG = "ListFragment"

    private lateinit var adapter: ArrayAdapter<Movie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_movie_list, container, false)

        adapter = ArrayAdapter(activity,
                R.layout.list_item, R.id.txt_item, ArrayList())

        val listView = rootView.findViewById<View>(R.id.list_view) as ListView
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val movie = parent.getItemAtPosition(position) as Movie
            Log.v(TAG, "You clicked on: $movie")
        }

        return rootView
    }


}
