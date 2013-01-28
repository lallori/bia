<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<div id="recovery">
				<h1>PASSWORD RECOVERY</h1>
				<p>Did you forget your password?<br/>Enter here your E-mail adress to get a new one:</p><br/>  		
				<form:form method="post" cssClass="recovery">
					<form:errors path="mail" cssClass="inputerrors"/>
					<form:input path="mail" cssClass="recoveryInput"/>
					<div id="captcha_recovery">
						<c:out value="${reCaptchaHTML}" escapeXml="false"/>
					</div>
					<input name="submit" type="submit" style="margin:10px 0px 0px 160px" title="submit" class="button_medium" value="Submit"/>
				</form:form>  
			</div>
			