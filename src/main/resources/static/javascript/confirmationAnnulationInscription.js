function confirmDeletion(id) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette inscription ?")) {
        window.location.href = '/participantEvenement/delete/' + id;
    }
}

function incrementEvent(eventId) {
    var incrementUrl = "/rest/IncrementerPlacesRestantes/" + eventId;
    $.post(incrementUrl, function () {
    });
}


