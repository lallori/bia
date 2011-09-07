<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
		<div id="EntryMenuDiv">
			<h1>Click the button below to add a new element in one of the following categories:</h1>
			
			<a id="AddNewDocument" href="<c:url value="/de/docbase/CreateDocument.do"/>">Add New Document</a>
		
			<a id="AddNewPerson" href="<c:url value="/de/peoplebase/CreatePerson.do"/>">Add New Person</a>
				
			<a id="AddNewVolume" href="<c:url value="/de/volbase/CreateVolume.do"/>">Add New Volume</a>
			
			<a id="AddNewPlace" href="<c:url value="/de/geobase/CreatePlaceMenu.do"/>">Add New Place</a>
			
			<input id="close" type="submit" title="Close Entry Menu window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
		
		<script>
			$j(document).ready(function() {
				$j("#AddNewDocument").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					$j("#menu_actions").load("/DocSources/de/docbase/menuActionsDocuments.html");
					Modalbox.hide(); 
					return false;
				});
				$j("#AddNewPerson").click(function(){
					$j("#body_left").load($j(this).attr("href"));
					$j("#menu_actions").load("/DocSources/de/peoplebase/menuActionsPeople.html");
					Modalbox.hide();
					return false;
				});
				$j("#AddNewVolume").click(function(){
					$j("#body_left").load($j(this).attr("href"));
					$j("#menu_actions").load("/DocSources/de/volbase/menuActionsVolumes.html");
					Modalbox.hide();
					return false;
				});
				$j("#AddNewPlace").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "ADD NEW PLACE", width: 750, height: 450})
					;return false;
				});
			});
		</script>
	</security:authorize>