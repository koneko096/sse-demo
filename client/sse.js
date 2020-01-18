var evtSource = new EventSource("http://localhost:8080/author/subscribe");

evtSource.onmessage = function(e) {
    var newElement = document.createElement("li");
    var eventList = document.getElementById('list');
    var data = JSON.parse(e.data);
    newElement.innerHTML = data.title + " from " + data.author + " published";
    eventList.appendChild(newElement);
};
