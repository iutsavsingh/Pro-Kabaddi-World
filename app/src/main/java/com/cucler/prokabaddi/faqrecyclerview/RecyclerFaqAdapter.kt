package com.cucler.prokabaddi.faqrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.extras.loadBannerAd
import com.google.android.gms.ads.AdView

class RecyclerFaqAdapter : RecyclerView.Adapter<RecyclerFaqAdapter.MyViewHolder>() {

    private val faqList = ArrayList<FaqModel>()
    private lateinit var progressBar: ProgressBar
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_faq,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = faqList[position]
        holder.Qtn.text = currentitem.Qtn
        holder.Ans.text = currentitem.Ans.toString().replace("\\n", "\n")
        loadBannerAd(context, holder.bannerAd)
        progressBar.visibility = View.GONE

        val isVisible: Boolean = currentitem.visibility
        holder.expandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        holder.Qtn.setOnClickListener {
            currentitem.visibility = !currentitem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    fun updateFaqList(faqList: List<FaqModel>, progressBar: ProgressBar) {
        this.faqList.clear()
        this.faqList.addAll(faqList)
        notifyDataSetChanged()
        this.progressBar = progressBar
        progressBar.visibility = View.GONE
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Qtn: TextView = itemView.findViewById(R.id.txtQtn)
        val Ans: TextView = itemView.findViewById(R.id.txtAns)
        val expandableLayout: LinearLayout = itemView.findViewById(R.id.expandableLayout)
        val bannerAd: AdView = itemView.findViewById(R.id.bannerFaqAdID)
    }

}