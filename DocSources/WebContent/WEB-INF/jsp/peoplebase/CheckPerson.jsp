<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditDetailsPersonDiv">
	<div id="EditPortraitPersonDiv">
		<img src="/DocSources/images/1024/img_user.jpg" alt="default image" />
		<p><b>Portrait</b> <a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></p>
	</div>
	<h3>${person.last}, ${person.first} ${person.sucNum}</h3>
	<ul id="activeEnd">
		<li><b>Gender:</b> ${person.gender}</li>
		<li><b>Date of Birth:</b> ${person.deathDate}</li>
		<li><b>Birth Place:</b><a href="#" id="linkSearch">${person.bornPlace.placeNameFull}</a></li>
		<li><b>Active Start:</b> ${person.activeStart}</li>
		<li><b>Date of Death:</b> ${person.deathDate}</li>
		<li><b>Modern Date:</b> 1577</li>
		<li><b>Death Place:</b> <a href="#" id="linkSearch">${person.deathPlace.placeNameFull}</a></li>
		<li><b>Active End:</b> ${person.activeEnd}</li>
	</ul>
</div>

<br /><br />


<div id="EditNamesPersonDiv">
	<h5>NAMES</h5>
	<hr id="lineSeparator"/>

	<ul>
		<c:forEach items="${person.altName}" var="currentName">
		<li>${currentName.nameType}: <a href="#" id="linkSearch">${currentName.namePrefix} ${currentName.altName}</a></li>
		</c:forEach>
	</ul>
</div>

<br /><br />

<div id="EditTitlesOccupationsPersonDiv">
	<h5>TITLES / OCCUPATIONS</h5>
	<hr id="lineSeparator"/>
	
	<ul>
		<c:forEach items="${person.poLink}" var="currentTitle">
		<li><a href="#" id="linkSearch"><b>${currentTitle.titleOccList.titleOcc}</b></a></li>
		<li><a href="#" id="linkSearch">${currentTitle.titleOccList.roleCat.roleCatMajor}, ${currentTitle.titleOccList.roleCat.roleCatMinor}</a><p id="info"><u>Start:</u> ${currentTile.startYear} | <u>End:</u> ${currentTile.endYear}</p></li>
		<br />
		</c:forEach>
	</ul>
</div>

<br /><br />

<div id="EditRelationshipPersonDiv">
	<h5>RELATIONSHIPS </h5>
	<hr id="lineSeparator"/>
</div>


<div id="EditParentsPersonDiv">	
	<b>Parents:</b> 
	<ul>
		<li>Father: <a href="#" id="linkSearch">${person.father.last}, ${person.father.first} ${person.father.sucNum}</a> <p id="info"><u>Birth:</u> ${person.father.bYear} | <u>Death:</u> ${person.father.dYear}</p></li>
		<li>Mother: <a href="#" id="linkSearch">${person.mother.last}, ${person.mother.first} ${person.mother.sucNum}</a> <p id="info"><u>Birth:</u> ${person.mother.bYear} | <u>Death:</u> ${person.mother.dYear}</p></li>
	</ul>
</div>

<br />

<div id="EditChildrenPersonDiv">
	<b>Children:</b>
	<ul>
	<c:forEach items="${children}" var="currentChildren">
		<li><a href="#" id="linkSearch">${currentChildren.last}, ${currentChildren.first} ${currentChildren.sucNum}</a> <p id="info"><u>Birth:</u> ${currentChildren.bYear} | <u>Death:</u> ${currentChildren.dYear}</p> </li>
	</c:forEach>
	</ul>
</div>

<br />

<div id="EditSpousesPersonDiv">
	<b>Spouses:</b>
	<ul>
	<c:forEach items="${marriages}" var="currentMarriage">
		<li><a href="#" id="linkSearch">${currentMarriage.wife.last}, ${currentMarriage.wife.first} ${currentMarriage.wife.sucNum}</a> <p id="info"><u>Marriage:</u> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <u>Death:</u> ${currentMarriage.wife.dYear}</p></li>
	</c:forEach>
	</ul>
</div>

<br /><br />


<div id="EditResearchNotesPersonDiv">
	<h5>RESEARCH NOTES</h5>
	<hr id="lineSeparator"/>

	<ul>
		<li>${person.bioNotes}</li>
	</ul>
</div>
