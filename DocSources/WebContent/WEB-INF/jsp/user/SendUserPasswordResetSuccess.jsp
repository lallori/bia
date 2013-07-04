<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<div id="usersuccess">
				<p class="usersuccess">An e-mail has been sent to the e-mail address you provided in the previous form.<br/><br/>To access the system, please check your email account and confirm your password reset.</p>  		
				<a href="<c:url value="/"/>"><img src="<c:url value="/images/button_login.jpg"/>" alt="Login"/></a>
					<input name="Login" class="button_medium" type="submit" value="Login-new" title="send the module">
			</div>

			
