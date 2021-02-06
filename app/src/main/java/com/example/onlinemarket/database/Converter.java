package com.example.onlinemarket.database;


import androidx.room.Dao;
import androidx.room.TypeConverter;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<String> stringToList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<String> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    @TypeConverter
    public String fromOptionValuesList(List<Object> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<Object> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    @TypeConverter
    public String fromOptionValue(Object optionValue) {
        if (optionValue == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Object>() {
        }.getType();
        String json = gson.toJson(optionValue, type);
        return json;
    }

    @TypeConverter
    public Object toOptionValue(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Object>() {
        }.getType();
        Object productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    @TypeConverter
    public String fromImageItemList(List<Image> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Image>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<Image> toImageItemList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Image>>() {
        }.getType();
        List<Image> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

  /*  @TypeConverter
    public String fromTagItemList(List<TagsItem> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TagsItem>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<TagsItem> toTagItemList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<TagsItem>>() {
        }.getType();
        List<TagsItem> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

*/
    @TypeConverter
    public String fromCategoryItemList(List<Category> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<Category> toCategoryItemList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Category>>() {
        }.getType();
        List<Category> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

    @TypeConverter
    public String fromIntegerRelatedList(List<Integer> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter
    public List<Integer> toIntegerRelatedList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}
