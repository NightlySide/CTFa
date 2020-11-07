$(document).on("click", ".challenge-button", function () {
    let challengeId = $(this).data('id');
    console.log("Click sur le challenge avec l'id : " + challengeId);

    // On fait la requête ajax vers l'api
    $.get( "/challenges/"+challengeId, function( data ) {
        console.log(data);
        $("#challenge-window-title").html(data["title"]);
        $("#challenge-window-score").html(data["score"]);

        let converter = new showdown.Converter();
        let html = converter.makeHtml(data["description"]);
        $("#challenge-window-body").html(html);
    });
});