<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
		<div id="menageTitlOccDiv">
			<a id="AddNewTitlOcc" href="#" class="button_large">Add new Title/Occupation</a>
		    <a id="EditDeleteTitlOcc" href="<c:url value="/src/ShowSearchTitlesOrOccupations.do"/>" class="button_large">Edit or delete Title/Occupation</a>
		    <a id="goBack" title="Go back to Entry Menu window" href="/DocSources/de/peoplebase/AddNewPersonModal.html">Go Back</a>
		</div>
		
		
		<script>
			$j(document).ready(function() {
				$j("#AddNewPerson").click(
					function(){
						$j("#body_left").load($j(this).attr("href"));
						Modalbox.hide(); return false;}
						);
				$j("#EditDeleteTitlOcc").open({width: 500, height: 130, scrollbars: "yes"});
				$j("#EditDeleteTitlOcc").click(
					function(){
						Modalbox.hide(); return false;}
						);
				$j("#goBack").click(
					function() {															
						Modalbox.show($j(this).attr("href"), {title: "PEOPLE BASE", width: 350, height: 200}); return false;}
						);	
			});
		</script>
		


	</security:authorize>
