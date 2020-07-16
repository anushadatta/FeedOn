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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-name")
public class UserName extends HttpServlet {
    private final String EMPTY_STRING = "";
    /**
    Displays the user's name in the servlet as a text. If the user is not 
    sign in, it will display an empty string. Also, if the user is signed in
    but is yet to register the name (that is the name is not within the 
    datastore), return an empty string as well
     */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("text/html");
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
        // user is not logged in, just return an empty string as a respond
        response.getWriter().println(EMPTY_STRING);
    }
    // from this point onwards, user is already signed in
    String userEmail = userService.getCurrentUser().getEmail();
    String name = getUserName(userEmail);
    response.getWriter().println(name);
  }

  /**
  Get the user's name base on email address
  Returns empty string if user is not registered yet, that is the email
  address is not found in the datastore. Otherwise, return the user's name
   */
  private String getUserName(String email) {
    Filter propertyFilter = new FilterPredicate(
      "email-address", FilterOperator.EQUAL, email);
    Query query = new Query("user").setFilter(propertyFilter);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity: results.asIterable()) {
      String name = (String) entity.getProperty("name");
      return name;
    }
    return EMPTY_STRING;
  }
}