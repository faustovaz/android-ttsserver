<html>
<head>
	<title>TTS::Gah-veta</title>
	<link rel="stylesheet" type="text/css" href="css/app.css">
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
					<button id="upload-files-button">
						Selecione-os
						<!-- Select them -->
					</button>
				</div>
			</div>
			<div id="files">
				<ul id="file-list">	
					<#list files as file>
					<li>
						<div class="file-image"><img src="img/${file.extension}.png"/></div>
						<div class="file-name">${file.name}</div>
						<div class="file-size">${file.normalizedSize}</div>
						<div class="file-download"><a href='download/${file.name}'>Download</a></div>
					</li>
					</#list>															
				</ul>
			</div>
		</div>
	</div>
</body>
</html>