var initEvtAll = function() {
    var allEventSource = new EventSource("/api/author/subscribe");
    allEventSource.onmessage = messageHandler;
}
var initEvtAuthor = function() {
    var authorName = document.getElementById('author').value;
    var authorEventSource = new EventSource("/api/author/subscribe?author=" + authorName);
    authorEventSource.onmessage = messageHandler;
}

var messageHandler = function(e) {
    var newElement = document.createElement('li');
    var eventList = document.getElementById('list');
    var data = JSON.parse(e.data);
    newElement.innerHTML = data.title + " from " + data.author + " published";
    eventList.appendChild(newElement);
};

