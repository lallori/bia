<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="google-site-verification" content="3yG_45ULuCtRoXlplTjaPVFRHCEbtl_D1sSPYnVtSQk" />
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>

		<title>${fn2:getApplicationProperty("project.name")}</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.png"/>" />
		<link rel="stylesheet" media="screen" href="<c:url value="/styles/1024/LoginUser.css"/>" />
		<!-- <link rel="stylesheet" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/LoginUser.css"/>" />
		<link rel="stylesheet" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/LoginUser.css"/>" /> -->
		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>

		<script>
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				// This control is to redirect on top.location if this page in loaded in a div
			    if (top.location.href.indexOf('LoginUser.do') == -1) {
			        top.location.href = "<c:url value="/LoginUser.do"/>";
			    }
		    });
			var RecaptchaOptions = {
			   theme : 'clean'
			};
		</script> 
	</head>
	
	<body>

		<div id="layout">
			<div id="map" class="login"></div>
<tiles:insertAttribute name="main"/>
<!--<tiles:insertAttribute name="footer"/>-->
		</div>
	</body>
</html>
