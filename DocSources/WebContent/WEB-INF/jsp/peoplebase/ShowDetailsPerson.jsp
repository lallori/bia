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

	<div id="EditDetailsPersonDiv">
		<h5>PERSON DETAILS</h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditDetailsPerson" href="${EditDetailsPersonURL}">edit</a><span id="loading"/>
	</security:authorize>
		<div id="CreatedSharePrintDiv">
			<div id="createdby">CREATED BY ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></div>
			<a title="Print this record" href="#" id="buttonPrint"></a>
			<div id="buttonShareLink">
				<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a>
				<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
			</div>
		</div>
		<hr id="lineSeparator"/>
		<div id="EditPortraitPersonDiv">
			<img src="/DocSources/images/default_user.jpg" alt="default image" />
			<p><b>Portrait</b> <a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></p>
		</div>
		<h2 class="titlepeople">${person.mapNameLf}</h2>
		<ul id="activeEnd">
			<li><b>Gender:</b> ${person.gender}</li>
			<li><b>Date of Birth:</b> ${person.bornDate}</li>
			<li><b>Birth Place:</b><a href="#" id="linkSearch">${person.bornPlace.placeNameFull}</a></li>
			<li><b>Active Start:</b> ${person.activeStart}</li>
			<li><b>Date of Death:</b> ${person.deathDate}</li>
			<li><b>Modern Date:</b> 1577</li>
			<li><b>Death Place:</b> <a href="#" id="linkSearch">${person.deathPlace.placeNameFull}</a></li>
			<li><b>Active End:</b> ${person.activeEnd}</li>
		</ul>
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