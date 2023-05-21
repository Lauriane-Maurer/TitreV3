function confirmDeletion(id) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette inscription ?")) {
        window.location.href = '/participantEvenement/delete/' + id;
    }
}
