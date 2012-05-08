<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- <base href="/DocSources"></base> -->
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>the MEDICI ARCHIVE PROJECT - Forums</title>
        
        <link rel="shortcut icon" type="image/x-icon" href="/DocSources/images/favicon_medici.png" />
        <link rel="stylesheet" type="text/css" href="/DocSources/styles/forum/forum.css" />
        <link rel="stylesheet" type="text/css" media="all" href="/DocSources/styles/1024/js/jquery-ui-1.8.13.custom.css" />
        
        <script type="text/javascript" src="<c:url value="/scripts/jquery.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/jquery.scrollTo-min.js"/>"></script>	
        <script type="text/javascript" src="<c:url value="/scripts/jquery-ui.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/jquery.dialogextend.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/forum/tinyeditor.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/forum/packed.js"/>"></script>
        
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
			<div id="map"></div>
            
			<tiles:insertAttribute name="search"/>
             
             <div id="top_bottom">
             	<tiles:insertAttribute name="chronology"/>
             	<tiles:insertAttribute name="user"/>
             	<tiles:insertAttribute name="members"/>
             </div>
             
             <div id="main">
             	<tiles:insertAttribute name="main"/>
                
                <tiles:insertAttribute name="footer"/>
           </div>
		</div>
	</body>
</html>