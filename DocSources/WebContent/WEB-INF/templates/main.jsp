<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:url var="MainContentMacURL" value="/styles/1024/MainContent_mac.css"/>

<c:url var="MainContentLinuxURL" value="/styles/1024/MainContent_linux.css"/>

<c:url var="TemplateLinuxURL" value="/styles/1024/Template_linux.css"/>
			
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<base href="${pageContext.request.contextPath}"></base>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Bia - the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.png"/>" />

		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/AdministrationMenu.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/Template.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />"/>
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/demo_table.css"/>" />
<!-- 	<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/TableTools.css"/>" />  -->
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery-ui.css"/>" />
		<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/styles/1024/js/jquery.Jcrop.css" />"/>
		
		
		<!--[if lte IE 7]>
		<style type="text/css">
		html .jquerycssmenu{height: 1%;} /*Holly Hack for IE7 and below*/
		</style>
		<![endif]-->
		
		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
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
		<script type="text/javascript" src="<c:url value="/scripts/jquery.form.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.Jcrop.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.color.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.open.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.shareButton.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.volumeExplorer.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/pirobox_extended.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.tooltip.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/prototype.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/effects.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/modalbox.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.scrollTo.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.expander.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/scrolltopcontrol.js"/>"></script>
		
		<script type="text/javascript">
	           window.onbeforeunload = function() {
	               return "If you leave this page you will exit the Software Platform";
	           };
	           
	          

		</script>


		<script type="text/javascript">
			history.go = function(){};

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
			
			if (navigator.appVersion.indexOf("Linux")!=-1)
				document.write('<link href="${MainContentLinuxURL}" rel="stylesheet" type="text/css">');
			 
			 if (navigator.appVersion.indexOf("X11")!=-1)
				 document.write('<link href="${MainContentLinuxURL}" rel="stylesheet" type="text/css">');
			var $timerId;
			
		</script>
	</head>
	
	<body>
		<div id="layout">

			<div id="map"></div>
			<!-- POINTER DIVS -->
            <div id="searchFormPointer"></div>
            <div id="advancedSearchPointer"></div>
            <div id="communityForumsPointer"></div>
            <div id="communityForumsPointer_guest"></div>
			<div id="top_top">
<tiles:insertAttribute name="searchForm"/>
<tiles:insertAttribute name="lastEntryMenu" />

			</div>
			<div id="top_middle">
				<div id="mainmenu" class="menumain">
					<ul>
<tiles:insertAttribute name="advancedSearchMenu" />					
<tiles:insertAttribute name="dataEntryMenu" />
<tiles:insertAttribute name="communityForumsMenu" />
<tiles:insertAttribute name="historyMenu" />
<tiles:insertAttribute name="helpMenu" />
<tiles:insertAttribute name="logoutMenu" />
					</ul>
				</div>
			</div>
			<div id="top_bottom" class="docs">
			<ul>
<tiles:insertAttribute name="administratorMenu"/>
<tiles:insertAttribute name="digitizationMenu"/>
<tiles:insertAttribute name="messagesMenu" />
<tiles:insertAttribute name="profileMenu" />
<tiles:insertAttribute name="markedListMenu"  />
			</ul>
<tiles:insertAttribute name="whoAmIMenu"/>
			</div>
<tiles:insertAttribute name="main"/>
		</div>
	</body>
</html>
