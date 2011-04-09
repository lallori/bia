<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesPersonURL" value="/de/peoplebase/EditNamesPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditNamesPersonDiv" class="background">
		<div class="title">
			<h5>NAMES </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditNamesPerson" href="${EditNamesPersonURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
	
		<ul>
		<c:forEach items="${person.altName}" var="currentName">
			<li>${currentName.nameType}: <a href="#" id="linkSearch">${currentName.namePrefix} ${currentName.altName}</a></li>
		</c:forEach>
		</ul>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditNamesPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamesPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>