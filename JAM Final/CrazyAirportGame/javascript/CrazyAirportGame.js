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
//Wechsel Lobby in Spiel
function startGame(){
	document.getElementById("game").style.display='block';
	document.getElementById("lobby").style.display='none';
	console.log("startGame");
	sendDataToServer('startGame');
}
//KI entfernen
function removeAI(){
	console.log("Bot wurde entfernt");
}
//KI hinzufuegen
function addAI() {
	console.log("addAI");
	sendDataToServer('addAI');
}
//Lobby Tabelle erstellen
addListener('USERJOINED',function (event) {
    var stringFromServer = event.data;
    // username*4
    var temp=stringFromServer.split(",");
    // alert(stringFromServer);
    var table='<tr><th class="tg-s6z2">Name</th><th class="tg-baqh">Rolle</th></tr>';
    for(var i=0; i<temp.length;i++){
        spieler[i]=temp[i];
        table+=('<tr><th class="tg-baqh">'+temp[i]+'</th><th class="tg-baqh">Spieler</th></tr>');
    }
    $("#lobbyTable").html(table);
    if(temp.length==5){
    	document.getElementById("addAIButton").setAttribute('onclick','');
    }
});


