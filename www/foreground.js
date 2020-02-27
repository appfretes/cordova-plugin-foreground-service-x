var exec = require('cordova/exec');

  exports.verifyPermissions = function(success, error){
    exec(success, error, "ForegroundPlugin", "verifyPermissions", []);
  };
  exports.start = function(success, error, title, text, icon, importance, notificationId){
    exec(success, error, "ForegroundPlugin", "start", [title || "", text || "", icon || "", importance || "1", notificationId || ""]);
  };
  exports.stop = function(){
    exec(null, null, "ForegroundPlugin", "stop", []);
  };
  exports.insertEvent = function(success, error, id, event, value){
    exec(success, error, "ForegroundPlugin", "insertEvent", [id || "", event || "", value || ""]);
  };
  exports.getEvents = function(success, error) {
    exec(success, error, "ForegroundPlugin", "getEvents", []);
  };
  exports.getLocations = function(success, error) {
    exec(success, error, "ForegroundPlugin", "getLocations", []);
  };
  exports.updateSyncLocations = function(success, error, idFrete) {
    exec(success, error, "ForegroundPlugin", "updateSyncLocations", [idFrete || ""]);
  };