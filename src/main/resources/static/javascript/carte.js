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