package com.googlesps.feedon.servlets;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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

@WebServlet("/donation-match")
public class MatchPageServlet extends HttpServlet {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private Gson gson = new Gson();

    /**
     * Get all entries in the Donation-match datastore and display them in home page
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
        Query query = new Query("DonationMatch");
        PreparedQuery results = DatastoreServiceFactory.getDatastoreService().prepare(query);

        List<DonationMatch> matches = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String restaurantName = (String) entity.getProperty("restaurantName");
            String charityName = (String) entity.getProperty("charityName");
            String location = (String) entity.getProperty("location");
            String category = (String) entity.getProperty("category");
            String pickUpTime = (String) entity.getProperty("pickUpTime");
            String quantity = (String) entity.getProperty("quantity");
            Date timestamp = (Date) entity.getProperty("timestamp");
            String specialInstructions = (String) entity.getProperty("specialInstructions");
            String imageURL = (String) entity.getProperty("imageURL");

            DonationMatch match = new DonationMatch(id, restaurantName, charityName, location, category, pickUpTime, quantity, specialInstructions, imageURL, timestamp);
            matches.add(match);
        }
        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(gson.toJson(matches));
    }
}
