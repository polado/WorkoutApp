package com.example.workoutapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CategoryModel implements Serializable {
    private String id;
    private String name;
    private String icon;

    public CategoryModel() {
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryID", id);
        map.put("categoryName", name);
        map.put("categoryIcon", icon);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
