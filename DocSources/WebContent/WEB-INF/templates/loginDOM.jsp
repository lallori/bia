<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>the MEDICI ARCHIVE PROJECT</title>
	<link href="<c:url value="/styles/style.css"/>" rel="stylesheet" type="text/css" />
	<script>
			var RecaptchaOptions = {
			   theme : 'clean'
			};
	</script> 
</head>

<body>
	<div id="layout">
		<div id="map"><a href="<c:url value="/LoginUser.do"/>"><img src="<c:url value="/images/map.png"/>" width="270" height="175" alt="the Medicis Archive Project" /></a></div>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
	</div>
</body>
</html>
