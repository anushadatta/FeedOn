// const loadingElement = document.querySelector(".loading");
const dataElement = document.querySelector(".data");

// loadingElement.style.display = "none";

function listAllFeeds() {
    getFeeds().then((feeds) => {
        dataElement.innerHTML = "";
        feeds.forEach((feed) => {
            const div = document.createElement("div");

            const header = document.createElement("h3");
            header.textContent = `${feed.restaurant.name} & ${feed.charity.name}`;

            const contents = document.createElement("p");
            contents.textContent = `${feed.amount} were delivered to people in need by the successful collaboration between ${feed.restaurant.name} and ${feed.charity.name}.`;

            div.appendChild(header);
            div.appendChild(contents);

            dataElement.appendChild(div);
        });
        loadingElement.style.display = "none";
    });
}

function listAllCharities() {
    getCharities().then((charities) => {
        dataElement.innerHTML = "";
        charities.forEach((charity) => {
            const div = document.createElement("div");

            const header = document.createElement("h3");
            header.textContent = charity.name;

            const contents = document.createElement("p");
            contents.textContent = charity.description;

            const address = document.createElement("p");
            address.textContent = charity.address;

            div.appendChild(header);
            div.appendChild(contents);
            div.appendChild(address);

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
            header.textContent = restaurant.name;

            const contents = document.createElement("p");
            contents.textContent = restaurant.description;

            const address = document.createElement("p");
            address.textContent = restaurant.address;

            div.appendChild(header);
            div.appendChild(contents);
            div.appendChild(address);

            dataElement.appendChild(div);
        });
        loadingElement.style.display = "none";
    });
}

/*
Mock API calls
*/

function getFeeds() {
    const feeds = [{
            restaurant: {
                name: "Burger King",
            },
            charity: {
                name: "GiveDirectly",
            },
            amount: "15 dinner meals",
        },
        {
            restaurant: {
                name: "Burger King",
            },
            charity: {
                name: "GiveDirectly",
            },
            amount: "15 dinner meals",
        },
        {
            restaurant: {
                name: "Burger King",
            },
            charity: {
                name: "GiveDirectly",
            },
            amount: "15 dinner meals",
        },
    ];

    // Using Promise here to mimick API response from the backend
    return Promise.resolve(feeds);
}

function getCharities() {
    const charities = [{
            name: "GiveDirectly",
            description: "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
            address: "Kent Ridge | Singapore",
        },
        {
            name: "GiveDirectly",
            description: "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
            address: "Kent Ridge | Singapore",
        },
        {
            name: "GiveDirectly",
            description: "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
            address: "Kent Ridge | Singapore",
        },
    ];

    // Using Promise here to mimick API response from the backend
    return Promise.resolve(charities);
}

function getRestaurants() {
    const restaurants = [{
            name: "Burger King",
            description: "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
            address: "Fast Food | VivoCity #02-80",
        },
        {
            name: "Burger King",
            description: "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
            address: "Fast Food | VivoCity #02-80",
        },
        {
            name: "Burger King",
            description: "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
            address: "Fast Food | VivoCity #02-80",
        },
    ];

    // Using Promise here to mimick API response from the backend
    return Promise.resolve(restaurants);
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
    } if (loginInfo.length == 2) {
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
    var notLoggedIn = 1;
    var loggedInAndRegistered = 3;
    if (loginInfo.length == notLoggedIn) {
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
