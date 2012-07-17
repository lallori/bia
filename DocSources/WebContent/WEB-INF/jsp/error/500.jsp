<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<!-- <div id="map">
		<img src="<c:url value="/images/map.png" />" alt="the Medici Archive Project" />
	</div> -->
			
	<div id="text_error">
		<h2>Error HTTP 500: Internal server error.</h2>
		<p>Error processing flow.</p><br />
	</div>
	
	<!--
	<c:forEach items="${exception.stackTrace}" var="element">
    	<c:out value="${element}">
	</c:forEach>-->
	