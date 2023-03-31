package com.example.fifascore.ui.adpters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fifascore.data.models.Rivalry
import com.example.fifascore.databinding.SingleRivalryBinding

private const val TAG = "RivalriesAdapter"

class RivalriesAdapter(
    private val onRivalryClicked: OnRivalryClicked
) : RecyclerView.Adapter<RivalriesAdapter.RivalriesHolder>() {

    interface OnRivalryClicked {
        fun onRivalryClicked(rivalry: Rivalry)
        fun onDeleteClicked(rivalry: Rivalry)
    }

    private var rivalriesList = mutableListOf<Rivalry>()

    inner class RivalriesHolder(private val binding: SingleRivalryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val currentRivalry = rivalriesList[position]
            binding.apply {
                player1Name.text = currentRivalry.player1Name
                player2Name.text = currentRivalry.player2Name
                player1Score.text = currentRivalry.player1Score.toString()
                player2Score.text = currentRivalry.player2Score.toString()

                rivalryWhole.setOnClickListener {
                    onRivalryClicked.onRivalryClicked(currentRivalry)
                }
                deleteRivalry.setOnClickListener {
                    onRivalryClicked.onDeleteClicked(currentRivalry)
                }
            }
        }
    }

    fun setRivalries(list: MutableList<Rivalry>) {
        rivalriesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RivalriesHolder {
        return RivalriesHolder(
            SingleRivalryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RivalriesHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = rivalriesList.size
}