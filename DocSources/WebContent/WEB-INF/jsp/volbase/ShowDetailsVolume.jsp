<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="checkVolumeDigitizedURL" value="/src/volbase/CheckVolumeDigitized.json">
		<c:param name="volNum"   value="${volume.volNum}" />
		<c:param name="volLetExt"   value="${volume.volLetExt}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
		<c:param name="flashVersion" value="false" />
	</c:url>

	<c:url var="EditDetailsVolumeURL" value="/de/volbase/EditDetailsVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	
	<c:url var="ShowDocumentsVolumeURL" value="/de/peoplebase/ShowDocumentsVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	<div id="personDiv">
		<div id="personTitle">	
	<c:if test="${volume.volNum != 0}">
		<h3>Mediceo del Principato Volume ${volume.volNum}</h3>
		<h4>${volume.serieList}</h4>
		<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
	</c:if>
	
	<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
			<a href="${ShowDocumentsVolumeURL}" class="num_docs" title="Click here to view all documents related">${volDocsRelated} Documents related</a>
		</c:if>
		<c:if test="${volDocsRelated == 0}">
			<a class="num_docs">0 Documents related</a>
		</c:if>
		<c:if test="${volDocsRelated == 1}">
			<a href="${ShowDocumentsVolumeURL}" class="num_docs" title="Click here to view all documents related">${volDocsRelated} Document related</a>
	</c:if>
	
	
	<div id="EditDetailsVolumeDiv" class="background">
		<div class="title">
			<h5>VOLUME DETAILS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditDetailsVolume" href="${EditDetailsVolumeURL}" class="editButton"></a><span id="loading"/>
		</security:authorize>
		</div>
		
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${not empty image}">
			<div id="SpineVolumeDigitDiv">
				<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
				<b>Volume Spine</b>
				<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="Show preview on the right screen"></a>
			</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == false}">
				<div id="SpineVolumeNotDigitDiv">
					<span>To be digitized</span>
					<img src="<c:url value="/images/1024/img_volume.png"/>" alt="Volume" width="120px" height="160px">
					<p>Volume Spine</p>
				</div>
			</c:if>
			<c:if test="${empty image && volume.digitized == true}">
				<div id="SpineVolumeNotDigitDiv">
					<span>Spine not available</span>
					<img src="<c:url value="/images/1024/img_volume.png"/>" alt="Volume" width="120px" height="160px">
					<p>Volume Spine</p>
				</div>
			</c:if>
		</security:authorize>
			<!-- <div id="SpineVolumeDiv">
			<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
			<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="/DocSources/de/peoplebase/EditPortraitPerson.html">edit</a></security:authorize></p>
			</div> -->
		
		<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<div id="SpineVolumeNotDigitDiv">
				<span class="register">To see this Volume you must register</span>
				<img alt="Volume" src="<c:url value="/images/1024/img_volume.png"/>" width="120px" height="160px">
				<p>Volume Spine</p>
			</div>
		</security:authorize>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Volume/Filza</div>
				<div class="value">${volume.volNum}${volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item">Start Date</div>
				<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
			</div>
			<div class="row">
				<div class="item">End Date</div>
				<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
			</div>
			<div class="row">	
				<div class="item">Date Notes</div>
				<div class="value">${volume.dateNotes}</div>
			</div>
		</div>
	</div>
	<br />
	<br />
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'visible');
	        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
	        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
			$j("#EditDetailsVolume").css('visibility', 'visible'); 

			if($j("#ShowVolumeInVolumeExplorer").length != 0){
				$j("#EditDetailsVolumeDiv").css('min-height', '300px');
			}
			
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