package com.example.forecaster

import android.os.Bundle
import android.os.RecoverySystem
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forecaster.adapter.WeatherAdapter
import com.example.forecaster.common.Common
import com.example.forecaster.model.ListItem
import com.example.forecaster.model.Wrapper
import com.example.forecaster.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var recyclerAdapter = WeatherAdapter()
var weatherList = mutableListOf<ListItem>()

/**
 * A simple [Fragment] subclass.
 * Use the [RetainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RetainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val service: RetrofitServices by lazy {
        Common.retrofitService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().findViewById<RecyclerView>(R.id.recycler_vew).apply {
//            Timber.e("this == null = ${view.context == null}")
//            val layout = LinearLayoutManager(view.context)
//            Timber.e("layout == $layout")
//
//            this.layoutManager = layout
//        }

        getRecycler(view)
    }

    fun getSavedData(){
        recyclerAdapter.submitList(weatherList)
    }



    fun getRecycler(view: View){
        view.findViewById<RecyclerView>(R.id.recycler_vew).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    fun getAllWeatherList(city: String) {
        service.getWeather(city).enqueue(object : Callback<Wrapper> {
            override fun onResponse(call: Call<Wrapper>, response: retrofit2.Response<Wrapper>) {
                val abc = response.body() as Wrapper
                weatherList = abc.list.toMutableList()
                recyclerAdapter.submitList(weatherList)
                recyclerAdapter.notifyDataSetChanged()
                Timber.e(abc.cod)
            }
            override fun onFailure(call: Call<Wrapper>, t: Throwable) {
                Timber.e(t.message.toString())
            }
        })

    }

    companion object {
        const val TAG = "fragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RetainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RetainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}