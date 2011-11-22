<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
	<div id="createPlace">
		<h1>Click the button below to add a new Preferred Place in one of the following categories:</h1>

		<div id="tgnPlace">
			<a id="AddNewTgnPlace" href="<c:url value="/de/geobase/CreatePlace.do?plSource=TGN"/>">Tgn Place Record</a>
			<p>Record from the Getty Thesaurus of Geographic Names to be added to the Doc Sources system.
			<br/><br/>
			Consult the Getty website from data to add here.</p>
		
		</div>
	
		<div id="mapPlace">
			<a id="AddNewMapPlace" href="<c:url value="/de/geobase/CreatePlace.do?plSource=MAPPLACE"/>">MAP Place Record</a>
			<p>Place names mentioned in M.A.P. document(s) but not found in the TGN. (Check online before adding)</p>
		</div>
	
		<div id="mapSite">
			<a id="AddNewMapSitePlace" href="<c:url value="/de/geobase/CreatePlace.do?plSource=MAPSITE"/>">MAP Site or Subsite</a>
			<p>Built Srtructures being tracked by the M.A.P.</p>
		</div>

		<div id="notes">
			<p><b>ONLINE TGN SEARCH:</b> <a href="http://www.getty.edu/research/tools/vocabularies/tgn/" target="_blank">http://www.getty.edu/research/tools/vocabularies/tgn/</a></p>
			<p><i>Note: in all cases, Variant Names are added via the Names Tab in Geobase.</i></p>
		</div>
	
		<div id="CloseButton">
			<input id="close" type="submit" title="Close Personal Notes window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
		
		<a id="goBack" href="<c:url value="/de/ShowEntryMenu.do"/>" title="Go Back to Entry Menu">Go back</a>
	
		
	</div>
		
	<script>
		$j(document).ready(function() {
			$j("#AddNewTgnPlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("/DocSources/de/geobase/menuActionsGeo.html");
					return false;
				}
			);
				
			$j("#AddNewMapPlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("/DocSources/de/geobase/menuActionsGeo.html");
					return false;
				}
			);

			$j("#AddNewMapSitePlace").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					$j("#menu_actions").load("/DocSources/de/geobase/menuActionsGeo.html");
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