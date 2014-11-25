<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditIipImagePropertiesURL" value="/admin/EditIIPImageProperties.do" />

<div id="IipImagePropertiesDiv" class="background">
	<div class="title">
		<h5><fmt:message key="adm.showIIPImageProperties.iIpImage"/></h5>
		<a id="EditIipImageProperties" class="editButton" href="${EditIipImagePropertiesURL}" title="IipImage Properties"></a><span id="loading" />
	</div>
    <p><fmt:message key="adm.showIIPImageProperties.iipImageServerR"/></p>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="adm.showIIPImageProperties.serverFcgi"/></div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.fcgi.path')}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showIIPImageProperties.serverHost"/></div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.host')}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showIIPImageProperties.serverPort"/></div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.port')}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showIIPImageProperties.serverProtocol"/></div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.protocol')}</div> 
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showIIPImageProperties.serverVersion"/></div> 
			<div class="value60">${fn2:getApplicationProperty('iipimage.reverseproxy.version')}</div> 
		</div>
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditForumProperties").css('visibility', 'visible');
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		$j("#EditSchedoneProperties").css('visibility', 'visible');
		$j("#EditUserProperties").css('visibility', 'visible');
		
		$j("#EditIipImageProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#IipImagePropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	})
</script>
</security:authorize>