
$(document).ready(function(){
  $("#hide").click(function(){
    $("p").hide();
  });
  
  $("#show").click(function(){
    $("p").show();
  });
  
  $("#testrest").click(function(){
	 $.ajax({
		 url: "/rest/todo/test"
	 }).then(function(data){
		$('.info').text(data)
	 });
  });
  
});