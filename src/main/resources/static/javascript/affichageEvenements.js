
document.addEventListener('DOMContentLoaded', () => {
    fetch('/api/evenements')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de la récupération des données.');
            }
            return response.json();
        })
        .then(data => {
            var evenementsHtml = "";
            data.forEach(evenement => {
                var username = document.getElementById("authName").innerHTML;
                evenementsHtml += `
                    <div class="card">
                        <img src="${evenement.photo}" alt="${evenement.titre}">
                        <div class="card-content">
                            <p class="card-date">${evenement.dateDebut} - ${evenement.dateFin}</p>
                            <h5 class="card-type">${evenement.type}</h5>
                            <h3 class="card-title">${evenement.titre}</h3>                                   
                            <button class="card-button" onclick="window.location.href='/evenements/${evenement.id}'">En savoir plus</button>
                        </div>
                    </div>
                `;
            });
            document.getElementById("listings").innerHTML = evenementsHtml;
        })

        .catch(error => console.error(error));
});