package com.cucler.prokabaddi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.fixturerecyclerview.FixtureViewModel
import com.cucler.prokabaddi.fixturerecyclerview.RecyclerFixtureAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fixture.*
import showCustomToast

class Fixture : Fragment() {

    private lateinit var viewModel: FixtureViewModel
    private lateinit var fixtureRecyclerView: RecyclerView
    lateinit var adapter: RecyclerFixtureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarFixture.visibility = View.VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshFixture.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Fixture.requireContext())) {
                pullToRefreshFixture.isRefreshing = true
                loadRecycler(view)
                pullToRefreshFixture.isRefreshing = false
            }
            else {
                Toast(this@Fixture.requireContext()).showCustomToast (this@Fixture.requireActivity())
                pullToRefreshFixture.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        fixtureRecyclerView = view.findViewById(R.id.fixtureRecyclerView)
        fixtureRecyclerView.layoutManager = LinearLayoutManager(context)
        fixtureRecyclerView.setHasFixedSize(true)
        adapter = RecyclerFixtureAdapter()
        fixtureRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(FixtureViewModel::class.java)
        viewModel.allFixture.observe(viewLifecycleOwner, Observer {
            adapter.updateFixtureList(this@Fixture.requireContext(), it, progressBarFixture)
        })
    }
}