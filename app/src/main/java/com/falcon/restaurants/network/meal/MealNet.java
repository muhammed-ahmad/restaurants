package com.falcon.restaurants.network.meal;

public class MealNet {

    private String id ;
    private String name;
    private String details ;
    private String imageUrl;
    private String restaurantId;
    private String updatedAt;
    private String active ;
    private String favorite ;


    public MealNet(String id, String name, String details, String imageUrl, String restaurantId, String updatedAt,
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

    public String getId() {
        return id;
    }
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
