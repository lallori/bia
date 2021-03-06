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
		<h5><fmt:message key="adm.showUserControl.uSerControl"/></h5>
		<a id="EditUserControl" class="editButton" href="${EditUserControlURL}" title="Edit User Control"></a><span id="loading"/>
	</div>
	
	<div class="list">
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.username"/></div> 
			<div class="value">${user.account}</div> 
		</div>
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.firstName"/></div>
			<div class="value">${user.firstName}</div>
		</div>
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.middleName"/></div>
			<div class="value">${user.middleName}</div>
		</div>
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.lastName"/></div>
            <div class="value">${user.lastName}</div>
		</div>
        <div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.newPassword"/></div> 
			<div class="value"></div>
		</div>
        <div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.passwordExpires"/></div> 
			<div class="value"><fmt:formatDate pattern="MM/dd/yyyy" value="${user.expirationPasswordDate}" /></div>
		</div>
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.groupPolicies"/></div>
			<div class="value">&nbsp;</div>
		</div>
		<div class="row">
			<c:forEach items="${user.userRoles}" var="currentRole">
				<ul>
				<li>
					<i>${currentRole.userAuthority.description}</i>
				</li>
				</ul>	
			</c:forEach>			
		</div>
<!-- 		<hr> -->
		<div class="row">
			<div class="item37"><fmt:message key="adm.showUserControl.accountExpirationTime"/></div> 
			<div class="value"><fmt:formatDate pattern="MM/dd/yyyy" value="${user.expirationDate}" /></div>
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