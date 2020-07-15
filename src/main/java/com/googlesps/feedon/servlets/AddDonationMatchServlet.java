package com.googlesps.feedon.servlets;

import com.google.appengine.api.datastore.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/add-match")
public class AddDonationMatchServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        long id = Long.parseLong(request.getParameter("id"));

        Key taskEntityKey = KeyFactory.createKey("Donation", id);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String restaurantName = null;
        String location = null;
        String pickUpTime = null;
        String category = null;
        String quantity = null;
        try {
            Entity donationEntity = datastore.get(taskEntityKey);
            restaurantName = (String) donationEntity.getProperty("restaurantName");
            location = (String) donationEntity.getProperty("location");
            pickUpTime = (String) donationEntity.getProperty("pickUpTime");
            category = (String) donationEntity.getProperty("category");
            quantity = (String) donationEntity.getProperty("quantity");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        Entity matchEntity = new Entity("DonationMatch");

        // TODO: feedEntity.setProperty("charityName", charityName);
        Date timestamp = new Date();
        matchEntity.setProperty("timestamp", timestamp);
        matchEntity.setProperty("restaurantName", restaurantName);
        matchEntity.setProperty("location", location);
        matchEntity.setProperty("category", category);
        matchEntity.setProperty("pickUpTime", pickUpTime);
        matchEntity.setProperty("quantity", quantity);
        datastore.put(matchEntity);
    }
}
