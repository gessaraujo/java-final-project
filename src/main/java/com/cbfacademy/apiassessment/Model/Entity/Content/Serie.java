package com.cbfacademy.apiassessment.Model.Entity.Content;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Serie extends Content{
    private List<UUID> seasonIds;

    public List<UUID> getSeasonIds() {
        return seasonIds;
    }

    public void setSeasonIds(List<UUID> seasonIds) {
        this.seasonIds = seasonIds;
    }

    @Override
    public void fillFromJson(JSONObject jsonObject) {
        super.fillFromJson(jsonObject);

        Object object = jsonObject.get("seasons");

        if (object instanceof JSONArray) {
            this.seasonIds = new ArrayList<>();
            JSONArray jsonSeasons = (JSONArray) object;
            for (Object obj : jsonSeasons) {
                this.seasonIds.add(UUID.fromString(obj.toString()));
            }
        }
    }

    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = super.converteToJson();

        if (!classIsEmpty()) {
            JSONArray jsonArray = new JSONArray();

            if (seasonIds != null) {
                for (UUID season : seasonIds) {
                    jsonArray.add(season.toString());
                }
            }

            jsonObject.put("seasons", jsonArray);
        }

        return jsonObject;
    }

    protected boolean classIsEmpty() {
        return super.classIsEmpty() && seasonIds.isEmpty();
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
