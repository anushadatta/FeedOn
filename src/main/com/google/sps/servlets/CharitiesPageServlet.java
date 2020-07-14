package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Charity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/charities")
public class CharitiesPageServlet extends HttpServlet {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private Gson gson = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Query queryCharity = new Query("Charity");
        PreparedQuery charityResults = DatastoreServiceFactory.getDatastoreService().prepare(queryCharity);

        List<Charity> charities = new ArrayList<>();
        for (Entity entity : charityResults.asIterable()) {
            long id = entity.getKey().getId();
            String name = (String) entity.getProperty("name");
            String location = (String) entity.getProperty("location");
            String description = (String) entity.getProperty("description");

            Charity charity = new Charity(id, name, location, description);
            charities.add(charity);
        }

        response.setContentType(JSON_CONTENT_TYPE);
        response.getWriter().println(gson.toJson(charities));
    }
}