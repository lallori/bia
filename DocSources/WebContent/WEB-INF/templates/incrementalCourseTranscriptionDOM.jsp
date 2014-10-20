<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!--
	The Medici Archive Project Inc. IT team: 

	Lorenzo Allori <lorenzo.allori@gmail.com>
	Ronny Rinaldi <rinaldi.ronny@gmail.com>
	Joana Amill <joana.amill@gmail.com>
--> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>${fn2:getApplicationProperty("project.name")} - Courses</title>
		
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Ronny Rinaldi &lt;rinaldi.ronny@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>
		
		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/1024/js/jquery-ui.css'/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/teaching.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/incrementalTranscription.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/styles/1024/js/jquery_layout.css'/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/modalbox.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/modalbox-custom.css" />" />
		
		<script type="text/javascript" src="<c:url value='/scripts/jquery.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/jquery-ui.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/forum/jquery.scrollTo-min.js"/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/teaching/jquery.layout.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/teaching/teaching.commons.js'/>"></script> <!-- ??? -->
		<script type='text/javascript' src="<c:url value="/scripts/jquery.blockUI.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/prototype.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/effects.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/ModalBox/modalbox.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/forum/tiny_mce.js"/>"></script>
		
		<script type="text/javascript">
		
			var $j = jQuery.noConflict();
		
			var myLayout; // a var is required because this page utilizes: myLayout.allowOverflow() method
			
// 			var adjustPageTurnerPosition = function() {
// 				var pageTurner = $j("#mainFrame").contents().find("#PageTurnerVerticalDiv").parent();
// 				if (typeof pageTurner !== 'undefined') {
// 					var newLeft = $j("#mainFrame").contents().find("body").width() - $j(pageTurner).width();
// 					$j(pageTurner).css('left', newLeft + 'px');
// 				}
// 			}
			
			$j(document).ready(function () {
				
				$j.ajaxSetup ({
					// Disable caching of AJAX responses
					cache: false
				});
		
				myLayout = $j('body.teachingModule').layout({
					west__size:					screen.width / 2
				,	west__spacing_closed:		20
				,	west__togglerLength_closed:	100
				,	west__togglerAlign_closed:	"top"
				,	west__togglerContent_closed:"M<BR>E<BR>N<BR>U"
				,	west__togglerTip_closed:	"Open & Pin Menu"
				,	west__sliderTip:			"Slide Open Menu"
				,	west__slideTrigger_open:	"mouseover"
				,	center__maskContents:		true // IMPORTANT - enable iframe masking
				,	west__onresize_end:	function() {
					setTimeout(function() {
// 						var pageTurner = $j("#mainFrame").contents().find("#PageTurnerVerticalDiv").parent();
// 						if (typeof pageTurner !== 'undefined') {
// 							var newLeft = $j("#mainFrame").contents().find("body").width() - $j(pageTurner).outerWidth();
// 							$j(pageTurner).css('left', newLeft + 'px');
// 						}
					}, 5);
				}
				});
				
		 	});
	
		</script>
	</head>
	<body class="teachingModule">
		<tiles:insertAttribute name="main"/>
	</body>
</html>