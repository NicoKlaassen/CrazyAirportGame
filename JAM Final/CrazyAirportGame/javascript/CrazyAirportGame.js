

//keine verfickten Umlaute in diese verhurten Kommentare - danke

//Testfunktion um Spiel verlassen zu blockieren
//function init() {
//	sendDataToServer('sawf');
//    console.log("loeschen der kopfzeile");
//    document.getElementById("content").style.top="100px";
//    var elem = document.getElementById('menu');
//    elem.parentNode.removeChild(elem);
//}

addListener('USERJOINED',function (event) {
	$("#lobbyTable").html("<thead><tr><th>Name</th><th>Rolle</th></tr></thead>");
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.users){
		$("#lobbyTable").append('<tr><td class="playerColumn">'+json.users[i].name+'</td><td class="roleColumn">Spieler</td></tr>');
	}
	 
});

addListener('tableStatus',function (event) {
	$("#playerTable").html("<thead><tr><th>Chip</th><th>Name</th><th>Steuerzahlertaler</th><th>Chips</th></tr></thead>");
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json);
	for(var i in json.players){
		if(i==0){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipBlau.png" alt="Icon Blau" id="iconBlau"></td><td style="color: #03A9F4">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #03A9F4">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==1){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipGelb.png" alt="Icon Gelb" id="iconGelb"></td><td style="color: #FFEB3B">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #FFEB3B">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==2){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipGruen.png" alt="Icon Gruen" id="iconGruen"></td><td style="color: #4CAF50">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #4CAF50">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==3){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipRot.png" alt="Icon Rot" id="iconRot"></td><td style="color: #D32F2F">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #D32F2F">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==4){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipLila.png" alt="Icon Lila" id="iconLila"></td><td style="color: #7C4DFF">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #7C4DFF">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
	}
});

addListener('diceResult',function (event) {
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json.result);
	if(json.result=='true'){
		document.getElementById('diceText').innerHTML="Ergebnis: E-Karte ziehen";
	}
	else{
		document.getElementById('diceText').innerHTML="Ergebnis: Chip setzen";
		}
	 
});

addListener('showECard',function (event) {
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json.eCardID);
	showECard(json.eCardID);
});


addListener('showDiceButton',function (event) {
	document.getElementById("wuerfelBut").removeAttribute("disabled");
});

addListener('startGame',function (event) {
	document.getElementById("game").style.display='block';
	document.getElementById("lobby").style.display='none';
	console.log("startGame");
});



addListener('sendMessage', function(event){
	console.log("event sendMessage");
	console.log(event.data);
});

//wuerfeln
function roll() {
    console.log("wuerfel rollen");
  //  if(document.getElementById("projects").style.display=='none'){
     //   document.getElementById("projects").style.display='block';
       // document.getElementById("projects").style.width='376px';
        //document.getElementById("diceText").style.display='block';
    //}
    //else{
      //  document.getElementById("projects").style.display='none';
        //document.getElementById("diceText").style.display='none';
    //}
    
    sendDataToServer('rollDice');
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
    setTimeout(hideCard, 5000);
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

function setChip(projectID, fieldID){
	switch(projectID){
		case 0:
			switch(fieldID){
				case 0:
					document.getElementById("feuw2").classList.remove('chidden');
					document.getElementById("feuw2").classList.add('cred');
			}
	}
}



