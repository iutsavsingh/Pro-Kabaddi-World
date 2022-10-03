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
import com.cucler.prokabaddi.teamrecyclerview.RecyclerTeamAdapter
import com.cucler.prokabaddi.teamrecyclerview.TeamViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_team.*
import showCustomToast

class Team : Fragment() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var teamRecyclerView: RecyclerView
    lateinit var adapter: RecyclerTeamAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarTeam.visibility = View.VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshTeam.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Team.requireContext())) {
                pullToRefreshTeam.isRefreshing = true
                loadRecycler(view)
                pullToRefreshTeam.isRefreshing = false
            }
            else {
                Toast(this@Team.requireContext()).showCustomToast (this@Team.requireActivity())
                pullToRefreshTeam.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        teamRecyclerView = view.findViewById(R.id.teamRecyclerView)
        teamRecyclerView.layoutManager = LinearLayoutManager(context)
        teamRecyclerView.setHasFixedSize(true)
        adapter = RecyclerTeamAdapter()
        teamRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        viewModel.allTeams.observe(viewLifecycleOwner, Observer {
            adapter.updateteamList(this@Team.requireContext(), it, progressBarTeam)
        })
    }
}