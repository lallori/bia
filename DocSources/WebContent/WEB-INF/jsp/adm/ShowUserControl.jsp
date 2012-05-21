<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%-- 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS"> --%>
<%-- 		<c:url var="" 	value=""> --%>
<%-- 			<c:param name=""   	value="" /> --%>
<%-- 		</c:url> --%>
<%-- 	</security:authorize> --%>
	
	
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
	<div id="EditUserControlDiv" class="background">
	<div class="title">
		<h5>USER CONTROL</h5>
		<a id="EditUserControl" class="editButton" href="" title="Edit User Control"></a>
	</div>
	
	<div class="list">
		<div class="row">
			<div class="item37">Username</div> 
			<div class="value">AA</div> 
		</div>
		<div class="row">
			<div class="item37">First Name</div>
			<div class="value">Alessio</div>
		</div>
		<div class="row">
			<div class="item37">Last Name</div>
            <div class="value">Assonitis</div>
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
			<div class="value">Onsite Fellow</div>
		</div>
		<div class="row">
			<div class="item37">Account Expiration Time</div> 
			<div class="value">july 22 2012</div>
		</div>
    </div>   
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditUserControl").click(
				function(){
					$j("#EditUserControlDiv").load($j(this).attr("href"));
					return false;
				});
	});
</script>
</security:authorize>