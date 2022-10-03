package com.cucler.prokabaddi.coachrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
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

class RecyclerCoachAdapter : RecyclerView.Adapter<RecyclerCoachAdapter.MyViewHolder>() {

    private val coachList = ArrayList<CoachModel>()
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_coach,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = coachList[position]
        Glide.with(context).load(coachList.get(position).imgTeam).into(holder.imgTeam)
        holder.Coach.text = currentitem.Coach
        holder.AssCoach.text = currentitem.AssCoach
        holder.Team.text = currentitem.Team
        loadBannerAd(context, holder.bannerAd)
        progressBar.visibility = GONE

        val isVisible: Boolean = currentitem.visibility
        holder.expandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        holder.collapsableLayout.setOnClickListener {
            currentitem.visibility = !currentitem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    fun updateCoachList(context: Context, coachList: List<CoachModel>, progressBar: ProgressBar) {
        this.context = context
        this.coachList.clear()
        this.coachList.addAll(coachList)
        notifyDataSetChanged()
        this.progressBar = progressBar
        progressBar.visibility = GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTeam: ImageView = itemView.findViewById(R.id.imgTeamLogo)
        val Coach: TextView = itemView.findViewById(R.id.txtTeamCoach)
        val AssCoach: TextView = itemView.findViewById(R.id.txtTeamAssCoach)
        val Team: TextView = itemView.findViewById(R.id.txtTeam)
        val bannerAd: AdView = itemView.findViewById(R.id.bannerCoachAdID)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val collapsableLayout: LinearLayout = itemView.findViewById(R.id.collapsableLayout)
    }

}