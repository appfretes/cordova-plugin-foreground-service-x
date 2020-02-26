var exec = require('cordova/exec');

  exports.start = function(title, text, icon, importance, notificationId){
    exec(null, null, "ForegroundPlugin", "start", [title || "", text || "", icon || "", importance || "1", notificationId || ""]);
  };
  exports.stop = function(){
    exec(null, null, "ForegroundPlugin", "stop", []);
  };
  exports.insertEvent = function(success, error, id, event, value){
    exec(success, error, "ForegroundPlugin", "insertEvent", [id || "", event || "", value || ""]);
  };
  exports.getEventsTest = function(success, error) {
    exec(success, error, "ForegroundPlugin", "getEvents", []);
  };
  exports.getEvents = function() {
    exec(success, error, "ForegroundPlugin", "getEvents", []);
  };