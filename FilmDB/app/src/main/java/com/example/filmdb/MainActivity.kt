package com.example.filmdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

        var addDummyButton = findViewById(R.id.AddDummyMoviesButton) as Button
        var clearDBButton = findViewById(R.id.ClearDB) as Button

        clearDBButton.setOnClickListener{
            CoroutineScope(IO).launch {
                db.ClearDB()
            }
        }

        addDummyButton.setOnClickListener{
            CoroutineScope(IO).launch {

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

                db.addFilm(Film(
                    0,
                    "Creed III",
                    "Michael B. Jordan",
                    "Keenan Coogler(screenplay by), Zach Baylin(screenplay by), Ryan Coogler(story by)",
                    "Tessa Thompson, Jonathan Majors, Michael B. Jordan",
                    "Drama Sport",
                    "After dominating the boxing world, Adonis has been thriving in his career and family life. When a childhood friend and former boxing prodigy resurfaces, the face off between former friends is more than just a fight."
                ))

                db.addFilm(Film(
                    0,
                    "Black Panther: Wakanda Forever",
                    "Ryan Coogler",
                    "Ryan Coogler(screenplay by), Joe Robert Cole(screenplay by)",
                    "Angela Bassett, Tenoch Huerta, Martin Freeman",
                    "Action Adventure",
                    "The nation of Wakanda is pitted against intervening world powers as they mourn the loss of their king T'Challa."
                ))

                db.addFilm(Film(
                    0,
                    "Deadpool",
                    "Tim Miller",
                    "Rhett Reese, Paul Wernick",
                    "Ryan Reynolds, Morena Baccarin, T.J. Miller",
                    "Action Adventure Comedy",
                    "A wisecracking mercenary gets experimented on and becomes immortal but ugly, and sets out to track down the man who ruined his looks."
                ))

                db.addFilm(Film(
                    0,
                    "Logan",
                    "James Mangold",
                    "James Mangold(story by), Scott Frank(screenplay by), Michael Green(screenplay by)",
                    "Hugh Jackman, Patrick Stewart, Dafne Keen",
                    "Action Drama",
                    "In a future where mutants are nearly extinct, an elderly and weary Logan leads a quiet life. But when Laura, a mutant child pursued by scientists, comes to him for help, he must get her to safety."
                ))

                db.addFilm(Film(
                    0,
                    "Guardians of the Galaxy",
                    "James Gunn",
                    "James Gunn, Nicole Pearlman, Dan Abnett",
                    "Chris Pratt, Vin Diesel, Bradley Cooper",
                    "Action Drama",
                    "A group of intergalactic criminals must pull together to stop a fanatical warrior with plans to purge the universe."
                ))

                db.addFilm(Film(
                    0,
                    "Predator ",
                    "John McTiernan ",
                    "Jim McTiernan, John Thomas",
                    "Arnold Schwarzenegger Carl Weathers Kevin Peter Hall",
                    "Action Adventure Horror",
                    "A team of commandos on a mission in a Central American jungle find themselves hunted by an extraterrestrial warrior."
                ))

                db.addFilm(Film(
                    0,
                    "The Terminator",
                    "James Cameron",
                    "James Cameron, Gale Anne Hurd, William Wisher",
                    "Arnold Schwarzenegger, Linda Hamilton, Michael Biehn",
                    "Action Sci-Fi",
                    "A human soldier is sent from 2029 to 1984 to stop an almost indestructible cyborg killing machine, sent from the same year, which has been programmed to execute a young woman whose unborn son is the key to humanity's future salvation."
                ))

                db.addFilm(Film(
                    0,
                    "The First Avenger 2: Second War",
                    "Anthony Russo, Joe Russo",
                    "Christopher Markus(screenplay by), Stephen McFeely(screenplay by), Joe Simon(based on the Marvel comics by)",
                    "Chris Evans, Samuel L. Jackson, Scarlett Johansson",
                    "Action Adventure",
                    "As Steve Rogers struggles to embrace his role in the modern world, he teams up with a fellow Avenger and S.H.I.E.L.D agent, Black Widow, to battle a new threat from history: an assassin known as the Winter Soldier."
                ))

                db.addFilm(Film(
                    0,
                    "The First Avenger",
                    "Joe Johnston",
                    "Christopher Markus, Stephen McFeely, Joe Simon",
                    "Chris Evans, Hugo Weaving, Samuel L. Jackson",
                    "Action Adventure",
                    "Steve Rogers, a rejected military soldier, transforms into Captain America after taking a dose of a Super-Soldier serum. But being Captain America comes at a price as he attempts to take down a warmonger and a terrorist organization."
                ))

                db.addFilm(Film(
                    0,
                    "Shazam",
                    "David F. Sandberg",
                    "Henry Gayden, Darren Lemke, Bill Parker",
                    "Zachary Levi, Mark Strong, Asher Angel",
                    "Action Adventure Comedy",
                    "A newly fostered young boy in search of his mother instead finds unexpected super powers and soon gains a powerful enemy."
                ))
            }
        }




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
                val intent = Intent(this@MainActivity, InfoScreen::class.java)
                intent.putExtra("id", position)
                startActivity(intent)
            }
        })
    }
}