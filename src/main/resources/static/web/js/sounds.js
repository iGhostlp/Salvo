
function sunken(numRep){
if(numRep == 0){
    let sunken = new Howl({
        src: ['./sounds/hundiste.mp3'],
        volume: 0.5
    })
    sunken.play();
  }
}
function victory(){
    let victory = new Howl({
        src: ['./sounds/youwin.mp3'],
        volume: 0.3
    })
    victory.play();
}