package main.java.com.google.sps.data;

public final class Restaurant {
    private final String restaurantName;
    private final long id;
    private final String location;
    private final String category;

    public Restaurant(String restaurantName, long id, String location, String category) {
        this.restaurantName = restaurantName;
        this.id = id;
        this.location = location;
        this.category = category;
    }
}

