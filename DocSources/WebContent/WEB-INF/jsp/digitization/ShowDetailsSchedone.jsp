<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditDetailsSchedoneURL" value="/digitization/EditDetailsSchedone.do">
		<c:param name="schedoneId"   value="${schedone.schedoneId}" />
	</c:url>

<div id="EditSchedoneDetailsDiv" class="background">
	<div class="title">
		<h5>SCHEDONE DETAILS</h5>
		<security:authorize ifAnyGranted="ROLE_DIGITIZATION_USERS">
			<a id="EditDetailsSchedone" href="${EditDetailsSchedoneURL}" class="editButton" title="Edit Schedone details"></a><span id="loading"/>
		</security:authorize>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Istituto</div> 
			<div class="value60">Archivio di Stato di Firenze</div> 
		</div>
        <div class="row">
			<div class="item">Fondo</div> 
			<div class="value60">Mediceo del Principato</div> 
		</div>
        <div class="row">
			<div class="item">Serie</div> 
			<div class="value60">Minute di Lettere</div> 
		</div>
        <div class="row">
			<div class="item">Date estreme</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Descrizione contenuto</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Legatura</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Supporto</div> 
			<div class="value60"></div> 
		</div>
        <div class="row">
			<div class="item">Cartulazione</div> 
			<div class="value60"></div> 
		</div>
	</div>
</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'visible');
	        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
	        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
			$j("#EditDetailsVolume").css('visibility', 'visible'); 

			$j("#EditDetailsVolume").volumeExplorer( {
				summaryId				: "${volume.summaryId}",
				volNum					: "${volume.volNum}",
				volLetExt				: "${volume.volLetExt}",
				checkVolumeDigitizedURL	: "${checkVolumeDigitizedURL}",
				showExplorerVolumeURL	: "${ShowExplorerVolumeURL}",
				target 					: $j("#body_right") 
			});  

			$j("#EditDetailsVolume").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsVolumeDiv").load($j(this).attr("href"));
				return false;
			});
			
			//For check if already exsist a tab with volume explorer
			$j("#ShowVolumeInVolumeExplorer").click(function(){
				var tabName = "Explore Volume ${volume.volNum}${volume.volLetExt}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".num_docs").click(function(){
				//var tabName = "Docs Volume ${volume.summaryId}";
				var tabName = "Docs Volume ${volume.volNum}${volume.volLetExt}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>
</security:authorize>