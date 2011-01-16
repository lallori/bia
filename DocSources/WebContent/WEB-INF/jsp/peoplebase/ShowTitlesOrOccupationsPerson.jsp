<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditTitlesOccupationsPersonDiv">
	<h5>TITLES / OCCUPATIONS <a id="EditTitlesOccupationsPerson" href="/DocSources/de/peoplebase/TitlesOccupationsPerson.html">edit</a></h5>
	<hr id="lineSeparator"/>
	
	<ul>
		<c:forEach items="${person.poLink}" var="currentPoLink">
			<li><a href="#" id="linkSearch"><b>${currentPoLink.titleOccList.titleOcc}</b></a></li>
			<li><a href="#" id="linkSearch">${currentPoLink.titleOccList.roleCat.roleCatMinor}</a><p id="info"><u>Start:</u> ${currentPoLink.startDate} | <u>End:</u>${currentPoLink.endDate} </p></li>
			<br />
		</c:forEach>
	</ul>
</div>
