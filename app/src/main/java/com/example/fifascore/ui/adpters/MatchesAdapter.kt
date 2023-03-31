package com.example.fifascore.ui.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fifascore.data.models.Match
import com.example.fifascore.databinding.SingleMatchBinding

class MatchesAdapter(private val onMatchClicked: OnMatchClicked) :
    RecyclerView.Adapter<MatchesAdapter.MatchesHolder>() {

    interface OnMatchClicked {
        fun onDeleteClicked(match: Match)
    }

    private var matchesList = mutableListOf<Match>()

    inner class MatchesHolder(private val binding: SingleMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val currentMatch = matchesList[position]
            binding.apply {
                team1Name.text = currentMatch.team1Name
                team2Name.text = currentMatch.team2Name
                team1Score.text = currentMatch.team1Score.toString()
                team2Score.text = currentMatch.team2Score.toString()

                deleteMatch.setOnClickListener {
                    onMatchClicked.onDeleteClicked(currentMatch)
                }
            }

        }
    }

    fun setMatches(list: MutableList<Match>) {
        matchesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesHolder {
        return MatchesHolder(
            SingleMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MatchesHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = matchesList.size
}