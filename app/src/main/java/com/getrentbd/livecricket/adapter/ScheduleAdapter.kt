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
import com.getrentbd.livecricket.model.ScheduleModelClass
import com.squareup.picasso.Picasso


class ScheduleAdapter(var context: Context, var listItem: List<ScheduleModelClass>) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder?>() {


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.schedule_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, i: Int) {
        val scheduleModelClass: ScheduleModelClass = listItem[i]
        viewHolder.dateView.text = scheduleModelClass.date
        viewHolder.timeView.text = scheduleModelClass.time
        viewHolder.matchView.text = scheduleModelClass.match
        viewHolder.stadiumView.text = scheduleModelClass.stadium
        viewHolder.leftCounrtyView.text = scheduleModelClass.leftCountry
        viewHolder.rightCountryzview.text = scheduleModelClass.rightCountry
        val team1: String = scheduleModelClass.leftCountry
        val team2: String = scheduleModelClass.rightCountry
        when {
            team1.contains("Bangladesh") -> {
                Picasso.get().load(R.drawable.ban).into(viewHolder.leftImgView)
            }
            team1.contains("South Africa") -> {
                Picasso.get().load(R.drawable.south).into(viewHolder.leftImgView)
            }
            team1.contains("Afghanistan") -> {
                Picasso.get().load(R.drawable.afg).into(viewHolder.leftImgView)
            }
            team1.contains("Australia") -> {
                Picasso.get().load(R.drawable.aus).into(viewHolder.leftImgView)
            }
            team1.contains("Sri Lanka") -> {
                Picasso.get().load(R.drawable.sri).into(viewHolder.leftImgView)
            }
            team1.contains("New Zealand") -> {
                Picasso.get().load(R.drawable.new_z).into(viewHolder.leftImgView)
            }
            team1.contains("Pakistan") -> {
                Picasso.get().load(R.drawable.pak).into(viewHolder.leftImgView)
            }
            team1.contains("Windies") -> {
                Picasso.get().load(R.drawable.wes).into(viewHolder.leftImgView)
            }
            team1.contains("England") -> {
                Picasso.get().load(R.drawable.eng).into(viewHolder.leftImgView)
            }
            team1.contains("India") -> {
                Picasso.get().load(R.drawable.india).into(viewHolder.leftImgView)
            }
        }
        when {
            team2.contains("Bangladesh") -> {
                Picasso.get().load(R.drawable.ban).into(viewHolder.rightImgView)
            }
            team2.contains("South Africa") -> {
                Picasso.get().load(R.drawable.south).into(viewHolder.rightImgView)
            }
            team2.contains("Afghanistan") -> {
                Picasso.get().load(R.drawable.afg).into(viewHolder.rightImgView)
            }
            team2.contains("Australia") -> {
                Picasso.get().load(R.drawable.aus).into(viewHolder.rightImgView)
            }
            team2.contains("Sri Lanka") -> {
                Picasso.get().load(R.drawable.sri).into(viewHolder.rightImgView)
            }
            team2.contains("New Zealand") -> {
                Picasso.get().load(R.drawable.new_z).into(viewHolder.rightImgView)
            }
            team2.contains("Pakistan") -> {
                Picasso.get().load(R.drawable.pak).into(viewHolder.rightImgView)
            }
            team2.contains("Windies") -> {
                Picasso.get().load(R.drawable.wes).into(viewHolder.rightImgView)
            }
            team2.contains("England") -> {
                Picasso.get().load(R.drawable.eng).into(viewHolder.rightImgView)
            }
            team2.contains("India") -> {
                Picasso.get().load(R.drawable.india).into(viewHolder.rightImgView)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateView: TextView = itemView.findViewById(R.id.dateid)
        var leftCounrtyView: TextView = itemView.findViewById(R.id.leftcountryid)
        var leftImgView: ImageView = itemView.findViewById(R.id.leftimgid)
        var matchView: TextView = itemView.findViewById(R.id.matchid)
        var rightCountryzview: TextView = itemView.findViewById(R.id.rightcountryid)
        var rightImgView: ImageView = itemView.findViewById(R.id.rightimgid)
        var stadiumView: TextView = itemView.findViewById(R.id.stadiumid)
        var timeView: TextView = itemView.findViewById(R.id.timeid)

    }
}