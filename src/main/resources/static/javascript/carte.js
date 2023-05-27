/**
 * Creates a Leaflet map centered on Bretagne, France and adds a tile layer from OpenStreetMap.
 * @type {L.Map} The Leaflet map object.
 */
let map = L.map('mapid').setView([48.2020471, -2.9326435], 8);

// ajouter une couche de tuiles OpenStreetMap à la carte
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
    maxZoom: 14
}).addTo(map);

/**
 * Fetches organization data from the API and adds markers for each organization to the map.
 */
fetch("/api/organismes")
    .then(response => response.json())
    .then(data => {
        // Browse the retrieved data and create a marker for each organizations.
        data.forEach(organisme => {
            let marker = L.marker([organisme.latitude, organisme.longitude]).addTo(map);
            let url = organisme.url;
            if (!/^http(s)?:\/\//i.test(url)) {
                url = "http://" + url;
            }
            let link = "<a href='" + url + "'>" + organisme.url + "</a>";
            marker.bindPopup("<b>" + organisme.libelle+ "</b><br>" + organisme.adresse + "<br>" + organisme.code_postal+ "<br>" + organisme.ville + "<br>" + link).openPopup();


            // Add a 'click' event listener to the marker
            marker.on('click', function() {
                // Change the zoom level of the map
                map.setView([organisme.latitude, organisme.longitude], 14);
            });


            // Add a 'click' event listener to the organisme name in the table
            var organismeNames = document.querySelectorAll("td.cellule:first-child");
            organismeNames.forEach(organismeName => {
                if (organismeName.textContent === organisme.libelle) {
                    organismeName.addEventListener('click', function() {
                        // Change the zoom level of the map
                        map.setView([organisme.latitude, organisme.longitude], 14);
                    });
                }
            });
        });
    })
    .catch(error => console.error(error));


//-----------------------------------------------------------------------------------------------------//


/**
 * Adds the ability to sort organizations by name when the "Organizattion name" column header is clicked.
 */
const nomHeader = document.querySelector(".en-tete-tableau:nth-child(1)");

// Add a click event listener to the header of the "Organization name" column.
nomHeader.addEventListener("click", function () {
    const table = document.querySelector(".tableau");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));

    // Sorting of the data according to the name of the festival.
    rows.sort(function (a, b) {
        const aNom = a.querySelector(".cellule:nth-child(1)").innerText;
        const bNom = b.querySelector(".cellule:nth-child(1)").innerText;

        return aNom.localeCompare(bNom);
    });

    // Reverse the order if the user has already sorted in ascending order.
    if (nomHeader.dataset.sort === "asc") {
        rows.reverse();
        nomHeader.dataset.sort = "desc";
    } else {
        nomHeader.dataset.sort = "asc";
    }

    // Update the table with the sorted data.
    rows.forEach(row => tbody.appendChild(row));
});


//-----------------------------------------------------------------------------------------------------//


/**
 * Adds the ability to sort organizations by activity when the "Organizattion Activity" column header is clicked.
 */
const activiteHeader = document.querySelector(".en-tete-tableau:nth-child(2)");

// Add a click event listener to the header of the "Organization name" column.
activiteHeader.addEventListener("click", function () {
    const table = document.querySelector(".tableau");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));

    // Sorting of the data according to the name of the festival.
    rows.sort(function (a, b) {
        const aActivite = a.querySelector(".cellule:nth-child(2)").innerText;
        const bActivite = b.querySelector(".cellule:nth-child(2)").innerText;

        return aActivite.localeCompare(bActivite);
    });

    // Reverse the order if the user has already sorted in ascending order.
    if (activiteHeader.dataset.sort === "asc") {
        rows.reverse();
        activiteHeader.dataset.sort = "desc";
    } else {
        activiteHeader.dataset.sort = "asc";
    }

    // Update the table with the sorted data.
    rows.forEach(row => tbody.appendChild(row));
});
//-----------------------------------------------------------------------------------------------------//


/**
 * Adds the ability to sort organizations by town when the "Organizattion town" column header is clicked.
 */
const villeHeader = document.querySelector(".en-tete-tableau:nth-child(4)");

// Add a click event listener to the header of the "Organization name" column.
villeHeader.addEventListener("click", function () {
    const table = document.querySelector(".tableau");
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));

    // Sorting of the data according to the name of the festival.
    rows.sort(function (a, b) {
        const aVille = a.querySelector(".cellule:nth-child(4)").innerText;
        const bVille = b.querySelector(".cellule:nth-child(4)").innerText;

        return aVille.localeCompare(bVille);
    });

    // Reverse the order if the user has already sorted in ascending order.
    if (villeHeader.dataset.sort === "asc") {
        rows.reverse();
        villeHeader.dataset.sort = "desc";
    } else {
        villeHeader.dataset.sort = "asc";
    }

    // Update the table with the sorted data.
    rows.forEach(row => tbody.appendChild(row));
});