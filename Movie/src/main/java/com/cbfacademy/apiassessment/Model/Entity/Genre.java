package com.cbfacademy.apiassessment.Model.Entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.json.simple.JSONObject;

@Entity
public class Genre implements ConverteJson {

    @Id
    private UUID id;
    private String name;

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

    @Override
    public JSONObject converteToJson() {
        JSONObject jsonObject = new JSONObject();

        if (!classIsEmpty()) {
            jsonObject.put("id", id.toString());
            jsonObject.put("name", name);
        }

        return jsonObject;
    }

    @Override
    public void fillFromJson(JSONObject jsonObject) {
        Object object = jsonObject.get("id");
        this.id = UUID.fromString(objectValid(object) ? object.toString() : "");

        object = jsonObject.get("name");
        this.name = objectValid(object) ? object.toString() : "";
    }

    private boolean classIsEmpty() {
        return id.toString().isEmpty() && name.isEmpty();
    }

    private boolean objectValid(Object object) {
        return object != null;
    }
}
