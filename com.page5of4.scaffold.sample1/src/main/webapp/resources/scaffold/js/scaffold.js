(function($) {
   $(function() {
      $('.scaffold.editor INPUT.datepicker').datepicker();
      $('.scaffold.editor INPUT.datetimepicker').datetimepicker({
         "timepicker" : {
            "amPmText" : [ "", "" ],
            "minuteText" : "Minute",
            "value" : "",
            "showPeriod" : false,
            "hourText" : "Hour"
         },
         "datepicker" : {
            "monthNamesShort" : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
            "firstDay" : 1,
            "dayNames" : [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ],
            "dateFormat" : "MM dd, yy",
            "value" : "",
            "dayNamesMin" : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
            "dayNamesShort" : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
            "monthNames" : [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]
         }
      });
   });
})(jQuery);