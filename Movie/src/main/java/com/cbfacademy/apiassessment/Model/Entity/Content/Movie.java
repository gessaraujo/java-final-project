package com.cbfacademy.apiassessment.Model.Entity.Content;

import org.json.simple.JSONObject;

import java.util.UUID;

public class Movie extends Content {
    private String synopsis;
    private int duration;

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void fillFromJson(JSONObject jsonObject) {
        super.fillFromJson(jsonObject);

        Object object = jsonObject.get("synopsis");
        this.synopsis = objectValid(object) ? object.toString() : "";

        object = jsonObject.get("duration");
        this.duration = objectValid(object) ? Integer.parseInt(jsonObject.get("duration").toString()) : 0;
    }

    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = super.converteToJson();

        if (!classIsEmpty()) {
            jsonObject.put("synopsis", synopsis);
            jsonObject.put("duration", duration);
        }

        return jsonObject;
    }

    protected boolean classIsEmpty() {
        return super.classIsEmpty() && //
            synopsis.isEmpty() && duration == 0;
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
