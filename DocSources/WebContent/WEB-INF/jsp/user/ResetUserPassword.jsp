<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<div id="reset">
			<h1>RESET PASSWORD</h1>
			<form:form method="post">
		  		<table width="400px" cellpadding="5px">
					<tr>
						<td width="50%" align="right"><form:label	id="passwordLabel" for="password" path="password" cssErrorClass="error">Password*</form:label></td>
						<td><form:password path="password" cssClass="registerInput"/><form:errors path="password" /></td>
					</tr>
					<tr>
						<td width="50%" align="right"><form:label	id="confirmPasswordLabel" for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password*</form:label></td>
						<td><form:password path="confirmPassword" cssClass="registerInput"/><form:errors path="confirmPassword" /></td>
					</tr>
				</table>
				<div id="captcha_reset">
					<c:out value="${reCaptchaHTML}" escapeXml="false"/>
				</div>
				<input id="update" type="submit" value="Update" style="margin:10px 0px 0px 170px" class="button_medium"/>
				
			
			</form:form>
		</div>
