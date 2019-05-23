$(document).ready(function() {
  $("#inlineFormCustomSelect").change(function() {
      $('#region').html($(this).text());
  }).change();
});