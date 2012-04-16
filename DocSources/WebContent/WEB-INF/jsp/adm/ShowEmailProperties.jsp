<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditEmailPropertiesURL" value="/admin/EditEmailProperties.do" />

<div id="EditEmailSystemPropertiesDiv" class="background">
	<div class="title">
		<h5>EMAIL SYSTEM PROPERTIES</h5>
		<a id="EditEmailSystemProperties" class="editButton" href="${EditEmailPropertiesURL}"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Activation message subject</div> 
			<div class="value60">${fn2:getApplicationProperty('mail.activationUser.subject')}</div>
		</div>
		<div class="row">
			<div class="item">Activation message text</div> 
			<div class="value60">${fn2:getApplicationProperty('mail.activationUser.text')}</div>
		</div>
        <div class="row">
			<div class="item">Reset user password subject</div> 
			<div class="value60">${fn2:getApplicationProperty('mail.resetUserPassword.subject')}</div>
		</div>
        <div class="row">
			<div class="item">Reset user password text</div> 
			<div class="value60">${fn2:getApplicationProperty('mail.resetUserPassword.text')}</div>
		</div>
	</div>
</div>
<br />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		
		$j("#EditEmailSystemProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditEmailSystemPropertiesDiv").load($j(this).attr("href"));
			return false;
		})
	})
</script>
</security:authorize>

