<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditChildrenPersonDiv">
	<b>Children:</b> <a id="EditChildrenPerson" href="/DocSources/de/peoplebase/ChildrenPerson.html">edit</a>
	<ul>
	<c:forEach items="${children}" var="currentChildren">
		<li><a href="#" id="linkSearch">${currentChildren.last}, ${currentChildren.first} ${currentChildren.sucNum}</a> <p id="info"><u>Birth:</u> ${currentChildren.bYear} | <u>Death:</u> ${currentChildren.dYear}</p> </li>
	</c:forEach>
	</ul>
</div>
