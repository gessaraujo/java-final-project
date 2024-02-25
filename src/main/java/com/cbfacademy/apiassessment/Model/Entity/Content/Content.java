package com.cbfacademy.apiassessment.Model.Entity.Content;

import java.util.UUID;

import org.json.simple.JSONObject;

import com.cbfacademy.apiassessment.Model.Entity.ConverteJson;
import com.cbfacademy.apiassessment.Model.Entity.Genre;

import jakarta.persistence.Id;

public class Content implements ConverteJson {

    @Id
    private UUID id;
    private String title;
    private String description;
    private String director;
    private Genre genre;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();

        if (!classIsEmpty()) {
            jsonObject.put("id", id.toString());
            jsonObject.put("title", title);
            jsonObject.put("description", description);
            jsonObject.put("director", director);

            if (genre != null) {
                jsonObject.put("genre", genre.converteToJson());
            }
        }

        return jsonObject;
    }

    @Override
    public void fillFromJson(JSONObject jsonObject) {

        Object object = jsonObject.get("id");
        this.id = UUID.fromString(objectValid(object) ? object.toString() : "");

        object = jsonObject.get("title");
        this.title = objectValid(object) ? object.toString() : null;

        object = jsonObject.get("description");
        this.description = objectValid(object) ? object.toString() : null;

        object = jsonObject.get("director");
        this.director = objectValid(object) ? object.toString() : null;

        this.genre = new Genre();

        object = jsonObject.get("genre");

        if (object != null) {
            JSONObject genreJson = (JSONObject) jsonObject.get("genre");

            Object genreObject = genreJson.get("id");
            this.genre.setId(UUID.fromString(objectValid(genreObject)? genreObject.toString() : ""));

            genreObject = genreJson.get("name");
            this.genre.setName(objectValid(genreObject)? genreObject.toString() : "");
        }
    }

    protected boolean classIsEmpty() {
        return id.toString().isEmpty() &&//
            description.isEmpty() &&//
            director.isEmpty();
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
