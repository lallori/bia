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
			<p>Exploring Volume: <font color="red" style="margin-left:5px; font-size:15px">${volumeExplorer.volNum}${volumeExplorer.volLetExt}</font></p>
		</div>
	
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"></a>
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
				<a id="previousPage" href="${previousPage}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"></a>
			</c:if>
			</div>
		</div>
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="inputerrors"/></form:form>
	
		<div>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS,ROLE_FORMER_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_USERS, ROLE_GUESTS">
				<a id="ShowManuscriptViewer${volumeExplorer.summaryId}" href="${ShowDocumentInManuscriptViewerURL}" title="Manuscript Viewer" class="showFullscreenMode">Show in Fullscreen mode</a>
			</security:authorize>
			<a id="volumeSummary${volumeExplorer.summaryId}" class="volumeSummary" href="#">Volume Summary</a>
			<a class="refreshVolumeExplorer" href="${currentPage}">Refresh</a>
			<c:if test="${volumeExplorer.totalRubricario > 0}">
				<a id="indexNames${volumeExplorer.summaryId}" class="indexNames" title="Index of Names" class="transcribe" href="${indexOfNamesURL}" ></a>
			</c:if>
		</div>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b>Total Folios in this Volume:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalCarta}</label>
			</div>
		
		<form:form id="moveToFolioForm" action="${ShowExplorerVolumeURL}" cssClass="editMoveToFolioForm">
			<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Folio:</label>
			<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolio" type="text" value="" />
			<input id="go" type="submit" value="Go" />
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
							$j("#titleTab${volumeExplorer.volNum}${volumeExplorer.volLetExt}").html('Explore Volume ${volumeExplorer.volNum}/' + data.imageProgTypeNum);
						}else{
							$j("#titleTab${volumeExplorer.volNum}${volumeExplorer.volLetExt}").html('Explore Volume ${volumeExplorer.volNum}');
						}
				}});},250);

				$j(".previousPage").click(function(){
					// we change selected tab url, 
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
				
				$j(".nextPage").click(function(){
					// we change selected tab url 
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
				
				$j(".refreshVolumeExplorer").click(function(){
					// we change selected tab url
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});

				$j(".indexNames").click(function (){
		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href")); 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
		        
				$j(".editMoveToFolioForm").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
		        	// we change selected tab url
		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), formSubmitURL);
		        	// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
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

				$j("#indexNames").click(function(){
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});

				$j("#ShowManuscriptViewer${volumeExplorer.summaryId}").open({width: screen.width, height: screen.height, scrollbars: false});
			});
		</script>