var express = require('express')
var app = express()

app.set('port', (process.env.PORT || 5000))
app.use(express.static(__dirname + '/public'))
app.get('/', function(request, r) {
  r.send('Hello World!')
})

app.listen(app.get('port'), function() {
//alama kota
  console.log("Node app is running at localhost:" + app.get('port'))
})
