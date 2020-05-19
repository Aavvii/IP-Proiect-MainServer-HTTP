package com.sarmales.reviewerserver.handler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ErrorHandling {
    public static boolean isValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (
                    JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    public static boolean isJsonEmpty(JSONObject jsonObject,String key) {
        if(jsonObject.has(key)){
            if(jsonObject.get(key).equals("")){
                return true;
            }
        }
        return false;
    }
}
