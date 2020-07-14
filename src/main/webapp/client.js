const dataElement = document.querySelector(".data");

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
  });
}

function listAllCharities() {
  getCharities().then((charities) => {
    dataElement.innerHTML = "";
    charities.forEach((charity) => {
      const div = document.createElement("div");
      div.className = "card";

      const header = document.createElement("h3");
      header.textContent = charity.name;

      const contents = document.createElement("p");
      contents.textContent = charity.description;

      const location = document.createElement("p");
      location.textContent = charity.location;

      div.appendChild(header);
      div.appendChild(contents);
      div.appendChild(location);

      dataElement.appendChild(div);
    });
  });
}

function listAllRestaurants() {
  getRestaurants().then((restaurants) => {
    dataElement.innerHTML = "";
    restaurants.forEach((restaurant) => {
      const div = document.createElement("div");
      div.className = "card";

      const header = document.createElement("h3");
      header.textContent = restaurant.name;

      const contents = document.createElement("p");
      contents.textContent = restaurant.description;

      const location = document.createElement("p");
      location.textContent = restaurant.location;

      div.appendChild(header);
      div.appendChild(contents);
      div.appendChild(location);

      dataElement.appendChild(div);
    });
  });
}

/*
Real API calls
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
