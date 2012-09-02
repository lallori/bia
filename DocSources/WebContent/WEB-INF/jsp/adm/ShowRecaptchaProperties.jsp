<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditRecaptchaPropertiesURL" value="/admin/EditRecaptchaProperties.do" />
<div id="RecaptchaPropertiesDiv" class="background">
	<div class="title">
		<h5>RECAPTCHA PROPERTIES</h5>
		<a id="EditRecaptchaProperties" class="editButton" href="${EditRecaptchaPropertiesURL}" title="Recaptcha Properties"></a><span id="loading" />
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Domain name</div> 
			<div class="value60">${fn2:getApplicationProperty('recaptcha.domainName')}</div> 
		</div>
        <div class="row">
			<div class="item">Private key</div> 
			<div class="value60">${fn2:getApplicationProperty('recaptcha.privateKey')}</div> 
		</div>
        <div class="row">
			<div class="item">Public key</div> 
			<div class="value60">${fn2:getApplicationProperty('recaptcha.publicKey')}</div> 
		</div>
        <div class="row">
			<div class="item">Server url</div> 
			<div class="value60">${fn2:getApplicationProperty('recaptcha.server')}</div> 
		</div>
        <div class="row">
			<div class="item">Site identifier</div> 
			<div class="value60">${fn2:getApplicationProperty('recaptcha.siteId')}</div> 
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
	
		$j("#EditRecaptchaProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#RecaptchaPropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	})
</script>
</security:authorize>