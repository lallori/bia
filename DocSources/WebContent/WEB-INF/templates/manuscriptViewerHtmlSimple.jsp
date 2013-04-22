<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" 
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<!--
The original iipmooviewer script was written by Ruven Pillay <ruven@users.sourceforge.net>.

This version, named 2.0, was updates and modified to suite the Manuscript Viewer BIA Project needs.

The Medici Archive Project Inc. IT team: 

Lorenzo Allori <lorenzo.allori@gmail.com>
Lorenzo Pasquinelli <lorenzo.pasquinelli@gmail.com>
Joana Amill <joana.amill@gmail.com>
--> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta name="author" content="Ruven Pillay &lt;ruven@users.sourceforge.netm&gt;"/>
		<meta name="author" content="Lorenzo Allori &lt;lorenzo.allori@gmail.com&gt;"/>
		<meta name="author" content="Lorenzo Pasquinelli &lt;lorenzo.pasquinelli@gmail.com&gt;"/>
		<meta name="author" content="Joana Amill &lt;joana.amill@gmail.com&gt;"/>
		<meta name="description" content="IIPImage: High Resolution Remote Image Streaming Viewing"/>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/iip.css"/>" />
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/iip-2.1.css"/>" />
		<!--[if lt IE 10]> 
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/mview/ie.compressed.css"/>" /> 
		<![endif]-->
 		<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/1024/mview.css"/>" />                                                                             
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="/images/favicon_medici.jpg"/>" />
		<title>${fn2:getApplicationProperty("project.name")} - Manuscript Viewer</title>
		<script type="text/javascript" src="<c:url value="/scripts/mview/mootools-core-1.4.5-full-nocompat.js"/>"></script>
  		<script type="text/javascript" src="<c:url value="/scripts/mview/mootools-more-1.4.0.1.js"/>"></script>
  		<script type="text/javascript" src="<c:url value="/scripts/mview/iipmooviewer-2.0.js"/>"></script>
  		<script type="text/javascript" src="<c:url value="/scripts/mview/annotations.js"/>"></script>
  		<script type="text/javascript" src="<c:url value="/scripts/mview/annotations-edit.js"/>"></script>
  		<script type="text/javascript" src="<c:url value="/scripts/mview/protocols/iip.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/mview/lang/help.en.js"/>"></script>
		
		
		<tiles:insertAttribute name="manuscriptviewer" />
	</head>

	<body>
		<div style="width:100%;height:100%;position:absolute" id="targetframe"></div>
	</body>
</html>