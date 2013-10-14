<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="explorerVolumeModalWindowURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false"/>
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="summaryId"   value="${volumeExplorer.summaryId}" />
		<c:param name="volNum"   value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt"   value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion"   value="false" />
	</c:url>

	<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="flashVersion"	value="${command.flashVersion}" />
		<c:param name="showHelp" value="true" />
		<c:param name="showThumbnail" value="true" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do" />
	
	<c:url var="searchCarta" value="/src/mview/SearchCarta.json">
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="nextPage" value="true" />
	</c:url>
	
	<c:url var="currentPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${command.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>

	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${command.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${command.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>
	
	<c:url var="VolumeSummaryDialogURL" value="/src/mview/ShowSummaryVolumeDialog.do">
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
	</c:url> 
	
	<c:url var="indexOfNamesURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${command.summaryId}"/>
		<c:param name="volNum" value="${volumeExplorer.volNum}" />
		<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
		<c:param name="imageType" value="R" />
		<c:param name="imageProgTypeNum" value="1" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>	
	
	<div id="ShowVolumeExplorer">
		<div class="yourSearchDiv">
			<p><fmt:message key="volbase.showExplorerVolume.exploring"/>: <font color="red" style="margin-left:5px; font-size:15px">${volumeExplorer.volNum}${volumeExplorer.volLetExt}</font></p>
		</div>
		
		<div id="baseOn"><fmt:message key="volbase.showExplorerVolume.basedOn"/> <a target="_blank" href="http://iipimage.sourceforge.net"><img src="<c:url value="/images/1024/img_iip.png"/>" width="30" /></a></div>
	
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage previousPage${volumeExplorer.summaryId}"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage nextPage${volumeExplorer.summaryId}"></a>
			</c:if>
			</div>
		</div>

		<div id="flipDiv">
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:0"></iframe>
		</div>	
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage previousPage${volumeExplorer.summaryId}"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage nextPage${volumeExplorer.summaryId}"></a>
			</c:if>
			</div>
		</div>
		<form:form>
			<form:errors path="imageProgTypeNum" id="folio.errors" cssClass="inputerrors"/>
			<form:errors path="insertNum" id="folio.errors" cssClass="inputerrors"/>
		</form:form>
	
		<div>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS,ROLE_FORMER_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_USERS, ROLE_GUESTS">
				<a id="ShowManuscriptViewer${volumeExplorer.summaryId}" 
				   href="${ShowDocumentInManuscriptViewerURL}" 
				   title="<fmt:message key='volbase.showExplorerVolume.help.manuscriptViewer'/>" 
				   class="showFullscreenMode button_large">
				   	<fmt:message key="volbase.showExplorerVolume.showInFullscreen"/>
				</a>
			</security:authorize>
			<a id="volumeSummary${volumeExplorer.summaryId}" class="volumeSummary button_medium" href="#"><fmt:message key="volbase.showExplorerVolume.volumeSummary"/></a>
			<a class="refreshVolumeExplorer refreshVolumeExplorer${volumeExplorer.summaryId} button_small" href="${currentPage}"><fmt:message key="volbase.showExplorerVolume.refresh"/></a>
			<c:if test="${volumeExplorer.totalRubricario > 0}">
				<a id="indexNames${volumeExplorer.summaryId}" class="indexNames indexNames${volumeExplorer.summaryId}" title="<fmt:message key="volbase.showExplorerVolume.help.IndexOfNames"/>" class="transcribe" href="${indexOfNamesURL}" ></a>
			</c:if>
		</div>
		
		<div id="folioMoveTo">
			
			<div id="folioCountForm"> 
				<b><fmt:message key="volbase.showExplorerVolume.totalFolios"/>:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalCarta}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerVolumeURL}" cssClass="editMoveToFolioForm${volumeExplorer.summaryId}">
				<div>
					<div class="moveToFolioTitle">
						Go To page
					</div> 
					
					<c:if test="${hasInsert}">
						<a class="helpIcon" title="Specify the insert number in the first input text and the insert extension in the second one (only if needed)">?</a>
						<label for="insertNum" id="insertNumLabel" class="folioLabel">Insert:</label>
						<input id="insertNum" name="insertNum" class="input_4c" type="text" value="${volumeExplorer.image.insertNum}" />
						<input id="insertLet" name="insertLet" class="input_4c" type="text" value="${volumeExplorer.image.insertLet}" />
					</c:if>
					
					<a class="helpIcon" title="Specify the folio number in the first input text and the folio extension in the second one (only if needed)">?</a>
					<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Folio:</label>
					<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4c" type="text" value="${volumeExplorer.image.imageProgTypeNum}" />
					<input id="missedNumbering" name="missedNumbering" class="input_4c" type="text" value="${volumeExplorer.image.missedNumbering}" />
					<input id="go" class="button_mini" type="submit" value="Go" />
				</div>
				
				<form:hidden path="summaryId" />
				<form:hidden path="volNum" value="${volumeExplorer.volNum}"/>
				<form:hidden path="volLetExt" value="${volumeExplorer.volLetExt}"/>
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="imageOrder" value="${volumeExplorer.image.imageOrder}"/>
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="false" />
			</form:form>
		
		</div>
		
		<script type="text/javascript">
			$j(document).ready(function() {
				$j('.piro_overlay,.piro_html').remove(); // trick to resolve scroll bug with pirobox

				$j().piroBox_ext({
					piro_speed : 700,
					bg_alpha : 0.5,
					piro_scroll : true
				});
				
				var delay = (function(){
					  var timer = 0;
					  return function(callback, ms){
					    clearTimeout (timer);
					    timer = setTimeout(callback, ms);
					  };
				})();
				
				delay(function(){
					$j.ajax({ type:"GET", url:"${searchCarta}", async:false, success:function(data) {
						if(data.imageType == 'C'){
							$j("#titleTab${volumeExplorer.volNum}${volumeExplorer.volLetExt}").html('Explore Volume ${volumeExplorer.volNum}${volumeExplorer.volLetExt}/' + data.imageProgTypeNum);
						}else{
							$j("#titleTab${volumeExplorer.volNum}${volumeExplorer.volLetExt}").html('Explore Volume ${volumeExplorer.volNum}${volumeExplorer.volLetExt}');
						}
				}});},250);

				$j(".previousPage${volumeExplorer.summaryId}").click(function(){
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					
					return false;
				});
				
				$j(".nextPage${volumeExplorer.summaryId}").click(function(){					
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					
					return false;
				});
				
				$j(".refreshVolumeExplorer${volumeExplorer.summaryId}").click(function(){
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});

				$j(".indexNames${volumeExplorer.summaryId}").click(function (){
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});
		        
				$j(".editMoveToFolioForm${volumeExplorer.summaryId}").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
		        	//New Script for jQuery 1.9 tabs
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', formSubmitURL);
					$j('#tabs').tabs('load', active);
					return false;
				});

				var $dialogVolumeSummary = $j('<div id="DialogVolumeSummaryDiv"></div>').dialog({
					resizable: false,
					width: 500,
					height: 600, 
					modal: true,
					autoOpen : false,
					zIndex: 3999,
					open: function(event, ui) { 
	            		$j(this).load('${VolumeSummaryDialogURL}');
	           		},
					overlay: {
						backgroundColor: '#000',
						opacity: 0.5
					},
					title: "VOLUME SUMMARY"
				});
				
				$j("#volumeSummary${volumeExplorer.summaryId}").click(function(){
					$dialogVolumeSummary.dialog('open');
					return false;
				});

				if (navigator.userAgent.indexOf('Chrome') != -1)
					$j("#ShowManuscriptViewer${volumeExplorer.summaryId}").open({windowName: "ShowDocumentInManuscriptViewer", width: window.innerWidth, height: window.innerHeight});
				else
					$j("#ShowManuscriptViewer${volumeExplorer.summaryId}").open({windowName: "ShowDocumentInManuscriptViewer", width: screen.width, height: screen.height});
			});
		</script>