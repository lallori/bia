<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

						<li><a href="#"><img src="<c:url value="/images/button_advsearch.png"/>" alt="Advanced Search" width="99" height="65" /></a></li>
					<security:authorize ifNotGranted="ROLE_GUESTS">
						<li><a href="#"><img src="<c:url value="/images/button_chronology.png"/>" alt="Chronology" width="99" height="65" /></a></li>
						<li><a href="#"><img src="<c:url value="/images/button_myprofile.png"/>" alt="My Profile" width="99" height="65" /></a></li>
						<li><a href="#"><img src="<c:url value="/images/button_messages.png"/>" alt="Messages" width="99" height="65" /></a></li>
					</security:authorize>
						<li><a href="<c:url value="/LogoutUser.do"/>"><img src="<c:url value="/images/button_logout.png"/>" alt="Log out" width="99" height="65" /></a></li>