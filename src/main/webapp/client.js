const loadingElement = document.querySelector(".loading");
const dataElement = document.querySelector(".data");

loadingElement.style.display = "none";

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
  const feeds = [
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
  const charities = [
    {
      name: "GiveDirectly",
      description:
        "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
      address: "Kent Ridge | Singapore",
    },
    {
      name: "GiveDirectly",
      description:
        "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
      address: "Kent Ridge | Singapore",
    },
    {
      name: "GiveDirectly",
      description:
        "GiveDirectly is the first — and largest — nonprofit that lets donors like you send food directly to the world’s poorest. We believe people living in poverty deserve the dignity to choose for themselves how best to improve their lives.",
      address: "Kent Ridge | Singapore",
    },
  ];

  // Using Promise here to mimick API response from the backend
  return Promise.resolve(charities);
}

function getRestaurants() {
  const restaurants = [
    {
      name: "Burger King",
      description:
        "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
      address: "Fast Food | VivoCity #02-80",
    },
    {
      name: "Burger King",
      description:
        "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
      address: "Fast Food | VivoCity #02-80",
    },
    {
      name: "Burger King",
      description:
        "Enjoy the best-selling, signature flame-grilled WHOPPER® sandwich as well as other top BK favourites such as the velvety smooth Double Mushroom Swiss, hot and crispy Tendercrisp Chicken, irresistible sides such as Onion Rings, HERSHEY’S® Sundae Pie and more!",
      address: "Fast Food | VivoCity #02-80",
    },
  ];

  // Using Promise here to mimick API response from the backend
  return Promise.resolve(restaurants);
}


function getSignIn() {
  fetch('/login').then(response => response.json()).then((loginInfo) => {
    if (loginInfo.length == 1) { // means user is not logged in
      //var parent = document.getElementById("para");
      var loginLink = loginInfo[0];
      var message = "Go " + loginLink + " to sign in";
      var para = document.createElement("p");
      para.appendChild(document.createTextNode(message));
      document.body.appendChild(para);
      window.location.href = loginLink;
    } else if (loginInfo.length ==2) {
      // user has yet to register
      // direct user to a new page asking them to choose between charity and restaurant
      var registerLink = "register.html";
      window.location.href = registerLink;
    } else {
      var userEmail = loginInfo[0];
      var userType = loginInfo[2];
      var para = document.createElement("p");
      var message = "You are already logged in as " + userEmail + " of type " + userType;
      para.appendChild(document.createTextNode(message));
      document.body.appendChild(para);
      // create the link to return to main page
      mainPageUrl = "index.html";
      var a = document.createElement('a');
      a.href = mainPageUrl;
      a.title = mainPageUrl;
      a.appendChild(document.createTextNode("here"));
      var para2 = document.createElement("p");
      para2.appendChild(document.createTextNode("Click "));
      para2.appendChild(a);
      para2.appendChild(document.createTextNode("to return to main page"));
      document.body.appendChild(para2);
      var logoutLink = loginInfo[1];
      var a2 = document.createElement('a');
      a2.href = logoutLink;
      a2.title = logoutLink;
      a2.appendChild(document.createTextNode("here"));
      var para3 = document.createElement("p");
      para3.appendChild(document.createTextNode("Logout "));
      para3.appendChild(a2);
      document.body.appendChild(para3);
    }
  })
} 

function getRegister() {
  console.log("at register page");
  fetch('/login').then(response => response.json()).then((loginInfo) => {
    if (loginInfo.length == 1) { // means user is not logged in
      console.log("loginInfo.length == 1");
      //var parent = document.getElementById("para");
      var loginLink = loginInfo[0];
      window.location.href = loginLink;
    } if (loginInfo.length == 2) {
      console.log("loginInfo.length == 2");
      var userEmail = loginInfo[0];
      var userInfo = document.getElementById("user-info");
      var message = "You have registered as " + userEmail;
      userInfo.appendChild(document.createTextNode(message));
    } else { // three element means user already registered
      console.log("loginInfo.length == 3");
      var signInLink = "signin.html";
      window.location.href = signInLink;
    }
  })
}