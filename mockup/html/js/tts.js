$(document).ready(function(){

	$("#show-files-to-upload").addClass('display-off');
	$("input[name='files[]']").bind('change', function(evt){
		var files = evt.target.files;
		var html = "";
		for (var i = 0; i < files.length; i++){
			var file = files[i];
			html = html + "<span>" + file.name + "</span><span>" + file.size + "</span>";
		}

		$("#show-files-to-upload").html(html);
		$("#show-files-to-upload").removeClass('display-off');
		$("#show-files-to-upload").addClass('display-on');
	});
});



	
