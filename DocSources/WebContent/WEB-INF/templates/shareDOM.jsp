<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    	<title>the MEDICI ARCHIVE PROJECT - Document Report</title>
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg" />"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css" />"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css" />"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery-ui-1.8.10.custom.css" />"/>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
		
		<script type="text/javascript">var switchTo5x=true;</script>
		<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
		<script type="text/javascript">stLight.options({publisher: "01c07551-45ac-4943-9792-a10bb7dc089a"});</script>
		
        <style type="text/css">
			
			@media print {
				input {
					display: none;
				}
				
				#notes, #top, h4 {
					display: block;
				}
				
				.print, .pdf {
					display: none;
				}
				
				div#footer {
					display: block; 
					bottom: 0;
				}
				
				@page {
					margin-bottom: 3cm;
				}
				
				@page :left {
					margin-top: 2cm;
				}
			}
		</style>
		<script type="text/javascript">
			var $j = jQuery.noConflict();
		</script>
	</head>
	
	<body>
		<tiles:insertAttribute name="main"/>
	</body>
</html>


