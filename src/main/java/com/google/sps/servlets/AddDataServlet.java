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


@WebServlet("/new-data")
public class AddDataServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // donation input sample
        for (int i = 1; i < 4; i++) {
            String restaurantName = "McDonald's JEM";
            String location = "50 Jurong Gateway Rd, #01-63, #02-55, Singapore 608549";
            String category = "Fast Food";
            String time = "18:30-22:30";
            int quantity = 100 * 3;
            long timestamp = System.currentTimeMillis();
            Entity donationEntity = new Entity("Donation");
            donationEntity.setProperty("restaurantName", restaurantName);
            donationEntity.setProperty("location", location);
            donationEntity.setProperty("category", category);
            donationEntity.setProperty("time", time);
            donationEntity.setProperty("quantity", quantity);
            donationEntity.setProperty("timestamp", timestamp);
            DatastoreService donationDatastore = DatastoreServiceFactory.getDatastoreService();
            donationDatastore.put(donationEntity);
        }
    }
}