package com.cucler.prokabaddi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.coachrecyclerview.CoachViewModel
import com.cucler.prokabaddi.coachrecyclerview.RecyclerCoachAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_coach.*
import showCustomToast

class Coach : Fragment() {

    private lateinit var viewModel: CoachViewModel
    private lateinit var coachRecyclerView: RecyclerView
    lateinit var adapter: RecyclerCoachAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarCoach.visibility = VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshCoach.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Coach.requireContext())) {
                pullToRefreshCoach.isRefreshing = true
                loadRecycler(view)
                pullToRefreshCoach.isRefreshing = false
            }
            else {
                Toast(this@Coach.requireContext()).showCustomToast (this@Coach.requireActivity())
                pullToRefreshCoach.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        coachRecyclerView = view.findViewById(R.id.coachRecyclerView)
        coachRecyclerView.layoutManager = LinearLayoutManager(context)
        coachRecyclerView.setHasFixedSize(true)
        adapter = RecyclerCoachAdapter()
        coachRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(CoachViewModel::class.java)
        viewModel.allCoach.observe(viewLifecycleOwner, Observer {
            adapter.updateCoachList(this@Coach.requireContext(), it, progressBarCoach)
        })
    }

}