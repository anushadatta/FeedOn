package com.googlesps.feedon.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import javax.annotation.Nullable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

@WebServlet("/donation-offer")
public class DonationOfferServlet extends HttpServlet {

    private static final String JSON_CONTENT_TYPE = "application/json";
    private Gson gson = new Gson();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        // TODO: Write code here to query all donation offers from Datastore "Donation" entity 

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the input from the form.
        String restaurantName = request.getParameter("restaurantName"); 
        String location = request.getParameter("location");
        String category = request.getParameter("category");
        String pickUpTime = request.getParameter("pickUpTime");
        String quantity = request.getParameter("quantity");
        String specialInstructions = request.getParameter("specialInstructions");
        
        // Image URL from Blobstore & current timestamp
        String imageURL = getUploadedFileUrl(request, "image");
        Date timestamp = new Date(); 

        // Create Donation offer entity 
        Entity donationOfferEntity = new Entity("Donation");
        donationOfferEntity.setProperty("restaurantName", restaurantName);
        donationOfferEntity.setProperty("location", location);
        donationOfferEntity.setProperty("category", category);
        donationOfferEntity.setProperty("pickUpTime", pickUpTime);
        donationOfferEntity.setProperty("quantity", quantity);
        donationOfferEntity.setProperty("specialInstructions", specialInstructions);
        donationOfferEntity.setProperty("imageURL", imageURL);
        donationOfferEntity.setProperty("timestamp", timestamp);

        // Upload donation offer to Datastore 
        datastore.put(donationOfferEntity);
        
        response.sendRedirect("/index.html");
    }

    /** Returns a URL that points to the uploaded file, or null if the user didn't upload a file. */
  @Nullable 
  private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("image");

    // User submitted form without selecting a file, so we can't get a URL. (dev server)
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    // Our form only contains a single file input, so get the first index.
    BlobKey blobKey = blobKeys.get(0);

    // User submitted form without selecting a file, so we can't get a URL. (live server)
    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }

    // Use ImagesService to get a URL that points to the uploaded file.
    ImagesService imagesService = ImagesServiceFactory.getImagesService();
    ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
    String url = imagesService.getServingUrl(options);

    // GCS's localhost preview is not actually on localhost, so make the URL relative to the current domain.
    if (url.startsWith("http://localhost:8080/")) {
      String relativeURL = formatURL(url);
      return relativeURL;
    }

    return url;
  }

  private String formatURL(String url) {

      url = url.replace("http://localhost:8080/", "/");

      return url; 
  }

}
