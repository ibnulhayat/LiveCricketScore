package com.getrentbd.livecricket

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.getrentbd.livecricket.adapter.LiveScoreAdapter
import com.getrentbd.livecricket.model.LiveScore
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LiveSoreActivity : AppCompatActivity() {
    private var mRequestQueue: RequestQueue? = null
    private lateinit var rvLiveScore: RecyclerView
    private var liveScoreList: MutableList<LiveScore> = ArrayList()
    private var favTeamLiveScoreAdapter: LiveScoreAdapter? = null
    private lateinit var progressDialog: ProgressDialog
    private val liveScoreLink = "http://ams.mapps.cricbuzz.com/cbzandroid/2.0/currentmatches.json"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_score)

        rvLiveScore = findViewById(R.id.rvLiveScore)
        liveScoreList = ArrayList()
        mRequestQueue = Volley.newRequestQueue(this)
        favTeamLiveScoreAdapter = LiveScoreAdapter(liveScoreList)
        rvLiveScore.layoutManager = LinearLayoutManager(this)
        rvLiveScore.setHasFixedSize(true)
        rvLiveScore.adapter = favTeamLiveScoreAdapter
        progressDialog = ProgressDialog(this@LiveSoreActivity)
        progressDialog.setMessage("Please Wait......!")
        progressDialog.show()
        parseLiveScoreDetails()

    }

    private fun parseLiveScoreDetails() {
        val request = StringRequest(
            Request.Method.GET,
            liveScoreLink,
            { response ->
                try {
                    var batTeamName = ""
                    var batTeamSname = ""
                    var bowlTeamname = ""
                    var bowlTeamSname = ""
                    var batTeamImg = ""
                    var bowlTeamImg = ""
                    var target = ""
                    val jsonArray = JSONArray(response)
                    val numberofItem: Int = jsonArray.length()
                    for (i in 0 until numberofItem) {
                        val `object`: JSONObject = jsonArray.getJSONObject(i)
                        val matchId: String = `object`.getString("matchId")
                        val srs: String = `object`.getString("srs")
                        val datapath: String = `object`.getString("datapath")
                        val header: JSONObject = `object`.getJSONObject("header")
                        val mchState: String = header.getString("mchState")
                        val type: String = header.getString("type")
                        val mnum: String = header.getString("mnum")
                        val status: String = header.getString("status")
                        val NoOfIngs: String = header.getString("NoOfIngs")
                        if (mchState == "inprogress" || mchState == "complete") {
                            val miniscore: JSONObject = `object`.getJSONObject("miniscore")
                            val batTeamId: String = miniscore.getString("batteamid")
                            val batteamscore: String = miniscore.getString("batteamscore")
                            val bowlteamscore: String = miniscore.getString("bowlteamscore")
                            target = bowlteamscore
                            target = if (target == "" || target == "00") {
                                "0"
                            } else {
                                try {
                                    val trg = target.split("/").toTypedArray()
                                    val res = trg[0].toInt()
                                    val trget = (res + 1).toString()
                                    trget
                                } catch (e: Exception) {
                                    "0"
                                }
                            }
                            val batteamovers: String = miniscore.getString("overs")
                            val bowlteamovers: String = miniscore.getString("bowlteamovers")
                            var batTeamCRR: String = miniscore.getString("crr")
                            var batTeamRR: String = miniscore.getString("rrr")
                            val teamOneObj: JSONObject = `object`.getJSONObject("team1")
                            val teamOneID: String = teamOneObj.getString("id")
                            val teamTwoObj: JSONObject = `object`.getJSONObject("team2")
                            if (batTeamId == teamOneID) {
                                val teamID: String = teamOneObj.getString("id")
                                val teamID2: String = teamTwoObj.getString("id")
                                batTeamName = teamOneObj.getString("name")
                                batTeamSname = teamOneObj.getString("sName")
                                bowlTeamname = teamTwoObj.getString("name")
                                bowlTeamSname = teamTwoObj.getString("sName")
                                batTeamImg =
                                    "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + teamID + "_50.png"
                                bowlTeamImg =
                                    "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + teamID2 + "_50.png"
                            } else {
                                val teamID: String = teamOneObj.getString("id")
                                val teamID2: String = teamTwoObj.getString("id")
                                batTeamName = teamTwoObj.getString("name")
                                batTeamSname = teamTwoObj.getString("sName")
                                bowlTeamname = teamOneObj.getString("name")
                                bowlTeamSname = teamOneObj.getString("sName")
                                batTeamImg =
                                    "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + teamID2 + "_50.png"
                                bowlTeamImg =
                                    "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + teamID + "_50.png"
                            }
                            if (mchState == "inprogress") {
                                batTeamCRR = "0"
                                batTeamRR = "0"
                            } else if (mchState == "stump") {
                                batTeamRR = "0"
                            } else {
                                target = "0"
                            }
                            liveScoreList.add(
                                LiveScore(
                                    "$mnum $srs",
                                    batTeamId,
                                    batTeamSname,
                                    batTeamImg,
                                    bowlTeamImg,
                                    bowlTeamSname,
                                    batteamscore,
                                    batteamovers,
                                    batTeamRR,
                                    batTeamCRR,
                                    target,
                                    bowlteamscore,
                                    bowlteamovers,
                                    status,
                                    datapath,
                                    mnum,
                                    matchId,
                                    type,
                                    mchState,
                                    NoOfIngs
                                )
                            )
                        }
                    }
                    favTeamLiveScoreAdapter?.notifyDataSetChanged()
                    progressDialog.dismiss()
                    //Toast.makeText(LiveSoreActivity.this, "RR : " + batTeamName, Toast.LENGTH_SHORT).show();
                } catch (e: JSONException) {
                    e.printStackTrace()
                    progressDialog.dismiss()
                    Toast.makeText(
                        this@LiveSoreActivity,
                        "Catch Error " + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { progressDialog.dismiss() })
        mRequestQueue?.add(request)
    }
}