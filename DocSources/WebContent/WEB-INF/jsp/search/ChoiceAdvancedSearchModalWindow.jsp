<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="advancedSearchModal">
		<h1>Click the button below to search in one of the following categories:</h1>
		
		<div id="documentSearchDiv">
			<a id="documentSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearchDocuments.do"/>"></a>
		</div>

		<div id="personSearchDiv">
			<a id="personSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearchPeople.do"/>"></a>
		</div>
			
		<div id="volumeSearchDiv">
			<a id="volumeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearchVolumes.do"/>"></a>
		</div>
		
		<div id="placeSearchDiv">
			<a id="placeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearchPlaces.do"/>"></a>
	
		</div>
		
		<div id="objectSearchDiv">
			<a id="objectSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearchDocuments.do"/>"></a>
		</div>
	
		<div id="CloseButton">
			<input id="close" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value=""/>
		</div>
	</div>
	
	
	<script>
	$j(document).ready(function() {
	    $j("#documentSearch").open({width: 920, height: 450, scrollbars: "yes"});
	    
		$j("#personSearch").open({width: 920, height: 450, scrollbars: "yes"});
		
		$j("#volumeSearch").open({width: 920, height: 450, scrollbars: "yes"});
				
		$j("#placeSearch").open({width: 920, height: 450, scrollbars: "yes"});
		
		$j("#objectSearch").open({width: 920, height: 450, scrollbars: "yes"});
		
		// This closes modal window
		$j(".advSearchButton").click(function() {										
			Modalbox.hide(); return false;
		});
		
	});
	</script>