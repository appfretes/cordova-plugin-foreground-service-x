var exec = require('cordova/exec');

module.exports = {
  start: function(title, text, icon, importance, notificationId) {
    exec(null, null, "ForegroundPlugin", "start", [title || "", text || "", icon || "", importance || "1", notificationId || ""]);
  },
  stop: function() {
    exec(null, null, "ForegroundPlugin", "stop", []);
  },
  insertEvent: function(success, error, id, event, value) {
    exec(success, error, "ForegroundPlugin", "insertEvent", [id || "", event || "", value || ""]);
  },
  getEvents: function(success, error) {
    exec(success, error, "ForegroundPlugin", "getEvents", []);
  }
};