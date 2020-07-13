package main.java.com.google.sps.data;

public final class Donation {
    private final String restaurantName;
    private final long id;
    private final String location;
    private final String category;
    private final String time;
    private final int quantity;
    private final long timestamp;

    public Donation(String restaurantName, long id, String location, String category, String time, int quantity, long timestamp) {
        this.restaurantName = restaurantName;
        this.id = id;
        this.location = location;
        this.category = category;
        this.time = time;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }
}
