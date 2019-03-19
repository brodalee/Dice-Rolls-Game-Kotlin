package com.example.test.Adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.Models.hof
import com.example.test.R
import kotlinx.android.synthetic.main.recycler_view_item.view.*

class HofAdapter(val list: List<hof>) : RecyclerView.Adapter<HofAdapter.HofViewHolder>() {

    inner class HofViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): HofViewHolder {
        val context = view.context
        val inflater = LayoutInflater.from(context)
        val theView = inflater.inflate(R.layout.recycler_view_item, view, false)

        return HofViewHolder(theView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: HofViewHolder, position: Int) {
        var hallof = list.get(position)
        viewHolder.itemView.textView1.text = hallof.name + " : " + hallof.date.toString()
        viewHolder.itemView.textView2.text =
            hallof.countnumber.toString() + " rolls for " + hallof.maxpoints.toString() + " pts."
        viewHolder.itemView.image.setImageResource(this.getImage(position))
    }

    /**
     * Récupère l'image selon le nombre de point choisi
     *
     * @return Int
     */
    private fun getImage(pos: Int): Int {
        var drawable = when {
            pos == 5 -> R.drawable.ic_account_circle_black_24dp
            pos == 50 -> R.drawable.ic_account_circle_black_24dp
            pos == 100 -> R.drawable.ic_account_circle_black_24dp
            else -> R.drawable.ic_account_circle_black_24dp
        }
        return drawable
    }

}