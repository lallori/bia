<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="${pageContext.request.contextPath}"></base>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />

		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/Template.css" />" />
<!-- 
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/Template.css" />" />

		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/Template.css" />" />
 -->
		<!--[if lte IE 7]>
		<style type="text/css">
		html .jquerycssmenu{height: 1%;} /*Holly Hack for IE7 and below*/
		</style>
		<![endif]-->
		
		<script type='text/javascript' src="<c:url value="/scripts/jquery.min.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>
		
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses
					cache: false,
					success: function(data) {
						var found = $j(data).find("#login");
						if (found.length > 0) {
							window.location = "<c:url value="/"/>";
							return;
						}
					},
					error: function(xhr, status, err) {
						console.log(err);
				    }
				});

			});
		</script>
	</head>
	
	<body>
		<div id="layout">

			<div id="map"></div>
			<div id="top_top">
<tiles:insertAttribute name="searchForm"/>
<tiles:insertAttribute name="lastEntryMenu" />

			</div>
			<div id="top_middle">
				<div id="mainmenu" class="menumain">
					<ul>
<tiles:insertAttribute name="dataEntryMenu" />
<tiles:insertAttribute name="profileMenu" />
					</ul>
				</div>
			</div>
			<div id="top_bottom" class="docs">
<!-- tiles:insertAttribute name="administratorMenu"/> -->
<tiles:insertAttribute name="whoAmIMenu"/>
			</div>
<tiles:insertAttribute name="main"/>
		</div>
	</body>
</html>
