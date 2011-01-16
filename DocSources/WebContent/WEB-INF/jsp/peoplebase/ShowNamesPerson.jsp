<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditNamesPersonDiv">
	<h5>NAMES <a id="EditNamesPerson" href="/DocSources/de/peoplebase/NamesPerson.html">edit</a></h5>
	<hr id="lineSeparator"/>

	<ul>
		<c:forEach items="${person.altName}" var="currentName">
			<li>${currentName.nameType}: <a href="#" id="linkSearch">${currentName.altName}</a></li>
		</c:forEach>
	</ul>
</div>

