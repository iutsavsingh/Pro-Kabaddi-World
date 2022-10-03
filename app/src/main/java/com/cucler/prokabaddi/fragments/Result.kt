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
import com.cucler.prokabaddi.resultrecyclerview.RecyclerResultAdapter
import com.cucler.prokabaddi.resultrecyclerview.ResultViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_result.*
import showCustomToast

class Result : Fragment() {

    private lateinit var viewModel: ResultViewModel
    private lateinit var resultRecyclerView: RecyclerView
    lateinit var adapter: RecyclerResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarResult.visibility = View.VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshResult.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Result.requireContext())) {
                pullToRefreshResult.isRefreshing = true
                loadRecycler(view)
                pullToRefreshResult.isRefreshing = false
            }
            else {
                Toast(this@Result.requireContext()).showCustomToast (this@Result.requireActivity())
                pullToRefreshResult.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        resultRecyclerView = view.findViewById(R.id.resultRecyclerView)
        resultRecyclerView.layoutManager = LinearLayoutManager(context)
        resultRecyclerView.setHasFixedSize(true)
        adapter = RecyclerResultAdapter()
        resultRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)
        viewModel.allResult.observe(viewLifecycleOwner, Observer {
            adapter.updateResultList(this@Result.requireContext(), it, progressBarResult)
        })
    }
}