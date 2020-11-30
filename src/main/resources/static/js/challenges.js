var selected_challenge = -1;

$(document).on("click", ".challenge-button", function () {
    let challengeId = $(this).data('id');
    console.log("Click sur le challenge avec l'id : " + challengeId);

    // on reset le modal
    $("#flag-input").val("");
    $("#chall-error-msg").css({
        display:"none",
        visibility: "hidden"
    })
    $("#challenge-file-download").css({
        display: "block",
        visibility: "visible"
    });

    // On fait la requête ajax vers l'api
    $.get( "/challenges/"+challengeId, function( data ) {
        console.log(data);
        selected_challenge = data["id"];
        $("#challenge-window-title").html(data["title"]);
        $("#challenge-window-score").html(data["score"]);
        if (data["linkToFile"] != null) {
            $("#file-link").attr("href", data["linkToFile"]);
            $("#challenge-file-download").css({
                display: "block",
                visibility: "visible"
            });
        } else {
            $("#file-link").attr("href", "");
            $("#challenge-file-download").css({
                display: "none",
                visibility: "hidden"
            });
        }


        let converter = new showdown.Converter();
        let html = converter.makeHtml(data["description"]);
        $("#challenge-window-body").html(html);
    });
});

$("#check-flag-btn").on("click", () => {
    const flag = $("#flag-input").val();
    console.log("Checking flag for id : " + selected_challenge + " with the flag : " + flag);

    const escaped_flag = escape(flag);
    $.get( "/challenges/check/" + selected_challenge + "?flag=" + escaped_flag, (data) => {
        console.log(data);

        // on choisit la tronche du message d'alert
        if (data["correct"]) {
            $("#chall-error-msg").removeClass("alert-danger");
            $("#chall-error-msg").addClass("alert-success");
        } else {
            $("#chall-error-msg").removeClass("alert-success");
            $("#chall-error-msg").addClass("alert-danger");
        }
        $("#chall-error-msg").html(data["reason"]);
        $("#chall-error-msg").css({
            display:"block",
            visibility: "visible"
        })
    });
});