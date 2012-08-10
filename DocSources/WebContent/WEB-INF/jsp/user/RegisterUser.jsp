<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<link rel="stylesheet" href="<c:url value="/styles/1024/js/jquery.autocomplete2.css" />" type="text/css" media="screen, projection">

			<div id="registrationForm">
				<form:form method="post">
					<table class="registrationForm" cellpadding="3">
						<tr>
							<td align="right"><form:label id="firstNameLabel" for="firstName" path="firstName" cssErrorClass="error"><b>First Name:<font color="#990000">*</font></b></form:label></td>
							<td><form:input id="firstName" path="firstName" cssClass="registerInput"/><form:errors path="firstName" cssClass="inputerrors"/></td>
							<td align="right"><form:label id="passwordLabel" for="password" path="password" cssErrorClass="error"><b>Password:<font color="#990000">*</font></b></form:label></td>
							<td><form:password path="password" cssClass="registerInput" cssStyle="align:left"/><form:errors path="password" cssClass="inputerrors"/></td>
							<td align="right" width="90"><form:label id="confirmPasswordLabel" for="confirmPassword" path="confirmPassword" cssErrorClass="error"><b>Confirm Password:<font color="#990000">*</font></b></form:label></td>       
							<td><input id="confirmPassword" name="confirmPassword" type="password"class="registerInput" value=""/></td>
						</tr>    	
						<tr>        		
							<td align="right"><form:label id="lastNameLabel" for="lastName" path="lastName" cssErrorClass="error"><b>Last Name:<font color="#990000">*</font></b></form:label></td>
							<td><form:input id="lastName" path="lastName" cssClass="registerInput"/><form:errors path="lastName" cssClass="inputerrors"/></td>
							<td align="right"><form:label id="countryDescriptionLabel" for="countryDescription" path="countryDescription" cssErrorClass="error"><b>Country:<font color="#990000">*</font></b></form:label></td>
							<td><form:input id="countryAutoCompleter" path="countryDescription" cssClass="registerInput"/><form:errors path="countryDescription" cssClass="inputerrors"/></td>
							<td align="right"><form:label id="organizationLabel" for="organization" path="organization" cssErrorClass="error"><b>Organization or University Affiliation:</b></form:label></td>
							<td><form:input id="organization" path="organization" cssClass="registerInput"/><form:errors path="organization" cssClass="inputerrors"/></td>
						</tr>	
						<tr>
							<td align="right"><form:label id="mailLabel" for="mail" path="mail" cssErrorClass="error"><b>Email:<font color="#990000">*</font></b></form:label></td>
							<td><form:input id="mail" path="mail" cssClass="registerInput"/><form:errors path="mail" cssClass="inputerrors"/></td>
							<td align="right"><form:label id="cityLabel" for="city" path="city" cssErrorClass="error"><b>City:<font color="#990000">*</font></b></form:label></td>
							<td><form:input id="city" path="city" cssClass="registerInput"/><form:errors path="city" cssClass="inputerrors"/></td>
							<td align="right"><form:label id="titleLabel" for="title" path="title" cssErrorClass="error">Title:</form:label></td>
							<td><form:input id="tittle" path="title" cssClass="registerInput"/><form:errors path="title" cssClass="inputerrors"/></td>
						</tr>     
						<tr>        	        	
							<td align="right"><b>Verification:<font color="#990000">*</font></b></td>
							<td><div id="captcha_register"><c:out value="${reCaptchaHTML}" escapeXml="false"/></div></td>
							<td align="right"></td>
							<td colspan="3"><form:checkbox path="agree" cssStyle="margin:10px 0px 0px 220px"/><p class="readThis">I have read and agree to the <b><a href="#">Terms of Use</a></b>.<font color="#990000">*</font></p></td>
						</tr>
						<tr>
							<td colspan="6"><input id="create" type="image" src="<c:url value="/images/1024/button_register.jpg"/>" alt="submit" title="submit form" style="margin:12px 0px 0px 670px"/></td>
						</tr>
						<tr>
							<td colspan="6"><p class="already">Already registered? <b><a href="<c:url value="/"/>">Log in</a></b></p></td>
						</tr>
					</table>
	  					<form:hidden id="countryCode" path="countryCode"/>
				</form:form>
			</div>
        
	<c:url var="findCountryUrl" value="/user/ajax/FindCountries.json"/>
	<c:url var="tickIcon" value="/images/tick.png"/>
	<c:url var="busyIcon" value="/images/busy_icon.gif"/>

	<script type="text/javascript">
		var RecaptchaOptions = {
		   theme : 'clean'
		};

		$j(document).ready(function() {
			var a = $j('#countryAutoCompleter').autocomplete({ 
			    serviceUrl:'${findCountryUrl}',
			    minChars:1, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:300,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#countryCode').val(data); }
			  });
		});

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
