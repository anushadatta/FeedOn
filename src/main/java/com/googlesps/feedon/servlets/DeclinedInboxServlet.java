package com.googlesps.feedon.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.googlesps.feedon.data.Donation;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/inbox-declined")
public class DeclinedInboxServlet extends HttpServlet {

    private Gson gson = new Gson();
    private static final String JSON_CONTENT_TYPE = "application/json";
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Get donationID from the declined donations and set the donation status of the current user to "declined"
     * @param request
     * @param response
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        // Get donationID of the declined donations
        long id = Long.parseLong(request.getParameter("id"));
        Key taskEntityKey = KeyFactory.createKey("Donation", id);

        String email = userService.getCurrentUser().getEmail();
        String userStatus = "status_" + email.replace('@', '_');

        try {
            // When the required donation can found by donationID, set the donation status to "declined"
            Entity donationEntity = datastore.get(taskEntityKey);
            donationEntity.setProperty(userStatus, "declined");
            datastore.put(donationEntity);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display all the declined donations of the currently logged-in charity on the Inbox page
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = userService.getCurrentUser().getEmail();
        String userStatus = "status_" + email.replace('@', '_');

        Filter propertyFilter = new FilterPredicate(userStatus, FilterOperator.EQUAL, "declined");
        Query query = new Query("Donation").setFilter(propertyFilter);

        PreparedQuery results = datastore.prepare(query);

        List<Donation> donations = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String restaurantName = (String) entity.getProperty("restaurantName");
            String location = (String) entity.getProperty("location");
            String category = (String) entity.getProperty("category");
            String pickUpTime = (String) entity.getProperty("pickUpTime");
            String quantity = (String) entity.getProperty("quantity");
            String specialInstructions = (String) entity.getProperty("specialInstructions");
            String imageURL = (String) entity.getProperty("imageURL");
            Date timestamp = (Date) entity.getProperty("timestamp");

            Donation donation = new Donation(id, restaurantName, location, category, pickUpTime, quantity, specialInstructions, imageURL, timestamp);
            donations.add(donation);
        }
        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(gson.toJson(donations));
    }
}
