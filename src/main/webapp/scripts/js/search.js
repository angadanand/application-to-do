/**
 * for the Search page
 */

var currentdata;
var currentindex;
function tableString(data){
	var result="<h3 style='text-align:center'>Results from Tasks Still Left to do</h3>" +
			"<table width='90%' border='1' style='margin-left:auto; margin-right:auto;'>" +
	"<tr> <th>Title</th> " +
	"<th>Body</th> " +
	"<th>Options</th> </tr>";
	for(var i=0;i<data.length;i++){
		if(!(data[i].done)){
			result+="<tr>" +
			"<td>"+data[i].title+"</td>" +
			"<td>"+data[i].body+"</td>" +
			"<td align='center'><button class='btn btn-primary btn-sm' onclick='markDone("+i+")'>Done</button> &nbsp;&nbsp;" +
			"<button class='btn btn-primary btn-sm' data-toggle='modal' data-target='#updateform' onclick='initEdit("+i+")'>Edit</button> &nbsp;&nbsp;" +
			"<button class='btn btn-danger btn-sm' onclick='markDone("+i+")'>Delete</button></td></td>" +
			"</tr>";	
		}
	}
	result+="</table> <br /> " +
			"<h3 style='text-align:center'>Results from Completed Tasks</h3>" +
			"<table width='90%' border='1' style='margin-left:auto; margin-right:auto;'>"+
			"<tr> <th>Title</th> " +
			"<th>Body</th> " +
			"<th>Options</th> </tr>";
	for(var i=0;i<data.length;i++){
		if(data[i].done){
			result+="<tr>" +
			"<td>"+data[i].title+"</td>" +
			"<td>"+data[i].body+"</td>" +
			"<td align='center'><button class='btn btn-primary btn-sm' onclick='markDone("+i+")'>Redo</button> &nbsp;&nbsp;" +
			"<button class='btn btn-primary btn-sm' data-toggle='modal' data-target='#updateform' onclick='initEdit("+i+")'>Edit</button> &nbsp;&nbsp;" +
			"<button class='btn btn-danger btn-sm' onclick='markDone("+i+")'>Delete</button></td></td>" +
			"</tr>";	
		}
	}
	return result+"</table>";
};

function markDone(index){
	if(confirm("Change '"+currentdata[index].title+"' status to Done/Redo ?")){
		var tosend=toFullJsonString(currentdata[index].id,currentdata[index].title, currentdata[index].body, currentdata[index].done);
		$.ajax({
			type:"POST",
			url:"/rest/todo/markdone",
			contentType:"application/json",
			data:tosend,
			success: function(data){
				successfulNotification("Marked as Completed.");
			},
			error: function(data){
				unsuccessfulNotification("Error Occurred!");
			}
		});
	}
};

function initEdit(index){
	currentindex=index;
	$("#update_title").val(currentdata[index].title);
	$("#update_desc").val(currentdata[index].body);
};

function updateToDo(){
	var tosend=toFullJsonString(currentdata[currentindex].id,$("#update_title").val(),$("#update_desc").val(),currentdata[currentindex].done);
	$.ajax({
		type:"POST",
		url:"/rest/todo/update",
		contentType:"application/json",
		data:tosend,
		success: function(data){
			successfulNotification("Updated Successfully.");
		},
		error: function(data){
			unsuccessfulNotification("Error Occurred!");
		}
	});
}

function deleteToDo(index){
	if(confirm("Are you sure you want to delete '"+currentdata[index].title+"' ?")){
		var tosend=toFullJsonString(currentdata[index].id,currentdata[index].title, currentdata[index].body, currentdata[index].done);
		$.ajax({
			type:"DELETE",
			url:"/rest/todo/delete",
			contentType:"application/json",
			data:tosend,
			success: function(data){
				successfulNotification("Successfully Deleted.");
			},
			error: function(data){
				unsuccessfulNotification("Error Occurred!");
			}
		});
	}
};

function toFullJsonString(id,title,body,done){
	return '{"id":"'+id+'","title":"'+title +'","body":"'+body+'","done" : "'+done+'"}';
};

function toJsonString(title,body,done){
	return '{"title":"'+title +'","body":"'+body+'","done" : "'+done+'"}';
};

function successfulNotification(message){
	$("#success").text(message);
	$("#failure").hide();
	$("#success").show();
	setTimeout('location.reload()',800);
};

function unsuccessfulNotification(message){
	$("#failure").text(message);
	$("#failure").show();
	$("#success").hide();
	setTimeout('location.reload()',800);
};

$(document).ready(function(){
	$("#success").hide();
	$("#failure").hide();
	$("#btn_search").click(function(){
		var tosearch=$("#search").val();
			$.ajax({
				type:"POST",
				url:"/rest/todo/search",
				contentType:"text/plain",
				data:tosearch,
				dataType:"json",
				success: function(data){
					currentdata = data;
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