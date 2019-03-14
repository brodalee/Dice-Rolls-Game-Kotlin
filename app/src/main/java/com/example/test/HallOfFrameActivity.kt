package com.example.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.test.Adapters.HofAdapter
import com.example.test.Models.hof
import io.realm.Realm
import kotlinx.android.synthetic.main.hallofframe.*

class HallOfFrameActivity : AppCompatActivity() {

    private var realm: Realm? = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hallofframe)
        this.initInterface()
    }

    /**
     * Initialise interface with adapters and getting data
     *
     * @return void
     */
    private fun initInterface() {
        var hofList = this.getHallOfFrameDatas()
        var adapter = HofAdapter(hofList)
        recycler_view_list_hof.adapter = adapter
    }

    /**
     * Get data from the DB
     *
     * @return RealmResults
     */
    private fun getHallOfFrameDatas(): List<hof> {
        realm!!.beginTransaction()
        var results = realm?.where(hof::class.java)!!.findAll()
        realm!!.commitTransaction()
        return results.toList()
    }
}