let map = L.map('mapLocalisationMoulin').setView([48.56790835686583, -4.6629741396325075], 8);

// Ajouter une couche de tuiles OpenStreetMap à la carte
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: 'Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors',
    maxZoom: 14
}).addTo(map);

// Ajouter le marqueur
let marker = L.marker([48.56790835686583, -4.6629741396325075]).addTo(map);

// Ajouter un événement 'click' au marqueur
marker.on('click', function() {
    // Changer le niveau de zoom de la carte
    map.setView([48.56790835686583, -4.6629741396325075], 14);
});

