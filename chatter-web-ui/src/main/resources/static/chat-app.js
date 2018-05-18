$(document).ready(function() {
	var MAX_HISTORY = 15;
	
	var isPWS = false; //(window.location.hostname.indexOf("cfapps.io") > -1);
	
	var ws = new SockJS(isPWS
			?'https://'+window.location.hostname+':4443/websocket'
			:'/websocket'
	);
	
	// Wire up websocket so that msg received from it are shown in the 'console'.
	ws.onopen = function () {
		log('CHANGED inf: WebSocket connection opened.');
	};
	ws.onerror = function (error) {
		log('err: '+error);
	}
	ws.onmessage = function (event) {
		log(event.data);
	};
	ws.onclose = function () {
		log('inf: WebSocket connection closed.');
	};

	$("#msg").keyup(function(e){
	    if(e.keyCode == 13) {
	        $("#send").trigger("click");
	    }
	})
	$("#send").click(function () {
		var msg = $("#msg").val();
		ws.send(msg);
	//	log('Sent: '+msg);
	});
	
	function log(message) {
		var msgs = $("#messages");
		var newMsg = $('<li>').text(message)
		msgs.append(newMsg);
		newMsg.get(0).scrollIntoView();
		while (msgs.scrollTop()>0) {
			$("#messages").children()[0].remove();
		}
	}

})