<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditParentsPersonDiv" class="background">
		<div class="title">	
			<h5>PARENTS:</h5>
		 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditParentsPerson" href="${EditParentsPersonURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				<div class="item">Father</div> 
				<div class="value"><a href="#">${person.father.last}, ${person.father.first} ${person.father.sucNum}</a></div> 
				<div class="info">${person.father.bornYear} | Death ${person.father.deathYear}</div>
			</div>
			<div class="row">
				<div class="item">Mother</div> 
				<div class="value"><a href="#">${person.mother.last}, ${person.mother.first} ${person.mother.sucNum}</a></div> 
				<div class="info">${person.mother.bornYear} | Death ${person.mother.deathYear}</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPerson").css('visibility', 'visible');
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditParentsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditParentsPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>