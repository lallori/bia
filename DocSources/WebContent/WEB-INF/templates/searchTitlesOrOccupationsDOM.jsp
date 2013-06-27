<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.png"/>" />
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>

		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery-ui-1.8.10.custom.css" />" />

		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>

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
		<div id="searchWindow">
            <div id="searchWindow_top">
            	<h1>SEARCH FOR TITLE/OCCUPATION</h1>
            </div>
            <tiles:insertAttribute name="main"/>
		</div>
	</body>
</html>
