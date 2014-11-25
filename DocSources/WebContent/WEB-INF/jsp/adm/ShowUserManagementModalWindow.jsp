<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_FELLOWS">

<div id="userManagementDiv">
	<div id="addNewDiv" class="adminModalButtonContainer">
        <a id="addNew" class="button_large" href="<c:url value="/admin/CreateUser.do" />"><fmt:message key="adm.showUserManagementModalWindow.addNewUser"/></a>
	</div>
    
	<div id="editOrShowDiv" class="adminModalButtonContainer">
        <a id="editOrShow" class="button_large" href="<c:url value="/admin/ShowUserSearch.do" />"><fmt:message key="adm.showUserManagementModalWindow.edit"/></a>
	</div>
    
    <div id="globalPropertiesDiv" class="adminModalButtonContainer">
        <a id="globalProperties" class="button_large" href="#"><fmt:message key="adm.showUserManagementModalWindow.globalUser"/></a>
    </div>
	<input id="close" class="button_small" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#addNew").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
		});
		$j("#editOrShow").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide(); 
			return false;
		});
		
		$j("#globalProperties").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
		});
		
		$j("#close").click(function(){
			Modalbox.hide(); 
			return false;
		});
	});
</script>
	</security:authorize>