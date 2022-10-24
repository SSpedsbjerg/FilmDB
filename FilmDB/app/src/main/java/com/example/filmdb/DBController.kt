package com.example.filmdb

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.*

class DBController(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DBName, factory, DBVersion) {

    //Create DB
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TableName + " ("
                + IDColumn + " INTEGER PRIMARY KEY, " +
                FilmName + " TEXT, " +
                Director + " TEXT, " +
                Writers + " TEXT, " +
                LeadActors + " TEXT, " +
                Genre + " TEXT, " +
                Description + " TEXT " + ")")
        db.execSQL(query);
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName)
        onCreate(db)
    }

    suspend fun ClearDB(){
        val db = this.readableDatabase
        db.execSQL("DELETE FROM " + TableName);
        db.close()
    }

    suspend fun addFilm(film : Film){
        val values = ContentValues()

        values.put(FilmName, film.FilmName); values.put(Director, film.Director); values.put(Writers, film.Writers); values.put(LeadActors, film.LeadActors); values.put(Genre, film.Genre); values.put(Description, film.Description);

        val db = this.writableDatabase
        db.insert(TableName, null, values)
        db.close()
    }

    suspend fun getFilm(filmName : String) : Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TableName + " WHERE " + FilmName + "=" + filmName + ";", null);
    }

    @SuppressLint("Range")
    suspend fun getFilmByID(id : Int) : Film?{
        val db = this.readableDatabase
        val reVal = db.rawQuery("SELECT * FROM " + TableName + " WHERE " + IDColumn + "=" + id, null)
        var film : Film? = null
        while (reVal.moveToNext()){
        film = Film( 0,
            reVal.getString(reVal.getColumnIndex(FilmName)),
            reVal.getString(reVal.getColumnIndex(Director)),
            reVal.getString(reVal.getColumnIndex(Writers)),
            reVal.getString(reVal.getColumnIndex(LeadActors)),
            reVal.getString(reVal.getColumnIndex(Genre)),
            reVal.getString(reVal.getColumnIndex(Description)),
        )
        }
        db.close()
        return film
    }

    @SuppressLint("Range")
    suspend fun getAllFilms() : ArrayList<Film> {
        val db = this.readableDatabase
        val reVal = db.rawQuery("SELECT * FROM " + TableName, null);
        val films = ArrayList<Film>()
        while (reVal.moveToNext()){
            films.add(Film( 0,
                reVal.getString(reVal.getColumnIndex(FilmName)),
                reVal.getString(reVal.getColumnIndex(Director)),
                reVal.getString(reVal.getColumnIndex(Writers)),
                reVal.getString(reVal.getColumnIndex(LeadActors)),
                reVal.getString(reVal.getColumnIndex(Genre)),
                reVal.getString(reVal.getColumnIndex(Description))
            ))
        }
        db.close()
        return films
    }

    companion object{
        private val DBName = "FilmDB"
        private val DBVersion = 1
        val TableName = "films"
        val IDColumn = "id"

        //variables
        val FilmName = "name"
        val Director = "director"
        val Writers = "writers"
        val LeadActors = "leadActors"
        val Genre = "genre"
        val Description = "description"

    }
}