
var baseUrl = window.location.origin;
function toWard() {
    var token = $("#token").val();
    window.location.href =  baseUrl + "/client/tiaozhuan?token=" +token;
}

function logout() {


}