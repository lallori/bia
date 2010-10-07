<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<div id="recovery">
		<div class="recovery">
			<p align="center">Did you forgot your password?</p>
			<p align="center">Just enter here your E-mail adress to get a new one:</p><br/>  		
			<form:form method="post">
				<table align="center">
					<tr>
						<td>
							<form:input path="mail" cssClass="input"/><form:errors path="mail" />
						</td>
					</tr>
					<tr>
						<td>
							<c:out value="${reCaptchaHTML}" escapeXml="false"/>
						</td>
					</tr>
					<tr>
						<td>
							<input name="submit" type="image" src="../images/button_submit.jpg" alt="submit" title=	"submit"/>
						</td>
					</tr>
				</table>
			</form:form>
		</div>             
	</div>

	<c:url var="tickIcon" value="/images/tick.png"/>
	<c:url var="busyIcon" value="/images/busy_icon.gif"/>


	<script type="text/javascript">
		function fieldValidated(field, result) {
			if (result.valid) {
				$("#" + field ).removeClass("error");
				$("#" + field + "\\.errors").remove();

				if ($("#" + field + "\\.errors").length == 0) {
					$("#" + field).after("<span id='" + field + ".errors'><image id='" + field + "OkIcon' src='${tickIcon}'/></span>");
				} else {
					$("#" + field + ".errors").html("<span id='" + field + ".errors'><image id='" + field + "OkIcon' src='${tickIcon}'/></span>");		
				}
			} else {
				$("#" + field ).addClass("error");
				if ($("#" + field + "\\.errors").length == 0) {
					$("#" + field).after("<span id='" + field + ".errors'>" + result.message + "</span>");		
				} else {
					$("#" + field + "\\.errors").html("<span id='" + field + ".errors'>" + result.message + "</span>");		
				}
			}			
		}
	</script>
