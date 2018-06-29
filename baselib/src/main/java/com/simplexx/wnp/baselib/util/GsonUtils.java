package com.simplexx.wnp.baselib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;

public final class GsonUtils {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public static <T> T tryParse(Class<T> classOfT, String json){
        try {
            return parse(classOfT, json);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(Class<T> classOfT, String json) throws JsonSyntaxException{
        return gson.fromJson(json, classOfT);
    }

    public static <T> T tryParse(Type typeOfT, String json){
        try {
            return parse(typeOfT, json);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(Type typeOfT, String json) throws JsonSyntaxException{
        return gson.fromJson(json, typeOfT);
    }

    public static <T> String stringify(T value){
        return gson.toJson(value);
    }

    public static  <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(gson.fromJson(elem, cls));
        }
        return mList;
    }

}