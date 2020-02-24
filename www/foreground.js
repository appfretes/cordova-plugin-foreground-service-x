var exec = require('cordova/exec');

module.exports = {
  start: function(title, text, icon, importance, notificationId) {
    exec(null, null, "ForegroundPlugin", "start", [title || "", text || "", icon || "", importance || "1", notificationId || ""]);
  },
  stop: function() {
    exec(null, null, "ForegroundPlugin", "stop", []);
  },
  insertEvent: function(event, value) {
    exec(null, null, "ForegroundPlugin", "insertEvent", [event || "", value || ""]);
  },
  getEvents: function() {
    exec(null, null, "ForegroundPlugin", "getEvents", []);
  }
};