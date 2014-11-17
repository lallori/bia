<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowTeachingUserURL" value="/teaching/ShowUser.do">
		<c:param name="account" value="${user.account}" />
	</c:url>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
	<div id="EditUserControlDiv" class="background">
		<div class="title">
			<h5><fmt:message key="teaching.showTeachingUser.uSerControl"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.username"/></div> 
				<div class="value">${user.account}</div> 
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.firstName"/></div>
				<div class="value">${user.firstName}</div>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.middleName"/></div>
				<div class="value">${user.middleName}</div>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.lastName"/></div>
				<div class="value">${user.lastName}</div>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.emailAddress"/></div> 
				<div class="value">${user.mail}</div>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key="teaching.showTeachingUser.teachingGroupPolicy"/></div>
				<c:choose>
					<c:when test="${empty teachingPolicy}">
						<c:url var="GrantUserURL" value="/teaching/GrantStudentPermission.json">
							<c:param name="account" value="${user.account}" />
						</c:url>
						<div class="value"><fmt:message key="teaching.showTeachingUser.noPermission"/></div>
						<a id="grantButton" class="button_large" href="${GrantUserURL}" title="Grant student permission"><fmt:message key="teaching.showTeachingUser.grantPermission"/></a>
					</c:when>
					<c:when test="${teachingPolicy == 'STUDENTS'}">
						<c:url var="RevokeUserURL" value="/teaching/RevokeStudentPermission.json">
							<c:param name="account" value="${user.account}" />
						</c:url>
						<div class="value"><fmt:message key="teaching.showTeachingUser.sTudents"/></div>
						<a id="revokeButton" href="${RevokeUserURL}" title="Revoke student permission"><fmt:message key="teaching.showTeachingUser.revokePermission"/></a>
					</c:when>
					<c:otherwise>
						<div class="value">${teachingPolicy}</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>   
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			$j("#grantButton").click(function() {
				$j.ajax({
					url: $j(this).attr("href"),
					type: 'POST',
					dataType: 'json',
					cache: false, 
					success: function(data) {
						if (data.operation === 'OK') {
	    					$j("#body_left").load('${ShowTeachingUserURL}');
						} else {
							alert('Grant failed!!! Please retry!');
						}
    				},
    				error: function() {
    					alert('Server error...please retry');
    				}
				});
				return false;
			});
			
			$j("#revokeButton").click(function() {
				$j.ajax({
					url: $j(this).attr("href"),
					type: 'POST',
					dataType: 'json',
					cache: false, 
					success: function(data) {
						if (data.operation === 'OK') {
	    					$j("#body_left").load('${ShowTeachingUserURL}');
						} else {
							alert('Revoke failed!!! Please retry!');
						}
    				},
    				error: function() {
    					alert('Server error...please retry');
    				}
				});
				return false;
			});
			
		});
	</script>
</security:authorize>