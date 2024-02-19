package com.cbfacademy.apiassessment.Model.Entity;

import org.json.simple.JSONObject;

public interface ConverteJson {
    JSONObject converteToJson();
    void fillFromJson(JSONObject jsonObject);
}
