package com.falcon.restaurants.room.meal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"},
        unique = true)})

public class Meal {

    @PrimaryKey
    @NonNull
    private String id ;

    @NonNull
    private String name;

    private String details ;
    private String imageUrl;
    private String restaurantId;
    private String updatedAt;
    private String active ;
    private String favorite ;

    public Meal(@NonNull String id, @NonNull String name, String details, String imageUrl, String restaurantId, String updatedAt,
                String active, String favorite) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.imageUrl = imageUrl;
        this.restaurantId = restaurantId;
        this.updatedAt = updatedAt;
        this.active = active;
        this.favorite = favorite;

    }

    @NonNull
    public String getId() {
        return id;
    }
    @NonNull
    public String getName() {
        return name;
    }
    public String getDetails() {
        return details;
    }
    public String getRestaurantId() {
        return restaurantId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public String getActive() {
        return active;
    }
    public String getFavorite() {
        return favorite;
    }

}
