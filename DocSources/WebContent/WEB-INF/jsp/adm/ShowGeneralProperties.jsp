<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="EditGeneralPropertiesDiv" class="background">
	<div class="title">
		<h5>GENERAL PROPERTIES</h5>
		<a id="EditGeneralProperties" class="editButton" href="/DocSources/adm/EditGeneralProperties.html" title="Edit General Properties"></a>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Website domain</div> 
			<div class="value60">${fn2:getApplicationProperty('website.domain')}</div> 
		</div>
        <div class="row">
			<div class="item">Images Path</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.image.path')}</div> 
		</div>
	</div>
</div>
</security:authorize>