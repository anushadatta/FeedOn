package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Restaurant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/restaurants")
public class RestaurantsPageServlet extends HttpServlet {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private Gson gson = new Gson();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query queryRestaurant = new Query("Restaurant");
        PreparedQuery restaurantResults = DatastoreServiceFactory.getDatastoreService().prepare(queryRestaurant);

        List<Restaurant> restaurants = new ArrayList<>();
        for (Entity entity : restaurantResults.asIterable()) {
            long id = entity.getKey().getId();
            String name = (String) entity.getProperty("name");
            String location = (String) entity.getProperty("location");
            String description = (String) entity.getProperty("description");

            Restaurant restaurant = new Restaurant(id, name, location, description);
            restaurants.add(restaurant);
        }

        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(gson.toJson(restaurants));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the input from the form.
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String description = request.getParameter("description");

        Entity restaurantEntity = new Entity("Restaurant");
        restaurantEntity.setProperty("name", name);
        restaurantEntity.setProperty("location", location);
        restaurantEntity.setProperty("description", description);

        datastore.put(restaurantEntity);
        response.sendRedirect("/index.html");
    }
}