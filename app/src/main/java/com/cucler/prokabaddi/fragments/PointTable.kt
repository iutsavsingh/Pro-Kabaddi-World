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
import com.cucler.prokabaddi.pointtablerecyclerview.PointTableViewModel
import com.cucler.prokabaddi.pointtablerecyclerview.RecyclerPointTableAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_point_table.*
import showCustomToast

class PointTable : Fragment() {

    private lateinit var viewModel: PointTableViewModel
    private lateinit var pointTableRecyclerView: RecyclerView
    lateinit var adapter: RecyclerPointTableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_point_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarPointTable.visibility = View.VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshPointTable.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@PointTable.requireContext())) {
                pullToRefreshPointTable.isRefreshing = true
                loadRecycler(view)
                pullToRefreshPointTable.isRefreshing = false
            }
            else {
                Toast(this@PointTable.requireContext()).showCustomToast (this@PointTable.requireActivity())
                pullToRefreshPointTable.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        pointTableRecyclerView = view.findViewById(R.id.pointTableRecyclerView)
        pointTableRecyclerView.layoutManager = LinearLayoutManager(context)
        pointTableRecyclerView.setHasFixedSize(true)
        adapter = RecyclerPointTableAdapter()
        pointTableRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(PointTableViewModel::class.java)
        viewModel.allPointTable.observe(viewLifecycleOwner, Observer {
            adapter.updatePointTableList(
                this@PointTable.requireContext(),
                it,
                progressBarPointTable
            )
        })
    }

}