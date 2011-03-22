<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<div id="recovery">
				<p>Did you forget your password?<br/>Enter here your E-mail adress to get a new one:</p><br/>  		
				<form:form method="post" cssClass="recovery">
					<form:input path="mail" cssClass="input_recovery"/><form:errors path="mail"/><br/><br/>
					<div id="captcha_recovery">
						<c:out value="${reCaptchaHTML}" escapeXml="false"/>
					</div>
					<input name="submit" type="image" src="<c:url value="/images/1024/button_submit.jpg"/>" alt="submit" title="submit" style="margin:10px 0px 0px 170px"/>
				</form:form>  
			</div>
			