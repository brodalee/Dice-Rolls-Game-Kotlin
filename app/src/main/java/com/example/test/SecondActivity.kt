package com.example.test

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.test.Entity.*
import com.example.test.Models.hof
import io.realm.Realm
import kotlinx.android.synthetic.main.game.*
import java.util.*
import kotlin.random.Random

class SecondActivity : AppCompatActivity() {

    lateinit var players: List<User>

    var turn: Int = 0
    var currentPot: Int = 0
    var maxPoints: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        this.maxPoints = intent.getIntExtra("maxPoints", 0)
        this.players = listOf(User(intent.getStringExtra("p1"), Bank(0)), User(intent.getStringExtra("p2"), Bank(0)))
        majInterface()
        initEvents()
    }

    /**
     * Init listener on roll button
     *
     * @return void
     */
    private fun initEvents() {
        roll.setOnClickListener {
            play()
        }

        get_current_pot.setOnClickListener {
            this.players[turn].bank.amount += this.currentPot
            this.currentPot = 0
            this.changeTurn()
            this.majInterface()
            var end = this.verifyIfThereIsAWinner()

            if (end != null) {
                Toast.makeText(this, "GG ! ${end.name} has won the game !", Toast.LENGTH_LONG)

                var realm = Realm.getDefaultInstance()
                /**
                 * On récupère le nombre max d'objets stockés
                 */
                realm.beginTransaction()
                var results = (realm.where(hof::class.java).max("id") ?: 0).toInt()
                realm.commitTransaction()
                results += 1
                /**
                 * On push le nouvel objet
                 */
                realm.beginTransaction()
                var hallof = realm.createObject(hof::class.java, results)
                hallof.maxpoints = this.maxPoints!!.toLong()
                hallof.countnumber = 50
                hallof.date = Date()
                hallof.name = end.name
                realm.commitTransaction()

                var dialog: AlertDialog.Builder = AlertDialog.Builder(this@SecondActivity)
                dialog.setTitle("Game finished")
                dialog.setMessage("${end.name} has won !!")
                dialog.setNeutralButton("OK") { _, _ -> finish() }
                dialog.show()
            }
        }
    }

    /**
     * Permit to play the game, this one is called with the event click on the button
     * Then it update the UI
     */
    private fun play() {
        var firstRandomNumber = Random.nextInt(6) + 1
        var secondRandomNumber = Random.nextInt(6) + 1

        this.majImageDice(firstRandomNumber, secondRandomNumber)

        if (firstRandomNumber != 1 || secondRandomNumber != 1) {
            //Cas ou pas de 1, donc on remplis le pot
            this.currentPot += firstRandomNumber + secondRandomNumber

        }
        if (firstRandomNumber == 1 || secondRandomNumber == 1) {
            //On reset le pot, mais on garde la cagnote
            this.currentPot = 0
            Toast.makeText(this, "Pot lost", Toast.LENGTH_SHORT).show()
            this.changeTurn()

        }
        if (firstRandomNumber == 1 && secondRandomNumber == 1) {
            //On vide la cagnote
            this.players.get(turn).bank.resetAmount()
            this.changeTurn()
        }
        this.majInterface()
    }

    /**
     * Permit to verify if there is a winner or not
     *
     * @return null|User
     */
    private fun verifyIfThereIsAWinner(): User? {
        if (this.players.get(0).bank.amount >= this.maxPoints!!) {
            return this.players.get(0)
        }
        if (this.players.get(1).bank.amount >= this.maxPoints!!) {
            return this.players.get(1)
        }
        return null
    }

    /**
     * Init interface
     *
     * @return void
     */
    private fun majInterface() {
        playerTurn.text = this.players.get(turn).name
        pOneAmount.text = this.players.get(0).bank.amount.toString()
        pTwoAmount.text = this.players.get(1).bank.amount.toString()
        current_points.text = this.currentPot.toString()
    }

    private fun majImageDice(first: Int, second: Int) {
        var drawableOne = this.getDrawableDice(first)
        first_dice.setImageResource(drawableOne)

        var drawableSec = this.getDrawableDice(second)
        second_dice.setImageResource(drawableSec)
    }

    /**
     * Permit to get the correct dice image
     * To draw it
     *
     * @return int
     */
    private fun getDrawableDice(number: Int): Int {
        return when (number) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }


    /**
     * Change the turn of player
     *
     * @return void
     */
    private fun changeTurn() = if (turn == 0) {
        turn = 1
    } else {
        turn = 0
    }

}