<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditEmailPropertiesURL" value="/admin/EditEmailProperties.do" />

<div id="EditEmailSystemPropertiesDiv" class="background">
	<div class="title">
		<h5><fmt:message key="adm.showEmailProperties.eMailSystemProperties"/></h5>
		<a id="EditEmailSystemProperties" class="editButton" href="${EditEmailPropertiesURL}"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpHost"/></div>
			<div class="value60">${fn2:getApplicationProperty('mail.server.host')}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpPort"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.server.port')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.mailTransportProtocol"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.transport.protocol')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpAuth"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.smtp.auth')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpStarttls"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.smtp.starttls.enable')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpSUsername"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.server.username')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.smtpSPassword"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.server.password')}</div> 
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.activationMessageSubject"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.activationUser.subject')}</div>
		</div>
		<div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.activationMessageText"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.activationUser.text')}</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.resetUserPasswordSubject"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.resetUserPassword.subject')}</div>
		</div>
        <div class="row">
			<div class="item"><fmt:message key="adm.showEmailProperties.resetUserPasswordText"/></div> 
			<div class="value60">${fn2:getApplicationProperty('mail.resetUserPassword.text')}</div>
		</div>
	</div>
</div>
<br />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditForumProperties").css('visibility', 'visible');
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		$j("#EditSchedoneProperties").css('visibility', 'visible');
		$j("#EditUserProperties").css('visibility', 'visible');

		$j("#EditEmailSystemProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditEmailSystemPropertiesDiv").load($j(this).attr("href"));
			return false;
		})
	})
</script>
</security:authorize>

