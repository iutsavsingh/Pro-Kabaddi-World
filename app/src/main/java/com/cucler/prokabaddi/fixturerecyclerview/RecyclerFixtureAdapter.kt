package com.cucler.prokabaddi.fixturerecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cucler.prokabaddi.R

class RecyclerFixtureAdapter : RecyclerView.Adapter<RecyclerFixtureAdapter.MyViewHolder>() {

    private val fixtureList = ArrayList<FixtureModel>()
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar
    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_fixture,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = fixtureList[position]
        holder.Match.text = currentitem.Match
        Glide.with(context).load(fixtureList.get(position).imgTeam1).into(holder.imgTeam1)
        Glide.with(context).load(fixtureList.get(position).imgTeam2).into(holder.imgTeam2)
        holder.Team1.text = currentitem.Team1
        holder.Team2.text = currentitem.Team2
        holder.VenueTime.text = currentitem.VenueTime
        progressBar.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        if (count == 0)
            return fixtureList.size
        return count
    }

    fun updateFixtureList(
        context: Context,
        fixtureList: List<FixtureModel>,
        progressBar: ProgressBar,
        count: Int = 0
    ) {
        this.count = count
        this.context = context
        this.fixtureList.clear()
        this.fixtureList.addAll(fixtureList)
        notifyDataSetChanged()
        this.progressBar = progressBar
        progressBar.visibility = View.GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Match: TextView = itemView.findViewById(R.id.txtMatch)
        val imgTeam1: ImageView = itemView.findViewById(R.id.imgTeam1)
        val imgTeam2: ImageView = itemView.findViewById(R.id.imgTeam2)
        val Team1: TextView = itemView.findViewById(R.id.txtTeam1)
        val Team2: TextView = itemView.findViewById(R.id.txtTeam2)
        val VenueTime: TextView = itemView.findViewById(R.id.txtVenueTime)
    }

}