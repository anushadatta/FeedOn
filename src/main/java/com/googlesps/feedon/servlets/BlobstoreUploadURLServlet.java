package com.googlesps.feedon.servlets;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When the fetch() function requests the /blobstore-upload-url URL, the content of the response is
 * the URL that allows a user to upload a file to Blobstore. 
 */
@WebServlet("/blobstore-upload-url")
public class BlobstoreUploadURLServlet extends HttpServlet {

    private static final String JSON_CONTENT_TYPE = "application/json";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        String uploadUrl = blobstoreService.createUploadUrl("/donation-offer");

        response.setContentType(JSON_CONTENT_TYPE);
      response.getWriter().println(uploadUrl);
    }
}
