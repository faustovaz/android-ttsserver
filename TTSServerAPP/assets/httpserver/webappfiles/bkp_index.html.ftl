<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>TTSlabs - PenMob</title>
</head>
<body>
	<form action="index.html" method="post" enctype="multipart/form-data">
		<fieldset>
		<label>Arquivo:</label>
		<input type="file" id="arquivo" name="arquivoName"/>
		<input type="submit" id="button" value="ok">
		</fieldset>
	</form>
	<div>
		<?devicefiles?>
	</div>
	<form action="index.html" method="post">
		<input type="submit" value="Send" name="btnSend" id="btnSend"/>
		<input type="hidden" value="Value1" name="hidden1" id="hidden1"/>
		<input type="hidden" value="Value2" name="hidden2" id="hidden2"/>
	</form>
	<a href="404.html">CLica aqui</a>	
	<div>
		<#list files as file>
		<div><a href="download/${file}">${file}</a></div>
		</#list>
	</div>
</body>
</html>
