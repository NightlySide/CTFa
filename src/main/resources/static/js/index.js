var countDownDate = new Date("6 Oct, 2020 18:00:00").getTime();

function updateTimer() {
    var now = new Date().getTime();
    var diff = countDownDate - now;

    var days = Math.floor(diff / (1000 * 60 * 60 * 24));
    var hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((diff % (1000 * 60)) / 1000);

    // si la date est dépassée
    if (diff <= 0) {
        days = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    $("#countdown-timer").html(days + "d " + hours + "h "
    + minutes + "m " + seconds + "s ");
}


updateTimer();
// Mise a jour du compte à rebours toutes les secondes
setInterval(updateTimer, 1000);