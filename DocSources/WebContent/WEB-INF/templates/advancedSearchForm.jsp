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
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/demo_table.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery-ui-1.8.13.custom.css"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>
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
		
		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.advancedSearch.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.general.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.person.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.place.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.title.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.open.js"/>"></script>
		
		<script type="text/javascript" src="<c:url value="/scripts/jquery.multi-open-accordion-1.0.1.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mview/jquery.dialogextend.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.tooltip.js"/>"></script>
		
		
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
		<script type="text/javascript">
			$j(document).ready(function() {
				$j('.helpIcon').tooltip({
					track: true,
					fade: 350 
				});
			});
		</script>
	</head>
	
	<body>
		<div id="advancedSearch">
			<div id="advancedSearch_top">
			</div>
			<div id="body_left">
				<tiles:insertAttribute name="advancedSearchForm"/>
			</div>
			
			<div id="body_right">
				<div id="yourSeachFilterDiv">
					<tiles:insertAttribute name="advancedSearchFilter"/>
				</div>
			</div>
		</div>
		
	</body>
</html>


