
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
                            <h3 class="card-title">${evenement.titre}</h3>
                            <p class="card-text">${evenement.description}</p>
                            <p class="card-text"><small class="text-muted">${evenement.dateDebut} - ${evenement.dateFin}</small></p>
                        </div>
                        <div class="card-footer">
                            <a href="/evenements/${evenement.id}">En savoir plus</a>
                        </div>
                    </div>
                `;
            });
            document.getElementById("listings").innerHTML = evenementsHtml;
        })

        .catch(error => console.error(error));
});