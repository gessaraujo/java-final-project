package com.cbfacademy.apiassessment.Model.Entity;

import jakarta.persistence.Id;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Season implements ConverteJson {
    @Id
    private UUID id;
    private List<UUID> episodeIds;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getEpisodeIds() {
        return episodeIds;
    }

    public void setEpisodeIds(List<UUID> episodeIds) {
        this.episodeIds = episodeIds;
    }

    public void fillFromJson(JSONObject jsonObject) {
        Object object = jsonObject.get("id");
        this.id = UUID.fromString(objectValid(object) ? object.toString() : "");

        object = jsonObject.get("episodes");

        if (object instanceof JSONArray) {
            this.episodeIds = new ArrayList<>();
            JSONArray jsonEpisodes = (JSONArray) object;
            for (Object obj : jsonEpisodes) {
                this.episodeIds.add(UUID.fromString(obj.toString()));
            }
        }
    }

    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();

        if (!classIsEmpty()) {
            jsonObject.put("id", id.toString());

            if (episodeIds == null)
                return jsonObject;

            JSONArray jsonArray = new JSONArray();

            if (episodeIds != null) {
                for (UUID episode : episodeIds) {
                    jsonArray.add(episode.toString());
                }
            }

            jsonObject.put("episodes", jsonArray);
        }

        return jsonObject;
    }

    protected boolean classIsEmpty() {
        return id.toString().isEmpty() && episodeIds.isEmpty();
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
