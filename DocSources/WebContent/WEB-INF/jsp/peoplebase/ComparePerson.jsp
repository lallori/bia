<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditDetailsPersonDiv" class="background">
	<div class="title">
		<h5>PERSON DETAILS</h5>
	</div>
	<div id="EditPortraitPersonDiv">
		<div id="imgPortraitPerson"></div>
		<p style="text-align:center"><b>Portrait</b></p>
	</div>
	<h2 class="titlepeople">${person.mapNameLf}</h2>
	<div class="listDetails">
		<div class="row">
			<div class="item">Gender:</div> <div class="value">${person.gender}</div>
		</div>
		<div class="row">
			<div class="item">Date of Birth:</div> <div class="value">${person.bornDate}</div>
		</div>
		<div class="row">
			<div class="item">Birth Place:</div><div class="value"><a href="#" id="linkSearch">${person.bornPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active Start:</div> <div class="value">${person.activeStart}</div>
		</div>
		<div class="row">
			<div class="item">Date of Death:</div> <div class="value">${person.deathDate}</div>
		</div>
		<div class="row">
			<div class="item">Modern Date:</div> <div class="value">1577</div>
		</div>
		<div class="row">
			<div class="item">Death Place:</div> <div class="value"><a href="#" id="linkSearch">${person.deathPlace.placeNameFull}</a></div>
		</div>
		<div class="row">
			<div class="item">Active End:</div> <div class="value">${person.activeEnd}</div>
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


<div id="EditTitlesOccupationsPersonDiv" class="background">
	<div class="title">	
		<h5>TITLES / OCCUPATIONS</h5>
	</div>
	
	<div class="listDetails">
		<c:forEach items="${person.poLink}" var="currentTitle">
		<div class="row">
			<div class="item"><a href="#" id="linkSearch"><b>${currentTitle.titleOccList.titleOcc}</b></a></div>
			<div class="item"><a href="#" id="linkSearch">${currentTitle.titleOccList.roleCat.roleCatMajor}, ${currentTitle.titleOccList.roleCat.roleCatMinor}</a><p id="info"><u>Start:</u> ${currentTile.startYear} | <u>End:</u> ${currentTile.endYear}</p></div>
			<br />
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
	<c:forEach items="${children}" var="currentChildren">
		<div class="row">
			<div class="item"><a href="#" id="linkSearch">${currentChildren.last}, ${currentChildren.first} ${currentChildren.sucNum}</a> <p id="info"><u>Birth:</u> ${currentChildren.bornYear} | <u>Death:</u> ${currentChildren.deathYear}</p> </div>
		</div>
	</c:forEach>
	</div>
</div>

<div id="EditSpousesPersonDiv" class="background">
	<div class="title">	
		<h5>SPOUSES</h5>
	</div>
	<ul>
	<c:forEach items="${marriages}" var="currentMarriage">
		<li><a href="#" id="linkSearch">${currentMarriage.wife.last}, ${currentMarriage.wife.first} ${currentMarriage.wife.sucNum}</a> <p id="info"><u>Marriage:</u> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <u>Death:</u> ${currentMarriage.wife.deathYear}</p></li>
	</c:forEach>
	</ul>
</div>

<div id="EditResearchNotesPersonDiv" class="background">
	<div class="title">	
	<h5>RESEARCH NOTES</h5>
	</div>

	<ul>
		<li>${person.bioNotes}</li>
	</ul>
</div>
