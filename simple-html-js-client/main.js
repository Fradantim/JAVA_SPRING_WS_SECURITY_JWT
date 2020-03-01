var lastJWT;

window.onload = function wakeUp() {
	// jwt='eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGcmFuY28iLCJleHAiOjE1ODMwNDM3NjQsImlhdCI6MTU4MzAwNzc2NH0.pUUK2G3mviHrsaQ6MtCBWSW2DhA26Qdc2RvLoTE_-hA';
	console.log("JS file started!");

	$('#endpoint').val('http://localhost:8080/');

	$("#JWTexpiredDiv").hide();
	$("#noJWTFoundDiv").hide();
	$("#loginDiv").hide();
	$("#jwtInfoDiv").hide();
	$("#getRequestDiv").hide();

	theInfiniteLoop();
	checkJWT();
}

function theInfiniteLoop(){
	function loop() {
		//console.log("In loop.");
		$('#currentDate').html(new Date());
		
		if(lastJWT){
			var jwt = parseJwt(lastJWT);
			
			if(toDateTime(jwt.exp).getTime() < new Date().getTime()){
				//console.log(toDateTime(jwt.exp).getTime()+" "+new Date().getTime());
				//console.log(toDateTime(jwt.exp)+" - "+new Date());
				$("#jwtInfoDiv").addClass("alert alert-danger");
				$("#JWTexpiredDiv").show();
				$("#loginDiv").show();
				$("#getRequestDiv").hide();
			}
		}

	}

	setInterval(function(){loop()},1000);//timer running every 1 second
}

function checkJWT() {
	console.log("Checking actual jwt.");

	var jwt=getCookie("jwt");

	if (jwt) {
		lastJWT=jwt;
	}
	
	if (lastJWT) {
		console.log("jwt found.");
		// if everything's allright
		$("#JWTexpiredDiv").hide();
		$("#noJWTFoundDiv").hide();
		$("#loginDiv").hide();
		
		$('#lastKWTText').html(lastJWT);
		$('#lastKWTData').html(JSON.stringify(parseJwt(lastJWT)));
		
		$('#lastKWTDataSub').html(parseJwt(lastJWT).sub);
		$('#lastKWTDataIat').html(toDateTime(parseJwt(lastJWT).iat));
		$('#lastKWTDataExp').html(toDateTime(parseJwt(lastJWT).exp));
		
		$("#jwtInfoDiv").show();
		
		$("#jwtInfoDiv").removeClass("alert alert-danger");
		$("#jwtInfoDiv").addClass("alert alert-success");
		
		setTimeout(() => {
			$("#jwtInfoDiv").removeClass("alert alert-success");
			}, 500);
		
		
		$("#getRequestDiv").show();
		
		$.notify("New JWT aquired!", "success");
		
	} else {
		console.log("jwt not found.");
		$("#noJWTFoundDiv").show();
		$("#loginDiv").show();
	}
}

function changeJWT(jwtText){
	console.log("Changing actual jwt.");
	
	if(jwtText){
		jwt=parseJwt(jwtText);
		//console.log(jwt);
		//console.log(JSON.stringify(jwt));
		createCookie("jwt", jwtText, toDateTime(jwt.exp));
		lastJWT=jwtText;
		checkJWT();
	}
}


function toDateTime(secs) {
    var t = new Date(1970, 0, 1); // Epoch
    t.setSeconds(secs);
    t.setTime( t.getTime() - t.getTimezoneOffset()*60*1000 );
    //remember kids, work with zulu time from the beginning
    
    return t;
}

function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
};

function authenticate() {
	logBar();
	console.log("Login in!");

	if (!$('#endpoint').val()) {
		console.error("The Enpoint must be specified!");
	}

	var user = $('#username').val();
	var pass = $('#password').val();
	// console.log(user+' '+pass);

	$.ajax({
		url : $('#endpoint').val() + "authenticate",
		type : "POST",
		data : JSON.stringify({ username : user, password : pass }),
		contentType : "application/json",
		crossDomain : true,
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			console.log("OK!");
			console.log(" -> data: " + JSON.stringify(data));
			//console.log(" -> data.jwt: " + data.jwt);
			//console.log(" -> textStatus: " + textStatus);
			//console.log(" -> jqXHR: " + JSON.stringify(jqXHR));
			//console.log(" -> jqXHR.header.new-jwt: " + jqXHR.getResponseHeader("new-jwt"));
			
			if(data.jwt){
				changeJWT(data.jwt);
			}
		},
		error : function() {
			console.log("ERROR!");
		},
	})
}

function sendRequest(){
	logBar();
	console.log("Sending request.")
	$.ajax({
		url : $('#endpoint').val() + "hello",
		type : "GET",
		//data : JSON.stringify({ username : user, password : pass }),
		headers: { Authorization : 'Bearer '+lastJWT },
		//contentType : "application/json",
		crossDomain : true,
		//dataType : "json",
		success : function(data, textStatus, jqXHR) {
			console.log("OK!");
			console.log(" -> data: " + JSON.stringify(data));
			//console.log(" -> data.jwt: " + data.jwt);
			//console.log(" -> textStatus: " + textStatus);
			//console.log(" -> jqXHR: " + JSON.stringify(jqXHR));
			//console.log(" -> jqXHR.header.new-jwt: " + jqXHR.getAllResponseHeaders());
			console.log(" -> jqXHR.header.new-jwt: " + jqXHR.getResponseHeader("new-jwt"));
			$.notify("Response: "+data.response, "success");
			var newjwt = jqXHR.getResponseHeader("new-jwt");
			if(newjwt){
				changeJWT(newjwt);
			}
		},
		error : function(data) {
			console.log("ERROR! "+JSON.stringify(data));
		},
	})
}

function logBar(){
	console.log("- - - - - - - - - - - - - - - - - - -");
}

function createCookie(name, value, expires, path, domain) {
	  var cookie = name + "=" + escape(value) + ";";

	  if (expires) {
	    // If it's a date
	    if(expires instanceof Date) {
	      // If it isn't a valid date
	      if (isNaN(expires.getTime()))
	       expires = new Date();
	    }
	    else
	      expires = new Date(new Date().getTime() + parseInt(expires) * 1000 * 60 * 60 * 24);

	    cookie += "expires=" + expires.toGMTString() + ";";
	  }

	  if (path)
	    cookie += "path=" + path + ";";
	  if (domain)
	    cookie += "domain=" + domain + ";";

	  document.cookie = cookie;
	}

function getCookie(name) {
	  var regexp = new RegExp("(?:^" + name + "|;\s*"+ name + ")=(.*?)(?:;|$)", "g");
	  var result = regexp.exec(document.cookie);
	  return (result === null) ? null : result[1];
	}