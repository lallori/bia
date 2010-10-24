<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form:form method="post">
  	<fieldset>		
		<legend>User Search</legend>
		<p>
			<form:label	id="accountLabel" for="alias" path="alias" cssErrorClass="error">Alias*</form:label><br/>
			<form:input path="alias" /><form:errors path="alias" />
		</p>
		<p>
			<input id="update" type="submit" value="Update" />
		</p>
	</fieldset>
</form:form>
