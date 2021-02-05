package com.getsquire.squirelocations.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getsquire.squirelocations.App
import com.getsquire.squirelocations.LocationsApi
import com.getsquire.squirelocations.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FragmentLocationsList() : Fragment() {

    @Inject
    lateinit var api: LocationsApi

    lateinit var recycler: RecyclerView
    lateinit var locationsAdapter: LocationsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as App).daggerComponent.inject(this)
        return inflater.inflate(R.layout.fragment_locations_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.back)
            .setOnClickListener { requireActivity().onBackPressed() }
        view.findViewById<Button>(R.id.button_nearby).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "The feature is in progress. Stay tuned",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        locationsAdapter = LocationsAdapter {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    FragmentLocationDetails.getInstance(it.name + ", " + it.address?.city)
                )
                .addToBackStack(null)
                .commit()
        }

        recycler = view.findViewById(R.id.locationList)
        recycler.adapter = locationsAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        api.loadHome()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //todo добавить данные в адаптер туm
                locationsAdapter.setList(it.brand?.shops ?: emptyList())
                Timber.d(it.brand?.shops.toString())
            }
    }


}