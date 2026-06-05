var os = require("os");
var express = require("express");
var app = express();

function getIPAddress() {
  var interfaces = os.networkInterfaces();
  for (var devName in interfaces) {
    var iface = interfaces[devName];
    for (var i = 0; i < iface.length; i++) {
      var alias = iface[i];
      if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal) {
        return alias.address;
      }
    }
  }
  return '0.0.0.0';
}

app.get("/api/backend", (req, res, next) => {
  const { greeting } = req.query;
  
  const json = {
    greeting: greeting + " from Cluster Backend",
    time: new Date().getTime(),
    ip: getIPAddress()
  };
  
  res.json(json);
});

app.listen(3000, () => {
  console.log("Server running on port 3000");
});