<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ServerURL" value="/mview/ReverseProxyIIPImage.do"></c:url>
	<c:url var="iipZoom" value="/swf/IIPZoom.swf" />
	<c:url var="expressInstall" value="/swf/expressInstall.swf" />

		<script type="text/javascript">
			var server = "${ServerURL}";
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
