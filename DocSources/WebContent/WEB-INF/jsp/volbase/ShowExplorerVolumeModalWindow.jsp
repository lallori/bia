<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerVolumeModalWindow" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${command.summaryId}"/>
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="${command.flashVersion}"/>
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="ChoiceStartFolioDocument" value="/de/docbase/ChoiceStartFolioDocument.do">
			<c:param name="summaryId" value="${command.summaryId}"/>
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="flashVersion" value="${command.flashVersion}"/>
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="imageDocumentToCreate" value="${volumeExplorer.image.imageId}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="${command.flashVersion}"/>
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="manuscriptViewer" value="/mview/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${volumeExplorer.image}" />
			<c:param name="flashVersion"   value="true" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerVolume" value="/src/volbase/ShowExplorerVolume.do" />
	
	<c:url var="currentPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<div id="modalBox">
		<div id="prevNextButtons">
		<c:if test="${volumeExplorer.total > 0}">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<img src="<c:url value="/images/button_next.png" />" alt="next" />
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
			</c:if>
			</div>
		</c:if>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:100"></iframe>
		
		<div>
			<div id="prevNextButtons">
				<div id="previousPage">
				<c:if test="${volumeExplorer.image.imageOrder == 1}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder > 1}">
					<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				</div>
				<div id="nextPage">
				<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
					<img src="<c:url value="/images/button_next.png" />" alt="next" />
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
					<a id="next" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
			</div>
		</div>
		
	<c:if test="${volumeExplorer.totalRubricario > 0}">
		<br/>
		<br/>
				
		<div id="rubricarioMoveTo">
			<div id="rubricarioCountForm">
				<b>Rubricario Count:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioForm" action="${ShowExplorerVolume}" commandName="command" method="get" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel">Move to rubricario</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4c" type="text" value="" />
				<input id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" value="${command.volLetExt}" />
				<form:hidden path="imageType" value="R"/>
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>
	</c:if>
	
		<br/>
		<br/>
		
		<div id="folioMoveTo">

			<div id="folioCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalCarta}</label>
			</div>

			<form:form id="moveToFolioForm" action="${ShowExplorerVolume}" commandName="command" method="get" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4c" type="text" value="" />
				<input class="openmodalbox" id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" value="${command.volLetExt}" />
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>
			
		<div id="transcribe">
			<a id="transcribeDocument" href="${ChoiceStartFolioDocument}" title="FIND THE DOCUMENT START FOLIO"><img src="/DocSources/images/button_transcribe.png" alt="Transcribe this document" /></a>
		</div>
		<form:errors path="imageProgTypeNum" />
		<div id="CloseButtonRight"><input value="Close" class="modalBox-close" onClick="Modalbox.hide(); return false;" type="submit"><br /><span>(or click the overlay)</span></div>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".simplemodal-close").click(function(){$j.modal.close(); return false;});
			$j(".previousPage").click(function(){$j("#modalBox").load($j(this).attr("href"));return false;});					
			$j(".nextPage").click(function(){$j("#modalBox").load($j(this).attr("href"));return false;});
			$j("#transcribeDocument").click(function() { 
				Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 750}); 
				return false;
			});
	        $j("#moveToRubricarioForm").submit(function (){
				$j.ajax({ type:"GET", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#modalBox").html(html);
				}});
				return false;
			});
	        $j("#moveToFolioForm").submit(function (){
				$j.ajax({ type:"GET", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					$j("#modalBox").html(html);
				}});
				return false;
			});
		});
	</script>
	