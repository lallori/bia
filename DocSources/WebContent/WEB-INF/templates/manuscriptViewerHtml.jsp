<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!--
The original iipmooviewer script was written by Ruven Pillay <ruven@users.sourceforge.net>.

This version, named 1.2, was updates and modified to suite the Manuscript Viewer DocSourcesV5 Project needs.

The Medici Archive Project Inc. IT team: 

Lorenzo Allori <lorenzo.allori@gmail.com>
Lorenzo Pasquinelli <lorenzo.pasquinelli@gmail.com>
Joana Amill <joana.amill@gmail.com>
--> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
	<head>
		<meta name="author" content="Ruven Pillay &lt;ruven@users.sourceforge.netm&gt;"/>
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>
		<meta name="description" content="IIPImage: High Resolution Remote Image Streaming Viewing"/>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/js/iip.compressed.css"/>" />
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/ManuscriptViewer.css"/>" />                                                                                
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/js/jquery-ui-1.8.9.custom.css"/>" /> 
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />
		
		<title>DocSourcesV5 Manuscript Viewer</title>

		<!-- mootools -->

		<script type="text/javascript" src="<c:url value="/scripts/mootools-core-1.3-full-compat-compressed.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mootools-more-1.3-full-compat-compressed.js"/>"></script>
		
		<!--iip-->
		<script type="text/javascript" src="<c:url value="/scripts/iipmooviewer-1.2.js"/>"/></script>
		
		<!-- jquery dialog -->
		<script type="text/javascript" src="<c:url value="/scripts/jquery-1.5.min.js"/>"/></script>                                                                                                
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui-1.8.9.custom.min.js"/>"/></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.dialogextend.min.js"/>"/></script>
		
		<tiles:insertAttribute name="manuscriptviewer" />
	</head>

	<body>
		<div style="width:99%;height:99%;margin-left:auto;margin-right:auto" id="targetframe"></div>
	</body>
</html>