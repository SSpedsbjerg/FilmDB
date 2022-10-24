package com.example.filmdb

data class Film(val image: Int, val FilmName: String, val Director: String, val Writers: String, val LeadActors: String, val Genre: String, val Description: String) {
    public override fun toString() : String{
        return "Film: " + FilmName + "\n\nDirector: " + Director + "\n\nWriters: " + Writers + "\n\nLeadActors: " + LeadActors + "\n\nGenre: " + Genre + "\n\nDescription: " + Description
    }
}