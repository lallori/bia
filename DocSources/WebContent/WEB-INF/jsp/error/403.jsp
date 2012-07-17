<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="map">
		<img src="<c:url value="/images/map.png" />" alt="the Medici Archive Project" />
	</div>
			
	<div id="text_error">
		<h2>Error HTTP 403: Forbidden.</h2>
		<p>You don't have permission to access this page.</p><br />
	</div>