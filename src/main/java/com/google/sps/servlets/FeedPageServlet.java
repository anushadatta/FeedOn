package main.java.com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import main.java.com.google.sps.data.Donation;
import main.java.com.google.sps.data.Selection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedPageServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query querySelection = new Query("Selection").addSort("timestamp", SortDirection.DESCENDING);
        PreparedQuery selectionResults = DatastoreServiceFactory.getDatastoreService().prepare(querySelection);

        List<Selection> selections = new ArrayList<>();
        for (Entity entity : selectionResults.asIterable()) {
            long id = entity.getKey().getId();
            long timestamp = (long) entity.getProperty("timestamp");

            long donationId = (long) entity.getProperty("donationId");
            String restaurantName = (String) entity.getProperty("restaurantName");
            String charityName = (String) entity.getProperty("charityName");
            String location = (String) entity.getProperty("location");
            String time = (String) entity.getProperty("time");
            int quantity = (int) entity.getProperty("quantity");
            String category = (String) entity.getProperty("category");
            int acceptedQuantity = (int) entity.getProperty("acceptedQuantity");

            Selection selection = new Selection(restaurantName, charityName, donationId, id, time, category, location, quantity, acceptedQuantity, timestamp);
            selections.add(selection);
        }

        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(selections));
    }
}