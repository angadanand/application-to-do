
$(document).ready(function(){
  $("#hide").click(function(){
    $("p").hide();
  });
  
  $("#show").click(function(){
    $("p").show();
  });
  
  $("#testrest").click(function(){
	 $.ajax({
		 url: "http://localhost:8080/rest/todo/test"
	 }).then(function(data){
		$('.info').text(data)
	 });
  });
  
});