<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="#" class="moreInfo" title="Browse The Medici Archive Project Database"></a><!-- Questo pulsante chiude la finestra "pop up" e a lo stesso tempo riporta questa persona nell body_left del main  -->
	<ul id="network">
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
	</ul>
	
	<div id="EditDetailsPersonDiv" class="background">
		<div class="title">
			<h5>PERSON DETAILS</h5>
		</div>
		
		<div id="EditPortraitPersonDiv">
			<div id="imgPortraitPerson"></div>
			<p style="text-align:center"><b>Portrait</b></p>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${person.personId != 0}">
				<a id="uploadPortrait" href="#">Upload Portrait</a>
			</c:if>
			</security:authorize>
		</div>
		<h3>${person.mapNameLf}</h3>
		<div class="listDetails">
			<div class="row">
					<div class="item">Gender</div> <div class="value">${person.gender}</div>
				</div>
				<div class="row">
					<div class="item">Date of Birth</div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
				</div>
				<div class="row">
					<div class="item">Birth Place</div>
					<div class="value">${person.bornPlace.placeNameFull}</div>
				</div>
				<div class="row">
					<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
				</div>
				<div class="row">
					<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
				</div>
				<div class="row">
					<div class="item">Death Place</div>
					<div class="value">${person.deathPlace.placeNameFull}</div>
				</div>
				<div class="row">
					<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
				</div>
		</div>
		
	</div>
	
	<br />
	<br />
	
	<div id="EditNamesPersonDiv" class="background">
		<div class="title">
			<h5>NAMES</h5>
		</div>
		
		<div class="list">
			<c:forEach items="${person.altName}" var="currentName">
				<div class="row">
					<div class="item">${currentName.nameType}</div>
					<div class="value">${currentName.namePrefix} ${currentName.altName}</div> 
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditTitlesOccupationsPersonDiv" class="background">
		<div class="title">
			<h5>TITLES / OCCUPATIONS</h5>
		</div>
		
		<div class="list">
			<c:forEach items="${person.poLink}" var="currentPoLink">
				<div class="row">
					<c:if test="${currentPoLink.preferredRole}">
						<div class="value5" title="Preferred Role" id="preferredRoleIcon"></div>
					</c:if>
					<c:if test="${!currentPoLink.preferredRole}">
						<div class="value5"></div>
					</c:if>
					<div class="value60"><b>${currentPoLink.titleOccList.titleOcc}</b><br>
					${currentPoLink.titleOccList.roleCat.roleCatMinor}</div> 
					<div class="info">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</div>
				</div>
			</c:forEach>
		</div>	
	</div>
	
	<br />
	<br />
	
	<div id="EditParentsPersonDiv" class="background">
		<div class="title">
			<h5>PARENTS</h5>
			<br />
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">Father</div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:if test="${currentParent.parent.gender == 'M'}">
				<div class="value">${currentParent.parent}</div> 
				<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
			<div class="row">
				<div class="item">Mother</div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:if test="${currentParent.parent.gender == 'F'}">
				<div class="value">${currentParent.parent}</div> 
				<div class="info">Born ${currentParent.parent.bornYear} | Death ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
		</div>
	</div>
	
	<br />
	
	<div id="EditChildrenPersonDiv" class="background">
		<div class="title">
			<h5>CHILDREN</h5>
		</div>
		
		<div class="list">
			<c:forEach items="${children}" var="currentChild">
				<div class="row">
					<div class="value">${currentChild.child}</div> 
					<div class="info">Birth ${currentChild.child.bornYear} | Death ${currentChild.child.deathYear}</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div id="EditSpousesPersonDiv"class="background">
		<div class="title">
			<h5>SPOUSES</h5>
		</div>
		
		<div class="list">
			<c:forEach items="${marriages}" var="currentMarriage">
				<div class="row">
					<c:if test="${person.personId == currentMarriage.husband.personId}">
						<div class="value">${currentMarriage.wife}</div> 
						<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.wife.deathYear}</div>
					</c:if>
					<c:if test="${person.personId == currentMarriage.wife.personId}">
						<div class="value">${currentMarriage.husband}</div> 
						<div class="info">Marriage ${currentMarriage.startYear} - ${currentMarriage.endYear} | Death ${currentMarriage.husband.deathYear}</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	<br />
	
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
