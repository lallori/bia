<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<script type="text/javascript" src="<c:url value="/scripts/mview/jquery-ui-1.8.9.custom.min.js"/>"></script>
	
	<div id="pageContainer" class="containerView">
		<img src="<c:url value='/images/1024/img_mapLogin.png' />" class="annotationViewImg"/>
		<div class="annotationViewTitle"><fmt:message key="openannotation.showAnnotationsTitle"></fmt:message></div>
		<div id="annotationcontent" class="annotationViewContent">
			<c:if test="${not empty jsonannotations}">
				<pre>${jsonannotations}</pre>
			</c:if>
			<c:if test="${empty jsonannotations}">
				<pre><fmt:message key="openannotation.emptyFile"></fmt:message></pre>
			</c:if>
		</div>
	</div>