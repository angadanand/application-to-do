/*
 * Hacking away at some JQuery and Java Script
 * I am not very good with JS and JQuery and I know there are many functions I can put into functions
 * but I chose to do it the quickest way possible
 * 
 * All RestFul services have used relative path since they exist in the same domain under /rest
 */
var currentdata;
var currentindex;
function tableString(data,done, welcomestring){
	var result="<h3 style='text-align:center'>"+welcomestring+"</h3>" +
			"<table width='90%' border='1' style='margin-left:auto; margin-right:auto;'>" +
	"<tr> <th>Title</th> " +
	"<th>Body</th> " +
	"<th>Options</th> </tr>";
	for(var i=0;i<data.length;i++){
		result+="<tr>" +
		"<td>"+data[i].title+"</td>" +
		"<td>"+data[i].body+"</td>" +
		"<td align='center'><button class='btn btn-primary btn-sm' onclick='markDone("+i+")'>"+done+"</button> &nbsp;&nbsp;" +
		"<button class='btn btn-primary btn-sm' data-toggle='modal' data-target='#updateform' onclick='initEdit("+i+")'>Edit</button> &nbsp;&nbsp; " +
		"<button class='btn btn-danger btn-sm' onclick='deleteToDo("+i+")'>Delete</button></td>" +
		"</tr>";
	}
	return result+"</table>";
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
}

// For some reason method overloading was not supported/not working. 
function toFullJsonString(id,title,body,done){
	return '{"id":"'+id+'","title":"'+title +'","body":"'+body+'","done" : "'+done+'"}';
}

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
	
	function addDefaultTable(){
		$.ajax({
		type:"GET",
		url:"/rest/todo/notdonelist",
		dataType:"json",
		success: function(data){
			currentdata = data;
			var res = tableString(data,"Done","To Do's");
			$("#todotable").empty();
			$("#todotable").append(res);
			}
		});
	};
	
	addDefaultTable();
	
	$("#btn_restore").click(function(){
		$.ajax({
			type:"GET",
			url:"/rest/todo/restore",
			success: function(data){
				successfulNotification(data)
			},
			error: function(data){
				unsuccessfulNotification(data)
			}
		});
	});
	
	$("#btn_addtodo").click(function(){
		var c = toJsonString($("#title").val(), $("#desc").val(),false);
		$.ajax({
			type:"POST",
			url:"/rest/todo",
			contentType:"application/json",
			data:c,
			success: function(data){
				successfulNotification(data);
			},
			error: function(data){
				unsuccessfulNotification("Error try Again.");
			}
		});
	});
	
	$("#btn_shownotdone").click(function(){
		$.ajax({
			type:"GET",
			url:"/rest/todo/notdonelist",
			dataType:"json",
			success: function(data){
				currentdata = data;
				var res = tableString(data,"Done","To Do's");
				$("#todotable").empty();
				$("#todotable").append(res);
				}
		});
	});
	
	$("#btn_showdone").click(function(){
		$.ajax({
			type:"GET",
			url:"/rest/todo/donelist",
			dataType:"json",
			success: function(data){
				currentdata = data;
				var res = tableString(data,"Redo","(Completed)To Do's");
				$("#todotable").empty();
				$("#todotable").append(res);
			}
		});
	});
});