<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="advancedSearchModal">
		<h1><fmt:message key="search.choiceAdvancedSearchModalWindow.choiceDescription"/>:</h1>
		
		<a id="documentSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=DOCUMENT"/>"><fmt:message key="search.choiceAdvancedSearchModalWindow.documents"/></a>
		<a id="volumeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=VOLUME"/>"><fmt:message key="search.choiceAdvancedSearchModalWindow.volumes"/></a>
		<a id="personSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=PEOPLE"/>"><fmt:message key="search.choiceAdvancedSearchModalWindow.people"/></a>
		<a id="placeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=PLACE"/>"><fmt:message key="search.choiceAdvancedSearchModalWindow.places"/></a>
		<a id="savedFilters" class="button_large" href="<c:url value="/src/ShowUserSearchFilters.do"/>"><fmt:message key="search.choiceAdvancedSearchModalWindow.yourSavedFilters"/></a>
	
		<div id="CloseButton">
			<input id="close" class="button_small" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
	</div>
	
	
	<script>
	$j(document).ready(function() {
		$j("#documentSearch").open({windowName: "<fmt:message key="search.choiceAdvancedSearchModalWindow.documentsWindowName"/>", width: 960, height: 350, scrollbars: "yes"});
			    
		$j("#personSearch").open({windowName: "<fmt:message key="search.choiceAdvancedSearchModalWindow.peopleWindowName"/>",width: 980, height: 350, scrollbars: "yes"});
		
		$j("#volumeSearch").open({windowName: "<fmt:message key="search.choiceAdvancedSearchModalWindow.volumesWindowName"/>",width: 980, height: 350, scrollbars: "yes"});
				
		$j("#placeSearch").open({windowName: "<fmt:message key="search.choiceAdvancedSearchModalWindow.placesWindowName"/>",width: 980, height: 350, scrollbars: "yes"});		

		$j("#savedFilters").click(function(){
			Modalbox.show($j(this).attr("href"), {title: "<fmt:message key="search.choiceAdvancedSearchModalWindow.savedSearchFiltersWindowName"/>", width: 760, height: 415});
			return false;
		});
	    
		// This closes modal window
		$j(".advSearchButton").click(function() {										
			Modalbox.hide(); return false;
		});

		
		$j("#close").click(function() {
			Modalbox.hide();
			return false;
		});
		
		$j("#MB_close").click(function(){
			Modalbox.hide();
			return false;
		})
		
	});
	</script>