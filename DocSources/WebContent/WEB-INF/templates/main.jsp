<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="${pageContext.request.contextPath}"></base>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />

		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/menu/AdministrationMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/menu/MainMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/menu/ActionsMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/Template.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/MyHistory.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/pirobox.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/demo_table.css"/>" />
<!-- 	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/TableTools.css"/>" />  -->
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery-ui-1.8.13.custom.css"/>" />
<!-- 
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/menu/AdministrationMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/menu/MainMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/menu/ActionsMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/Template.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/Chronology.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/js/demo_table.css"/>" />
		<link rel="stylesheet" type="text/css" media="screen and (max-width: 1024px)" href="<c:url value="/styles/1024/js/TableTools.css"/>" />

		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/menu/AdministrationMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/menu/MainMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/menu/ActionsMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/Template.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/Chronology.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/js/jquery.autocomplete2.css" />"/>
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/js/demo_table.css"/>" />
		<link rel="stylesheet" type="text/css" media="screen and (min-width: 1025px)" href="<c:url value="/styles/1280/js/TableTools.css"/>" />
 -->
		<!--[if lte IE 7]>
		<style type="text/css">
		html .jquerycssmenu{height: 1%;} /*Holly Hack for IE7 and below*/
		</style>
		<![endif]-->
		
		<!--  <script type="text/javascript" src="<c:url value="/scripts/mview/mootools-core-1.3-full-compat-compressed.js"/>"></script>-->
		<!-- <script type="text/javascript" src="<c:url value="/scripts/mview/mootools-more-1.3-full-compat-compressed.js"/>"></script>-->
		<script src="/DocSources/scripts/mview/mootools-core-1.3.2-full-nocompat-yc.js" type="text/javascript"></script>
		<script src="/DocSources/scripts/mview/mootools-more-1.3.2.1.js" type="text/javascript"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
<!-- <script type="text/javascript" src="<c:url value="/scripts/mview/iipmooviewer-1.2.js"/>"></script> -->
		<script type="text/javascript" src="<c:url value="/scripts/jquery.advancedSearch.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.general.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.person.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.place.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.autocomplete.title.js"/>"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mview/jquery.dialogextend.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mview/jquery.pageTurner.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.dataTables.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.dataTables.pagination.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.form-2.47.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.open.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.documentExplorer.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.shareButton.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.volumeExplorer.js"/>"></script>
<%-- 		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui-1.8.16.custom.min.js"/>"></script> --%>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/pirobox_extended.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.tooltip.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/prototype.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/effects.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/modalbox.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.scrollTo.js"/>"></script>
		<!-- <script type="text/javascript" src="<c:url value="/scripts/TableTools.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ZeroClipboard.js"/>"></script> -->

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
			
			
			if (navigator.appVersion.indexOf("Mac")!=-1)
				document.write('<link href="/DocSources/styles/1024/MainContent_mac.css" rel="stylesheet" type="text/css">');
			
			 if (navigator.appVersion.indexOf("Linux")!=-2)
				 document.write('<link href="/DocSources/styles/1024/MainContent_linux.css" rel="stylesheet" type="text/css">','<link href="/DocSources/styles/1024/Template_linux.css" rel="stylesheet" type="text/css">');
			
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
