package com.cbfacademy.apiassessment.Model.Repository;

import com.cbfacademy.apiassessment.Model.Entity.ConverteJson;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Entities implements ConverteJson {
    private List<Genre> genres;
    private List<Movie> movies;

    public Entities() {
        this.genres = new ArrayList<>();
        this.movies = new ArrayList<>();
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("genres", convertListToJson(genres));
        jsonObject.put("movies", convertListToJson(movies));
        return jsonObject;
    }

    @Override
    public void fillFromJson(JSONObject jsonObject) {
        JSONArray jsonGenres = (JSONArray) jsonObject.get("genres");
        for (Object obj : jsonGenres) {
            JSONObject jsonGenre = (JSONObject) obj;
            Genre genre = new Genre();
            genre.fillFromJson(jsonGenre);
            this.genres.add(genre);
        }

        JSONArray jsonMovies = (JSONArray) jsonObject.get("movies");
        for (Object obj : jsonMovies) {
            JSONObject jsonMovie = (JSONObject) obj;
            Movie movie = new Movie();
            movie.fillFromJson(jsonMovie);
            this.movies.add(movie);
        }
    }

    private JSONArray convertListToJson(List<? extends ConverteJson> list) {
        JSONArray jsonArray = new JSONArray();
        for (ConverteJson obj : list) {
            jsonArray.add(obj.converteToJson());
        }
        return jsonArray;
    }
}
