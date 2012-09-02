<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">

<div id="userManagementDiv">
	<div id="addNewDiv">
        <a id="addNew" href="<c:url value="/admin/CreateUser.do" />"><p>Add New User</p></a>
	</div>
    
	<div id="editOrShowDiv">
        <a id="editOrShow" href="<c:url value="/admin/ShowUserSearch.do" />"><p>Edit or Show Existing User</p></a>
	</div>
    
    <div id="globalPropertiesDiv">
        <a id="globalProperties" href="#"><p>Global User Properties</p></a>
    </div>
	<input id="close" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#addNew").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); return false;}
					);
		$j("#editOrShow").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide(); 
			return false;
			}
		);
			$j("#globalProperties").click(
				function(){
					$j("#body_left").load($j(this).attr("href"));
					Modalbox.hide(); return false;}
					);
			$j("#close").click(
				function(){
						Modalbox.hide(); return false;
							});
	});
</script>
	</security:authorize>