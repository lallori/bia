<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditSpousesPersonDiv">
		<b>Spouses:</b>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditSpousesPerson" href="${EditSpousesPersonURL}">edit</a><span id="loading"/>
	</security:authorize>

		<ul>
		<c:forEach items="${marriages}" var="currentMarriage">
			<li><a href="#" id="linkSearch">${currentMarriage.wife.last}, ${currentMarriage.wife.first} ${currentMarriage.wife.sucNum}</a> <p id="info"><u>Marriage:</u> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <u>Death:</u> ${currentMarriage.wife.dYear}</p></li>
		</c:forEach>
		</ul>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditSpousesPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditSpousesPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>