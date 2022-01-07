package com.getrentbd.livecricket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.getrentbd.livecricket.R
import com.getrentbd.livecricket.model.LiveScore
import com.squareup.picasso.Picasso

class LiveScoreAdapter(liveScoreList: List<LiveScore>) :
    RecyclerView.Adapter<LiveScoreAdapter.ViewHolder?>() {
    private val liveScoreList: List<LiveScore> = liveScoreList
    private var context: Context? = null
    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ViewHolder {
        context = viewGroup.context
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.livescore_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, i: Int) {
        val items: LiveScore = liveScoreList[i]
        val batTeamName: String = items.batTeamName
        val batTeamImg: String = items.batTeamImg
        val bowlTeamName: String = items.bowlTeamName
        val bowlTeamImg: String = items.bowlTeamImg
        val batTeamScore: String = items.batTeamScore
        var batTeamOvers: String = items.batTeamOvers
        val batTeamRR: String = items.batTeamRR
        val batTeamCRR: String = items.batTeamCRR
        val bowlTeamScore: String = items.bowlTeamScore
        val bowlTeamOvers: String = items.bowlTeamOvrs
        val target: String = items.target
        val type: String = items.type
        val matchId: String = items.matchId
        if (type == "ODI") {
            batTeamOvers = "$batTeamOvers/50"
        } else if (type == "T20" || type == "T20I") {
            batTeamOvers = "$batTeamOvers/20"
        }
        viewHolder.setDetails(
            batTeamName,
            batTeamImg,
            bowlTeamName,
            bowlTeamImg,
            batTeamScore,
            batTeamOvers,
            batTeamRR,
            batTeamCRR,
            bowlTeamScore,
            bowlTeamOvers,
            target
        )

    }

    override fun getItemCount(): Int {
        return liveScoreList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mView: View = itemView
        fun setDetails(
            batTeamName: String?, batTeamImg: String?, bowlTeamName: String?, bowlTeamImg: String?,
            batTeamScore: String, batTeamOvers: String, batTeamRR: String?, batTeamCRR: String?,
            bowlTeamScore: String, bowlTeamOvrs: String, target: String
        ) {

            val tvBatTeamName: TextView = mView.findViewById(R.id.tvBatTeamName)
            tvBatTeamName.text = batTeamName
            val tvBowlTeamName: TextView = mView.findViewById(R.id.tvBowlTeamName)
            tvBowlTeamName.text = bowlTeamName
            val tvBatTeamScore: TextView = mView.findViewById(R.id.tvBatTeamScore)
            tvBatTeamScore.text = "SCORE: $batTeamScore"
            val tvBatTeamOvers: TextView = mView.findViewById(R.id.tvBatTeamOvers)
            tvBatTeamOvers.text = "OVER: $batTeamOvers"
            val tvBowlTeamScore: TextView = mView.findViewById(R.id.tvBowlTeamScore)
            val tvBowlTeamOver: TextView = mView.findViewById(R.id.tvBowlTeamOver)
            val tvTarget: TextView = mView.findViewById(R.id.tvTarget)
            val ivBatTeam: ImageView = mView.findViewById(R.id.ivBatTeam)
            Picasso.get().load(batTeamImg).into(ivBatTeam)
            val ivBowlTeam: ImageView = mView.findViewById(R.id.ivBowlTeam)
            Picasso.get().load(bowlTeamImg).into(ivBowlTeam)
            if (target == "0" || target == "") {
                tvTarget.visibility = View.GONE
            }else{
                tvTarget.text = "TARGET: $target"
            }
            if (bowlTeamScore == "") {
                tvBowlTeamScore.visibility = View.GONE
            }else{
                tvBowlTeamScore.text = "SCORE: $bowlTeamScore"
            }
            if (bowlTeamOvrs == "0") {
                tvBowlTeamOver.visibility = View.GONE
            }else{
                tvBowlTeamOver.text = "OVER: $bowlTeamOvrs"
            }
        }
    }

}