package com.example.filmdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        CoroutineScope(Main).launch {
            buildRecycle()
        }

    }

    suspend fun buildRecycle() {

        val recyclerview = findViewById<RecyclerView>(R.id.FilmRecycler)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<FilmShort>()
        val db = DBController(this,null)

        /*
        db.addFilm(Film(0,
            "Spiderman",
            "Sam Raimi",
            "Stan Lee, Steve Ditko, David Koepp",
            "Tobey Maguire, Kirsten Dunst, Willem Dafoe",
            "Action Adventure",
            "After being bitten by a genetically-modified spider, a shy teenager gains spider-like abilities that he uses to fight injustice as a masked superhero and face a vengeful enemy."
        ));

        db.addFilm(Film(
            0,
            "Hulk",
            "Ang Lee",
            "Stan Lee, Jack Kirby, James Schamus",
            "Eric Bana, Jennifer Connelly, Sam Elliot",
            "Action Sci-Fi",
            "Bruce Banner, a genetics researcher with a tragic past, suffers an accident that causes him to transform into a raging green monster when he gets angry."
        ))
*/
        var films = ArrayList<Film>()
        val job = CoroutineScope(IO).launch {
            films = db.getAllFilms();
        }

        job.join()
        for (film in films) {
            data.add(FilmShort(R.drawable.ic_baseline_folder_24, film.FilmName))
        }

        val adapter = CustomAdapter(data)

        recyclerview.adapter = adapter

        adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {
                Log.d("id", position.toString());
                val intent = Intent(this@MainActivity, InfoScreen::class.java)
                intent.putExtra("id", position)
                startActivity(intent)
            }

        })
    }
}