<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/app.css">
	<script src="http:////ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>
	<script src="js/app.js"></script>
</head>
<body>
	<div id="main">
		<div id="content">
			<div id="fork-me">
				<img src="img/fork-me.png" />
			</div>
			<div id="upload-files">
				<div id="upload-files-text">
					Arraste seus</br>arquivos aqui</br>ou selecione-os
					<!-- Drag n' drop</br>your files here</br>or select them -->
				</div>
				<div id="upload-files-holder-button">	
					<div id="upload-files-button">
						Selecione-os
						<input type='file' name='files[]' multiple id='input-file'/>
					</div>
				</div>
			</div>
			<div id="files">
				<ul class="file-list">	
					<#list files as file>
					<li>
						<div class="file-image"><img src="img/${file.extension}.png"/></div>
						<div class="file-name">${file.normalizedFileName}</div>
						<div class="file-size">${file.normalizedSize}</div>
						<div class="file-download"><a href='download/${file.name}'>Download</a></div>
					</li>
					</#list>																					
				</ul>
			</div>
		</div>
	</div>
	<div id="show-files-to-upload">
		<div id='show-files-to-upload-title-bar'>
			<div id='show-files-to-upload-title'>Arquivos selecionados</div>
			<div id='show-files-to-upload-title-close'>[X]</div>
		</div>
		<ul class="file-list" id="list-selected-files">																		
		</ul>
	</div>
	<div id="show-files-to-upload-background" />
</body>
</html>