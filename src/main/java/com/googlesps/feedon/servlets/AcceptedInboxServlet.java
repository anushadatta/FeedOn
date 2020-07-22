package com.googlesps.feedon.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.googlesps.feedon.data.DonationMatch;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/inbox-accepted")
public class AcceptedInboxServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    UserService userService = UserServiceFactory.getUserService();
    private static final String JSON_CONTENT_TYPE = "application/json";
    private final String EMPTY_STRING = "";
    private Gson gson = new Gson();

    /**
     * Add matched donations into Donation-match datastore and delete it from Donation datastore
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        // Get ID of the accepted donations and retrieve all other attributes of this donation
        long id = Long.parseLong(request.getParameter("id"));
        Key taskEntityKey = KeyFactory.createKey("Donation", id);

        String restaurantName = null;
        String location = null;
        String pickUpTime = null;
        String category = null;
        String quantity = null;
        String specialInstructions = null;
        String imageURL = null;
        try {
            // when the required donation can found by donationID
            Entity donationEntity = datastore.get(taskEntityKey);
            restaurantName = (String) donationEntity.getProperty("restaurantName");
            location = (String) donationEntity.getProperty("location");
            pickUpTime = (String) donationEntity.getProperty("pickUpTime");
            category = (String) donationEntity.getProperty("category");
            quantity = (String) donationEntity.getProperty("quantity");
            specialInstructions = (String) donationEntity.getProperty("specialInstructions");
            imageURL = (String) donationEntity.getProperty("imageURL");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        // Create Donation-match entity and update it to datastore
        Entity matchEntity = new Entity("DonationMatch");

        // The type of the currently logged-in user should be charity. We get its user name by a helper function
        String charityName = getUserName();
        matchEntity.setProperty("charityName", charityName);
        Date timestamp = new Date();
        matchEntity.setProperty("timestamp", timestamp);
        matchEntity.setProperty("restaurantName", restaurantName);
        matchEntity.setProperty("location", location);
        matchEntity.setProperty("category", category);
        matchEntity.setProperty("pickUpTime", pickUpTime);
        matchEntity.setProperty("quantity", quantity);
        matchEntity.setProperty("specialInstructions", specialInstructions);
        matchEntity.setProperty("imageURL", imageURL);
        datastore.put(matchEntity);
        datastore.delete(taskEntityKey);
    }

    /**
     * Display the accepted donations of the currently logged-in user by filtering in the Donation-match datastore
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Filter in the Donation-match datastore to get the current charity's name
        String charityName = getUserName();
        Query.Filter propertyFilter = new Query.FilterPredicate("charityName", Query.FilterOperator.EQUAL, charityName);
        Query query = new Query("DonationMatch").setFilter(propertyFilter);
        PreparedQuery results = datastore.prepare(query);

        List<DonationMatch> matches = new ArrayList<>();
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

            DonationMatch match = new DonationMatch(id, restaurantName, charityName, location, category, pickUpTime, quantity, specialInstructions, imageURL, timestamp);
            matches.add(match);
        }
        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(gson.toJson(matches));
    }

    /**
     * Displays the user's name in the servlet as a text.
     * If the user is not signed in, it will display an empty string.
     * Also, if the user is signed in but is yet to register the name
     * (that is the name is not within the datastore), return an empty string as well
     * @return The name of the current logged-in user
     */
    private String getUserName() {
        if (!userService.isUserLoggedIn()) {
            // user is not logged in, just return an empty string as a respond
            return EMPTY_STRING;
        }

        // Find users by their emails
        String email = userService.getCurrentUser().getEmail();
        Query.Filter propertyFilter = new Query.FilterPredicate(
                "email-address", Query.FilterOperator.EQUAL, email);
        Query query = new Query("user").setFilter(propertyFilter);
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity: results.asIterable()) {
            String name = (String) entity.getProperty("name");
            return name;
        }
        return EMPTY_STRING;
    }
}
