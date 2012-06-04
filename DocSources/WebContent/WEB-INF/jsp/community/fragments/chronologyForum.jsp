<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="chronologyDiv">
					<c:if test="${not empty category}">
						${fn2:getForumChronology(category)}
					</c:if>
					<c:if test="${not empty forum}">
						${fn2:getForumChronology(forum)}
					</c:if>
				</div>
