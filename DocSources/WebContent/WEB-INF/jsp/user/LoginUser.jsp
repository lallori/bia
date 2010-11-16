<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<script type='text/javascript' src='<c:url value="/scripts/jquery-1.4.2.js"/>'></script>
			<script type='text/javascript' src='<c:url value="/scripts/jquery.blockUI.js"/>'></script>

			<div id="login">
	  			<form name="login" action="<c:url value="/loginProcess" />" method="post">
					<table class="loginform">
						<tr>
							<td>Name:</td>
						</tr>
						<tr>
							<td><input type="text" name="j_username" class="input" style="margin-left:15px"/></td>
						</tr>
						<tr>
							<td>Password:</td>
						</tr>
						<tr>
							<td>
								<input type="password" name="j_password" class="input" style="margin-left:15px"/><br/><br/>
							</td>
						</tr>
						<tr>
							<td align="right"><input name="Login" type="image" src="<c:url value="/images/button_login.jpg"/>" alt="Log_in" title="send the module" style="margin-left:35px"/>
							</td>
						</tr>
						<tr>
							<td><input type="checkbox" name="_spring _security_remember_me" style="margin:11px 0px 0px 50px"/><p class="remember">Remember me</p>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<p class="fyp"><fmt:message key="jsp.user.LoginUser.SendUserPasswordResetLink"/><a id="SendUserPasswordReset" href="<c:url value="/user/SendUserPasswordReset.do"/>"><i><b>Click here</b></i></a><br/><fmt:message key="jsp.user.LoginUser.SendUserActivationCodeLink"/><a id="SendUserActivationCode" href="<c:url value="/user/SendUserActivationCode.do"/>"><i><b>Click here</b></i></a></p>
							</td>
						</tr>
					</table>
				</form>  
			</div>
      
			<div id="register_here">
				<a id="RegisterUser" href="<c:url value="/user/RegisterUser.do"/>"><img src="<c:url value="/images/button_register.jpg"/>" alt="register_here" /></a>
			</div>
  
			<div id="guest">
				<form name="LoginGuest"  action="<c:url value="/loginProcess" />" method="post"><input id="loginAsGuest" type="image" src="<c:url value="/images/button_guest.jpg"/>" alt="LoginGuest" title="Login as guest"/><input type="hidden" name="j_username" value="guest" /><br/><input type="hidden" name="j_password" value="guest" /></form>
			</div>

			<c:if test="${not empty param.login_error}">
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
					<span class="errormessage" >
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
					</span>
				</c:if>
			</c:if>
			<script type="text/javascript">
			$(document).ready( function(){
				$("#login").submit(function (){$.blockUI({message: $('img#displayBox'), css: { top:  ($(window).height() - 300) /2 + 'px', left: ($(window).width() - 170) /2 + 'px', width: '300px'}});});
				$("#RegisterUser").click(function (){$.blockUI({message: $('img#displayBox'), css: { top:  ($(window).height() - 300) /2 + 'px', left: ($(window).width() - 170) /2 + 'px', width: '300px'}});});
				$("#loginAsGuest").click(function (){$.blockUI({message: $('img#displayBox'), css: { top:  ($(window).height() - 300) /2 + 'px', left: ($(window).width() - 170) /2 + 'px', width: '300px'}});});
				$("#SendUserPasswordReset").click(function (){$.blockUI({message: $('img#displayBox'), css: { top:  ($(window).height() - 300) /2 + 'px', left: ($(window).width() - 170) /2 + 'px', width: '300px'}});});
				$("#SendUserActivationCode").click(function (){$.blockUI({message: $('img#displayBox'), css: { top:  ($(window).height() - 300) /2 + 'px', left: ($(window).width() - 170) /2 + 'px', width: '300px'}});});
				$(window).unload(function (){$.unblockUI();});
			});
			</script>

			<img id="displayBox" src="<c:url value="/images/waiting.gif" />" style="display:none" />
