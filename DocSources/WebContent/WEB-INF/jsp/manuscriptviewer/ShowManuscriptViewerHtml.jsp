<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >

	<head>
		<meta name="author" content="Ruven Pillay &lt;ruven@users.sourceforge.netm&gt;"/>
		<meta name="keywords" content="IIPImage Ajax Internet Imaging Protocol IIP Zooming Streaming High Resolution Mootools"/>
		<meta name="description" content="IIPImage: High Resolution Remote Image Streaming Viewing"/>
		<meta name="copyright" content="&copy; 2003-2008 Ruven Pillay"/>
		
		<link rel="stylesheet" type="text/css" media="all" href="/DocSources/styles/iip.compressed.css" />
		<link rel="stylesheet" type="text/css" media="all" href="/DocSources/styles/jquery-ui-1.8.9.custom.css" />
		<link rel="shortcut icon" href="images/iip-favicon.png" />
		<title>IIPMooViewer 1.2 :: IIPImage High Resolution Ajax Image Streaming Viewer</title>
		
		<script type="text/javascript" src="<c:url value="/scripts/mootools-core-1.3-full-compat-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mootools-more-1.3-full-compat-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/iipmooviewer-1.1-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-1.5.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui-1.8.9.custom.min.js"/>"></script>
  
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});

				var $dialog = $j('<div></div>')
				.html('<textarea id="extract" class="txtarea" name="ccontext" id="ccontext" style="width: 100%; height: 94%;"></textarea><br><input value="A submit button" type="submit">')
				.dialog({
					autoOpen: true,
					title: 'Extract',
					closeOnEscape: false,
					open: function(event, ui) { $j(".ui-dialog-titlebar-close").hide(); },
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				});
			});

			iip = new IIP( "targetframe", {
				server: '/DocSources/mview/ProxyIIPImage.do',
				image: '${image}',
				credit: '&copy; copyright or information message', 
				zoom: 1,
				showNavButtons: true,
				render: 'random'
			});

		</script>

 </head>

 <body>
   <div style="width:99%;height:99%;margin-left:auto;margin-right:auto" id="targetframe"></div>

 </body>

</html>

