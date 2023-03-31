package com.example.fifascore.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fifascore.R
import com.example.fifascore.data.db.RivalriesDataBase
import com.example.fifascore.data.models.Rivalry
import com.example.fifascore.data.repositories.RivalriesRepository
import com.example.fifascore.databinding.ActivityMainBinding
import com.example.fifascore.databinding.DialogViewBinding
import com.example.fifascore.ui.adpters.RivalriesAdapter
import com.example.fifascore.ui.viewmodels.MainActivityModel
import com.example.fifascore.utils.OnDialogClicked
import com.example.fifascore.utils.makeDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityModel
    private lateinit var rivalriesAdapter: RivalriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = MainActivityModel(
            RivalriesRepository(
                RivalriesDataBase.getInstance(this).rivalriesDao()
            )
        )
        rivalriesAdapter = RivalriesAdapter(
            object : RivalriesAdapter.OnRivalryClicked {
                override fun onRivalryClicked(rivalry: Rivalry) {
                    goToRivalryActivity(rivalry)
                }

                override fun onDeleteClicked(rivalry: Rivalry) {
                    makeDialog(
                        this@MainActivity,
                        object : OnDialogClicked {
                            override fun onPositiveButtonClicked() {
                                lifecycleScope.launch(Dispatchers.IO) {
                                    viewModel.deleteRivalry(rivalry)
                                }
                            }

                            override fun onNegativeButtonClicked() {
                                Log.d(TAG, "onNegativeButtonClicked canceled delete rivalry")
                            }

                        },
                        getString(R.string.delete_rivalry_title),
                        getString(R.string.delete_rivalry_message)
                    ).show()
                }
            }
        )

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.apply {
//                getAllRivalries()
            }

            binding.addRivalry.setOnClickListener {
                val binding = DialogViewBinding.inflate(layoutInflater)
                makeDialog(
                    this@MainActivity,
                    object : OnDialogClicked {
                        override fun onPositiveButtonClicked() {
                            val rivalry = Rivalry(
                                gameName = binding.gameNameEt.text.toString(),
                                player1Name = binding.player1Et.text.toString(),
                                player2Name = binding.player2Et.text.toString()
                            )
                            Log.i(TAG, "rivalry to add = $rivalry")
                            lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.addRivalry(rivalry)
                            }
                        }

                        override fun onNegativeButtonClicked() {
                            Log.i(TAG, "onNegativeButtonClicked: adding canceled")
                        }

                    },
                    getString(R.string.add_dialog_title),
                    view = binding.root
                ).show()
            }
        }
        binding.rivalriesRv.apply {
            adapter = rivalriesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            viewModel.rivalries.observe(this@MainActivity) { rivalries ->
                rivalriesAdapter.setRivalries(rivalries.toMutableList())
            }
        }


        setContentView(binding.root)
    }

    private fun goToRivalryActivity(rivalry: Rivalry) {
        val intent = Intent(this, RivalryActivity::class.java)
        intent.putExtra("rivalry", rivalry)
        startActivity(intent)
    }
}