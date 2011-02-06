<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="iipZoom" value="/swf/IIPZoom.swf" />
	<c:url var="expressInstall" value="/swf/expressInstall.swf" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript" src="<c:url value="/scripts/swfobject.js" />"></script>
		<script type="text/javascript">
			var server = "/DocSources/mview/ProxyIIPImage.do";
			var image = "${image}";
			var credit = "${image}";
			var flashvars = {
				server: server,
				serverKeepAlive : "false",
				image: image,
				navigation: true,
				credit: credit
			}
			var params = {
				scale: "noscale",
				bgcolor: "#000000",
				allowfullscreen: "true",
				allowscriptaccess: "always",
				wmode: "transparent" 
			}
			swfobject.embedSWF("${iipZoom}", "container", "100%", "100%", "9.0.0","${expressInstall}", flashvars, params);
		</script>
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

