package at.technikum_wien.tscheppen.homework4.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.technikum_wien.tscheppen.homework4.adapter.ListAdapter
import at.technikum_wien.tscheppen.homework4.viewmodels.NewsListViewModel
import at.technikum_wien.tscheppen.homework4.R

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRSSFeed()



    }

    override fun onDestroy() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(settingsmenu: Menu?): Boolean {
        menuInflater.inflate(R.layout.settingsmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if (itemId == R.id.action_settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(getString(R.string.key_display_images))) {
            val defaultValue = resources.getBoolean(R.bool.settings_images_displayed_default)
            displayTextView?.visibility = if (sharedPreferences?.getBoolean(
                    getString(R.string.settings_output_visible_key),
                    defaultValue) ?: defaultValue) View.VISIBLE else View.GONE
        }
    }

    fun getSettings(){
        var errorTextView = findViewById<TextView>(R.id.tv_error)
        var recyclerView = findViewById<RecyclerView>(R.id.rv_list)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        errorTextView?.visibility = if (sharedPreferences.getBoolean(
                getString(R.string.key_display_images),
                resources.getBoolean(R.bool.settings_images_displayed_default))) View.VISIBLE else View.GONE
    }


    fun getRSSFeed(){
        val viewModel by viewModels<NewsListViewModel>()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_list)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = ListAdapter()
        recyclerView.adapter = adapter

        adapter.itemClickListener = {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.ITEM_KEY, it)
            startActivity(intent)
        }

        val reloadButton = findViewById<Button>(R.id.btn_reload)
        reloadButton.setOnClickListener {
            viewModel.reload()
        }

        val errorTextView = findViewById<TextView>(R.id.tv_error)

        viewModel.error.observe(this) {
            errorTextView.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.newsItems.observe(this) {
            adapter.items = it
        }

        viewModel.busy.observe(this) {
            reloadButton.isEnabled = !it
        }
    }

}

