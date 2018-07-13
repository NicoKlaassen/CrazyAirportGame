window.onload = init;


addListener('USERJOINED',function (event) {
	console.log("ausfuehren");
	console.log(event.data);
});

addListener('sendMessage', function(event){
	console.log("event sendMessage");
	console.log(event.data);
});

//keine verfickten Umlaute in diese verhurten Kommentare - danke

//Testfunktion um Spiel verlassen zu blockieren
function init() {
	sendDataToServer('sawf');
    console.log("loeschen der kopfzeile");
    document.getElementById("content").style.top="100px";
    var elem = document.getElementById('menu');
    elem.parentNode.removeChild(elem);
}
//wuerfeln
function roll(number) {
    console.log("wuerfel rollen");
    /*
    switch(number){
		case 1 : document.getElementById("wuerfel").src="images/wuerfel/1.jpg"; break;
		case 2 : document.getElementById("wuerfel").src="images/wuerfel/2.jpg"; break;
		case 3 : document.getElementById("wuerfel").src="images/wuerfel/3.jpg"; break;
		case 4 : document.getElementById("wuerfel").src="images/wuerfel/4.jpg"; break;
		case 5 : document.getElementById("wuerfel").src="images/wuerfel/5.jpg"; break;
		case 6 : document.getElementById("wuerfel").src="images/wuerfel/6.jpg"; break;
    }	
    */
    switch(number){
    	case 1 : document.getElementById("wuerfel").src="images/wuerfel/ergebnisLosWuerfel.jpg"; break;
    	case 2 : document.getElementById("wuerfel").src="images/wuerfel/setzeChipWuerfel.jpg"; break;
    }
    if(document.getElementById("projects").style.display=='none'){
        document.getElementById("wuerfel").style.display='block';
        document.getElementById("wuerfel").style.height="250px";
    	document.getElementById("wuerfel").style.width="250px";
    	setTimeout(smallerDice, 3000);
    }
    else{
        document.getElementById("projects").style.display='none';
        document.getElementById("wuerfel").style.display='none';
        document.getElementById("exit").style.display='none';
    }
}
// Nach 3 Sekunden wird der Wuerfel kleiner gemacht
function smallerDice(){
	document.getElementById("wuerfel").style.height="40px";
	document.getElementById("wuerfel").style.width="40px";
	document.getElementById("projects").style.display='block';
	document.getElementById("exit").style.display='block';
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



