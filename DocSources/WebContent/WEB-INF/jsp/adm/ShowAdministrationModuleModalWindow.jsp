<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
		<link rel="stylesheet" type="text/css" href="/DocSources/styles/1024/MainContent.css" />
<script type="text/javascript" src="/DocSources/scripts/jquery.tooltip.js"></script>

<div id="administrationModalDiv">
	<div id="SystemManagementDiv">
        <a id="SystemManagement" href="/DocSources/adm/SystemManagement.html"><p>System Management</p></a>
	</div>
    
	<div id="UserManagementDiv">
        <a id="UserManagement" href="/DocSources/adm/UserManagement.html"><p>User Management</p></a>
	</div>
    
    <div id="ReportsRevisionsDiv">
        <a id="ReportsRevisions" href="/DocSources/adm/ReportsRevisions.html"><p>Reports and Revisions</p></a>
    </div>
	<input id="close" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>

<script>
	$j(document).ready(function() {
		$j("#SystemManagement").click(
			function(){
				$j("#body_left").load($j(this).attr("href"));
				Modalbox.hide(); return false;}
				);
		$j("#UserManagement").click(
			function(){
				$j("#body_left").load($j(this).attr("href"));
				Modalbox.hide(); return false;}
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