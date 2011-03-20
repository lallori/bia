<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditParentsPersonDiv">	
	<b>Parents:</b> <a id="EditParentsPerson" href="/DocSources/de/peoplebase/ParentsPerson.html">edit</a>
	<ul>
		<li>Father: <a href="#" id="linkSearch">${person.father.last}, ${person.father.first} ${person.father.sucNum}</a> <p id="info"><u>Birth:</u> ${person.father.bYear} | <u>Death:</u> ${person.father.dYear}</p></li>
		<li>Mother: <a href="#" id="linkSearch">${person.mother.last}, ${person.mother.first} ${person.mother.sucNum}</a> <p id="info"><u>Birth:</u> ${person.mother.bYear} | <u>Death:</u> ${person.mother.dYear}</p></li>
	</ul>
</div>
