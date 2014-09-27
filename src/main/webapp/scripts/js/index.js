/*
 * Hacking away at some JQuery and Java Script
 * I am not very good with JS and JQuery and I know there are many functions I can put into functions
 * but I chose to do it the quickest way possible
 */
var currentdata;
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
		"<td align='center'><button class='btn btn-primary btn-sm'>"+done+"</button> &nbsp;&nbsp;" +
		"<button class='btn btn-primary btn-sm'>Edit</button> &nbsp;&nbsp; " +
		"<button class='btn btn-warning btn-sm'>Delete</button></td>" +
		"</tr>";
	}
	return result+"</table>";
};

function toJsonString(title,body,done){
	return '{"title":"'+title +'","body":"'+body+'","done" : "'+done+'"}';
};

function successfulNotification(message){
	$("#success").text(message);
	$("#success").css("height","auto");
	$("#failure").css("height","0");
	$("#failure").css("visibility","hidden");
	$("#success").css("visibility","visible");
	setTimeout('location.reload()',800);
};

function unsuccessfulNotification(message){
	$("#failure").text(message);
	$("#success").css("height","0");
	$("#failure").css("height","auto");
	$("#failure").css("visibility","visible");
	$("#success").css("visibility","hidden");
	setTimeout('location.reload()',800);
};

$(document).ready(function(){
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
				successfulNotification(data)
			},
			error: function(data){
				unsuccessfulNotification(data)
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