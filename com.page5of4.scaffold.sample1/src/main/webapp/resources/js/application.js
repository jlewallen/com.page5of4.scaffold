(function() {
   window.encodeURL = function() {
      var joined = "";
      for ( var i = 0; i < arguments.length; i++) {
         if (arguments[i][0] == '/') {
            joined += arguments[i].substring(1);
         }
         else {
            joined += arguments[i];
         }
      }
      var url = window.BASE_URL;
      var suffix = url.indexOf(';');
      if (suffix >= 0) { return url.substring(0, suffix) + joined + url.substring(suffix); }
      return url + joined;
   }

   window.changeURLExtension = function(url, extension) {
      var suffix = url.indexOf(';');
      if (suffix < 0) { return url + "." + extension; }
      return url.substring(0, suffix) + "." + extension + url.substring(suffix);
   }
})();

(function($) {
   $(function() {
      // jQuery uniform controls (http://pixelmatrixdesign.com/uniform)
      $("input:checkbox, input:radio, input:file").uniform();
      // Enable uniform for non filtering select elements:
      $("select.uniform").uniform();
      // For select boxes that we want to be smaller. Used in the controls area
      // of the list view:
      $("select.small-uniform").uniform({
         selectClass : 'selector small'
      });

      // jQuery datepicker for formtastic (http://gist.github.com/271377)
      $('input.ui-datepicker').datepicker({
         dateFormat : 'dd-mm-yy'
      });

      // Tooltips (http://onehackoranother.com/projects/jquery/tipsy)
      $('img').each(function() {
         if ($(this).get(0).title != '') {
            $(this).tipsy();
         }
      });

      // Scroll effect for anchors
      // (http://flesler.blogspot.com/2007/10/jqueryscrollto.html)
      $('a').click(function() {
         if ($(this).attr('class') == 'anchor') {
            $.scrollTo(this.hash, 500);
            $(this.hash).find('span.message').text(this.href);
            return false;
         }
      });

      // Icons, this is kind of ugly.
      $('.icon-show img').hover(function() {
         this.src = encodeURL('/resources/images/icons/24/show-hover.png');
      }, function() {
         this.src = encodeURL('/resources/images/icons/24/show.png');
      });
      $('.icon-edit img').hover(function() {
         this.src = encodeURL('/resources/images/icons/24/edit-hover.png');
      }, function() {
         this.src = encodeURL('/resources/images/icons/24/edit.png');
      });
      $('.icon-delete img').hover(function() {
         this.src = encodeURL('/resources/images/icons/24/slash-hover.png');
      }, function() {
         this.src = encodeURL('/resources/images/icons/24/slash.png');
      });
   });
})(jQuery)
