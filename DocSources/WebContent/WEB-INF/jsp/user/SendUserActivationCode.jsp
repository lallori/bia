<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

		<form:form method="post" cssClass="recovery">
			<div id="captcha_recovery">
				<c:out value="${reCaptchaHTML}" escapeXml="false"/>
			</div>
			
			<div id="recovery">
				<p>Did you not receive Activation code?<br/>Enter here your E-mail adress to get a new one:</p><br/>  		

					<table align="center">
						<tr>
							<td><form:input path="mail" cssClass="input_recovery"/><form:errors path="mail" /></td>
						</tr>
						<tr>
							<td align="center">
								<input name="submit" type="image" src="<c:url value="/images/button_submit.jpg"/>" alt="submit" title="submit" style="margin:145px 0px 0px 70px"/>
							</td>
						</tr>

					</table>
			</div>
		</form:form>  
			