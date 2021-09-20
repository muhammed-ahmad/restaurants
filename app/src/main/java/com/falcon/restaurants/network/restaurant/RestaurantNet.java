package com.falcon.restaurants.network.restaurant;

public class RestaurantNet {

    private String id ;
    private String parentId;
    private String name;
    private String imageUrl;
    private String active ;
    private String updatedAt;

    public RestaurantNet(String id, String parentId, String name, String imageUrl, String active, String updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.active = active;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }
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
