<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditDetailsDocument" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocument" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactCheckDocument" value="/de/docbase/EditFactCheckDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>

	<div id="EditDetailsPersonDiv">
		<div id="createdby"><h6>CREATED BY ${person.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${person.dateCreated}" /></h6></div>
		<h5>PERSON DETAILS <a id="EditDetailsPerson" href="/DocSources/de/peoplebase/EditDetailsPerson.html">edit</a></h5>
		<hr id="lineSeparator"/>
		<div id="EditPortraitPersonDiv">
			<img src="/DocSources/images/default_user.jpg" alt="default image" />
			<p><b>Portrait</b> <a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></p>
		</div>
		<h2 class="titlepeople">${person.mapNameLf}</h2>
		<ul id="activeEnd">
			<li><b>Gender:</b> ${person.gender}</li>
			<li><b>Date of Birth:</b> ${person.bornDate}</li>
			<li><b>Birth Place:</b><a href="#" id="linkSearch">Firenze / Toscana / Italia</a></li>
			<li><b>Active Start:</b> ${person.activeStart}</li>
			<li><b>Date of Death:</b> ${person.deathDate}</li>
			<li><b>Modern Date:</b> 1577</li>
			<li><b>Death Place:</b> <a href="#" id="linkSearch">Firenze / Toscana / Italia</a></li>
			<li><b>Active End:</b> ${person.activeEnd}</li>
		</ul>
	</div>
