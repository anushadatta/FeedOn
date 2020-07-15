// const loadingElement = document.querySelector(".loading");
const dataElement = document.querySelector(".data");

// loadingElement.style.display = "none";

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

            div.appendChild(header);
            div.appendChild(contents);
            div.appendChild(category);
            div.appendChild(time);
            div.appendChild(address);

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

function getMatch() {
    return fetch("/donation-match")
        .then((response) => response.json())
        .then((entries) => {
        return entries;
        });
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


function listInbox() {
    getDonations().then((donations) => {
        dataElement.innerHTML = "";
        donations.forEach((donation) => {
            const div = document.createElement("div");
            div.className = "card";

            const header = document.createElement("h3");
            header.textContent = donation.restaurantName;

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

            var acceptButtonElement = document.createElement("button");
            acceptButtonElement.innerHTML = "ACCEPT";
            acceptButtonElement.type = "submit";
            acceptButtonElement.className = "accept-btn";
            acceptButtonElement.addEventListener('click', () => {
                deleteDonation(donation);
                addMatch(donation);

                div.remove();
            });

            var declineButtonElement = document.createElement("button");
            declineButtonElement.innerHTML = "DECLINE";
            declineButtonElement.type = "button";
            declineButtonElement.className = "decline-btn";
            declineButtonElement.addEventListener('click', () => {
                div.remove();
            });

            div.appendChild(header);
            div.appendChild(category);
            div.appendChild(quantity);
            div.appendChild(time);
            div.appendChild(address);
            div.appendChild(instructions);
            div.appendChild(acceptButtonElement);
            div.appendChild(declineButtonElement);

            dataElement.appendChild(div);
        });
    });
}

function getDonations() {
    return fetch("/inbox")
        .then((response) => response.json())
        .then((entries) => {
        return entries;
        });
}

function deleteDonation(donation) {
    const params = new URLSearchParams();
    params.append('id', donation.id);
    fetch('/delete-donation', {method: 'POST', body: params});
}

function addMatch(donation) {
    const params = new URLSearchParams();
    params.append('id', donation.id);
    fetch('/add-match', {method: 'POST', body: params});
}


