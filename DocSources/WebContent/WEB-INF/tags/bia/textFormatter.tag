<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>

<%@ attribute name="inputText" required="true" type="java.lang.String" %>
<%@ attribute name="size" required="true" type="java.lang.Integer" %>

<c:choose>
	<c:when test="${fn:length(inputText) > size}">
		<c:out value="${fn2:substring(inputText, 0, size)}"/>
	</c:when>
	<c:otherwise>
		<c:out value="${inputText}"/>
	</c:otherwise>
</c:choose>

