package com.getsquire.squirelocations.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.getsquire.squirelocations.R

class FragmentLocationDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tv_name_and_city).text =
            arguments?.getString(NAME_AND_CITY)
    }

    companion object {
        const val NAME_AND_CITY = "nameAndCity"

        fun getInstance(nameAndCity: String): FragmentLocationDetails {
            val bundle = Bundle()
            bundle.putString(NAME_AND_CITY, nameAndCity)
            val fragment = FragmentLocationDetails()
            fragment.arguments = bundle
            return fragment
        }
    }
}