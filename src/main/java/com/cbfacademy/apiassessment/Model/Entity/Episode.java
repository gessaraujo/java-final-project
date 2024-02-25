package com.cbfacademy.apiassessment.Model.Entity;

import jakarta.persistence.Id;
import org.json.simple.JSONObject;

import java.util.UUID;

public class Episode implements ConverteJson {
    @Id
    private UUID id;
    private String name;
    private String description;
    private int duration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void fillFromJson(JSONObject jsonObject) {
        Object object = jsonObject.get("id");
        this.id = UUID.fromString(objectValid(object) ? object.toString() : "");

        object = jsonObject.get("name");
        this.name = objectValid(object) ? jsonObject.get("duration").toString() : "";

        object = jsonObject.get("description");
        this.description = objectValid(object) ? jsonObject.get("description").toString() : "";

        object = jsonObject.get("duration");
        this.duration = objectValid(object) ? Integer.parseInt(jsonObject.get("duration").toString()) : 0;
    }

    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();

        if (!classIsEmpty()) {
            jsonObject.put("id", id.toString());
            jsonObject.put("name", name);
            jsonObject.put("description", description);
            jsonObject.put("duration", duration);
        }

        return jsonObject;
    }

    protected boolean classIsEmpty() {
        return id.toString().isEmpty() && name.isEmpty() && description.isEmpty() && duration == 0;
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
