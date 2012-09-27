var popupManager = {
	hidePopup: function(){
		$("#show-files-to-upload").addClass('display-off');
		$("#show-files-to-upload-background").addClass('display-off');
	},
	showPopup: function(){
		$("#show-files-to-upload").removeClass('display-off');
		$("#show-files-to-upload").addClass('display-on');
		$('#show-files-to-upload-background').removeClass('display-off');
		$('#show-files-to-upload-background').addClass('display-on');
	}
};

var selectedFilesManager = {
	calculateFileSize: function(fileSize){
		var size = fileSize / 1024;
		if((size) > 1024){
			return Math.round(size / 1024) + "MB"; 
		}
		else{
			return Math.round(size) + "KB"
		}
	},
	processSelectedFiles: function(evt){
		var selectedFiles = evt.target.files;
		var html="";
		for (var i = 0; i < selectedFiles.length; i++){
			var file = selectedFiles[i];
			html = html + '<li>';
			html = html + '<div class=\'file-image\'><img src=\'img/unknown.png\'/></div>';
			html = html + '<div class=\'file-name\'>' + file.name + '</div>';
			html = html + '<div class=\'file-size\'>' + this.calculateFileSize(file.size) + '</div>';
			html = html + '<div class=\'file-upload-button\'>Enviar</div>';
			html - html + '</li>';
		}
		return html;
	}
}


function Uploader(){
		var files = $("#input-file");
		var file = files[0].files[0];
		
		  var fd = new FormData();
          fd.append("fileToUpload", file);
          var xhr = new XMLHttpRequest();
         	xhr.upload.addEventListener("progress", function(evt){
         		if(evt.lengthComputable){
         			console.log(evt.loaded);
         		}
         	}
         	, false);
         xhr.addEventListener("load", function(){alert("fecho mano brown")}, false);
         // xhr.addEventListener("error", uploadFailed, false);
         // xhr.addEventListener("abort", uploadCanceled, false);
          xhr.open("POST", "http://localhost:8086/index.html", true);
          xhr.send(fd);		
}


var eventHandlers = {
	init: function(){
		popupManager.hidePopup();
		$("#input-file").bind('change', this.changeFileInputHandler);
		$('#show-files-to-upload-title-close').bind('click', popupManager.hidePopup);
		$("#list-selected-files").on("click", "div.file-upload-button", this.uploadButtonClickedHandler);
	},	
	changeFileInputHandler: function(evt){
		$('#list-selected-files').html(selectedFilesManager.processSelectedFiles(evt));
		popupManager.showPopup();
	},
	uploadButtonClickedHandler: function(evt){
		
	}
}

$(document).ready(function(){
	eventHandlers.init();
});



	
