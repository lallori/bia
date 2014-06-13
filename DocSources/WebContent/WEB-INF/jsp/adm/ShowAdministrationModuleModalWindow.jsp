<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_FELLOWS">
		
<div id="administrationModalDiv">
	<div id="systemWideDiv">
        <a id="systemWide" class="button_large" href="<c:url value="/admin/ShowApplicationProperties.do" />">System-Wide Properties</a>
	</div>
    
	<div id="userManagementDiv">
        <a id="userManagement" class="button_large" href="<c:url value="/admin/ShowUserManagement.do" />">User Management</a>
	</div>
    
	<div id="memoryUsageDiv">
        <a id="memoryUsage" class="button_large" href="<c:url value="/admin/ShowMemoryUsage.do" />">Memory Usage</a>
	</div>

	<div id="accessLogDiv">
        <a id="accessLog" class="button_large" href="<c:url value="/admin/ShowAccessLogSearch.do" />">Access Log</a>
	</div>
	
	<div id="whoIsOnlineDiv">
        <a id="whoIsOnline" class="button_large" href="<c:url value="/admin/ShowWhoIsOnline.do" />">Who is Online</a>
	</div>
	
	<div id="europeanaManagerDiv">
        <a id="europeana" class="button_large" href="<c:url value="/europeana/europeanaView.do" />">Manage Europeana Job</a>
	</div>

	<input id="close" class="button_small" type="submit" title="Close Digitization Module window" value="Close"/>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#systemWide").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
		});

		$j("#userManagement").click(function(){
			Modalbox.show($j(this).attr("href"), {title: "USER MANAGEMENT", width: 270, height: 210});
			return false;
		});

		$j("#memoryUsage").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
		});
		
		$j("#accessLog").click(function(){
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide();
			return false;
		});
		$j("#whoIsOnline").click(function(){
			var tabName = 'Who is Online' 
			var numTab = 0;
			
			if(tabName.length > 20){
				tabName = tabName.substring(0,17) + "...";
			}
			
			//Check if already exist a tab with this name
			var tabExist = false;
			$j("#tabs ul li a").each(function(){
				if(!tabExist){
					if(this.text != ""){
						numTab++;
					}
				}
				if(this.text == tabName){
					tabExist = true;
				}
			});
			
			if(!tabExist){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
			}else{
				$j("#tabs").tabs("select", numTab);
			}

			Modalbox.hide();
			return false;
		});
		
		$j("#europeana").click(function() {
			window.open($j(this).attr("href"));
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