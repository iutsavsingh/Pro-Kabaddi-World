package com.cucler.prokabaddi.teamrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.extras.loadBannerAd
import com.google.android.gms.ads.AdView

class RecyclerTeamAdapter : RecyclerView.Adapter<RecyclerTeamAdapter.MyViewHolder>() {

    private val teamList = ArrayList<TeamModel>()
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_team,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = teamList[position]
        Glide.with(context).load(teamList.get(position).imgTeam).into(holder.imgTeam)
        holder.Team.text = currentitem.Team
        holder.Raiders.text = currentitem.Raiders.toString().replace("\\n", "\n")
        holder.Defenders.text = currentitem.Defenders.toString().replace("\\n", "\n")
        holder.AllRounders.text = currentitem.AllRounders.toString().replace("\\n", "\n")
        holder.TopBuy.text = currentitem.TopBuy
        holder.TtlPlrPurd.text = currentitem.TtlPlrPurd
        loadBannerAd(context, holder.bannerAd)
        progressBar.visibility = View.GONE

        val isVisible: Boolean = currentitem.visibility
        holder.expandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        holder.collapsableLayout.setOnClickListener {
            currentitem.visibility = !currentitem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    fun updateteamList(context: Context, teamList: List<TeamModel>, progressBar: ProgressBar) {
        this.context = context
        this.teamList.clear()
        this.teamList.addAll(teamList)
        notifyDataSetChanged()
        this.progressBar = progressBar
        progressBar.visibility = View.GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTeam: ImageView = itemView.findViewById(R.id.imgTeamLogo)
        val Team: TextView = itemView.findViewById(R.id.txtTeam)
        val Raiders: TextView = itemView.findViewById(R.id.txtRaiders)
        val Defenders: TextView = itemView.findViewById(R.id.txtDefenders)
        val AllRounders: TextView = itemView.findViewById(R.id.txtAllRounders)
        val TopBuy: TextView = itemView.findViewById(R.id.txtTopBuy)
        val TtlPlrPurd: TextView = itemView.findViewById(R.id.txtTtlPlrPurd)
        val bannerAd: AdView = itemView.findViewById(R.id.bannerTeamAdID)

        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val collapsableLayout: LinearLayout = itemView.findViewById(R.id.collapsableLayout)
    }

}