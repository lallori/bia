<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="advancedSearchModal">
		<h1>Click the button below to search in one of the following categories:</h1>
		
		<a id="documentSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=DOCUMENT"/>">Documents</a>
		<a id="volumeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=VOLUME"/>">Volumes</a>
		<a id="personSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=PEOPLE"/>">People</a>
		<a id="placeSearch" class="advSearchButton" href="<c:url value="/src/AdvancedSearch.do?searchType=PLACE"/>">Places</a>
		<a id="savedFilters" href="<c:url value="/src/ShowUserSearchFilters.do"/>">Your saved filters</a>
	
		<div id="CloseButton">
			<input id="close" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
	</div>
	
	
	<script>
	$j(document).ready(function() {
		$j("#documentSearch").open({windowName: "Advanced Search Documents", width: 960, height: 350, scrollbars: "yes"});
			    
		$j("#personSearch").open({windowName: "Advanced Search People",width: 980, height: 350, scrollbars: "yes"});
		
		$j("#volumeSearch").open({windowName: "Advanced Search Volume",width: 980, height: 350, scrollbars: "yes"});
				
		$j("#placeSearch").open({windowName: "Advanced Search Place",width: 980, height: 350, scrollbars: "yes"});		

		$j("#savedFilters").click(function(){
			Modalbox.show($j(this).attr("href"), {title: "SAVED SEARCH FILTERS", width: 760, height: 415});
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