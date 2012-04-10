<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="IipImagePropertiesDiv" class="background">
	<div class="title">
		<h5>IIP IMAGE</h5>
		<a id="IipImageProperties" class="editButton" href="/DocSources/adm/IipImageProperties.html" title="IipImage Properties"></a>
	</div>
    <p>IIpImage server reverse proxy properties</p>
	<div class="list">
		<div class="row">
			<div class="item">Server fcgi-bin path</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.fcgi.path')}</div> 
		</div>
        <div class="row">
			<div class="item">Server host-name</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.host')}</div> 
		</div>
        <div class="row">
			<div class="item">Server port</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.port')}</div> 
		</div>
        <div class="row">
			<div class="item">Server protocol</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.protocol')}</div> 
		</div>
        <div class="row">
			<div class="item">Server version</div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.version')}</div> 
		</div>
	</div>
</div>
</security:authorize>