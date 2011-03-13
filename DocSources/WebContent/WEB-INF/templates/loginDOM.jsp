<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>

		<title>the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/LoginUser.css"/>" />

		<script type="text/javascript" src="<c:url value="/scripts/jquery-1.5.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.cssLoader.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>

		<script>
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j(document).cssLoader({
					contextPath : "<c:url value="/"/>",
					forceResolution : 0,
					stylePath : "styles/",
					styleSheets : ["LoginUser.css"]});
				/*if (location.pathname != "<c:url value="/LoginUser.do"/>" ) {
					window.location = "<c:url value="/"/>";
				}*/
		    });
			var RecaptchaOptions = {
			   theme : 'clean'
			};
		</script> 
	</head>
	
	<body>

		<div id="layout">
			<div id="map"></div>
<tiles:insertAttribute name="main"/>
<tiles:insertAttribute name="footer"/>
		</div>
	</body>
</html>
