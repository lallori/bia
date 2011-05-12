<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="advancedSearchModal">
		<h1>Click the button below to search in one of the following categories:</h1>
		
		<a id="documentSearch" class="pirobox" rel="iframe-full-full" href="<c:url value="/src/AdvancedSearchDocuments.do"/>"></a>
		<a id="personSearch" class="pirobox" rel="iframe-full-full" href="<c:url value="/src/AdvancedSearchPeople.do"/>"></a>
		<a id="volumeSearch" class="pirobox" rel="iframe-full-full" href="<c:url value="/src/AdvancedSearchVolumes.do"/>"></a>
		<a id="placeSearch" class="pirobox" rel="iframe-full-full" href="<c:url value="/src/AdvancedSearchPlaces.do"/>"></a>
		<a id="savedFilters" class="advSearchButton" href="<c:url value="/src/AdvancedSearchDocuments.do"/>"></a>
	
		<div id="CloseButton">
			<input id="close" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value=""/>
		</div>
	</div>
	
	
	<script>
	$j(document).ready(function() {
		$j(".piro_overlay, .piro_html").remove();
		$j().piroBox_ext({
			piro_speed: 700,
			bg_alpha: 0.5,
			piro_scroll: true
		});
		
	    // This closes modal window
		$j(".advSearchButton").click(function() {										
			Modalbox.hide(); return false;
		});

		$j(".pirobox").click(function() {
			Modalbox.hide();
			return false;
		});

		$j("#close").click(function() {
			Modalbox.hide();
			return false;
		});
		
	});
	</script>