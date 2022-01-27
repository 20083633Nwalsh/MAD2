package org.wit.musicka.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.musicka.R
import org.wit.musicka.adapters.MusickaAdapter
import org.wit.musicka.adapters.MusickaListener
import org.wit.musicka.databinding.ActivityMusickaListBinding
import org.wit.musicka.main.MainApp
import org.wit.musicka.models.MusickaModel

class MusickaListActivity : AppCompatActivity(), MusickaListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityMusickaListBinding

    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusickaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadGames()



        registerRefreshCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        timber.log.Timber.i("menu triggered")
        when (item.itemId) {
            R.id.item_addgame -> {
                timber.log.Timber.i("clicked add")
                val launcherIntent = Intent(this, MusickaActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMusickaClick(musicka: MusickaModel) {
        val launcherIntent = Intent(this, MusickaActivity::class.java)
        launcherIntent.putExtra("musicka_edit", musicka)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadGames() }
    }

    private fun loadGames() {
        showGames(app.games.findAll())
    }

    fun showGames (games: List<MusickaModel>) {
        binding.recyclerView.adapter = MusickaAdapter(games, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }


}