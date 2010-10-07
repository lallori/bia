<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

				<form:form method="post">
				  	<fieldset>		
						<legend>Account Fields</legend>
						<p>
							<form:label	id="accountLabel" for="account" path="account" cssErrorClass="error">Name*</form:label><br/>
							<form:input path="account" /><form:errors path="account" />
						</p>
						<p>	
							<form:label id="firstNameLabel" for="firstName" path="firstName" cssErrorClass="error">First Name</form:label><br/>
							<form:input path="firstName" /><form:errors path="firstName"/>
						</p>
						<p>	
							<form:label id="lastNameLabel" for="lastName" path="lastName" cssErrorClass="error">Last Name</form:label><br/>
							<form:input path="lastName" /><form:errors path="lastName"/>
						</p>
						<p>	
							<form:label id="organizationLabel" for="organization" path="organization" cssErrorClass="error">Organization</form:label><br/>
							<form:input path="organization" /><form:errors path="organization"/>
						</p>
						<p>	
							<form:label id="mailLabel" for="mail" path="mail" cssErrorClass="error">Mail</form:label><br/>
							<form:input path="mail" /><form:errors path="mail"/>
						</p>
						<p>	
							<input id="search" type="submit" value="Search" />
						</p>
					</fieldset>
      				<form:hidden path="cookie"/>
				</form:form>
