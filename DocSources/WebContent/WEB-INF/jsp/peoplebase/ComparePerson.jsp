<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditDetailsPersonDiv" class="background">
	<div class="title">
		<h5>PERSON DETAILS</h5>
	</div>
	<h3>${person.mapNameLf}</h3>
	
	<c:if test="${person.senderDocuments.size()+person.recipientDocuments.size()+person.epLink.size() != 0}">
		<a href="${ShowDocumentsPersonURL}" class="num_docs" title="Click here to view all documents related">${person.senderDocuments.size()+person.recipientDocuments.size()+person.epLink.size()} Documents related</a>
	</c:if>
	<c:if test="${person.senderDocuments.size()+person.recipientDocuments.size()+person.epLink.size() == 0}">
		<a class="num_docs">0 Document related</a>
	</c:if>
	<br />
	<br />
	
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
			<div class="item">Birth Place</div><div class="value"><a id="linkSearch">${person.bornPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
		</div>
		<div class="row">
			<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
		</div>
		<div class="row">
			<div class="item">Death Place</div> <div class="value"><a id="linkSearch">${person.deathPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
		</div>
	</div>
</div>


<div id="EditNamesPersonDiv" class="background">
	<div class="title">
		<h5>NAMES </h5>
	</div>
	<div class="list">
		<c:forEach items="${person.altName}" var="currentName">
			<div class="row">
				<div class="item">${currentName.nameType}</div> 
				<div class="value"><a id="linkSearch" href="#">${currentName.namePrefix} ${currentName.altName}</a></div> 
			</div>
		</c:forEach>
	</div>	
</div>


<div id="EditTitlesOrOccupationsPersonDiv" class="background">
	<div class="title">	
		<h5>TITLES / OCCUPATIONS</h5>
	</div>
	
	<div class="list">
		<c:forEach items="${person.poLink}" var="currentPoLink">
		<div class="row">
			<c:if test="${currentPoLink.preferredRole}">
				<a class="value5" title="Preferred Role" id="preferredRoleIcon" href="#"></a>
			</c:if>
			<c:if test="${!currentPoLink.preferredRole}">
				<div class="value5"></div>
			</c:if>
			<div class="value60"><a class="linkSearch" href="#"><b>${currentPoLink.titleOccList.titleOcc}</b></a><br />
			<a class="linkSearch" href="#">${currentPoLink.titleOccList.roleCat.roleCatMinor}</a></div>
			<div class="info">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</div>
		</div>
		</c:forEach>
	</div>
</div>


<div id="EditParentsPersonDiv" class="background">
	<div class="title">	
		<h5>PARENTS</h5>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Father</div> 
	<c:forEach items="${person.parents}" var="currentParent">
		<c:if test="${currentParent.parent.gender == 'M'}">
			<div class="value"><a href="#">${currentParent.parent}</a></div> 
			<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
		</c:if>				
	</c:forEach>
		</div>
		<div class="row">
			<div class="item">Mother</div> 
	<c:forEach items="${person.parents}" var="currentParent">
		<c:if test="${currentParent.parent.gender == 'F'}">
			<div class="value"><a href="#">${currentParent.parent}</a></div> 
			<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
		</c:if>				
	</c:forEach>
		</div>
	</div>
</div>

<div id="EditChildrenPersonDiv" class="background">
	<div class="title">	
		<h5>CHILDREN</h5>
	</div>
	<div class="list">
	<c:forEach items="${person.children}" var="currentChild">
		<div class="row">
			<div class="value"><a href="#">${currentChild.child}</a></div>
			<div class="info">Birth ${currentChild.child.bornYear} | Death ${currentChild.child.deathYear}</div>
		</div>
	</c:forEach>
	</div>
</div>

<div id="EditSpousesPersonDiv" class="background">
	<div class="title">	
		<h5>SPOUSES</h5>
	</div>
	<div class="list">
	<c:forEach items="${marriages}" var="currentMarriage">
		<div class="row">
			<div class="value"><a class="linkSpouse">${currentMarriage.wife}</a></div>
			<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.wife.deathYear}</div>
		</div>
	</c:forEach>
	</div>
</div>

<div id="EditResearchNotesPersonDiv" class="background">
	<div class="title">	
	<h5>RESEARCH NOTES</h5>
	</div>

	<div class="list">
		<div class="row">
			<div class="value">
				${person.bioNotes}
			</div>
		</div>
	</div>
</div>
