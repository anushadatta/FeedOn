package com.googlesps.feedon.data;

import java.util.Date;

/**
 * Class for donation accepted by charities
 */
public class DonationMatch {
    private final long id;
    private final String restaurantName;
    private final String charityName;
    private final String pickUpTime;
    private final String category;
    private final String location;
    private final String quantity;
    private final String specialInstructions;
    private final String imageURL;
    private final Date timestamp;

    public DonationMatch(long id, String restaurantName, String charityName, String location, String category, String pickUpTime,
                         String quantity, String specialInstructions, String imageURL,  Date timestamp) {
        this.restaurantName = restaurantName;
        this.charityName = charityName;
        this.id = id;
        this.category = category;
        this.location = location;
        this.pickUpTime = pickUpTime;
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
        this.imageURL = imageURL;
        this.timestamp = timestamp;
    }
}
