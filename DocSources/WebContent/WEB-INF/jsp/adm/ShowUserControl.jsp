<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
		<c:url var="EditUserControlURL" 	value="/admin/EditUserControl.do">
			<c:param name="account"   	value="${user.account}" />
		</c:url>
	</security:authorize>
	
	
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<div id="EditUserControlDiv" class="background">
	<div class="title">
		<h5>USER CONTROL</h5>
		<a id="EditUserControl" class="editButton" href="${EditUserControlURL}" title="Edit User Control"></a>
	</div>
	
	<div class="list">
		<div class="row">
			<div class="item37">Username</div> 
			<div class="value">${user.account}</div> 
		</div>
		<div class="row">
			<div class="item37">First Name</div>
			<div class="value">${user.firstName}</div>
		</div>
		<div class="row">
			<div class="item37">Last Name</div>
            <div class="value">${user.lastName}</div>
		</div>
        <div class="row">
			<div class="item37">New Password</div> 
			<div class="value"></div>
		</div>
        <div class="row">
			<div class="item37">Password expires</div> 
			<div class="value">march 03 2012</div>
		</div>
		<div class="row">
			<div class="item37">Group policies</div> 
			<c:forEach items="${user.userRoles}" var="currentRole">
				<div class="value">${currentRole}</div>
			</c:forEach>			
		</div>
		<div class="row">
			<div class="item37">Account Expiration Time</div> 
			<div class="value">${user.expirationDate}</div>
		</div>
    </div>   
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditUserControl").click(function(){
			$j("#EditUserControlDiv").load($j(this).attr("href"));
			return false;
		});
	});
</script>
</security:authorize>