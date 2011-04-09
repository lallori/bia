<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditChildrenPersonURL" value="/de/peoplebase/EditChildrenPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditChildrenPersonDiv" class="background">
		<div class="title">
			<h5>CHILDREN:</h5>
	 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditChildrenPerson" href="${EditChildrenPersonURL}" class="editButton"></a><span id="loading"/>
		</security:authorize>
		</div>
		<ul>
		<c:forEach items="${children}" var="currentChildren">
			<li><a href="#" id="linkSearch">${currentChildren.last}, ${currentChildren.first} ${currentChildren.sucNum}</a> <p id="info"><u>Birth:</u> ${currentChildren.bYear} | <u>Death:</u> ${currentChildren.dYear}</p> </li>
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
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditChildrenPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditChildrenPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>