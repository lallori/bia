<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
							<td align="right"><input name="Login" type="image" src="<c:url value="/images/1024/img_transparent.png"/>" alt="Log_in" title="send the module" style="margin-left:35px" class="button_login"/>
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
      
			<a id="register_here" href="<c:url value="/user/RegisterUser.do"/>"></a>
  
			<div id="guest">
				<form name="LoginGuest"  action="<c:url value="/loginProcess" />" method="post"><input id="register" type="image" src="<c:url value="/images/1024/img_transparent.png"/>" alt="LoginGuest" title="Login as guest"/><input type="hidden" name="j_username" value="guest" /><br/><input type="hidden" name="j_password" value="guest" /></form>
			</div>

			<c:if test="${not empty param.login_error}">
				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
					<span class="errormessage" >
						<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
					</span>
				</c:if>
			</c:if>
			<script type="text/javascript">
			$j(document).ready( function(){
				$j("#login").submit(function (){$j.blockUI({message: $('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '300px'}});});
				$j("#RegisterUser").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '300px'}});});
				$j("#loginAsGuest").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '300px'}});});
				$j("#SendUserPasswordReset").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '300px'}});});
				$j("#SendUserActivationCode").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '300px'}});});
				$j(window).unload(function (){$j.unblockUI();});
			});
			</script>

			<img id="displayBox" src="<c:url value="/images/waiting.gif" />" style="display:none" />
