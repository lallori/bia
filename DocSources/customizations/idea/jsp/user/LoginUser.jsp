<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

			<c:if test="${not empty param.login_error}">
					<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
						<span id="password.errors" class="loginerror" >
							<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
						</span>
					</c:if>
				</c:if>
			<div id="login">
				<h1>PLEASE LOG IN</h1>
	  			<form name="login" action="<c:url value="/loginProcess" />" method="post" accept-charset="utf-8">
					<table class="loginform">
						<tr>
							<td>Username:</td>
						</tr>
						<tr>
							<td><input type="text" name="j_username" class="loginInput"/></td>
						</tr>
						<tr>
							<td>Password:</td>
						</tr>
						<tr>
							<td>
								<input type="password" name="j_password" class="loginInput"/>
							</td>
						</tr>
						<tr>
							<td><input name="Login" class="button_medium" type="submit" value="Login" title="send the module" class="button_medium"/>
							</td>
						</tr>
						<tr>
							<td><input type="checkbox" name="_spring_security_remember_me" value="yes"/><p class="remember">Remember me</p>
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
     
			<div class="buttons">
				<a id="register_here" href="<c:url value="/user/RegisterUser.do"/>"></a>
			</div>

<%--
 			<div id="guest">
	 				<form name="LoginGuest"  action="<c:url value="/loginProcess" />" method="post"><input id="register" type="image" src="<c:url value="/images/1024/img_transparent.png"/>" alt="LoginGuest" title="Login as guest"/><input type="hidden" name="j_username" value="guest" /><br/><input type="hidden" name="j_password" value="guest" /></form>
 			</div>
--%>
  
			<div id="footer">
				<a href="http://www.delmas.org/" class="delmas"><img src="<c:url value="/images/1024/img_delmas.jpg"/>" alt="The Gladys Krieble Delmas Foundation" /></a>
				<a href="http://bia.medici.org" target="_blank" class="bia"><img src="<c:url value="/images/1024/img_poweredBy.png"/>" alt="Bia - The Medici Archive Project" /></a>
				<a href="http://www.mellon.org" target="_blank" class="mellon"><img src="<c:url value="/images/1024/img_mellon.png"/>" alt="The Andrew W. Mellon Foundation" /></a>
				<a href="http://www.archivi.beniculturali.it" target="_blank" class="dga"><img src="<c:url value="/images/1024/img_dga.png"/>" alt="DGA" /></a>
				<a href="http://www.archiviodistato.firenze.it" target="_blank" class="archivio"><img src="<c:url value="/images/1024/img_archivio.png"/>" alt="Archivio di Stato di Firenze"></a>
			</div>
			
			<script type="text/javascript">
			$j(document).ready( function(){
				if ($j.browser.msie) {
				    alert( "WARNING! Microsoft Internet Explorer is NOT supported by BIA, please use Mozilla Firefox or Google Chrome." );
				    window.location.replace("http://www.medici.org");
				  }
				$j("#login").submit(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '100px'}});});
				$j("#RegisterUser").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '100px'}});});
				$j("#loginAsGuest").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '100px'}});});
				$j("#SendUserPasswordReset").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '100px'}});});
				$j("#SendUserActivationCode").click(function (){$j.blockUI({message: $j('img#displayBox'), css: { top:  ($j(window).height() - 300) /2 + 'px', left: ($j(window).width() - 170) /2 + 'px', width: '100px'}});});
				$j(window).unload(function (){$j.unblockUI();});
			});
			</script>

			<img id="displayBox" src="<c:url value="/images/1024/img_waiting.gif" />" style="display:none" />
