//keine verfickten Umlaute in diese verhurten Kommentare - danke

//Testfunktion um Spiel verlassen zu blockieren
function init() {
    console.log("loeschen der kopfzeile");
    var elem = document.getElementById('menu');
    elem.parentNode.removeChild(elem);
}
//wuerfeln
function roll() {
    console.log("wuerfel rollen");
    if(document.getElementById("projects").style.display=='none'){
        document.getElementById("projects").style.display='block';
        document.getElementById("projects").style.width='376px';
        document.getElementById("diceText").style.display='block';
    }
    else{
        document.getElementById("projects").style.display='none';
        document.getElementById("diceText").style.display='none';
    }
}
//Einblenden der Ergebnislose
function showECard(cardNumber) {
    var link = "images/ergebnislose/BER_Ergebnislos_VS_06_";
    link += cardNumber;
    link += ".jpg";
    document.getElementById("los").src=link;
    document.getElementById("los").style.display="block";
    setTimeout(hideCard, 3000);
}
//Einblenden der Verantwortungslose
function showVCard(cardNumber) {
    var link = "images/verantwortungslose/BER_Verantwortungslos_VS_06_";
    link += cardNumber;
    link += ".jpg";
    document.getElementById("los").src=link;
    document.getElementById("los").style.display="block";
    setTimeout(hideCard, 3000);
}
function hideCard() {
    document.getElementById("los").style.display="none";
}

//Wechsel Lobby in Spiel
function startGame(){
	document.getElementById("game").style.display='block';
	document.getElementById("lobby").style.display='none';
	console.log("startGame");
	sendDataToServer('startGame');
}
//add new AI
function addAI() {
    console.log("addAI");
    $("#lobbyTable").append('<tr><td class="playerColumn">Computer</td><td class="roleColumn">Spieler</td></tr>');
    sendDataToServer('addAI');
}

//prints the table of the players
addListener('showPlayer', function(event) {
    console.log("Listener showPlayer");
    var stringFromServer = event.data;
    // console.log(event.data);
    var obj = JSON.parse(stringFromServer);

    $("#lobbyTable tr").remove();

    for (var i = 0; i < obj.length; i++) {
        if (obj[i].current) {
            var row = '<tr id="active">'
        } else var row = '<tr>';

        $("#lobbyTable").append(
            row+ (obj[i].isHuman ? '<td class="playerColumn">' : '<td class="computerColumn">' )+obj[i].name+'</td></tr>'
        );
    };

});

//leave lobby
function byeBye() {
	console.log("Spiel verlassen");
	sendDataToServer('quit');
}

