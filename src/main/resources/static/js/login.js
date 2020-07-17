
var baseUrl = window.location.origin;

function login(){
    var data = {};
    data['client_id'] = 'c1';
    data['client_secret'] = 'secret';
    data['grant_type'] = 'password';
    //data['redirect_uri'] = "http://www.baidu.com";
    data['username'] = $("#username").val();
    data['password'] = $("#password").val();
    $.ajax({
        url:  baseUrl + '/oauth/token',
        async: false,
        type: 'POST',
        data: data,
        contentType: "application/x-www-form-urlencoded",
        success: function (result) {
            console.log(result.access_token);
            console.log("登录成功");
            console.log("跳转到首页");
            window.location.href = baseUrl + "/link/gotoSuccess?token=" +result.access_token;
            //window.location.href =  baseUrl + "/client/tiaozhuan?token=" +result.access_token;
        }
    });
}

function toSuccess(token) {

    $.ajax({
        url: baseUrl + '/link/gotoSuccess',
        async: false,
        type: 'POST',
        contentType: 'application/json',
        success: function (result) {
            console.log("跳转到首页")
        }

    });

}


