package main.java.com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import main.java.com.google.sps.data.Selection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/inbox")
public class InboxServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //when there is a new donation
        Query queryDonation = new Query("Donation").addSort("timestamp", Query.SortDirection.DESCENDING);
        PreparedQuery results = DatastoreServiceFactory.getDatastoreService().prepare(queryDonation);

        long donationId = 0;
        String restaurantName = null;
        String location = null;
        String time = null;
        String category = null;
        int quantity = 0;

        for (Entity entity : results.asIterable()) {
            donationId = entity.getKey().getId();
            restaurantName = (String) entity.getProperty("restaurantName");
            location = (String) entity.getProperty("location");
            time = (String) entity.getProperty("time");
            quantity = (int) entity.getProperty("quantity");
            category = (String) entity.getProperty("category");
            break;
        }

        //input
        boolean accepted = true;
        int acceptedQuantity = 50;
        String charityName = "Beautiful Mind Charity";

        if (accepted && acceptedQuantity <= quantity) {
            Entity selectionEntity = new Entity("Selection");
            selectionEntity.setProperty("charityName", charityName);
            selectionEntity.setProperty("donationId", donationId);
            selectionEntity.setProperty("acceptedQuantity", acceptedQuantity);
            long timestamp = System.currentTimeMillis();
            selectionEntity.setProperty("timestamp", timestamp);

            selectionEntity.setProperty("restaurantName", restaurantName);
            selectionEntity.setProperty("location", location);
            selectionEntity.setProperty("category", category);
            selectionEntity.setProperty("time", time);
            selectionEntity.setProperty("quantity", quantity);
            DatastoreService selectionDatastore = DatastoreServiceFactory.getDatastoreService();
            selectionDatastore.put(selectionEntity);
        }
    }
}
