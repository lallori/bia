<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<form:form method="post">
		  	<fieldset>		
				<legend>Your Password is expirated!</legend>
				<p>
					<form:label	id="passwordLabel" for="password" path="password" cssErrorClass="error">Password*</form:label><br/>
					<form:password path="password" cssClass="registerInput"/><form:errors path="password" />
				</p>
				<p>
					<form:label	id="confirmPasswordLabel" for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password*</form:label><br/>
					<form:password path="confirmPassword" cssClass="registerInput"/><form:errors path="confirmPassword" />
				</p>
				<p>
					<c:out value="${reCaptchaHTML}" escapeXml="false"/>
				</p>
				<p>	
					<input id="update" type="submit" value="Update" />
				</p>
			</fieldset>
		</form:form>
