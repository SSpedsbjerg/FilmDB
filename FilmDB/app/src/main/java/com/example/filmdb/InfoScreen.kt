package com.example.filmdb

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class InfoScreen : AppCompatActivity() {
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        val intent = intent
        position = intent.getIntExtra("id", 0)
        var actionBar = getSupportActionBar()

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        CoroutineScope(Dispatchers.Main).launch {
            UpdateText()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    suspend fun UpdateText(){
        val db = DBController(this,null)
        var film = db.getFilmByID(position + 1)

        val job = CoroutineScope(Dispatchers.IO).launch {
            film = db.getFilmByID(position + 1)
        }

        job.join()

        findViewById<TextView>(R.id.filmView).text = film.toString()
    }
}