<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    	<title><fmt:message key="digitization.showSearchDigitizedVolumes.browse"/></title>
		<link rel="shortcut icon" type="image/x-icon" href="<c:url value="images/favicon_medici.png"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/MainContent.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/Template.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/1024/js/jquery-ui.css"/>" />

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
<body class="search">

<c:url var="SearchSchedoniURL" value="/digitization/ShowSearchDigitizedVolumes.do"/> 


<div id="searchWindow">
	<div id="searchWindow_top">
       	<h1><fmt:message key="digitization.showSearchDigitizedVolumes.bRowseOrSearch"/></h1>
    </div>
    <div id="browseContent">
    	<div class="listAdvSearch">
        	<form:form id="browseSearchSchedoni" method="post" class="edit" action="${SearchSchedoniURL}">
            	<div class="row">
                	<div class="col_l">
                    	<form:select id="volumeType" name="volumeType" class="selectform_long" path="searchType">
                        	<option value="Exactly" selected="selected"><fmt:message key="digitization.showSearchDigitizedVolumes.exactly"/></option>
                            <option value="Between"><fmt:message key="digitization.showSearchDigitizedVolumes.between"/></option>
                            <option value="All"><fmt:message key="digitization.showSearchDigitizedVolumes.all"/></option>
                        </form:select>
                    </div>
                    <div class="col_l"><form:input id="volumeSearch" name="volumeSearch" class="input_5c" type="text" value="" maxlength="5" path="volNum"/></div>
                    <div class="col_l">
                    	<form:label for="volLetExt" id="volExtensionLabel" path="volLetExt"><fmt:message key="digitization.showSearchDigitizedVolumes.volExt"/></form:label>
                    	<form:label for="volNumBetween" id="betweenSearchLabel" path="volNumBetween" style="display:none"><fmt:message key="digitization.showSearchDigitizedVolumes.and"/></form:label>
                    </div>
                    <div class="col_l">
                    	<form:input id="volExtension" name="volLetExt" class="input_5c" type="text" value="" maxlength="5" path="volLetExt"/>
                    	<form:input id="betweenSearch" name="betweenSearch" class="input_5c" type="text" value="" maxlength="5" style="display:none" path="volNumBetween"/>
                    </div>
                    <div class="col_r"><input type="submit" id="search" class="button_small" value="Search" title="Search this 'Schedone'"></div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script type="text/javascript">
		$j(document).ready(function() {
			$j("#volumeType").change(function(){
				if(this.options[0].selected) {
					$j('#volExtensionLabel').css('display','inherit');
				    $j('#betweenSearchLabel').css('display','none');  
				    $j('#volExtension').css('display','inherit');
				    $j('#betweenSearch').css('display','none');
				    $j('#volumeSearch').css('display','inherit');
			
				}
				if(this.options[1].selected) {
					$j('#volExtensionLabel').css('display','none');
					$j('#betweenSearchLabel').css('display','inherit');  
				    $j('#volExtension').css('display','none');
				    $j('#betweenSearch').css('display','inherit');
				    $j('#volumeSearch').css('display','inherit');  
				}
				if(this.options[2].selected){
				    $j('#volumeSearch').css('display','none');
				    $j('#betweenSearch').css('display','none');
				    $j('#betweenSearchLabel').css('display','none');
				    $j('#volExtensionLabel').css('display','none');
				    $j('#volExtension').css('display','none');
				}
			});

	
			$j("#browseSearchSchedoni").submit(function(){
				if(($j("#volumeType option:selected").val() == 'Exactly' && $j("#volumeSearch").val() == '') || ($j("#volumeType option:selected").val() == 'Between' && ($j("#volumeSearch").val() == '' || $j("#betweenSearch").val() == ''))){
					return false;
				}else{
					var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Schedoni</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					window.close();
				}
				
				return false;
			});
		});
		</script>
</body>
</html>

