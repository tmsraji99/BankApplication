app.service('controllerSharingData', function() {
  var __variables = {};

  return {
   get: function(varname) {
    return (typeof __variables[varname] !== 'undefined') ? __variables[varname] : false;
   },
   set: function(varname, value) {
    __variables[varname] = value;
   }
  };
});