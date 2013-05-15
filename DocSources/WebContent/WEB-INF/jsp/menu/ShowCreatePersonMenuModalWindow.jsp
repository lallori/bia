<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">

	<div id="addNewPersonModalDiv">
		<a id="AddNewPerson" href="<c:url value="/de/peoplebase/CreatePerson.do"/>"><p><fmt:message key="menu.showCreatePersonModal.addNewPerson"/></p></a>
	    <a id="MenageTitlOcc" href="<c:url value="/de/ShowTitlesOrOccupationsMenu.do"/>"><p><fmt:message key="menu.showCreatePersonModal.manageTitlesAndOccupations"/></p></a>
	    <a id="goBack" class="button_small" title="Go back to Entry Menu window" href="<c:url value="/de/ShowEntryMenu.do"/>"><fmt:message key="menu.showCreatePersonModal.goBack"/></a>
	</div>
	
	
	<script>
		$j(document).ready(function() {
			$j("#AddNewPerson").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				Modalbox.hide(); 
				return false;
			});
			$j("#MenageTitlOcc").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "TITLES AND OCCUPATIONS", width: 250, height: 180});
				return false;
			});	
			$j("#goBack").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "ENTRY MENU", width: 750, height: 225});
				return false;
			});	
		});
	</script>
	</security:authorize>
