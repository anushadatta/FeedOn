package com.googlesps.feedon.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/add-match")
public class AddDonationMatchServlet extends HttpServlet {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private final String EMPTY_STRING = "";

    /**
     * Add matched donations into Donation-match datastore
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{

        // Get donationID from the accepted donations and retrieve all other attributes of this donation
        long id = Long.parseLong(request.getParameter("id"));
        Key taskEntityKey = KeyFactory.createKey("Donation", id);

        String restaurantName = null;
        String location = null;
        String pickUpTime = null;
        String category = null;
        String quantity = null;
        try {
            // when the required donation is found by donationID
            Entity donationEntity = datastore.get(taskEntityKey);
            restaurantName = (String) donationEntity.getProperty("restaurantName");
            location = (String) donationEntity.getProperty("location");
            pickUpTime = (String) donationEntity.getProperty("pickUpTime");
            category = (String) donationEntity.getProperty("category");
            quantity = (String) donationEntity.getProperty("quantity");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        // Create Donation-match entity and update it to datastore
        Entity matchEntity = new Entity("DonationMatch");

        // The type of the currently logined user should be charity. We get its user name by a helper function
        String charityName = getUserName();
        matchEntity.setProperty("charityName", charityName);
        Date timestamp = new Date();
        matchEntity.setProperty("timestamp", timestamp);
        matchEntity.setProperty("restaurantName", restaurantName);
        matchEntity.setProperty("location", location);
        matchEntity.setProperty("category", category);
        matchEntity.setProperty("pickUpTime", pickUpTime);
        matchEntity.setProperty("quantity", quantity);
        datastore.put(matchEntity);
    }

    /**
     Displays the user's name in the servlet as a text. If the user is not
     signed in, it will display an empty string. Also, if the user is signed in
     but is yet to register the name (that is the name is not within the
     datastore), return an empty string as well
     */
    private String getUserName() {
        UserService userService = UserServiceFactory.getUserService();
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
