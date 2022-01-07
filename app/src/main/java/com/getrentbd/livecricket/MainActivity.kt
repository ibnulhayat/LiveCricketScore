package com.getrentbd.livecricket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.getrentbd.livecricket.adapter.ScheduleAdapter
import com.getrentbd.livecricket.model.ScheduleModelClass
import org.json.JSONException

open class MainActivity : AppCompatActivity() {

    private var scheduleAdapter: ScheduleAdapter? = null
    private val modelList: MutableList<ScheduleModelClass> = ArrayList()
    private var requestQueue: RequestQueue? = null
    var url = "https://api.jsonbin.io/b/5ca32ba857e7bb33d8ab1523/3"

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestQueue = Volley.newRequestQueue(this)
        val fixtureRecyclerView: RecyclerView = findViewById(R.id.fixtureRecyclerView)
        val ivLiveScore: ImageView = findViewById(R.id.ivLiveScore)
        val layoutTeam: LinearLayout = findViewById(R.id.layoutTeam)
        val layoutLiveTv: LinearLayout = findViewById(R.id.layoutLiveTv)
        val layoutPointTable: LinearLayout = findViewById(R.id.layoutPointTable)
        fixtureRecyclerView.layoutManager = LinearLayoutManager(this)
        scheduleAdapter = ScheduleAdapter(this, modelList)
        fixtureRecyclerView.adapter = scheduleAdapter

        jsonParsing()
        ivLiveScore.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    LiveSoreActivity::class.java
                )
            )
        }
        layoutTeam.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    TeamsActivity::class.java
                )
            )
        }
        layoutLiveTv.setOnClickListener {
            val intent = Intent(this@MainActivity, LiveTvActivity::class.java)
            intent.putExtra("link", "https://www.the-daily-story.com/gtv-live/")
            startActivity(intent)
        }
        layoutPointTable.setOnClickListener { }
    }

    private fun jsonParsing() {
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("schedule_data")
                    for (i in 0 until jsonArray.length() - 3) {
                        val `object` = jsonArray.getJSONObject(i)
                        val date = `object`.getString("date")
                        val left_country = `object`.getString("left_country")
                        val right_country = `object`.getString("right_country")
                        val stadium = `object`.getString("stadium")
                        val time = `object`.getString("time")
                        val no = i + 1
                        val matchNo = "Match $no"
                        modelList.add(
                            ScheduleModelClass(
                                date,
                                time,
                                matchNo,
                                stadium,
                                left_country,
                                right_country
                            )
                        )
                    }
                    scheduleAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("JSONERROR", e.message!!)
                }
            }) { error ->
            Log.e("VolleyERROR", error.message!!)
            //Toast.makeText(MainActivity.this, ""+error.message, Toast.LENGTH_SHORT).show();
        }
        requestQueue?.add(request)
    }
}