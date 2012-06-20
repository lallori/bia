<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    	<title>Browse or search "Schedoni"</title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="images/favicon_medici.png"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery-ui-1.8.13.custom.css"/>" />

		<script type="text/javascript" src="<c:url value="/scripts/jquery.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/pirobox_extended.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery-ui-1.8.16.custom.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.multi-open-accordion-1.0.1.js" />"></script>
		<script type='text/javascript' src="<c:url value="/scripts/jquery.blockUI.js" />"></script>
        <script type="text/javascript" src="<c:url value="/scripts/jquery.open.js" />"></script>
		
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
       	<h1>BROWSE OR SEARCH "SCHEDONI"</h1>
    </div>
    <div id="browseContent">
    	<div class="listAdvSearch">
        	<form:form id="browseSearchSchedoni" method="post" class="edit">
            	<div class="row">
                	<div class="col_l">
                    	<form:select id="volumeType" name="volumeType" class="selectform_long" path="searchType">
                        	<option value="Exactyly" selected="selected">Exactly</option>
                            <option value="Between">Between</option>
                            <option value="All">All</option>
                        </form:select>
                    </div>
                    <div class="col_l"><form:input id="volumeSearch" name="volumeSearch" class="input_5c" type="text" value="" maxlength="5" path="volNum"/></div>
                    <div class="col_l"><form:input id="betweenSearch" name="betweenSearch" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden" path="volNumBetween"/></div>
                    <div class="col_r"><input type="submit" id="search" value="Search" title="Search this 'Schedone'"></div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">
		$j(document).ready(function() {
			$j("#volumeType").change(function(){
						if(this.options[1].selected) {
							   $j('#betweenSearch').css('visibility','visible'); 
							   $j('#volumeSearch').css('visibility','visible'); 
						}
						if(this.options[2].selected){
							   $j('#volumeSearch').css('visibility','hidden'); 
							   $j('#betweenSearch').css('visibility','hidden');
						}
						if(this.options[0].selected) {
							   $j('#betweenSearch').css('visibility','hidden'); 
							   $j('#volumeSearch').css('visibility','visible'); 
						}
			   });	
			$j("#search").click(function(){
						window.opener.$j("#body_right").load('/DocSources/dm/Risultati.html');
						window.close();						
						return false;}
						);	
		});
		</script>
</body>
</html>

