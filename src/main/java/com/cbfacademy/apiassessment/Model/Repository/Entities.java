package com.cbfacademy.apiassessment.Model.Repository;

import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Entity.ConverteJson;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;

import com.cbfacademy.apiassessment.Model.Entity.Season;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Entities implements ConverteJson {
    private List<Genre> genres;
    private List<Movie> movies;
    private List<Episode> episodes;
    private List<Season> seasons;
    private List<Serie> series;

    public Entities() {
        this.genres = new ArrayList<>();
        this.movies = new ArrayList<>();
        this.episodes = new ArrayList<>();
        this.seasons = new ArrayList<>();
        this.series = new ArrayList<>();
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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("genres", convertListToJson(genres));
        jsonObject.put("movies", convertListToJson(movies));
        jsonObject.put("episodes", convertListToJson(episodes));
        jsonObject.put("seasons", convertListToJson(seasons));
        jsonObject.put("series", convertListToJson(series));

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

        JSONArray jsonEpisodes = (JSONArray) jsonObject.get("episodes");
        for (Object obj : jsonEpisodes) {
            JSONObject jsonEpisode = (JSONObject) obj;
            Episode episode = new Episode();
            episode.fillFromJson(jsonEpisode);
            this.episodes.add(episode);
        }

        JSONArray jsonSeasons = (JSONArray) jsonObject.get("seasons");
        for (Object obj : jsonSeasons) {
            JSONObject jsonSeason = (JSONObject) obj;
            Season season = new Season();
            season.fillFromJson(jsonSeason);
            this.seasons.add(season);
        }

        JSONArray jsonSeries = (JSONArray) jsonObject.get("series");
        for (Object obj : jsonSeries) {
            JSONObject jsonSeason = (JSONObject) obj;
            Serie serie = new Serie();
            serie.fillFromJson(jsonSeason);
            this.series.add(serie);
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
