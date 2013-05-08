<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="explorerDocumentModalWindowURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false"/>
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="entryId"   value="${documentExplorer.entryId}" />
		<c:param name="volNum"   value="${documentExplorer.volNum}" />
		<c:param name="volLetExt"   value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion"   value="false" />
	</c:url>
	
	<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
		<c:param name="entryId" value="${documentExplorer.entryId}"/>
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="flashVersion"   value="false" />
		<c:param name="showHelp" value="true" />
		<c:param name="showThumbnail" value="true" />
	</c:url>
	
	<c:url var="ShowExplorerDocumentURL" value="/src/docbase/ShowExplorerDocument.do" />
	
	<c:url var="currentPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>

	<c:url var="nextPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>
	
	<c:url var="searchCarta" value="/src/mview/SearchCarta.json">
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="nextPage" value="true" />
	</c:url>

	<c:url var="previousPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>
	
	<c:url var="VolumeSummaryDialogURL" value="/src/mview/ShowSummaryVolumeDialog.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
	</c:url> 
	
	<c:url var="indexOfNamesURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${command.summaryId}"/>
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageType" value="R" />
		<c:param name="imageProgTypeNum" value="1" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>	
	
	<div id="ShowDocumentExplorer">
		<div class="yourSearchDiv">
			<p><fmt:message key="docbase.showExplorerDocument.exploringVolume"/>: <font color="red" style="margin-left:5px; font-size:15px">${documentExplorer.volNum}${documentExplorer.volLetExt}</font></p>
		</div>
		
		<div id="baseOn"><fmt:message key="docbase.showExplorerDocument.basedOn"/> <a target="_blank" href="http://iipimage.sourceforge.net"><img src="<c:url value="/images/1024/img_iip.png"/>" width="30" /></a></div>
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPageURL}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPageURL}" class="nextPage"></a>
			</c:if>
			</div>
		</div>

		<div id="flipDiv">
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:0"></iframe>
		</div>	
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPageURL}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPageURL}" class="nextPage"></a>
			</c:if>
			</div>
		</div>
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="inputerrors"/></form:form>
		
		


		<div>
			<%-- 
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="flipItInFullScreen" href="${explorerDocumentModalWindowURL}" title="DOCUMENT EXPLORER" class="pirobox" rel="content-full-full">Fullscreen Mode</a>
			</security:authorize>
			--%>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS,ROLE_FORMER_FELLOWS, ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_USERS, ROLE_GUESTS">
				<a id="ShowManuscriptViewer${documentExplorer.entryId}" href="${ShowDocumentInManuscriptViewerURL}" title="Manuscript Viewer" class="showFullscreenMode button_large"><fmt:message key="docbase.showExplorerDocument.showInFullscreen"/></a>
			</security:authorize>
			<a id="volumeSummary${documentExplorer.entryId}" class="volumeSummary button_medium" href="#"><fmt:message key="docbase.showExplorerDocument.volumeSummary"/></a>
			<a class="refreshVolumeExplorer button_small" href="${currentPageURL}"><fmt:message key="docbase.showExplorerDocument.refresh"/></a>
			<c:if test="${documentExplorer.totalRubricario > 0}">
				<a id="indexNames${documentExplorer.entryId}" class="indexNames" title="Index of Names" class="transcribe" href="${indexOfNamesURL}" ></a>
			</c:if>
		</div>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b><fmt:message key="docbase.showExplorerDocument.totalFolios"/>:</b> <label for="folioCount" id="folioCount">${documentExplorer.totalCarta}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerDocumentURL}" cssClass="editMoveToFolioForm">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel"><fmt:message key="docbase.showExplorerDocument.goToFolio"/>:</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolio" type="text" value="" />
				<input id="go" class="button_mini" type="submit" value="Go" />
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="imageOrder" value="${documentExplorer.image.imageOrder}"/>
				<form:hidden path="entryId" value="${documentExplorer.entryId}" />
				<form:hidden path="total" value="${documentExplorer.total}" />
				<form:hidden path="totalRubricario" value="${documentExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${documentExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${documentExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${documentExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${documentExplorer.totalGuardia}" />
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
							$j("#titleTab${documentExplorer.volNum}${documentExplorer.volLetExt}").html('Volume ${documentExplorer.volNum} - Folio ' + data.imageProgTypeNum);
						}else{
							$j("#titleTab${documentExplorer.volNum}${documentExplorer.volLetExt}").html('Volume ${documentExplorer.volNum}');
						}
				}});},250);
				
								
				$j(".previousPage").click(function(){
					// we change selected tab url, 
// 					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
// 					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});
				
				$j(".nextPage").click(function(){
// 					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
// 					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));

					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});
				
				$j(".refreshVolumeExplorer").click(function(){
// 					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
// 					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));

					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});
				
				$j(".indexNames").click(function (){
// 					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
// 					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					
					//New Script for jQuery 1.9 tabs
					var href = $j(this).attr("href");
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', href);
					$j('#tabs').tabs('load', active);
					return false;
				});
		        
		        $j(".editMoveToFolioForm").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
// 		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), formSubmitURL);
// 					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));

					//New Script for jQuery 1.9 tabs
					var active = $j("#tabs").tabs("option", "active");
					$j('#tabs ul li').eq(active).data('loaded', false).find('a').attr('href', formSubmitURL);
					$j('#tabs').tabs('load', active);
					return false;
				});

		        var $dialogVolumeSummary = $j('<div id="DialogVolumeSummaryDiv"></div>').dialog({
					resizable: false,
					width: 550,
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
					}
				});
				
				$j("#volumeSummary${documentExplorer.entryId}").click(function(){
					$dialogVolumeSummary.dialog('open');
					return false;
				});

				if (navigator.userAgent.indexOf('Chrome') != -1)
					$j("#ShowManuscriptViewer${documentExplorer.entryId}").open({windowName: "ShowDocumentInManuscriptViewer", width: window.innerWidth, height: window.innerHeight});
				else
					$j("#ShowManuscriptViewer${documentExplorer.entryId}").open({windowName: "ShowDocumentInManuscriptViewer", width: screen.width, height: screen.height});
			});
		</script>
		