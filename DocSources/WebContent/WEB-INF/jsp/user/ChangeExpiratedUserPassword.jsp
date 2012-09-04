<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


		<div id="passwordExpirated">
		<h1>PASSWORD EXPIRED</h1>
		<form:form method="post">
			<fieldset> 
				<div class="list"> 
					<div class="row"> 
						<div class="col_l">
							<form:label for="password" id="passwordLabel" path="password">Password <font color="#990000">*</font></form:label>
						</div> 
						<div class="col_l">
							<form:password path="password" id="password" class="registerInput" /><form:errors path="password" />
						</div> 
					</div> 
					<div class="row"> 
						<div class="col_l">
							<form:label for="confirmPassword" path="confirmPassword" id="confirmPasswordLabel">Confirm Password <font color="#990000">*</font></form:label>
						</div> 
						<div class="col_l">
							<form:password path="confirmPassword" id="confirmPassword" class="registerInput" type="password" /><form:errors path="confirmPassword" />
						</div>
					</div>
					<div class="row"> 
						<div class="col_l">
							<c:out value="${reCaptchaHTML}" escapeXml="false"/>
						</div>
					</div> 
					<div class="row"> 
						<div class="col_l">
					<input id="update" type="submit" value="Update" class="button_small">
						</div>
					</div> 
				</div>
			</fieldset>		
		</form:form>
