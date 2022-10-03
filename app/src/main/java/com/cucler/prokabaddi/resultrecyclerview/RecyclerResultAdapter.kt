package com.cucler.prokabaddi.resultrecyclerview

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

class RecyclerResultAdapter : RecyclerView.Adapter<RecyclerResultAdapter.MyViewHolder>() {

    private val resultList = ArrayList<ResultModel>()
    private lateinit var context: Context
    private lateinit var progressBar: ProgressBar
    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_result,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = resultList[position]
        holder.Match.text = currentitem.Match
        Glide.with(context).load(resultList.get(position).imgTeam1).into(holder.imgTeam1)
        Glide.with(context).load(resultList.get(position).imgTeam2).into(holder.imgTeam2)
        holder.Team1.text = currentitem.Team1
        holder.Team2.text = currentitem.Team2
        holder.Team1Score.text = currentitem.Team1Score.toString()
        holder.Team2Score.text = currentitem.Team2Score.toString()
        holder.Result.text = currentitem.Result
        progressBar.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        if (count == 0)
            return resultList.size
        return count
    }

    fun updateResultList(
        context: Context,
        fixtureList: List<ResultModel>,
        progressBar: ProgressBar,
        count: Int = 0
    ) {
        this.count = count
        this.context = context
        this.resultList.clear()
        this.resultList.addAll(fixtureList)
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
        val Team1Score: TextView = itemView.findViewById(R.id.txtTeam1Score)
        val Team2Score: TextView = itemView.findViewById(R.id.txtTeam2Score)
        val Result: TextView = itemView.findViewById(R.id.txtResult)
    }

}