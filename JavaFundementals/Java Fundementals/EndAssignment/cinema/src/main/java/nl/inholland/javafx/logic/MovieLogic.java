package nl.inholland.javafx.logic;

import javafx.collections.ObservableList;
import nl.inholland.javafx.dal.Database;
import nl.inholland.javafx.model.Movie;

public class MovieLogic extends Database {
    Database database;

    public MovieLogic(Database database) {
        this.database = database;
    }

    public void addMovie(Movie movie){ database.addMovie(movie); } // add movie to database

    public ObservableList<Movie> getMovies(){ return database.getMovies(); } // get movies from database
}
