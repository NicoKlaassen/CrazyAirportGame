

function init() {
    console.log("loeschen der kopfzeile");
    var elem = document.getElementById('menu');
    elem.parentNode.removeChild(elem);
}

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