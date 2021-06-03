function getResult() {

    var url = "http://localhost:8000";   // The URL and the port number must match server-side
    var endpoint = "/result";            // Endpoint must match server endpoint

    var http = new XMLHttpRequest();

    // prepare GET request
    http.open("GET", url+endpoint, true);

    http.onreadystatechange = function() {
        var DONE = 4;       // 4 means the request is done.
        var OK = 200;       // 200 means a successful return.
        if (http.readyState == DONE && http.status == OK && http.responseText) {

            // JSON string
            var replyString = http.responseText;

            // convert JSON string to JS object
            var obj = JSON.parse(replyString);

            document.getElementById("result").innerHTML = "Model: " + obj.model;
            document.getElementById("result").innerHTML += "<br>";
            document.getElementById("result").innerHTML += "Accuracy: " + obj.result[0];
            document.getElementById("result").innerHTML += "<br>";
            document.getElementById("result").innerHTML += "Precision: " + obj.result[1];
            document.getElementById("result").innerHTML += "<br>";
            document.getElementById("result").innerHTML += "Recall: " + obj.result[2];
            document.getElementById("result").innerHTML += "<br>";

            // convert JSON string into JavaScript object and get the scores

        }
    };

    // Send request
    http.send();
}