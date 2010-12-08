<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="map">
		<img src="<c:url value="/images/map.png" />" alt="the Medicis Archive Project" />
	</div>
			
	<div id="text_error">
		<h2>Error HTTP 404: Page not found.</h2>
		<p>The URL you requested was not found.</p><br />
	</div>