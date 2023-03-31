package com.example.fifascore.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fifascore.R
import com.example.fifascore.data.db.RivalriesDataBase
import com.example.fifascore.data.models.Match
import com.example.fifascore.data.models.Rivalry
import com.example.fifascore.data.repositories.MatchesRepository
import com.example.fifascore.databinding.ActivityRivalryBinding
import com.example.fifascore.databinding.MatchDialogViewBinding
import com.example.fifascore.ui.adpters.MatchesAdapter
import com.example.fifascore.ui.viewmodels.RivalryActivityModel
import com.example.fifascore.utils.OnDialogClicked
import com.example.fifascore.utils.makeDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RivalryActivity"

class RivalryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRivalryBinding
    private lateinit var viewModel: RivalryActivityModel
    private lateinit var matchesAdapter: MatchesAdapter
    private var rivalry: Rivalry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        rivalry = intent.getParcelableExtra("rivalry")
        if (rivalry == null) finish()
        binding = ActivityRivalryBinding.inflate(layoutInflater)
        viewModel = RivalryActivityModel(
            rivalry!!,
            MatchesRepository(
                RivalriesDataBase.getInstance(this).matchesDao(),
                RivalriesDataBase.getInstance(this).rivalriesDao()
            )
        )
        matchesAdapter = MatchesAdapter(
            object : MatchesAdapter.OnMatchClicked {
                override fun onDeleteClicked(match: Match) {

                    makeDialog(
                        this@RivalryActivity,
                        object : OnDialogClicked {
                            override fun onPositiveButtonClicked() {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    viewModel.deleteMatch(match)
                                }
                            }

                            override fun onNegativeButtonClicked() {
                                Log.d(TAG, "onNegativeButtonClicked canceled delete match")
                            }

                        },
                        getString(R.string.delete_rivalry_title),
                        getString(R.string.delete_rivalry_message)
                    ).show()
                }
            }
        )
        Log.d(TAG, "rivalry: $rivalry")

        super.onCreate(savedInstanceState)

        rivalry?.apply {

            binding.apply {
//                viewModel.getRivalryMatches(currentRivalry.id!!)

                viewModel.rivalry.observe(this@RivalryActivity) { currentRivalry ->

                    Log.d(TAG, "currentRivalry: $currentRivalry")

                    addMatch.setOnClickListener {
                        val dialogBinding = MatchDialogViewBinding.inflate(layoutInflater)
                        makeDialog(
                            this@RivalryActivity,
                            object : OnDialogClicked {
                                override fun onPositiveButtonClicked() {
                                    val match = Match(
                                        rivalryId = currentRivalry.id!!,
                                        team1Name = dialogBinding.name1.editText?.text.toString(),
                                        team2Name = dialogBinding.name2.editText?.text.toString(),
                                        team1Score = dialogBinding.score1.editText?.text.toString()
                                            .toInt(),
                                        team2Score = dialogBinding.score2.editText?.text.toString()
                                            .toInt()
                                    )
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        viewModel.addMatch(match)
                                    }

                                }

                                override fun onNegativeButtonClicked() {
                                    Log.d(TAG, "onNegativeButtonClicked canceled")
                                }

                            },
                            getString(R.string.add_dialog_title),
                            view = dialogBinding.root
                        ).show()
                    }


                    //game name
                    game.text = currentRivalry.gameName

                    //names data
                    player1Name.text = currentRivalry.player1Name
                    player2Name.text = currentRivalry.player2Name

                    //scores data
                    player1Score.text = currentRivalry.player1Score.toString()
                    player2Score.text = currentRivalry.player2Score.toString()

                    //percentage data
                    Log.d(TAG, "player1WinPercentage: ${currentRivalry.player1WinPercentage()}")
                    player1WinPercentage.text = getString(
                        R.string.percentage, currentRivalry.player1WinPercentage()
                    )
                    player2WinPercentage.text = getString(
                        R.string.percentage, currentRivalry.player2WinPercentage()
                    )
                }

                //matches
                matchesRv.apply {
                    adapter = matchesAdapter
                    layoutManager = LinearLayoutManager(this@RivalryActivity)
                    viewModel.matches.observe(this@RivalryActivity) { matches ->
                        matchesAdapter.setMatches(matches.toMutableList())
                    }
                }
            }
        }


        setContentView(binding.root)
    }
}