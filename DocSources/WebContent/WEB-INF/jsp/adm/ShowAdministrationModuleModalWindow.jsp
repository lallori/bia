<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
		
<div id="administrationModalDiv">
	<div id="systemWideDiv">
        <a id="systemWide" href="<c:url value="/admin/ShowApplicationProperties.do" />">System-Wide Properties</a>
	</div>
    
	<div id="userManagementDiv">
        <a id="userManagement" href="<c:url value="/admin/ShowUserManagement.do" />">User Management</a>
	</div>
    
<!--     <div id="reportsRevisionsDiv"> -->
<!--         <a id="reportsRevisions" href="/DocSources/adm/ReportsRevisions.html"><p>Reports and Revisions</p></a> -->
<!--     </div> -->
	<input id="close" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#systemWide").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
			}
		);
		$j("#userManagement").click(function(){
				Modalbox.show($j(this).attr("href"), {title: "USER MANAGEMENT", width: 270, height: 190});
				return false;
			}
		);
		$j("#ReportsRevisions").click(
			function(){
				$j("#body_right").load($j(this).attr("href"));
				Modalbox.hide(); return false;}
				);
		$j("#close").click(
			function(){
					Modalbox.hide(); return false;
						});
	});
</script>
	</security:authorize>