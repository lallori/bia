<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitlesOccupationsPersonURL" value="/de/peoplebase/EditTitlesOccupationsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

<div id="EditTitlesOccupationsPersonDiv">
	<h5>TITLES / OCCUPATIONS </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditTitlesOccupationsPerson" href="${EditTitlesOccupationsPersonURL}">edit</a><span id="loading"/>
	</security:authorize>
	<hr id="lineSeparator"/>
	
	<ul>
		<c:forEach items="${person.poLink}" var="currentPoLink">
			<li><a href="#" id="linkSearch"><b>${currentPoLink.titleOccList.titleOcc}</b></a></li>
			<li><a href="#" id="linkSearch">${currentPoLink.titleOccList.roleCat.roleCatMinor}</a><p id="info"><u>Start:</u> ${currentPoLink.startDate} | <u>End:</u>${currentPoLink.endDate} </p></li>
			<br />
		</c:forEach>
	</ul>
</div>


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditDetailsPerson").css('visibility', 'visible'); 
			$j("#EditNamesPerson").css('visibility', 'visible');
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditTitlesOccupationsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTitlesOccupationsPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>