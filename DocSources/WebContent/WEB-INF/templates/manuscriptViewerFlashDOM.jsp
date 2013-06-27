<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

	<c:url var="iipZoom" value="/swf/IIPZoom.swf" />
	<c:url var="expressInstall" value="/swf/expressInstall.swf" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="<c:url value="/scripts/swfobject.js" />"></script>

		<tiles:insertAttribute name="manuscriptviewer" />
       	
       	<style type="text/css">
       		html, body { background-color: #000; height: 100%; overflow: hidden; margin: 0; padding: 0; }
       		body { font-family: Helvetica, Arial, sans-serif; font-weight: bold; color: #ccc; }
       		#container { width: 100%; height: 100%; text-align: center; }
       	</style>
	</head>
	<body>

		<div id="container"></div>

	</body>
</html>

