<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_FELLOWS">
	<div id="createPlace">
		<h1><fmt:message key="geobase.createPlaceMenuModal.clickButtonBelow"/></h1>

		<div id="tgnPlace">
			<a id="AddNewTgnPlace" class="button_medium" href="<c:url value="/de/geobase/CreatePlace.do?plSource=TGN"/>"><fmt:message key="geobase.createPlaceMenuModal.tgnPlaceRecord"/></a>
			<p><fmt:message key="geobase.createPlaceMenuModal.recordFromGettyAdded"/>
			<br/><br/>
			<fmt:message key="geobase.createPlaceMenuModal.consultGettyWebsite"/></p>
		
		</div>
	
		<div id="mapPlace">
			<a id="AddNewMapPlace" class="button_medium" href="<c:url value="/de/geobase/CreatePlace.do?plSource=MAPPLACE"/>"><fmt:message key="geobase.createPlaceMenuModal.mapPlaceRecord"/></a>
			<p><fmt:message key="geobase.createPlaceMenuModal.placeNamesMentionedInMapNotFoundTGN"/></p>
		</div>
	
		<div id="mapSite">
			<a id="AddNewMapSitePlace" class="button_medium" href="<c:url value="/de/geobase/CreatePlace.do?plSource=MAPSITE"/>"><fmt:message key="geobase.createPlaceMenuModal.mapSiteSub"/></a>
			<p><fmt:message key="geobase.createPlaceMenuModal.builtStructuresBeingTracked"/></p>
		</div>

		<div id="notes">
			<p><b><fmt:message key="geobase.createPlaceMenuModal.onlineTgnSearch"/></b> <a href="http://www.getty.edu/research/tools/vocabularies/tgn/" target="_blank"><fmt:message key="geobase.createPlaceMenuModal.website"/></a></p>
			<p><i><fmt:message key="geobase.createPlaceMenuModal.noteInAllCases"/></i></p>
		</div>
	
		<div id="CloseButton">
			<input id="close" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
		
		<a id="goBack" href="<c:url value="/de/ShowEntryMenu.do"/>" title="Go Back to Entry Menu"><fmt:message key="geobase.createPlaceMenuModal.goBack"/></a>
	
		
	</div>
		
	<c:url var="menuActionsGeoURL" value="/de/geobase/menuActionsGeo.html"/>
	
	<script>
		$j(document).ready(function() {
			$j("#AddNewTgnPlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("${menuActionsGeoURL}");
					return false;
				}
			);
				
			$j("#AddNewMapPlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("${menuActionsGeoURL}");
					return false;
				}
			);

			$j("#AddNewMapSitePlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("${menuActionsGeoURL}");
					return false;
				}
			);

			$j("#goBack").click(
				function(){
					Modalbox.show($j(this).attr("href"), {title: "ENTRY MENU", width: 750, height: 190});
					return false;
				}
			);
		});
	</script>
	</security:authorize>