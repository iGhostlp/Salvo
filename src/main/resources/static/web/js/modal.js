function displayModal(event){

    if(event == "WON"){
        $("#winModal").modal('show');
    }else if(event == "LOST"){
        $("#lostModal").modal('show');
    }else {
        $("#tieModal").modal('show');
    }
}