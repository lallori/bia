<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
		<div id="EntryMenuDiv">
			<h1>Click the button below to add a new element in one of the following categories:</h1>
			
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
			<a id="AddNewDocument" href="<c:url value="/de/docbase/CreateDocument.do"/>"><p>Add New Document</p></a>
		</security:authorize>
		
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
			<a id="AddNewPerson" href="<c:url value="/de/ShowCreatePersonMenu.do"/>"><p>Add New Person & Menage Titles and Occupations</p></a>
		</security:authorize>
				
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS,ROLE_DIGITIZATION_TECHNICIANS">
			<a id="AddNewVolume" href="<c:url value="/de/volbase/CreateVolume.do"/>"><p>Add New Volume</p></a>
		</security:authorize>
			
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
			<a id="AddNewPlace" href="<c:url value="/de/geobase/CreatePlaceMenu.do"/>"><p>Add New Place</p></a>
		</security:authorize>
			
			<input id="close" type="submit" title="Close Entry Menu window" onClick="Modalbox.hide(); return false;" value="Close"/>
		</div>
		
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS,ROLE_DIGITIZATION_TECHNICIANS">
		<script>
			$j(document).ready(function() {
				$j("#AddNewDocument").click(function() {
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					return false;
				});
				$j("#AddNewPerson").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "PEOPLE BASE", width: 350, height: 200});
					return false;
				});
				$j("#AddNewPlace").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "ADD NEW PLACE", width: 750, height: 450});
					return false;
				});

				$j("#AddNewVolume").click(function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});
				
				$j("#MB_close").click(function(){
					Modalbox.hide();
					return false;
				})
			});
		</script>
	</security:authorize>