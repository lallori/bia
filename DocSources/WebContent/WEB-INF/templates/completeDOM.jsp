<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />

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
    </head>
    <body>
        <div id="nonFooter">
            <div id="content">
                <div id="content_main">
                    <tiles:insertAttribute name="main"/>
                </div>
            </div>
        </div>
        <tiles:insertAttribute name="footer"/>
    </body>
</html>
