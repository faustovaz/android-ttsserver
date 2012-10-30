<html>
<head>
	<title>${title}</title>
</head>
<body>
	<h1>Hi, ${name}. You were generated using ${framework_name}</h1>
	<div>
		<#list vars as var>
			<div>${var}</div>
		</#list>
	</div>
</body>
</html>