package com.example.test

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.Events()
        Realm.init(this)
        var config = RealmConfiguration.Builder().name("db.realm").build()
        Realm.setDefaultConfiguration(config)
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
        if(this.verifyIfNamesAreFilled()) {
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
        if(firstPlayerPseudo.text.toString() != "" && SecondPlayerPseudo.text.toString() != "" ){
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
    }
}
