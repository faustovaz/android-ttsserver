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
	},
	closePopup: function(){
		popupManager.hidePopup();
		window.location.reload();
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
			html = html + '<div class=\'file-upload-button\' id=\'' + i + '\'>Enviar</div>';
			html - html + '</li>';
		}

		return html;
	}
};


var uploaderHandler = {
	targetHTMLElement: null,

	startUpload: function(evt){
		evt.stopPropagation();
		this.targetHTMLElement = evt.target;
		var index = this.targetHTMLElement.id;
		var file = $("#input-file")[0].files[index];
		var formData = new FormData();
		formData.append("file", file);
		this.send(formData)
	},
	send: function(formData){
        var httpRequest = new XMLHttpRequest();
        httpRequest.upload.targetHTMLElement = this.targetHTMLElement;
        httpRequest.upload.addEventListener("progress", this.onUploadProgress, false);
        httpRequest.upload.addEventListener("load", this.onUploadComplete, false);
        httpRequest.upload.addEventListener("error", this.onUploadFail, false);
        httpRequest.upload.addEventListener("abort", this.onUploadCancel, false);
        httpRequest.open("POST", "index.html", true);
        httpRequest.send(formData);	
	},

	onUploadProgress: function(evt){
		if (evt.lengthComputable){
			var percent = Math.round((evt.loaded * 100) / evt.total);
			this.targetHTMLElement.innerHTML = 'Enviando...  ' + percent + "%";
		}
	},

	onUploadComplete: function(){
		this.targetHTMLElement.innerHTML = 'Enviado';
	}
};

var eventHandlers = {
	init: function(){
		popupManager.hidePopup();
		$("#input-file").bind('change', this.changeFileInputHandler);
		$('#show-files-to-upload-title-close').bind('click', popupManager.closePopup);
		$("#list-selected-files").on("click", "div.file-upload-button", this.uploadButtonClickedHandler);
	},	
	changeFileInputHandler: function(evt){
		$('#list-selected-files').html(selectedFilesManager.processSelectedFiles(evt));
		popupManager.showPopup();
	},
	uploadButtonClickedHandler: function(evt){
		var uploader = Object.create(uploaderHandler);
		uploader.startUpload(evt);
	}
};

$(document).ready(function(){
	eventHandlers.init();
});



	
