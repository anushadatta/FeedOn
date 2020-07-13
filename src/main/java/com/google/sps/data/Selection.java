package main.java.com.google.sps.data;

public class Selection {
    private final String restaurantName;
    private final String charityName;
    private final long donationId;
    private final long id;
    private final String time;
    private final String category;
    private final String location;
    private final int quantity;
    private final int acceptedQuantity;
    private final long timestamp;

    public Selection(String restaurantName, String charityName, long donationId, long id, String time, String category, String location, int quantity, int acceptedQuantity, long timestamp) {
        this.restaurantName = restaurantName;
        this.charityName = charityName;
        this.donationId = donationId;
        this.id = id;
        this.category = category;
        this.location = location;
        this.time = time;
        this.quantity = quantity;
        this.acceptedQuantity = acceptedQuantity;
        this.timestamp = timestamp;
    }
}
