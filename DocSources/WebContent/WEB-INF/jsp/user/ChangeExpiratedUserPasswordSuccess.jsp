<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<div id="usersuccess">
					<h1>CONGRATULATIONS!</h1>
					<p class="activsuccess">Your password has been changed.<br/><br/>
					<a href="<c:url value="/"/>"><img src="<c:url value="/images/button_login.jpg"/>" alt="Login"/></a></p>
			</div>
