<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

		<div id="login">
	  		<form action="<c:url value="/loginProcess" />" method="post" class="form">
				Name:<br/>
				<input type="text" name="j_username" class="input"/><br/>
				Password:<br/>
				<input type="password" name="j_password" class="input"/><br/><br/>
				<input name="Log in" type="image" src="<c:url value="/images/butt_login.jpg"/>" alt="Log_in" title="invia il modulo" class="login_button"/>
				<br/>
			</form>  
			<p class="fyp">
				<fmt:message key="jsp.user.LoginUser.SendUserOrPasswordLink"/><a href="<c:url value="/user/SendUserDetails.do"/>"><i><b> Click here</b></i></a>
	        </p>             
		</div>
		
		<div id="register">
			<a href="<c:url value="/user/RegisterUser.do"/>">
				<img src="<c:url value="/images/butt_register.jpg"/>" alt="register_here" />		
		    </a>
		</div>
	
		<div id="guest">
			<form action="<c:url value="/loginProcess" />" method="post" class="form">
				<input type="hidden" name="j_username" value="guest" /><br/>
				<input type="hidden" name="j_password" value="guest" /><br/>
	  			<input id="loginAsGuest" type="image" src="<c:url value="/images/button_guest.jpg"/>" alt="LoginGuest" title="Login as guest"/>
			</form>
		</div>
	
		<c:if test="${not empty param.login_error}">
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
				<span class="errormessage" >
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
				</span>
			</c:if>
		</c:if>
