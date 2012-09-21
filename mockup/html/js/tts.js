$(document).ready(function(){

	$("#show-files-to-upload").addClass('display-off');
	$("#show-files-to-upload-background").addClass('display-off');


	$("input[name='files[]']").bind('change', function(evt){

		var selectedFiles = evt.target.files;
		var file;
		var html="";
		var readableFileSize;
		for (var i = 0; i < selectedFiles.length; i++){
			file = selectedFiles[i];
			if ((file.size / 1024 ) > 1024){
				readableFileSize = ((file.size / 1024) / 1024) + 'MB';
			}
			else{
				readableFileSize = (file.size / 1024) + 'KB';
			}
			html = html + '<li>';
			html = html + '<div class=\'file-image\'><img src=\'img/unknown.png\'/></div>';
			html = html + '<div class=\'file-name\'>' + file.name + '</div>';
			html = html + '<div class=\'file-size\'>' + readableFileSize + '</div>';
			html = html + '<div class=\'file-download\'>Enviar</div>';
			html - html + '</li>';
		}

		$('#list-selected-files').html(html);
		
		$("#show-files-to-upload").removeClass('display-off');
		$("#show-files-to-upload").addClass('display-on');

		$('#show-files-to-upload-background').removeClass('display-off');
		$('#show-files-to-upload-background').addClass('display-on');
	});

	$('#show-files-to-upload-title-close').bind('click', function(evt){
		$("#show-files-to-upload").removeClass('display-on');
		$("#show-files-to-upload").addClass('display-off');

		$('#show-files-to-upload-background').removeClass('display-on');
		$('#show-files-to-upload-background').addClass('display-off');
	});
});



	
