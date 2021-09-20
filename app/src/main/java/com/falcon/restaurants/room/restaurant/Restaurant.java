package com.falcon.restaurants.room.restaurant;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(indices = {@Index(value = {"name"},
        unique = true)})

public class Restaurant {

    @PrimaryKey
    @NonNull
    private String id ;
    private String parentId;
    private String name;
    private String imageUrl;
    private String active ;
    private String updatedAt;

    public Restaurant(@NonNull String id, String parentId, String name, String imageUrl, String active, String updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.active = active;
        this.updatedAt = updatedAt;
    }

    @NonNull
    public String getName() {return name;}
    public String getImageUrl() {
        return imageUrl;
    }
    public String getActive() {
        return active;
    }
    public String getId() {
        return id;
    }
    public String getParentId() {
        return parentId;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }

}
