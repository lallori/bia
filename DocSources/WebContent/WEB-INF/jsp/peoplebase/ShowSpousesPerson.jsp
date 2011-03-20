<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="EditSpousesPersonDiv">
	<b>Spouses:</b> <a id="EditSpousesPerson" href="/DocSources/de/peoplebase/SpousesPerson.html">edit</a>
	<ul>
	<c:forEach items="${marriages}" var="currentMarriage">
		<li><a href="#" id="linkSearch">${currentMarriage.wife.last}, ${currentMarriage.wife.first} ${currentMarriage.wife.sucNum}</a> <p id="info"><u>Marriage:</u> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <u>Death:</u> ${currentMarriage.wife.dYear}</p></li>
	</c:forEach>
	</ul>
</div>
