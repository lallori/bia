<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<script type="text/javascript" src="<c:url value="/scripts/mview/jquery-ui-1.8.9.custom.min.js"/>"></script>
	
	<div id="pageContainer" class="containerView">
		<img src="<c:url value='/images/1024/img_mapLogin.png' />" class="annotationViewImg"/>
		<div class="annotationViewTitle"><fmt:message key="openannotation.showAnnotationsTitle"></fmt:message></div>
		<a id="link" class="annotationDownloadButton" title="<fmt:message key='openannotation.downloadbutton.downloadTitle'></fmt:message>" 
			href="<c:url value='/src/openannotation/downloadOAFile.do'/>"><fmt:message key='openannotation.downloadbutton.download'></fmt:message></a> 
		<div id="annotationcontent" class="annotationViewContent">
			<c:if test="${not empty jsonannotations}">
				<pre>${jsonannotations}</pre>
			</c:if>
			<c:if test="${empty jsonannotations}">
				<pre><fmt:message key="openannotation.emptyFile"></fmt:message></pre>
			</c:if>
		</div>
	</div>
	
	<script>
		$j(document).ready(function() {
			
			document.title = '${fn2:getApplicationProperty("project.name")} - OAC';
			
		});
	</script>