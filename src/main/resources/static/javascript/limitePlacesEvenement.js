function togglePlacesTotales() {
    var selectElement = document.getElementById("limite_places_select");
    var placesTotalesSection = document.getElementById("places_totales_section");
    var placesTotalesSectionp = document.getElementById("places_totales_section-p");
    var placesRestantesSection = document.getElementById("places_restantes_section");
    var placesRestantesSectionp = document.getElementById("places_restantes_section-p");


    if (selectElement.value === "1") {
        placesTotalesSection.style.display = "block";
        placesTotalesSectionp.style.display = "block";
        placesRestantesSection.style.display = "block";
        placesRestantesSectionp.style.display = "block";
    } else {
        placesTotalesSection.style.display = "none";
        placesTotalesSectionp.style.display = "none";
        placesRestantesSection.style.display = "none";
        placesRestantesSectionp.style.display = "none";
        placesTotalesSection.value = 0; // Mettre à 0
        placesRestantesSection.value = 0; // Mettre à 0
    }
    var dispo = parseInt(placesRestantesSection.value);
    var placesTotales = parseInt(placesTotalesSection.value);
    var nonOption = selectElement.querySelector("option[value='0']");

    if (dispo !== 0 && placesTotales !==0 && dispo !== placesTotales) {
        nonOption.disabled = true; // Désactiver l'option "NON"
    } else {
        nonOption.disabled = false; // Activer l'option "NON"
    }
}
    togglePlacesTotales();
