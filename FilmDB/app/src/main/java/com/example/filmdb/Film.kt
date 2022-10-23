package com.example.filmdb

data class Film(val image: Int, val FilmName: String, val Director: String, val Writers: String, val LeadActors: String, val Genre: String, val Description: String) {
    public override fun toString() : String{
        return "Film: " + FilmName + "\nDirector: " + Director + "\nWriters: " + Writers + "\nLeadActors: " + LeadActors + "\nGenre: " + Genre + "\nDescription: " + Description
    }
}