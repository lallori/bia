<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<div id="RecaptchaPropertiesDiv" class="background">
	<div class="title">
		<h5>RECAPTCHA PROPERTIES</h5>
		<a id="RecaptchaProperties" class="editButton" href="/DocSources/adm/RecaptchaProperties.html" title="Recaptcha Properties"></a>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Domain name</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Private key</div> 
			<div class="value60">6LcA-LsSAAAAAInVIlSHKjxqKKre-40BOpb3Abcs</div> 
		</div>
        <div class="row">
			<div class="item">Public key</div> 
			<div class="value60">6LcA-LsSAAAAAKYVTuOi0KQWArgUQwPtMQFqwe_6</div> 
		</div>
        <div class="row">
			<div class="item">Server url</div> 
			<div class="value60">https://api-secure.recaptcha.net</div> 
		</div>
        <div class="row">
			<div class="item">Site identifier</div> 
			<div class="value60">314849700</div> 
		</div>
	</div>
</div>
</security:authorize>