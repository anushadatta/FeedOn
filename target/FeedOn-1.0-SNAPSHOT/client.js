const loadingElement = document.querySelector(".loading");
const dataElement = document.querySelector(".data");

loadingElement.style.display = "none";

function getFeeds() {
    const feedsDiv = document.getElementById("feeds");
    fetch('/load-feeds')
        .then(response => response.json())
        .then(feedsList => {
            console.log(feedsList);
            if(feedsList.length == 0) {
                var noFeeds = document.createElement("p");
                noFeeds.innerText = "There is none. Please come back later.";
                feedsDiv.appendChild(noFeeds);
            } else {
                for (let i = 0; i < feedsList.length; i++) {
                    feedsDiv.appendChild(createFeedElement(feedsList[i]));
                }
            }
        })
        .catch(error => console.log(error));
}

function createFeedElement(input) {
    console.log(input. + input.postDate);

    // To prevent HTML and Script injections
    var feedText = input.text;
    var feedText = input..replace(/</g, "&lt;").replace(/>/g, "&gt;");

    var commentHTML = `
    <div class="text">${commentText}</div>
    <div class="date">${input.postDate}</div>
    `;

    var comment = document.createElement("div");
    comment.classList.add("comment-card");
    comment.innerHTML = commentHTML;

    return comment;
}



function getComments() {
  fetch("/data").then((response) => response.json())
    .then((comments) => {
      document.getElementById("notes-container").innerHTML = `${comments
        .map((comment) => `<p>${comment}</p>`)
        .join("")}`;
    });
}
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

function getInbox() {
  fetch('/inbox').then(response => response.json()).then((inbox) => {
    document.getElementById('inbox').innerText = inbox;
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
