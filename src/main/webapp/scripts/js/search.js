/**
 * for the Search page
 */

function tableString(data){
	var result="<h3 style='text-align:center'>Results from Tasks Still Left to do</h3>" +
			"<table width='70%' border='1' style='margin-left:auto; margin-right:auto;'>" +
	"<tr> <th>Title</th> " +
	"<th>Body</th> " +
	"<th>Options</th> </tr>";
	for(var i=0;i<data.length;i++){
		if(!(data[i].done)){
			result+="<tr>" +
			"<td>"+data[i].title+"</td>" +
			"<td>"+data[i].body+"</td>" +
			"<td align='center'><button class='btn btn-primary btn-sm'>Done</button> &nbsp;&nbsp;" +
			"<button class='btn btn-primary btn-sm'>Edit</button> &nbsp;&nbsp;" +
			"<button class='btn btn-warning btn-sm'>Delete</button></td></td>" +
			"</tr>";	
		}
	}
	result+="</table> <br /> " +
			"<h3 style='text-align:center'>Results from Completed Tasks</h3>" +
			"<table width='70%' border='1' style='margin-left:auto; margin-right:auto;'>"+
			"<tr> <th>Title</th> " +
			"<th>Body</th> " +
			"<th>Options</th> </tr>";
	for(var i=0;i<data.length;i++){
		if(data[i].done){
			result+="<tr>" +
			"<td>"+data[i].title+"</td>" +
			"<td>"+data[i].body+"</td>" +
			"<td align='center'><button class='btn btn-primary btn-sm'>Redo</button> &nbsp;&nbsp;" +
			"<button class='btn btn-primary btn-sm'>Edit</button> &nbsp;&nbsp;" +
			"<button class='btn btn-warning btn-sm'>Delete</button></td></td>" +
			"</tr>";	
		}
	}
	return result+"</table>";
};

$(document).ready(function(){
	
	$("#btn_search").click(function(){
		var tosearch=$("#search").val();
			$.ajax({
				type:"POST",
				url:"/rest/todo/search",
				contentType:"text/plain",
				data:tosearch,
				dataType:"json",
				success: function(data){
					var table = tableString(data);
					$("#resulttable").empty();
					$("#resulttable").append(table);
				},
				error: function(){
					alert("Please enter a valid search query");
				}
			});
	});
});