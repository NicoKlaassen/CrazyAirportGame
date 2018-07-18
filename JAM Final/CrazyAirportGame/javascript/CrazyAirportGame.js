//keine Umlaute in den Kommentaren oder Funktionen bitte

//Testfunktion um Spiel verlassen zu blockieren
	function init() {
	sendDataToServer('sawf');
    console.log("loeschen der kopfzeile");
    document.getElementById("content").style.top="100px";
    var elem = document.getElementById('menu');
    elem.parentNode.removeChild(elem);
}
	
function load(){
		init();
		console.log("Loading done");
		sendDataToServer("lobbyJoin");
		}

var onClickDecide='';
var removeChipProject='';
var outActiveProjects='';
var inActiveProjects='';
var stealPlayersSize='';
var chosenProject='';

addListener('USERJOINED',function (event) {
	$("#lobbyTable").html("<thead><tr><th>Name</th><th>Rolle</th></tr></thead>");
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.users){
		$("#lobbyTable").append('<tr><td class="playerColumn">'+json.users[i].name+'</td><td class="roleColumn">'+json.users[i].role+'</td></tr>');
	}
});

addListener('tableStatus',function (event) {
	$("#playerTable").html("<thead><tr><th>Chip</th><th>Name</th><th>Steuerzahlertaler</th><th>Chips</th><th>SZT wegnehmen</th><th>Chip wegnehmen</th><th>Karte 11 nutzen</th><th>Karte 23 nutzen</th></tr></thead>");
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json);
	for(var i in json.players){
		if(i==0){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipBlau.png" alt="Icon Blau" id="iconBlau"></td><td style="color: #03A9F4">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td><td><button type="button" id="takeSZTPlayer1" class="btn btn-primary btn-sm" disabled onclick="takeSZTPlayer1()" >SZT wegnehmen</button></td><td><button type="button" id="takeChipPlayer1" class="btn btn-primary btn-sm" disabled onclick="removeChipPlayer1()">Chip wegnehmen</button></td><td><button type="button" id="userSpecialCard11" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard11()" >Karte 11 nutzen</button></td><td><button type="button" id="userSpecialCard23" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard23()" >Karte 23 nutzen</button></td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #03A9F4">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==1){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipGelb.png" alt="Icon Gelb" id="iconGelb"></td><td style="color: #FFEB3B">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td><td><button type="button" id="takeSZTPlayer2" class="btn btn-primary btn-sm" disabled onclick="takeSZTPlayer2()" >SZT wegnehmen</button></td><td><button type="button" id="takeChipPlayer2" class="btn btn-primary btn-sm" disabled onclick="removeChipPlayer2()">Chip wegnehmen</button></td><td><button type="button" id="userSpecialCard11" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard11()" >Karte 11 nutzen</button></td><td><button type="button" id="userSpecialCard23" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard23()" >Karte 23 nutzen</button></td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #FFEB3B">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==2){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipGruen.png" alt="Icon Gruen" id="iconGruen"></td><td style="color: #4CAF50">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td><td><button type="button" id="takeSZTPlayer3" class="btn btn-primary btn-sm" disabled onclick="takeSZTPlayer3()" >SZT wegnehmen</button></td><td><button type="button" id="takeChipPlayer3" class="btn btn-primary btn-sm" disabled onclick="removeChipPlayer3()">Chip wegnehmen</button></td><td><button type="button" id="userSpecialCard11" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard11()" >Karte 11 nutzen</button></td><td><button type="button" id="userSpecialCard23" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard23()" >Karte 23 nutzen</button></td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #4CAF50">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==3){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipRot.png" alt="Icon Rot" id="iconRot"></td><td style="color: #D32F2F">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td><td><button type="button" id="takeSZTPlayer4" class="btn btn-primary btn-sm" disabled onclick="takeSZTPlayer4()" >SZT wegnehmen</button></td><td><button type="button" id="takeChipPlayer4" class="btn btn-primary btn-sm" disabled onclick="removeChipPlayer4()">Chip wegnehmen</button></td><td><button type="button" id="userSpecialCard11" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard11()" >Karte 11 nutzen</button></td><td><button type="button" id="userSpecialCard23" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard23()" >Karte 23 nutzen</button></td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #D32F2F">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
		if(i==4){
			$("#playerTable").append('<tr><td class="chipColumn"><img src="images/chipLila.png" alt="Icon Lila" id="iconLila"></td><td style="color: #7C4DFF">'+json.players[i].name+'</td><td>'+json.players[i].score+'</td><td>'+json.players[i].chips.length+'</td><td><button type="button" id="takeSZTPlayer5" class="btn btn-primary btn-sm" disabled onclick="takeSZTPlayer5()" >SZT wegnehmen</button></td><td><button type="button" id="takeChipPlayer5" class="btn btn-primary btn-sm" disabled onclick="removeChipPlayer5()">Chip wegnehmen</button></td><td><button type="button" id="userSpecialCard11" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard11()" >Karte 11 nutzen</button></td><td><button type="button" id="userSpecialCard23" class="btn btn-primary btn-sm" disabled onclick="useSpecialCard23()" >Karte 23 nutzen</button></td></tr>')
			if(json.players[i].name==json.currentPlayer.name){
				$("#amZug").html('<span style="color : #7C4DFF">'+json.players[i].name+'</span><span style="color : #FFFFFF"> ist am Zug</span>');
			}
		}
	}
	for(var a in json.finished){
		if(json.finished[a].id==0){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==1){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==2){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==3){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==4){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==5){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==6){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("park"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.finished[a].id==7){
			for(var b in json.finished[a].fields){
				if(json.finished[a].fields[b].Chip=="none"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
	}
	for(var a in json.active){
		if(json.active[a].id==0){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("feuw"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("feuw"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==1){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("landn"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("landn"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==2){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("lands"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("lands"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==3){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("terma"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("terma"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==4){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("termb"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("termb"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==5){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("maint"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("maint"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==6){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("park"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("park"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("park"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
		if(json.active[a].id==7){
			for(var b in json.active[a].fields){
				if(json.active[a].fields[b].Chip=="blue"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cblue');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="yellow"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cyellow');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="green"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cgreen');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="red"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cred');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="purple"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cpurple');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('cvisible');
				}
				if(json.active[a].fields[b].Chip=="none"){
					document.getElementById("pfield"+(parseInt(b)+1)).classList.add('chidden');
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('cpurple', 'cred', 'cgreen', 'cyellow', 'cblue', );
					document.getElementById("pfield"+(parseInt(b)+1)).classList.remove('cvisible');
				}
			}
		}
	}
	for(var c in json.active){
		if(json.active[c].id==0){
			document.getElementById('feuwStatus').style="color:#31FF00";
			document.getElementById('feuwStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==1){
			document.getElementById('landnStatus').style="color:#31FF00";
			document.getElementById('landnStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==2){
			document.getElementById('landsStatus').style="color:#31FF00";
			document.getElementById('landsStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==3){
			document.getElementById('termaStatus').style="color:#31FF00";
			document.getElementById('termaStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==4){
			document.getElementById('termbStatus').style="color:#31FF00";
			document.getElementById('termbStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==5){
			document.getElementById('maintStatus').style="color:#31FF00";
			document.getElementById('maintStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==6){
			document.getElementById('parkStatus').style="color:#31FF00";
			document.getElementById('parkStatus').innerHTML="Aktiv";
		}
		if(json.active[c].id==7){
			document.getElementById('vorfStatus').style="color:#31FF00";
			document.getElementById('vorfStatus').innerHTML="Aktiv";
		}
	}
	for(var c in json.available){
		if(json.available[c].id==0){
			document.getElementById('feuwStatus').style="color:#FF0800";
			document.getElementById('feuwStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==1){
			document.getElementById('landnStatus').style="color:#FF0800";
			document.getElementById('landnStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==2){
			document.getElementById('landsStatus').style="color:#FF0800";
			document.getElementById('landsStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==3){
			document.getElementById('termaStatus').style="color:#FF0800";
			document.getElementById('termaStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==4){
			document.getElementById('termbStatus').style="color:#FF0800";
			document.getElementById('termbStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==5){
			document.getElementById('maintStatus').style="color:#FF0800";
			document.getElementById('maintStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==6){
			document.getElementById('parkStatus').style="color:#FF0800";
			document.getElementById('parkStatus').innerHTML="Inaktiv";
		}
		if(json.available[c].id==7){
			document.getElementById('vorfStatus').style="color:#FF0800";
			document.getElementById('vorfStatus').innerHTML="Inaktiv";
		}
	}
	for(var c in json.finished){
		if(json.finished[c].id==0){
			document.getElementById('feuwStatus').style="color:#FFFFFF";
			document.getElementById('feuwStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==1){
			document.getElementById('landnStatus').style="color:#FFFFFF";
			document.getElementById('landnStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==2){
			document.getElementById('landsStatus').style="color:#FFFFFF";
			document.getElementById('landsStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==3){
			document.getElementById('termaStatus').style="color:#FFFFFF";
			document.getElementById('termaStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==4){
			document.getElementById('termbStatus').style="color:#FFFFFF";
			document.getElementById('termbStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==5){
			document.getElementById('maintStatus').style="color:#FFFFFF";
			document.getElementById('maintStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==6){
			document.getElementById('parkStatus').style="color:#FFFFFF";
			document.getElementById('parkStatus').innerHTML="Abgeschlossen";
		}
		if(json.finished[c].id==7){
			document.getElementById('vorfStatus').style="color:#FFFFFF";
			document.getElementById('vorfStatus').innerHTML="Abgeschlossen";
		}
	}
});

addListener('showAvailableProjects', function(event){
	console.log("projects");
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfSetz").removeAttribute("disabled");
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

addListener('aksForInAndOutProject',function (event) {
	onClickDecide=2;
	var obj = event.data;
	var json = JSON.parse(obj);
	outActiveProjects=json;
	for(var i in outActiveProjects.outProjects){
		if(outActiveProjects.outProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkEntf").removeAttribute("disabled");
		}
		if(outActiveProjects.outProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfEntf").removeAttribute("disabled");
		}
	}
});

addListener('showAvailableProjectsTwiceBurn', function (event){
	onClickDecide=3;
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfSetz").removeAttribute("disabled");
		}
	}
});

addListener('showAvailableProjectTwoSelection', function(event){
	onClickDecide=4;
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkSetz").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfSetz").removeAttribute("disabled");
		}
	}
});

addListener('showAvailableProjectsTakeOneChip', function(event){
	onClickDecide=6;
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfEntf").removeAttribute("disabled");
		}
	}
});

addListener('choosePlayerToStealFrom', function(event) {
	var obj = event.data;
	var json = JSON.parse(obj);
	stealPlayersSize=json.players.length;
	for(var i in json.players){
		console.log(json.players[i].name);
		console.log(document.getElementById('playerTable').rows[1].cells[1].innerHTML);
		if(json.players[i].name==document.getElementById('playerTable').rows[1].cells[1].innerHTML){
			console.log("takeSZT");
			document.getElementById("takeSZTPlayer1").removeAttribute("disabled");
			document.getElementById("takeSZTPlayer1").style.display = 'none';
			document.getElementById("takeSZTPlayer1").style.display = 'block';
		}
		if(json.players[i].name==document.getElementById('playerTable').rows[2].cells[1].innerHTML){
			console.log("takeSZT");
			document.getElementById("takeSZTPlayer2").removeAttribute("disabled");
			document.getElementById("takeSZTPlayer2").style.display = 'none';
			document.getElementById("takeSZTPlayer2").style.display = 'block';
			if(json.players.length>2){
				if(json.players[i].name==document.getElementById('playerTable').rows[3].cells[1].innerHTML){
					console.log("takeSZT");
					document.getElementById("takeSZTPlayer3").removeAttribute("disabled");
					document.getElementById("takeSZTPlayer3").style.display = 'none';
					document.getElementById("takeSZTPlayer3").style.display = 'block';
					if(json.players.length>3){
						if(json.players[i].name==document.getElementById('playerTable').rows[4].cells[1].innerHTML){
							console.log("takeSZT");
							document.getElementById("takeSZTPlayer4").removeAttribute("disabled");
							document.getElementById("takeSZTPlayer4").style.display = 'none';
							document.getElementById("takeSZTPlayer4").style.display = 'block';
							if(json.players.length>4){
								if(json.players[i].name==document.getElementById('playerTable').rows[5].cells[1].innerHTML){
									console.log("takeSZT");
									document.getElementById("takeSZTPlayer5").removeAttribute("disabled");
									document.getElementById("takeSZTPlayer5").style.display = 'none';
									document.getElementById("takeSZTPlayer5").style.display = 'block';
								}
							}
						}
					}
				}
			}
		}
	}
});

function takeSZTPlayer1(){
	//console.log("subprojectAnswerTwoChipsInOneProject", '{"player":' + document.getElementById('playerTable').rows[1].cells[1].innerHTML + "}');
	executeOnServer("takeAway20SZTFromPlayer", '{"player":"' + document.getElementById('playerTable').rows[1].cells[1].innerHTML + '"}');
	enableTakeSZTButtons();
}

function takeSZTPlayer2(){
	//console.log("subprojectAnswerTwoChipsInOneProject", '{"player":"' + document.getElementById('playerTable').rows[2].cells[1].innerHTML + '"}');
	executeOnServer("takeAway20SZTFromPlayer", '{"player":"' + document.getElementById('playerTable').rows[2].cells[1].innerHTML + '"}');
	enableTakeSZTButtons();
}

function takeSZTPlayer3(){
	//console.log("subprojectAnswerTwoChipsInOneProject", '{"player":"' + document.getElementById('playerTable').rows[3].cells[1].innerHTML + '"}');
	executeOnServer("takeAway20SZTFromPlayer", '{"player":"' + document.getElementById('playerTable').rows[3].cells[1].innerHTML + '"}');
	enableTakeSZTButtons();
}

function takeSZTPlayer4(){
	//console.log("subprojectAnswerTwoChipsInOneProject", '{"player":"' + document.getElementById('playerTable').rows[4].cells[1].innerHTML + '"}');
	executeOnServer("takeAway20SZTFromPlayer", '{"player":"' + document.getElementById('playerTable').rows[4].cells[1].innerHTML + '"}');
	enableTakeSZTButtons();
}

function takeSZTPlayer5(){
	//console.log("subprojectAnswerTwoChipsInOneProject", '{"player":"' + document.getElementById('playerTable').rows[5].cells[1].innerHTML + '"}');
	executeOnServer("takeAway20SZTFromPlayer", '{"player":"' + document.getElementById('playerTable').rows[5].cells[1].innerHTML + '"}');
	enableTakeSZTButtons();
}

function enableRemoveChipButtons(){
	document.getElementById("feuwEntf").disabled=true;
	document.getElementById("landnEntf").disabled=true;
	document.getElementById("landsEntf").disabled=true;
	document.getElementById("termaEntf").disabled=true;
	document.getElementById("termbEntf").disabled=true;
	document.getElementById("maintEntf").disabled=true;
	document.getElementById("parkEntf").disabled=true;
	document.getElementById("vorfEntf").disabled=true;
}

function enableTakeSZTButtons(){
	if(!$("#takeSZTPlayer1").is('[disabled]')){
		document.getElementById("takeSZTPlayer1").disabled=true;
	}
	if(!$("#takeSZTPlayer2").is('[disabled]')){
		document.getElementById("takeSZTPlayer2").disabled=true;
	}
	if(stealPlayersSize>=3){
		document.getElementById("takeSZTPlayer3").disabled=true;
	}
	if(stealPlayersSize>=4){
		document.getElementById("takeSZTPlayer4").disabled=true;
	}
	if(stealPlayersSize==5){
		document.getElementById("takeSZTPlayer5").disabled=true;
	}
}


function removeEnableActiveProjects(){
	console.log(outActiveProjects.inProjects);
	console.log(outActiveProjects.inProjects);
	for(var i in outActiveProjects.inProjects){
		if(outActiveProjects.inProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwSetz").removeAttribute("disabled");
			document.getElementById("feuwSetz").style.display = 'none';
			document.getElementById("feuwSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnSetz").removeAttribute("disabled");
			document.getElementById("landnSetz").style.display = 'none';
			document.getElementById("landnSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsSetz").removeAttribute("disabled");
			document.getElementById("landsSetz").style.display = 'none';
			document.getElementById("landsSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaSetz").removeAttribute("disabled");
			document.getElementById("termaSetz").style.display = 'none';
			document.getElementById("termaSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbSetz").removeAttribute("disabled");
			document.getElementById("termbSetz").style.display = 'none';
			document.getElementById("termbSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintSetz").removeAttribute("disabled");
			document.getElementById("maintSetz").style.display = 'none';
			document.getElementById("maintSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkSetz").removeAttribute("disabled");
			document.getElementById("parkSetz").style.display = 'none';
			document.getElementById("parkSetz").style.display = 'block';
		}
		if(outActiveProjects.inProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfSetz").removeAttribute("disabled");
			document.getElementById("vorfSetz").style.display = 'none';
			document.getElementById("vorfSetz").style.display = 'block';
		}
	}
}


function removeChipFeuw(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 0 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 0 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 0 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=0;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipLandn(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 1 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 1 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 1 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=1;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipLands(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 2 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 2 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 2 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=2;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipTermA(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 3 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 3 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 3 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=3;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipTermb(){
if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 4 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 4 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 4 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=4;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipMainT(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 5 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 5 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 5 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=5;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipPark(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 6 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 6 + '}');
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 6 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=6;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

function removeChipVorfeld(){
	if(onClickDecide==6){
		executeOnServer("remove1ChipFromProjectAndAddToPlayer", '{"projectID":'+ 7 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	if(onClickDecide==7){
		executeOnServer("removeFirstChipFromProjectAndAddToPlayer", '{"projectID":'+ 7 + '}');
		onClickDecide=8;
		onClickDecide==0;
	}
	if(onClickDecide==8){
		executeOnServer("removeSecondChipFromProjectAndAddToPlayer", '{"projectID":'+ 7 + '}');
		enableRemoveChipButtons();
		onClickDecide==0;
	}
	else{
		removeChipProject=7;
		enableRemoveChipButtons();
		removeEnableActiveProjects();
	}
}

addListener('askForProjectToSetTwoChips', function (event) {
	console.log("ist reingelaufen");
	onClickDecide=1;
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwSetz").removeAttribute("disabled");
			document.getElementById("feuwSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnSetz").removeAttribute("disabled");
			document.getElementById("landnSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsSetz").removeAttribute("disabled");
			document.getElementById("landsSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaSetz").removeAttribute("disabled");
			document.getElementById("termaSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbSetz").removeAttribute("disabled");
			document.getElementById("termbSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintSetz").removeAttribute("disabled");
			document.getElementById("maintSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkSetz").removeAttribute("disabled");
			document.getElementById("parkSetz").firstChild.data="Zwei Chips setzen";
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfSetz").removeAttribute("disabled");
			document.getElementById("vorfSetz").firstChild.data="Zwei Chips setzen";
		}
	}
});

addListener('showAvailableProjectTwoSelectionTakeChips', function(event){
	onClickDecide=7;
	var obj = event.data;
	var json = JSON.parse(obj);
	for(var i in json.availableProjects){
		if(json.availableProjects[i].id==0){
			console.log("projects0");
			document.getElementById("feuwEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==1){
			console.log("projects1");
			document.getElementById("landnEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==2){
			console.log("projects2");
			document.getElementById("landsEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==3){
			console.log("projects3");
			document.getElementById("termaEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==4){
			console.log("projects4");
			document.getElementById("termbEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==5){
			console.log("projects5");
			document.getElementById("maintEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==6){
			console.log("projects6");
			document.getElementById("parkEntf").removeAttribute("disabled");
		}
		if(json.availableProjects[i].id==7){
			console.log("projects7");
			document.getElementById("vorfEntf").removeAttribute("disabled");
		}
	}
});

function setTwoChipsOnFeuw(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 0 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnLandn(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 1 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnLands(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 2 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnTermA(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 3 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnTermB(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 4 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnMainT(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 5 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnPark(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 6 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function setTwoChipsOnVorfeld(){
	console.log("zwei");
	executeOnServer("subprojectAnswerTwoChipsInOneProject", '{"projectID":'+ 7 + '}');
	onClickDecide=0;
	setBackPlaceChipText();
	enableSetChipButtons();
}

function removedChipOnFeuw(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 0 + '}');
	enableSetChipButtons();
}

function removedChipOnLandn(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 1 + '}');
	enableSetChipButtons();
}

function removedChipOnLands(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 2 + '}');
	enableSetChipButtons();
}

function removedChipOnTerma(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 3 + '}');
	enableSetChipButtons();
}

function removedChipOnTermb(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 4 + '}');
	enableSetChipButtons();
}

function removedChipOnMaint(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 5 + '}');
	enableSetChipButtons();
}

function removedChipOnPark(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 6 + '}');
	enableSetChipButtons();
}

function removedChipOnVorfeld(){
	executeOnServer("removeChipFromProjectAndPutItIntoAnotherAnswer", '{"fromProjectID":'+ removeChipProject + ', "toProjectID":'+ 7 + '}');
	enableSetChipButtons();
}

addListener('showECard',function (event) {
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json.eCardID);
	showECard(json.eCardID);
});

addListener('showVCard', function (event) {
	var obj = event.data;
	var json = JSON.parse(obj);
	console.log(json.vCardID);
	showVCard(json.vCardID);
});


addListener('showDiceButton',function (event) {
	document.getElementById("wuerfelBut").removeAttribute("disabled");
});

addListener('startGame',function (event) {
	document.getElementById("game").style.display='block';
	document.getElementById("lobby").style.display='none';
	console.log("startGame");
});

// executes the command, works with data in json object
function executeOnServer(keyword, json) {
	json = json.replace(/{/g, "%7B");
	json = json.replace(/}/g, "%7D");
	sendDataToServer(keyword + " " + json);
}

addListener('sendMessage', function(event){
	console.log("event sendMessage");
	console.log(event.data);
});

function setChipOnFeuw(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnFeuw();
		return;
	}
	if(onClickDecide==2){
		removedChipOnFeuw();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 0 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 0 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 0 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
	console.log("chosen Project feuw");
	executeOnServer("chosenProject", '{"projectID":' + 0 + '}');
	enableSetChipButtons();
	}
}

function setChipOnLandn(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnLandn();
		return;
	}
	if(onClickDecide==2){
		removedChipOnLandn();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":'+ 1 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 1 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 1 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project landn");
		executeOnServer("chosenProject", '{"projectID":' + 1 + '}');
		enableSetChipButtons();
	}
}

function setChipOnLands(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnLands();
		return;
	}
	if(onClickDecide==2){
		removedChipOnLands();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 2 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 2 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 2 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project lands");
		executeOnServer("chosenProject", '{"projectID":' + 2 + '}');
		enableSetChipButtons();
	}
}

function setChipOnTermA(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnTermA();
		return;
	}
	if(onClickDecide==2){
		removedChipOnTerma();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 3 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 3 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 3 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project termA");
		executeOnServer("chosenProject", '{"projectID":' + 3 + '}');
		enableSetChipButtons();
	}
}

function setChipOnTermB(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnTermB();
		return;
	}
	if(onClickDecide==2){
		removedChipOnTermb();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 4 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 4 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 4 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project termB");
		executeOnServer("chosenProject", '{"projectID":' + 4 + '}');
		enableSetChipButtons();
	}
}

function setChipOnMainT(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnMainT();
		return;
	}
	if(onClickDecide==2){
		removedChipOnMaint();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 5 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 5 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 5 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project maint");
		executeOnServer("chosenProject", '{"projectID":' + 5 + '}');
		enableSetChipButtons();
	}
}

function setChipOnPark(){
	if(onClickDecide==1){
		setTwoChipsOnPark();
		return;
	}
	if(onClickDecide==2){
		removedChipOnPark();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 6 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 6 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 6 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project park");
		executeOnServer("chosenProject", '{"projectID":' + 6 + '}');
		enableSetChipButtons();
	}
}

function setChipOnVorfeld(){
	if(onClickDecide==1){
		console.log("onClickDecide=1");
		setTwoChipsOnVorfeld();
		return;
	}
	if(onClickDecide==2){
		removedChipOnVorfeld();
		onClickDecide=0;
	}
	if(onClickDecide==3){
		executeOnServer('chipOnFieldBurnSZTTwice', '{"projectID":' + 7 + '}');
		onClickDecide=0;
		enableSetChipButtons();
	}
	if(onClickDecide==4){
		console.log("runInto");
		executeOnServer("chosenProject1", '{"projectID":' + 7 + '}');
		onClickDecide=5
		return;
	}
	if(onClickDecide==5){
		executeOnServer("chosenProject", '{"projectID":' + 7 + '}');
		chosenProject=0;
		enableSetChipButtons();
	}
	else{
		console.log("chosen Project vfeld");
		executeOnServer("chosenProject", '{"projectID":' + 7 + '}');
		enableSetChipButtons();
	}
}

function enableSetChipButtons(){
	document.getElementById("feuwSetz").disabled=true;
	document.getElementById("landnSetz").disabled=true;
	document.getElementById("landsSetz").disabled=true;
	document.getElementById("termaSetz").disabled=true;
	document.getElementById("termbSetz").disabled=true;
	document.getElementById("maintSetz").disabled=true;
	document.getElementById("parkSetz").disabled=true;
	document.getElementById("vorfSetz").disabled=true;
}

function setBackPlaceChipText(){
	document.getElementById("feuwSetz").firstChild.data="Chip setzen";
	document.getElementById("landnSetz").firstChild.data="Chip setzen";
	document.getElementById("landsSetz").firstChild.data="Chip setzen";
	document.getElementById("termaSetz").firstChild.data="Chip setzen";
	document.getElementById("termbSetz").firstChild.data="Chip setzen";
	document.getElementById("maintSetz").firstChild.data="Chip setzen";
	document.getElementById("parkSetz").firstChild.data="Chip setzen";
	document.getElementById("vorfSetz").firstChild.data="Chip setzen";
}

//wuerfeln
function roll() {
    sendDataToServer('rollDice');
    document.getElementById("wuerfelBut").disabled=true;
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