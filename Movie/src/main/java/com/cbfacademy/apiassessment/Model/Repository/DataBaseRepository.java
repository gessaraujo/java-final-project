package com.cbfacademy.apiassessment.Model.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.cbfacademy.apiassessment.Model.Entity.Genre;
import com.cbfacademy.apiassessment.Model.Entity.Content.Movie;
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

            // Fill Genres List
            JSONArray jsonGenres = (JSONArray) jsonEntities.get("genres");

            if (jsonGenres != null ) {
                for (Object obj : jsonGenres) {
                    JSONObject jsonGenre = (JSONObject) obj;
                    Genre genre = new Genre();
                    genre.fillFromJson(jsonGenre);
                    entities.getGenres().add(genre);
                }
            }

            // Fill Genres List
            JSONArray jsonMovies = (JSONArray) jsonEntities.get("movies");

            if (jsonMovies != null) {
                for (Object obj : jsonMovies) {
                    JSONObject jsonMovie = (JSONObject) obj;
                    Movie movie = new Movie();
                    movie.fillFromJson(jsonMovie);
                    entities.getMovies().add(movie);
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
