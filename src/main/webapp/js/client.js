const dataElement = document.querySelector(".data");

// Load the number of unread donations in the inbox as badge
fetch('/inbox-unread').then(response => response.json()).then((donations) => {
        document.getElementById("numOfUnread").innerText = donations.length.toString();
    });

// Display the information of all matched donations after clicking on the "Donation Match" tab
function listAllMatches() {
    getMatch().then((matches) => {
        dataElement.innerHTML = "";
        matches.forEach((match) => {
            const div = document.createElement("div");
            div.className = "card";

            const header = document.createElement("h3");
            header.textContent = `${match.restaurantName} & ${match.charityName}`;

            const contents = document.createElement("p");
            contents.textContent = `${match.quantity} meals were delivered to people in need by the successful collaboration between ${match.restaurantName} and ${match.charityName}.`;

            const category = document.createElement("p");
            category.textContent = `Food Category : ${match.category}`;

            const time = document.createElement("p");
            time.textContent = `Pick-up Time : ${match.pickUpTime}`;

            const address = document.createElement("p");
            address.textContent = `Location : ${match.location}`;

            const instructions = document.createElement("p");
            instructions.textContent = `Special Instructions : ${match.specialInstructions}`;

            div.appendChild(header);
            div.appendChild(contents);
            div.appendChild(category);
            div.appendChild(time);
            div.appendChild(address);
            div.appendChild(instructions);

            // If an image is uploaded in the donation form, display it
            if (match.imageURL != "N.A."){
                const imageName = document.createElement("p");
                imageName.textContent = `Image : `;
                var img = document.createElement("img");
                img.src = match.imageURL;
                div.appendChild(imageName);
                div.appendChild(img);
            }
            dataElement.appendChild(div);
        });
      });
    }

function listAllCharities() {
  getCharities().then((charities) => {
    dataElement.innerHTML = "";
    charities.forEach((charity) => {
      const div = document.createElement("div");

      const header = document.createElement("h3");
      header.textContent = charity.CharityName;

      const description = document.createElement("p");
      description.textContent = charity.description;

      const location = document.createElement("p");
      location.textContent = charity.location;

      div.appendChild(header);
      div.appendChild(description);
      div.appendChild(location);

      dataElement.appendChild(div);
    });
    loadingElement.style.display = "none";
  });
}

function listAllRestaurants() {
  getRestaurants().then((restaurants) => {
    dataElement.innerHTML = "";
    restaurants.forEach((restaurant) => {
      const div = document.createElement("div");

      const header = document.createElement("h3");
      header.textContent = restaurant.restaurantName;

      const description = document.createElement("p");
      description.textContent = restaurant.description;

      const location = document.createElement("p");
      location.textContent = restaurant.location;

      div.appendChild(header);
      div.appendChild(description);
      div.appendChild(location);

      dataElement.appendChild(div);
    });
    loadingElement.style.display = "none";
  });
}

// Get all entries from Donation-match datastore
function getMatch() {
    return fetch("/donation-match")
        .then((response) => response.json())
        .then((entries) => {
        return entries;
        });
}

function getCharities() {
  return fetch("/charities")
    .then((response) => response.json())
    .then((entries) => {
      return entries;
    });
}

function getRestaurants() {
  return fetch("/restaurants")
    .then((response) => response.json())
    .then((entries) => {
      return entries;
    });
}

// DONATION FORM 

// Fetches Blobstore URL for form action to faciliate upload of images 
function getBlobstoreURL() {
    fetch('/blobstore-upload-url')
        .then((response) => {
            return response.text();
        })
        .then((imageUploadUrl) => {
            const commentForm = document.getElementById('donation-form');
            commentForm.action = imageUploadUrl;
        });
}

// Display the information of all the unread donations in the Donation datastore
function listUnreadInbox() {
    getDonations("/inbox-unread").then((donations) => {
        dataElement.innerHTML = "";
        donations.forEach((donation) => {
            const div = document.createElement("div");
            div.className = "card";

            const header = document.createElement("h3");
            header.textContent = `Restaurant Name : ${donation.restaurantName}`;

            const category = document.createElement("p");
            category.textContent = `Food Category : ${donation.category}`;

            const quantity = document.createElement("p");
            quantity.textContent = `Quantity : ${donation.quantity}`;

            const time = document.createElement("p");
            time.textContent = `Pick-up Time : ${donation.pickUpTime}`;

            const address = document.createElement("p");
            address.textContent = `Location : ${donation.location}`;

            const instructions = document.createElement("p");
            instructions.textContent = `Special Instructions : ${donation.specialInstructions}`;

            // After clicking on Accept button, the corresponding donation will be deleted from Donation datastore
            // but be added into Donation-match datastore.
            var acceptButtonElement = document.createElement("button");
            acceptButtonElement.innerHTML = "ACCEPT";
            acceptButtonElement.type = "submit";
            acceptButtonElement.className = "accept-btn";
            acceptButtonElement.addEventListener('click', () => {
                acceptDonation(donation);
                div.remove();
            });

            // Decline button
            var declineButtonElement = document.createElement("button");
            declineButtonElement.innerHTML = "DECLINE";
            declineButtonElement.type = "button";
            declineButtonElement.className = "decline-btn";
            declineButtonElement.addEventListener('click', () => {
                declineDonation(donation);
                div.remove();
            });

            div.appendChild(header);
            div.appendChild(category);
            div.appendChild(quantity);
            div.appendChild(time);
            div.appendChild(address);
            div.appendChild(instructions);

            // If an image is uploaded in the donation form, display it
            if (donation.imageURL != "N.A."){
                const imageName = document.createElement("p");
                imageName.textContent = `Image : `;
                var img = document.createElement("img");
                img.src = donation.imageURL;
                div.appendChild(imageName);
                div.appendChild(img);
            }
            div.appendChild(acceptButtonElement);
            div.appendChild(declineButtonElement);

            dataElement.appendChild(div);
        });
    });
}

// Display the information of all the accepted donations in the Donation-match datastore
function listAcceptedInbox(){
    getDonations("/inbox-accepted").then((donations) => {
        dataElement.innerHTML = "";
        donations.forEach((donation) => {
            const div = document.createElement("div");
            div.className = "card";

            const header = document.createElement("h3");
            header.textContent = `Restaurant Name : ${donation.restaurantName}`;

            const category = document.createElement("p");
            category.textContent = `Food Category : ${donation.category}`;

            const quantity = document.createElement("p");
            quantity.textContent = `Quantity : ${donation.quantity}`;

            const time = document.createElement("p");
            time.textContent = `Pick-up Time : ${donation.pickUpTime}`;

            const address = document.createElement("p");
            address.textContent = `Location : ${donation.location}`;

            const instructions = document.createElement("p");
            instructions.textContent = `Special Instructions : ${donation.specialInstructions}`;

            div.appendChild(header);
            div.appendChild(category);
            div.appendChild(quantity);
            div.appendChild(time);
            div.appendChild(address);
            div.appendChild(instructions);

            // If an image is uploaded in the donation form, display it
            if (donation.imageURL != "N.A."){
                const imageName = document.createElement("p");
                imageName.textContent = `Image : `;
                var img = document.createElement("img");
                img.src = donation.imageURL;
                div.appendChild(imageName);
                div.appendChild(img);
            }
            dataElement.appendChild(div);
        });
    });
}

// Display the information of all the declined donations in the Donation datastore
function listDeclinedInbox(){
    getDonations("/inbox-declined").then((donations) => {
        dataElement.innerHTML = "";
        donations.forEach((donation) => {
            const div = document.createElement("div");
            div.className = "card";

            const header = document.createElement("h3");
            header.textContent = `Restaurant Name : ${donation.restaurantName}`;

            const category = document.createElement("p");
            category.textContent = `Food Category : ${donation.category}`;

            const quantity = document.createElement("p");
            quantity.textContent = `Quantity : ${donation.quantity}`;

            const time = document.createElement("p");
            time.textContent = `Pick-up Time : ${donation.pickUpTime}`;

            const address = document.createElement("p");
            address.textContent = `Location : ${donation.location}`;

            const instructions = document.createElement("p");
            instructions.textContent = `Special Instructions : ${donation.specialInstructions}`;

            div.appendChild(header);
            div.appendChild(category);
            div.appendChild(quantity);
            div.appendChild(time);
            div.appendChild(address);
            div.appendChild(instructions);

            // If an image is uploaded in the donation form, display it
            if (donation.imageURL != "N.A."){
                const imageName = document.createElement("p");
                imageName.textContent = `Image : `;
                var img = document.createElement("img");
                img.src = donation.imageURL;
                div.appendChild(imageName);
                div.appendChild(img);
            }
            dataElement.appendChild(div);
        });
    });
}

// Get entries from Donation datastore according to status input
function getDonations(status) {
    return fetch(status)
        .then((response) => response.json())
        .then((entries) => {
        return entries;
        });
}

function declineDonation(donation) {
    const params = new URLSearchParams();
    params.append('id', donation.id);
    
    fetch('/inbox-declined', {method: 'POST', body: params});
}

function acceptDonation(donation) {
    const params = new URLSearchParams();
    params.append('id', donation.id);
    
    fetch('/inbox-accepted', {method: 'POST', body: params});
}

// Add with user's information to signin.html
// userType is either "charity" or "restaurant"
function addUserInfo(userEmail, userType, logoutLink) {
    var para = document.createElement("p");
    var message = "You are already logged in as " + userEmail + " of type " + userType;
    
    para.appendChild(document.createTextNode(message));
    document.body.appendChild(para);
    
    // create the link to return to main page (that is index.html)
    mainPageUrl = "index.html";
    var returnToMainPageElement = createParaWithLink(
    mainPageUrl, "click ", "here", " to return to main page");
    document.body.appendChild(returnToMainPageElement);
    
    // create the link to logout
    var logoutElement = createParaWithLink(logoutLink, "Logout ", "here", "");
    document.body.appendChild(logoutElement);
}

function createParaWithLink(link, startText, linkText, endText) {
    var a = document.createElement('a');
    a.href = link;
    a.title = link;
    a.appendChild(document.createTextNode(linkText));
    
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(startText));
    p.appendChild(a);
    p.appendChild(document.createTextNode(endText));
    
    return p;
}

//Populate signin.html with sign in related information
function handleSignInPage() {
    fetch('/login').then(response => response.json()).then((loginInfo) => {
        
        if (loginInfo.length == 1) { // means user is not logged in
            // loginInfo's first element stores the login link
            var loginLink = loginInfo[0];
            // redirect to Google's login page
            window.location.href = loginLink;
        } else if (loginInfo.length == 2) {
            // user has yet to register
            // direct user to a new page asking them to choose between charity and restaurant
            var registerLink = "register.html";
            window.location.href = registerLink;
        } else {
            var userEmail = loginInfo[0];
            var logoutLink = loginInfo[1];
            var userType = loginInfo[2];
            
            addUserInfo(userEmail, userType, logoutLink);
        }
    })
}

function getRegister() {
    fetch('/login').then(response => response.json()).then((loginInfo) => {
        
        if (loginInfo.length == 1) { // means user is not logged in
            var loginLink = loginInfo[0];
            window.location.href = loginLink;
        } else if (loginInfo.length == 2) {
            var userEmail = loginInfo[0];
            var userInfo = document.getElementById("user-info");
            var message = "You have registered as " + userEmail;
            userInfo.appendChild(document.createTextNode(message));
        } else { // three element means user already registered
            var signInLink = "signin.html";
            window.location.href = signInLink;
        }
    })
}

function removeInboxButton() {
    var inboxButton = document.getElementById("inbox-button");
    inboxButton.parentNode.removeChild(inboxButton);
}

function removeDonateButton() {
    var donateButton = document.getElementById("donation-button");
    donateButton.parentNode.removeChild(donateButton);
}

function removeSignInButton() {
    var signInButton = document.getElementById("sign-in-button");
    signInButton.parentNode.removeChild(signInButton);
}

function controlButton() {
    // remove sign in and register button if user is already signin;
    fetch('/login').then(response => response.json()).then((loginInfo) => {
        // loginInfo has different length base on user's state
        // it has length of 1 if user is not logged in, 2 if user is logged
        // in but not registered, and 3 if user is logged in and registered
        var loggedInAndRegistered = 3;
        if (loginInfo.length != loggedInAndRegistered) {
            // user is not sign in
            removeDonateButton();
            removeInboxButton();
            return;
        }
        
        var userEmail = loginInfo[0];
        var registerButton = document.getElementById("register-button");
        registerButton.innerHTML = userEmail;
        registerButton.href = "signin.html";
        
        // can i change the inner href to point to signin.html?
        removeSignInButton();

        if (loginInfo.length == loggedInAndRegistered) {
        // the user-type is known
            const restaurantType = "restaurant";
            const charityType = "charity";
            if (loginInfo[2] == restaurantType) {
                removeInboxButton();
            } else if (loginInfo[2] == charityType) {
                removeDonateButton();
            }
        }
    })
}
