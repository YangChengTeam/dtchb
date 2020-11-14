package com.lq.lianjibusiness.base_libary.utils;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class IntegerDefault0Adapter implements JsonSerializer<JsonObject>, JsonDeserializer<JsonObject> {
//    @Override
//    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        try{
//            Log.d("ccc", "---0-----deserialize: ");
//            if(json.getAsString().equals("") || json.getAsString().equals("null")) {
//                Log.d("ccc", "---1-----deserialize: ");
//                return "{}";
//            }
//        }catch (Exception ignore) {
//
//        }
//        try{
//            return json.getAsString();
//        }catch (NumberFormatException e) {
//            throw new JsonSyntaxException(e);
//        }
//    }

    @Override
    public JsonObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try{
            if(json.getAsString().equals("") || json.getAsString().equals("null")) {
                return new JsonObject();
            }
        }catch (Exception ignore) {

        }
        try{
            return json.getAsJsonObject();
        }catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(JsonObject src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
