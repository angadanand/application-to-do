/**
 * 
 */

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

function unsuccessfulNotificationNoReload(message){
	$("#failure").text(message);
	$("#failure").show();
	$("#success").hide();
};


$(document).ready(function(){
	$("#success").hide();
	$("#failure").hide();
	
	$.ajax({
		type:"GET",
		url:"/rest/todo/storrednumber",
		dataType:"text",
		success: function(data){
			$("#currentclientnumber").text(data);
		},
		error: function(data){
			unsuccessfulNotificationNoReload("Server contact error.");
		}
	});
	
	$("#btn_deletenotdone").click(function(){
		if(confirm("Are you sure you want to delete All from To Do list?")){
			$.ajax({
				type:"DELETE",
				url:"/rest/todo/delete/notdonelist",
				dataType:"text",
				success: function(data){
					successfulNotification(data);
				},
				error: function(data){
					unsuccessfulNotification(data);
				}
			});
		}
	});
	
	$("#btn_deletedone").click(function(){
		if(confirm("Are you sure you want to delete the list of completed tasks?")){
			$.ajax({
				type:"DELETE",
				url:"/rest/todo/delete/donelist",
				dataType:"text",
				success: function(data){
					successfulNotification(data);
				},
				error: function(data){
					unsuccessfulNotification(data);
				}
			});
		}
	});
	
	$("#btn_deletenum").click(function(){
		if(confirm("Are you sure you want to delete the number on tab?")){
			$.ajax({
				type:"DELETE",
				url:"rest/todo/delete/clientnumber",
				dataType:"text",
				success: function(data){
					successfulNotification(data);
				},
				error: function(data){
					unsuccessfulNotification(data);
				}
			});
		}
	});
	
	$("#btn_updatenumber").click(function(){
		$.ajax({
			type:"POST",
			url:"/rest/todo/changenumber",
			data:$("#clientnumber").val(),
			contentType:"text/plain",
			dataType:"text",
			success: function(data){
				successfulNotification(data);
			},
			error: function(data){
				unsuccessfulNotification(data);
			}
		});
	});
});