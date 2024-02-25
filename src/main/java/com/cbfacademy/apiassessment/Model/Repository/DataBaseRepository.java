package com.cbfacademy.apiassessment.Model.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.cbfacademy.apiassessment.Model.Entity.Content.Serie;
import com.cbfacademy.apiassessment.Model.Entity.Episode;
import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
import com.cbfacademy.apiassessment.Model.Entity.Season;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import com.cbfacademy.apiassessment.Model.CustomException.CustomException;

@Repository
public class DataBaseRepository {

    public Entities getAll() throws CustomException {

        Entities entities = new Entities();

        try (FileReader fileReader = new FileReader("DataBase.json")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonEntities = (JSONObject) jsonParser.parse(fileReader);

            JSONArray jsonGenres = (JSONArray) jsonEntities.get("genres");

            if (jsonGenres != null ) {
                for (Object obj : jsonGenres) {
                    JSONObject jsonGenre = (JSONObject) obj;
                    Genre genre = new Genre();
                    genre.fillFromJson(jsonGenre);
                    entities.getGenres().add(genre);
                }
            }

            JSONArray jsonMovies = (JSONArray) jsonEntities.get("movies");

            if (jsonMovies != null) {
                for (Object obj : jsonMovies) {
                    JSONObject jsonMovie = (JSONObject) obj;
                    Movie movie = new Movie();
                    movie.fillFromJson(jsonMovie);
                    entities.getMovies().add(movie);
                }
            }

            JSONArray jsonEpisodes = (JSONArray) jsonEntities.get("episodes");

            if (jsonEpisodes != null) {
                for (Object obj : jsonEpisodes) {
                    JSONObject jsonEpisode = (JSONObject) obj;
                    Episode episode = new Episode();
                    episode.fillFromJson(jsonEpisode);
                    entities.getEpisodes().add(episode);
                }
            }

            JSONArray jsonSeasons = (JSONArray) jsonEntities.get("seasons");

            if (jsonSeasons != null) {
                for (Object obj : jsonSeasons) {
                    JSONObject jsonSeason = (JSONObject) obj;
                    Season season = new Season();
                    season.fillFromJson(jsonSeason);
                    entities.getSeasons().add(season);
                }
            }

            JSONArray jsonSeries = (JSONArray) jsonEntities.get("series");

            if (jsonSeries != null) {
                for (Object obj : jsonSeries) {
                    JSONObject jsonSerie = (JSONObject) obj;
                    Serie serie = new Serie();
                    serie.fillFromJson(jsonSerie);
                    entities.getSeries().add(serie);
                }
            }
        } catch (FileNotFoundException e) {
            throw new CustomException("JSON file not found");
        } catch (Exception e) {
            throw new CustomException("Error when reading JSON");
        }

        return  entities;
    }

    public void saveAll(Entities entities) throws CustomException {
        JSONObject object = entities.converteToJson();

        try {
            FileWriter jsonFile = new FileWriter("DataBase.json");
            jsonFile.write(object.toJSONString());
            jsonFile.flush();
        } catch (Exception e) {
            throw new CustomException("Erro when saving JSON");
        }
    }
}
