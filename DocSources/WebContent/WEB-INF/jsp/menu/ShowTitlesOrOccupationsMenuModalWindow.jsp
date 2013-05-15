<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
		<div id="menageTitlOccDiv">
			<a id="AddNewTitlOcc" href="<c:url value="/de/peoplebase/CreateTitleOrOccupation.do"/>" class="button_large"><fmt:message key="menu.showTitlesOrOccupationMenuModal.addNewTitleOrOccupation"/></a>
		    <a id="EditDeleteTitlOcc" href="<c:url value="/src/peoplebase/ShowSearchTitlesOrOccupations.do"/>" class="button_large"><fmt:message key="menu.showTitlesOrOccupationMenuModal.editOrDeleteTitleOrOccupation"/></a>
		    <a id="goBack" title="<fmt:message key="menu.showTitlesOrOccupationMenuModal.goBackToEntryMenu"/>" href="<c:url value="/de/ShowCreatePersonMenu.do"/>"><fmt:message key="menu.showTitlesOrOccupationMenuModal.goBack"/></a>
		</div>
		
		
		<script>
			$j(document).ready(function() {
				$j("#AddNewPerson").click(function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); 
					return false;
				});

				$j("#AddNewTitlOcc").click(function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide();
					return false;
				});

			$j("#EditDeleteTitlOcc").open({width: 750, height: 150, scrollbars: "yes"});
				$j("#EditDeleteTitlOcc").click(function(){
					Modalbox.hide();
					return false;
				});
				$j("#goBack").click(function() {															
					Modalbox.show($j(this).attr("href"), {title: "<fmt:message key="menu.showTitlesOrOccupationMenuModal.open"/>", width: 350, height: 200}); 
					return false;
				});	
			});
		</script>
		


	</security:authorize>
