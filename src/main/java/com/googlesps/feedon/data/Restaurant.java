package com.googlesps.feedon.data;

/**
 * Class for restaurant users
 */
public final class Restaurant {
    private final long id;
    private final String restaurantName;
    private final String location;
    private final String description;

    public Restaurant(long id, String restaurantName, String location, String description) {
        this.restaurantName = restaurantName;
        this.id = id;
        this.location = location;
        this.description = description;
    }
}

