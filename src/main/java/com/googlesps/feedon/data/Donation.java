package com.googlesps.feedon.data;

import java.util.Date;
import javax.annotation.Nullable;

/**
 * Class for donation offer made by restaurant
 */
public final class Donation {
    private final long id;
    private final String restaurantName;
    private final String location;
    private final String category;
    private final String pickUpTime;
    private final String quantity;
    private final String specialInstructions;
    private final String imageURL; 
    private final Date timestamp;

    public Donation(long id, 
        String restaurantName, 
        String location, 
        String category, 
        String pickUpTime, 
        String quantity,
        String specialInstructions,
        String imageURL, 
        Date timestamp) {
            this.id = id;
            this.restaurantName = restaurantName;
            this.location = location;
            this.category = category;
            this.pickUpTime = pickUpTime;
            this.quantity = quantity;
            this.specialInstructions = specialInstructions;
            this.imageURL = imageURL;
            this.timestamp = timestamp;
    }
}
