<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="checkVolumeDigitizedURL" value="/src/volbase/CheckVolumeDigitized.json">
		<c:param name="volNum"   value="${volume.volNum}" />
		<c:param name="volLetExt"   value="${volume.volLetExt}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
		<c:param name="volNum"	value="${volume.volNum}" />
		<c:param name="volLetExt" value="${volume.volLetExt}" />
		<c:param name="flashVersion" value="false" />
	</c:url>
	
	<c:url var="ShowVolumeInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="summaryId"	value="${volume.summaryId}" />
		<c:param name="volNum"	value="${volume.volNum}" />
		<c:param name="volLetExt" value="${volume.volLetExt}" />
		<c:param name="flashVersion"	value="false" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
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
		<h2 class="addNew"><fmt:message key="volbase.showDetailsVolume.addNew"/></h2>
		</c:if>	
		<%-- Editing Volume Record --%>	
		<c:if test="${volume.summaryId != 0}">
		<div id="volumeTitle">
			<div id="text">
				<%--<h3>${schedone.fondo} ${volume.volNum}${volume.volLetExt}</h3>--%>
				<h3>${fn2:getApplicationProperty("schedone.fondo")} - <fmt:message key="volbase.showDetailsVolume.volume2"/> ${volume.volNum}${volume.volLetExt}</h3>
				<h4>${volume.serieList}</h4>
				<h7>${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay} <fmt:message key="volbase.showDetailsVolume.to"/> ${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay} </h7>
				<c:if test="${volDocsRelated != 0 && volDocsRelated != 1}">
					<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.showDetailsVolume.documentsRelated"/>: <font color="#900">${volDocsRelated}</font><a href="${ShowDocumentsVolumeURL}" class="button_medium" id="showDocumentsRelated" title="View all the documents related to this Volume record" id="showDocumentsRelated"><fmt:message key="volbase.showDetailsVolume.showDocuments"/></a></p>
				</c:if>
				<c:if test="${volDocsRelated == 0}">
					<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.showDetailsVolume.documentsRelated"/>: <font color="#900">0</font></p>
				</c:if>
				<c:if test="${volDocsRelated == 1}">
					<p style="margin:10px 0 8px 10px;"><fmt:message key="volbase.showDetailsVolume.documentsRelated"/>: <font color="#900">${volDocsRelated}</font><a href="${ShowDocumentsVolumeURL}" class="button_medium" title="View the document related to this Volume record" id="showDocumentsRelated"><fmt:message key="volbase.showDetailsVolume.showDocument"/></a></p>
				</c:if>
			</div>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
				<c:choose>
					<c:when test="${not empty spine}">
						<div id="SpineVolumeDigitDiv">
							<img src="<c:url value="/mview/IIPImageServer.do?FIF=${spine}&WID=120"/>">
							<b><fmt:message key="volbase.showDetailsVolume.volumeSpine"/></b>
							<a id="ShowVolumeInManuscriptViewer" title="<fmt:message key="volbase.showDetailsVolume.showInManuscript"/>" href="${ShowVolumeInManuscriptViewerURL}"></a>
							<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="<fmt:message key="volbase.showDetailsVolume.help.showPreview"/>"></a>
						</div>
					</c:when>
					<c:when test="${not empty image}">
						<div id="SpineVolumeDigitDiv">
							<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
							<a id="ShowVolumeInManuscriptViewer" title="<fmt:message key="volbase.showDetailsVolume.showInManuscript"/>" href="${ShowVolumeInManuscriptViewerURL}"></a>
							<a id="ShowVolumeInVolumeExplorer" href="${ShowExplorerVolumeURL}" title="<fmt:message key="volbase.showDetailsVolume.help.showPreview"/>"></a>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${volume.digitized == false}">
							<div id="SpineVolumeNotDigitDiv">
								<span><fmt:message key="volbase.showDetailsVolume.toBeDigitized"/></span>
							</div>
						</c:if>
					</c:otherwise>
				</c:choose>
			</security:authorize>
				<!-- <div id="SpineVolumeDiv">
				<img src="<c:url value="/images/image_volume.png"/>" alt="default image" />
				<p><b>Costola</b> <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS"><a id="EditPortraitPerson" href="<c:url value="/de/peoplebase/EditPortraitPerson.html"/>">edit</a></security:authorize></p>
				</div> -->
			
			<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
				<div id="SpineVolumeNotDigitDiv">
					<span class="register"><fmt:message key="volbase.showDetailsVolume.register"/></span>
				</div>
			</security:authorize>
		</div>
		</c:if>
			
		<div id="EditDetailsVolumeDiv" class="background">
			<div class="title">
				<h5><fmt:message key="volbase.showDetailsVolume.title.details"/> </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
				<a id="EditDetailsVolume" href="${EditDetailsVolumeURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
			</div>
			
			
			<div class="list">
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDetailsVolume.volume"/></div>
					<div class="value">${volume.volNum}${volume.volLetExt}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDetailsVolume.startDate"/></div>
					<div class="value">${volume.startYear} ${volume.startMonthNum.monthName} ${volume.startDay}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="volbase.showDetailsVolume.endDate"/></div>
					<div class="value">${volume.endYear} ${volume.endMonthNum.monthName} ${volume.endDay}</div>
				</div>
				<div class="row">	
					<div class="item"><fmt:message key="volbase.showDetailsVolume.dateNotes"/></div>
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
<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS">
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