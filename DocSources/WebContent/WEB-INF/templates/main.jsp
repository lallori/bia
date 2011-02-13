<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="${pageContext.request.contextPath}"></base>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="images/favicon_medici.jpg"/>" />

		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/menu/AdministrationMenu.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/menu/MainMenu.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/menu/ActionsMenu.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Chronology.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/demo_table.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/TableTools.css"/>" />

		<!--[if lte IE 7]>
		<style type="text/css">
		html .jquerycssmenu{height: 1%;} /*Holly Hack for IE7 and below*/
		</style>
		<![endif]-->
		
		<script type="text/javascript" src="<c:url value="/scripts/mootools-core-1.3-full-compat-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mootools-more-1.3-full-compat-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/iipmooviewer-1.1-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-1.5.min.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.person.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.dataTables.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.dataTables.pagination.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.form-2.47.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.open.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.volumeExplorer.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquerycssmenuADM.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquerycssmenuMAIN.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/prototype.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/effects.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/modalbox.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/TableTools.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ZeroClipboard.js"/>"></script>
		
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});
			});
		</script>
	</head>
	
	<body>
		<div id="layout">

			<div id="map"><img src="<c:url value="/images/map.png"/>" alt="the MEDICI ARCHIVE PROJECT" /></div>
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
<tiles:insertAttribute name="administratorMenu"/>
<tiles:insertAttribute name="whoAmIMenu"/>
			</div>
<tiles:insertAttribute name="main"/>
		</div>
	</body>
</html>
