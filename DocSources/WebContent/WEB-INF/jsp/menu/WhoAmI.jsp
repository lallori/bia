<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<p>Signed in as - <i><security:authentication property="principal.firstName"/> <security:authentication property="principal.lastName"/><security:authorize ifNotGranted="ROLE_GUESTS">, <i><security:authentication property="principal.significantRoleDescription"/></i></security:authorize></i></p>