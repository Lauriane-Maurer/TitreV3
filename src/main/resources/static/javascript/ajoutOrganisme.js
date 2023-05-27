/**
 *
 * Retrieves the latitude and longitude values from the HTML form and initializes the map.
 * Creates a marker at the specified latitude and longitude and adds a click event to update the marker position.
 * Uses the OpenStreetMap tile layer and Nominatim API to retrieve the city and postal code corresponding to the coordinates.
 *
 */

// Get the latitude and longitude values from the form.
const latitudeInput = document.getElementById('latitude');
const longitudeInput = document.getElementById('longitude');
const villeInput = document.getElementById('ville');
const cpInput = document.getElementById('code_postal');

// create a map centered on Bretagne
let map = L.map('mapid').setView([48.2020471, -2.9326435], 8);

// add an OpenStreetMap tile layer to the map.
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
    maxZoom: 18
}).addTo(map);

// create an initial marker
let marker = L.marker([latitudeInput.value, longitudeInput.value]).addTo(map);

// add a click event on the map to update the marker position.
map.on('click', function (e) {
    marker.setLatLng(e.latlng);
    latitudeInput.value = e.latlng.lat.toFixed(6);
    longitudeInput.value = e.latlng.lng.toFixed(6);

    // retrieve the city and the postal code corresponding to the coordinates.
    fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${e.latlng.lat}&lon=${e.latlng.lng}`)
        .then(response => response.json())
        .then(data => {
            villeInput.value = data.address.city || data.address.town || '';
            cpInput.value = data.address.postcode || '';
        })
        .catch(error => console.error(error));
});