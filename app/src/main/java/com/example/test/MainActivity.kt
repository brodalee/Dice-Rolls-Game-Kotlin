package com.example.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.test.Models.HallOfFrame
import com.example.test.Utils.Converter
import com.example.test.Utils.HallOfFrameCalls
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HallOfFrameCalls.CallBacks<Any?> {

    /**
     * Si tout va bien, on récupère notre liste, et on
     * l'inject dans realm
     *
     * @return void
     */
    override fun onResponse(hofframe: List<HallOfFrame>) {
        Log.d("TAG", "PASSAGE RESPONSE GET FETCHING DATA")
        var realm: Realm = Realm.getDefaultInstance()

        /**
         * On récupère le last ID pour l'incrémenter
         */
        realm.beginTransaction()
        var result = (realm.where(com.example.test.Models.hof::class.java).max("id") ?: 0).toInt()
        realm.commitTransaction()
        result.plus(1)

        hofframe.forEach { hallofFrame ->

            /**
             * Et on insère chaque données trouvés
             */
            realm.beginTransaction()

            var test = Converter().toHof(hallofFrame)
            realm.copyToRealmOrUpdate(test)

            realm.commitTransaction()
        }
        Toast.makeText(this, "Sync done", Toast.LENGTH_LONG).show()
        Log.d("TAG NB CURRENT", hofframe.size.toString())
    }

    override fun onFailure() {
        Toast.makeText(this, "ERROR HAPPENED", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.Events()
        Realm.init(this)
        this.realm().initDataBase()
    }

    /**
     * Fill the dataBase with API data
     *
     * @return void
     */
    fun initDataBase() {
        HallOfFrameCalls().fetchAllHallOfFrame(this)
    }

    /**
     * Init Realm
     * Destroy DB for re init it
     *
     * @return void
     */
    private fun realm(): MainActivity {
        var config = RealmConfiguration.Builder().name("db.realm").build()
        var instances = Realm.getGlobalInstanceCount(config)
        if (instances > 0) {
        } else {
            Realm.deleteRealm(config)
            Realm.setDefaultConfiguration(config)
        }
        return this
    }

    /**
     * Launch the game (Open the Game activity)
     *
     * @return void
     */
    private fun play() {
        val maxPoint = when {
            five.isChecked -> 5
            fifteen.isChecked -> 50
            hundred.isChecked -> 100
            else -> return
        }
        if (this.verifyIfNamesAreFilled()) {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("maxPoints", maxPoint)
            intent.putExtra("p1", firstPlayerPseudo.text.toString())
            intent.putExtra("p2", SecondPlayerPseudo.text.toString())
            startActivity(intent)
        }

    }

    /**
     * Verify if both fields are completed or not
     *
     * @return boolean
     */
    private fun verifyIfNamesAreFilled(): Boolean {
        if (firstPlayerPseudo.text.toString().trim() != "" && SecondPlayerPseudo.text.toString().trim() != "") {
            return true
        }
        return false
    }

    /**
     * Init event on this activity
     *
     * @return void
     */
    private fun Events() {
        play.setOnClickListener {
            play()
        }

        hof.setOnClickListener {
            var intent = Intent(this@MainActivity, HallOfFrameActivity::class.java)
            startActivity(intent)
        }

        sync.setOnClickListener {
            this.realm().initDataBase()
        }
    }

}
