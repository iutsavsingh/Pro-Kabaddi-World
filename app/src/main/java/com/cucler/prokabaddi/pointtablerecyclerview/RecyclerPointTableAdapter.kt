package com.cucler.prokabaddi.pointtablerecyclerview

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

class RecyclerPointTableAdapter : RecyclerView.Adapter<RecyclerPointTableAdapter.MyViewHolder>() {

    private val pointTableList = ArrayList<PointTableModel>()
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_point_table,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = pointTableList[position]
        Glide.with(context).load(pointTableList.get(position).imgTeam).into(holder.imgTeam)
        holder.Team.text = currentitem.Team
        holder.Mp.text = currentitem.Mp.toString()
        holder.W.text = currentitem.W.toString()
        holder.L.text = currentitem.L.toString()
        holder.D.text = currentitem.D.toString()
        holder.Sd.text = currentitem.Sd.toString()
        holder.Pts.text = currentitem.Pts.toString()

        progressBar.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return pointTableList.size
    }

    fun updatePointTableList(
        context: Context,
        pointTableList: List<PointTableModel>,
        progressBar: ProgressBar
    ) {
        this.context = context
        this.pointTableList.clear()
        this.pointTableList.addAll(pointTableList)
        notifyDataSetChanged()
        this.progressBar = progressBar
        progressBar.visibility = View.GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTeam: ImageView = itemView.findViewById(R.id.imgTeamLogo)
        val Team: TextView = itemView.findViewById(R.id.txtTeam)
        val Mp: TextView = itemView.findViewById(R.id.txtMp)
        val W: TextView = itemView.findViewById(R.id.txtW)
        val L: TextView = itemView.findViewById(R.id.txtL)
        val D: TextView = itemView.findViewById(R.id.txtD)
        val Sd: TextView = itemView.findViewById(R.id.txtSd)
        val Pts: TextView = itemView.findViewById(R.id.txtPts)
    }

}