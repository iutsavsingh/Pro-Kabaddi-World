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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.faqrecyclerview.FaqViewModel
import com.cucler.prokabaddi.faqrecyclerview.RecyclerFaqAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_faq.*
import showCustomToast


class Faq : Fragment() {

    private lateinit var viewModel: FaqViewModel
    private lateinit var faqRecyclerView: RecyclerView
    lateinit var adapter: RecyclerFaqAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarFaq.visibility = VISIBLE
        loadRecycler(view)

        val btnBack = activity?.btn_back
        if (btnBack != null) {
            btnBack.visibility = View.VISIBLE
        }

        pullToRefreshFaq.setOnRefreshListener(OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Faq.requireContext())) {
                pullToRefreshFaq.isRefreshing = true
                loadRecycler(view)
                pullToRefreshFaq.isRefreshing = false
            }
            else {
                Toast(this@Faq.requireContext()).showCustomToast (this@Faq.requireActivity())
                pullToRefreshFaq.isRefreshing = false
            }
        })
    }

    private fun loadRecycler(view: View) {
        faqRecyclerView = view.findViewById(R.id.faqRecyclerView)
        faqRecyclerView.layoutManager = LinearLayoutManager(context)
        faqRecyclerView.setHasFixedSize(true)
        adapter = RecyclerFaqAdapter()
        faqRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(FaqViewModel::class.java)
        viewModel.allFaq.observe(viewLifecycleOwner, Observer {
            adapter.updateFaqList(it, progressBarFaq)
        })
    }
}