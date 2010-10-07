<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

		<form:form method="post">
		  	<fieldset>		
				<legend>Change User Password</legend>
				<p>
					<form:label	id="oldPasswordLabel" for="oldPassword" path="oldPassword" cssErrorClass="error">Old Password*</form:label><br/>
					<form:password path="oldPassword" /><form:errors path="oldPassword" />
				</p>
				<p>
					<form:label	id="passwordLabel" for="password" path="password" cssErrorClass="error">Password*</form:label><br/>
					<form:password path="password" /><form:errors path="password" />
				</p>
				<p>
					<form:label	id="confirmPasswordLabel" for="confirmPassword" path="confirmPassword" cssErrorClass="error">Confirm Password*</form:label><br/>
					<form:password path="confirmPassword" /><form:errors path="confirmPassword" />
				</p>
				<p>	
					<input id="update" type="submit" value="Update" />
				</p>
			</fieldset>
		</form:form>
