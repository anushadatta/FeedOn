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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println("get method called");
    response.setContentType("text/html");
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
      //somewhere over here need to know if this is for charity / restaurant
      // how to know?
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      result.add(loginUrl);
    }
    String json = new Gson().toJson(result);
    response.getWriter().println(json);
  }


  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println("post method called");
    String name = request.getParameter("name-input");
    String userType = request.getParameter("user-type");
    String description = request.getParameter("description-input");
    String locationAddress = request.getParameter("address-input");
    UserService userService = UserServiceFactory.getUserService();
    String userEmail = userService.getCurrentUser().getEmail();

    Entity userEntity = new Entity("user");
    userEntity.setProperty("email-address", userEmail);
    userEntity.setProperty("name", name);
    userEntity.setProperty("user-type", userType);
    userEntity.setProperty("location address", locationAddress);
    userEntity.setProperty("description", description);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(userEntity);
    response.sendRedirect("index.html");
  }

  /**
   * TODO: Quick hack method, improve later on
   */
  private String getUserType(String email) {
    Filter propertyFilter =
            new FilterPredicate("email-address", FilterOperator.EQUAL, email);
    Query query = new Query("user").setFilter(propertyFilter);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity: results.asIterable()) {
      String userType = (String) entity.getProperty("user-type");
      System.out.println("userType of " + email + " = " + userType);
      return userType;
    }
    // this email address is not found in the database
    System.out.println("userType of " + email + " not found");
    return "";
  }

  /**
   * TODO: quick hack method, improve later on
   */
  private boolean hasRegistered(String email) {
    if (getUserType(email).equals("")) {
      return false;
    } else {
      return true;
    }
  }
}