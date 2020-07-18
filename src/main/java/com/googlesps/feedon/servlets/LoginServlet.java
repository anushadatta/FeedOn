package com.googlesps.feedon.servlets;

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
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String RESPONSE_CONTENT_TYPE = "text/html";
    private Gson gson = new Gson();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * response will contain a json with different number of elements
     * depending on the user's state. If the user is
     * 1) Not logged in -
     *    - Return one element, containing the url for logging in using google's
     *      api
     * 2) Logged in but not registered -
     *    - Return two elements. The first element contains the user's email
     *      address. Second element contains the url for logging out
     * 3) Logged in and registered -
     *    - Return three elements
     *    - First element is the user's email address
     *    - Second element is the url for logging out
     *    - Third element is the user's type, either a "charity" or
     *    - "restaurant" string
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        ArrayList<String> result = new ArrayList<String>();
        UserService userService = UserServiceFactory.getUserService();
        if (userService.isUserLoggedIn()) {
            String userEmail = userService.getCurrentUser().getEmail();
            //check if this user has registered
            String urlToRedirectToAfterUserLogsOut = "/index.html";
            String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
            result.add(userEmail);
            result.add(logoutUrl);
            if (hasRegistered(userEmail)) {
                // return third element if user already registered
                String userType = getUserType(userEmail);
                result.add(userType);
            }
        } else { // not yet signed in
            String urlToRedirectToAfterUserLogsIn = "/signin.html";
            String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
            result.add(loginUrl);
        }
        String json = gson.toJson(result);
        response.getWriter().println(json);
    }

    /**
     * Used by user's registration form. Refer to register.html
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name-input");
        String userType = request.getParameter("user-type");
        String description = request.getParameter("description-input");
        String location = request.getParameter("location-input");
        UserService userService = UserServiceFactory.getUserService();
        String userEmail = userService.getCurrentUser().getEmail();

        Entity userEntity = new Entity("user");
        userEntity.setProperty("email-address", userEmail);
        userEntity.setProperty("name", name);
        userEntity.setProperty("user-type", userType);

        // When user is a charity, add user's email address as one property in Donation datastore
        // userStatus is used to store users' status of all donations - unread, accepted or declined
        if (userType.equals("charity")) {
            Query query = new Query("Donation");
            PreparedQuery results = datastore.prepare(query);
            for (Entity entity : results.asIterable()) {
                String userStatus = "status_" + userEmail.replace('@', '_');
                entity.setProperty(userStatus, "unread");
                datastore.put(entity);
            }
        }
        userEntity.setProperty("location", location);
        userEntity.setProperty("description", description);
        datastore.put(userEntity);
        response.sendRedirect("index.html");
    }

    /**
     * Returns an empty string is the user is not registered, that is not
     * found in the datastore. Otherwise returns either type stored in the
     * datastore, which is either "restaurant" or "charity"
     */
    private String getUserType(String email) {
        Filter propertyFilter = new FilterPredicate("email-address", FilterOperator.EQUAL, email);
        Query query = new Query("user").setFilter(propertyFilter);
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity: results.asIterable()) {
            String userType = (String) entity.getProperty("user-type");
            return userType;
        }
        // this email address is not found in the database
        return ""; // return empty string if user is not found
    }

    /**
     * Return whether this email address is already registered, i.e.
     * in the datastore with "User" entity.
     */
    private boolean hasRegistered(String email) {
        if (getUserType(email).equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
