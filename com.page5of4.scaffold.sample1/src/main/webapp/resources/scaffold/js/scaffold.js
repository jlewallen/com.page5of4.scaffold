(function($) {
   $(function() {
      $('.scaffold.editor INPUT.datetimepicker').each(function(i, e) {
         var parsed = Date.parse($(e).val());
         var formattedTimeValue = parsed.toString("HH:mm");
         var formattedDateValue = parsed.toString("MMMM dd, yyyy");
         $(this).datetimepicker({
            "timepicker" : {
               "amPmText" : [ "", "" ],
               "minuteText" : "Minute",
               "value" : formattedTimeValue,
               "showPeriod" : false,
               "hourText" : "Hour"
            },
            "datepicker" : {
               "monthNamesShort" : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ],
               "firstDay" : 1,
               "dayNames" : [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ],
               "dateFormat" : "MM dd, yy",
               "value" : formattedDateValue,
               "dayNamesMin" : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
               "dayNamesShort" : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ],
               "monthNames" : [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]
            }
         });
      });

      $('.scaffold.editor INPUT.datepicker').datepicker();
   });
})(jQuery);