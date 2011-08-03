<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPersonURL" value="/de/peoplebase/EditDetailsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>

	<div id="EditDetailsPersonDiv" class="background">
		<div class="title">
			<h5>PERSON DETAILS</h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditDetailsPerson" href="${EditDetailsPersonURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		<h3>${person.mapNameLf}</h3>
		<div id="EditPortraitPersonDiv">
			<div id="imgPortraitPerson"></div>
			<p style="text-align:center"><b>Portrait</b></p>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Gender</div> <div class="value">${person.gender}</div>
			</div>
			<div class="row">
				<div class="item">Date of Birth</div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
			</div>
			<div class="row">
				<div class="item">Birth Place</div><div class="value"><a href="#" id="linkSearch">${person.bornPlace.placeNameFull}</a></div>
			</div>
			<div class="row">
				<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
			</div>
			<div class="row">
				<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
			</div>
			<div class="row">
				<div class="item">Death Place</div> <div class="value"><a href="#" id="linkSearch">${person.deathPlace.placeNameFull}</a></div>
			</div>
			<div class="row">
				<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 

			$j("#EditDetailsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsPersonDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>