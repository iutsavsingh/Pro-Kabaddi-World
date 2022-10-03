package com.cucler.prokabaddi.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.extras.*
import com.cucler.prokabaddi.fixturerecyclerview.FixtureViewModel
import com.cucler.prokabaddi.fixturerecyclerview.RecyclerFixtureAdapter
import com.cucler.prokabaddi.resultrecyclerview.RecyclerResultAdapter
import com.cucler.prokabaddi.resultrecyclerview.ResultViewModel
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import showCustomToast


class Home : Fragment() {

    lateinit var navController: NavController
    lateinit var imageList: ArrayList<SlideModel>

    private lateinit var viewModelFixture: FixtureViewModel
    private lateinit var fixtureRecyclerView: RecyclerView
    lateinit var adapterFixture: RecyclerFixtureAdapter

    private lateinit var viewModelResult: ResultViewModel
    private lateinit var resultRecyclerView: RecyclerView
    lateinit var adapterResult: RecyclerResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.btn_back?.visibility = GONE

        progressBarHomeUpcomingMatch.visibility = View.VISIBLE
        progressBarHomeResult.visibility = View.VISIBLE
        loadFixtureRecycler(view)
        loadResultRecycler(view)

        //Ads
        loadBannerAd(this@Home.requireActivity(), bannerHomeAdID1)
        loadVideoInterestitial(
            this@Home.requireContext(),
            R.string.videoInterstitialFixtureAdId.toString()
        )

        //ImageSlider
        imageList = ArrayList<SlideModel>()
        addArrImg("https://firebasestorage.googleapis.com/v0/b/pro-kabaddi-2022.appspot.com/o/Slider%2Fs1.png?alt=media&token=17f5aa2e-ad32-4103-9144-1192523ba615")
        addArrImg("https://firebasestorage.googleapis.com/v0/b/pro-kabaddi-2022.appspot.com/o/Slider%2Fs2.png?alt=media&token=28b4f086-82ce-4342-ae4a-5aa36e4c33d3")
        addArrImg("https://firebasestorage.googleapis.com/v0/b/pro-kabaddi-2022.appspot.com/o/Slider%2Fs3.png?alt=media&token=90a8b5f6-2f1b-4012-92bf-6f68e0135ef6")
        image_slider.setImageList(imageList, ScaleTypes.FIT)

        //Navigation
        navController = Navigation.findNavController(view)

        btnHomeResult.setOnClickListener {
            navController.navigate(R.id.action_home2_to_result)
        }
        btnHomeFixture.setOnClickListener {
            showVideoInterstitial(this@Home.requireActivity())
            navController.navigate(R.id.action_home2_to_fixture)
        }
        btnHomePointTable.setOnClickListener {
//            loadVideoInterestitial(this@Home.requireContext())
//            showVideoInterstitial(this@Home.requireActivity())
            navController.navigate(R.id.action_home2_to_pointTable)
        }
        btnHomeCoach.setOnClickListener {
            loadInterestitial(this@Home.requireContext(), R.string.interstitialCoachAdId.toString())
            showInterstitial(this@Home.requireActivity())
            navController.navigate(R.id.action_home2_to_coach)
        }
        btnHomeTeams.setOnClickListener {
            loadInterestitial(this@Home.requireContext(), R.string.interstitialTeamsAdId.toString())
            showInterstitial(this@Home.requireActivity())
            navController.navigate(R.id.action_home2_to_team)
        }
        btnHomeFaq.setOnClickListener {
            loadInterestitial(this@Home.requireContext(), R.string.interstitialFaqAdId.toString())
            showInterstitial(this@Home.requireActivity())
            navController.navigate(R.id.action_home2_to_faq)
        }
        btnHomeRateUs.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.cucler.prokabaddi")
                )
            )
        }
        btnHomeShareUs.setOnClickListener {
            shareUs(this@Home.requireContext())
        }
        btnHomeMoreApps.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=5618520249899005145")
                )
            )
        }
        btnHomeGreetUs.setOnClickListener {
            loadVideoInterestitial(
                this@Home.requireContext(),
                R.string.videoInterstitialGreetUsAdId.toString()
            )
            showVideoInterstitial(this@Home.requireActivity())
        }

        moreUpcomingMatch.setOnClickListener {
            navController.navigate(R.id.action_home2_to_fixture)
        }
        moreResult.setOnClickListener {
            navController.navigate(R.id.action_home2_to_result)
        }

        pullToRefreshHome.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            if (com.cucler.prokabaddi.extras.CheckNetwork.isInternetAvailable(this@Home.requireContext())) {
                pullToRefreshHome.isRefreshing = true
                loadFixtureRecycler(view)
                loadResultRecycler(view)
                image_slider.setImageList(imageList, ScaleTypes.FIT)
                pullToRefreshHome.isRefreshing = false
            }
            else {
                Toast(this@Home.requireContext()).showCustomToast (this@Home.requireActivity())
                pullToRefreshHome.isRefreshing = false
            }
        })

    }

    fun addArrImg(url: String) {
        imageList.add(
            SlideModel(
                url
            )
        )
    }

    private fun loadFixtureRecycler(view: View) {
        val layoutManager =
            LinearLayoutManager(this@Home.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fixtureRecyclerView = view.findViewById(R.id.homeFixtureRecyclerView)
        fixtureRecyclerView.layoutManager = layoutManager
        fixtureRecyclerView.setHasFixedSize(true)
        adapterFixture = RecyclerFixtureAdapter()
        fixtureRecyclerView.adapter = adapterFixture
        viewModelFixture = ViewModelProvider(this).get(FixtureViewModel::class.java)
        viewModelFixture.allFixture.observe(viewLifecycleOwner, Observer {
            adapterFixture.updateFixtureList(
                this@Home.requireContext(),
                it,
                progressBarHomeUpcomingMatch,
                2
            )
        })
    }

    private fun loadResultRecycler(view: View) {
        val layoutManager =
            LinearLayoutManager(this@Home.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        resultRecyclerView = view.findViewById(R.id.homeResultRecyclerView)
        resultRecyclerView.layoutManager = layoutManager
        resultRecyclerView.setHasFixedSize(true)
        adapterResult = RecyclerResultAdapter()
        resultRecyclerView.adapter = adapterResult
        viewModelResult = ViewModelProvider(this).get(ResultViewModel::class.java)
        viewModelResult.allResult.observe(viewLifecycleOwner, Observer {
            adapterResult.updateResultList(this@Home.requireContext(), it, progressBarHomeResult, 2)
        })
    }

}