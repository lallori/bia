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
	
	<c:url var="ShowVolumeInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="summaryId"	value="${volume.summaryId}" />
		<c:param name="volNum"	value="${volume.volNum}" />
		<c:param name="volLetExt" value="${volume.volLetExt}" />
		<c:param name="flashVersion"	value="false" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
		<c:url var="EditDetailsVolumeURL" value="/de/volbase/EditDetailsVolume.do">
			<c:param name="summaryId"   value="${volume.summaryId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowDocumentsVolumeURL" value="/src/volbase/ShowDocumentsVolume.do">
		<c:param name="summaryId" value="${volume.summaryId}" />
	</c:url>
	
	<div id="volumeDiv">
		<%-- Create new Volume Record --%>
		<c:if test="${volume.summaryId == 0}">
		<h2 class="addNew">ADD New - Volume Record</h2>
		</c:if>	
		<%-- Editing Volume Record --%>	
		<c:if test="${volume.summaryId != 0}">
		<div id="volumeTitle">
			<div id="text">
				<%--<h3>${schedone.fondo} ${volume.volNum}${volume.volLetExt}</h3>--%>
				<h3>${volume.volNum}${volume.volLetExt}</h3>
				<h4>${volume.serieList}</h4>
				<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} to ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
				<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
					<p style="margin:10px 0 8px 10px;">Documents related to this Volume record: <font color="#900">${volDocsRelated}</font><a href="${ShowDocumentsVolumeURL}" class="button_medium" id="showDocumentsRelated" title="View all the documents related to this Volume record" id="showDocumentsRelated">Show documents</a></p>
				</c:if>
				<c:if test="${volDocsRelated == 0}">
					<p style="margin:10px 0 8px 10px;">Documents related to this Volume record: <font color="#900">0</font></p>
				</c:if>
				<c:if test="${volDocsRelated == 1}">
					<p style="margin:10px 0 8px 10px;">Document related to this Volume record: <font color="#900">${volDocsRelated}</font><a href="${ShowDocumentsVolumeURL}" class="button_medium" title="View the document related to this Volume record" id="showDocumentsRelated">Show document</a></p>
				</c:if>
			</div>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
				<c:if test="${not empty image}">
				<div id="SpineVolumeDigitDiv">
					<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
					<b>Volume Spine</b>
					<a id="ShowVolumeInManuscriptViewer" title="Show in Manuscript Viewer" title="Show in Manuscript Viewer" href="${ShowVolumeInManuscriptViewerURL}"></a>
					<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="Show preview on the right screen"></a>
				</div>
				</c:if>
				<c:if test="${empty image && volume.digitized == false}">
					<div id="SpineVolumeNotDigitDiv">
						<span>To be digitized</span>
					</div>
				</c:if>
				<c:if test="${empty image && volume.digitized == true}">
					<div id="SpineVolumeNotDigitDiv">
						<span>Spine not available</span>						
					</div>
				</c:if>
			</security:authorize>
				<!-- <div id="SpineVolumeDiv">
				<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
				<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditPortraitPerson" href="<c:url value="/de/peoplebase/EditPortraitPerson.html"/>">edit</a></security:authorize></p>
				</div> -->
			
			<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
				<div id="SpineVolumeNotDigitDiv">
					<span class="register">To see this Volume you must register</span>
				</div>
			</security:authorize>
		</div>
		</c:if>
			
		<div id="EditDetailsVolumeDiv" class="background">
			<div class="title">
				<h5>VOLUME DETAILS </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
				<a id="EditDetailsVolume" href="${EditDetailsVolumeURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
			</div>
			
			
			<div class="list">
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
	</div>

	<script type="text/javascript">
	$j(document).ready(function() {
		if($j("#ShowVolumeInVolumeExplorer").length != 0){
			$j("#EditDetailsVolumeDiv").css('min-height', '260px');
		}

		$j("#EditDetailsVolumeDiv").volumeExplorer( {
			summaryId				: "${volume.summaryId}",
			volNum					: "${volume.volNum}",
			volLetExt				: "${volume.volLetExt}",
			checkVolumeDigitizedURL	: "${checkVolumeDigitizedURL}",
			showExplorerVolumeURL	: "${ShowExplorerVolumeURL}",
			target 					: $j("#body_right") 
		});  
		
		$j("#ShowVolumeInManuscriptViewer").open({width: screen.width, height: screen.height, scrollbars: false});

		//For check if already exsist a tab with volume explorer
		$j("#ShowVolumeInVolumeExplorer").click(function(){
			var tabName = "<span id='titleTab${volume.volNum}${volume.volLetExt}'>Explore Volume ${volume.volNum}${volume.volLetExt}</span>";
			var numTab = 0;
			
			//Check if already exist a tab with this person
			var tabExist = false;
			$j("#tabs ul li a").each(function(){
				var toTest = "";
				toTest += this.text;
				if(!tabExist){
					if(toTest != ""){
						numTab++;
					}
				}
				if(this.text == tabName || toTest.indexOf("Explore Volume ${volume.volNum}${volume.volLetExt}") != -1){
					tabExist = true;
				}
			});
			
			if(!tabExist){
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				return false;
			}else{
				$j("#tabs").tabs("select", numTab);
				$j('#tabs ul li').eq(numTab).data('loaded', false).find('a').attr('href', $j(this).attr('href'));
				$j("#tabs").tabs("load", numTab);
				return false;
			}
		});
		
		$j("#showDocumentsRelated").click(function(){
			//var tabName = "Docs Volume ${volume.summaryId}";
			var tabName = "Docs Volume ${volume.volNum}${volume.volLetExt}";
			var numTab = 0;
			
			//Check if already exist a tab with this person
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
				return false;
			}else{
				$j("#tabs").tabs("select", numTab);
				return false;
			}
		});
	});
	</script>
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditContextVolume").css('visibility', 'visible');
	        $j("#EditCorrespondentsVolume").css('visibility', 'visible'); 
	        $j("#EditDescriptionVolume").css('visibility', 'visible'); 
			$j("#EditDetailsVolume").css('visibility', 'visible'); 

			$j("#EditDetailsVolume").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsVolumeDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>