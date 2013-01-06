<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<c:url var="EditUserEmailURL" value="/admin/EditUserEmail.do">
		<c:param name="account"   	value="${user.account}" />
	</c:url>

	<div id="EditEmailControlDiv" class="background">
		<div class="title">
			<h5>EMAIL CONTROL</h5>
			<a id="EditEmailControl" class="editButton" href="${EditUserEmailURL}" title="Edit Email Control"></a><span id="loading"/>
		</div>
		<div class="list">
			<div class="row">
				<div class="item37">Email adress</div> 
				<div class="value">${user.mail}</div>
			</div>
			<div class="row">
				<div class="item37">Edit display</div> 
				<div class="value">${user.mailHide ? 'Hide my email from everyone' : 'Allow others to see my email'}</div>
			</div>
			<div class="row">
				<div class="item37">Receive notification by email</div> 
				<div class="value">${user.mailNotification ? 'Activated' : 'Deactivated'}</div>
			</div>
			<div class="row">
				<div class="item37">Subscribe to Forum Topic</div> 
				<div class="value">${user.forumTopicSubscription ? 'Activated' : 'Deactivated'}</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	 	$j(document).ready(function() {			
	 		$j("#EditEmailControl").click(function(){
	 			$j("#EditEmailControlDiv").load($j(this).attr("href"));
	 			return false;
	 		});
	 	});
	</script>
</security:authorize>