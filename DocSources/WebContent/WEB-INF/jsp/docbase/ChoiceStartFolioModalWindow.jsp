<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
			<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
			<c:param name="imageName"   value="${volumeExplorer.image}" />
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>

		<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
			<c:param name="volNum" value="${volumeExplorer.volNum}" />
			<c:param name="volLetExt" value="${volumeExplorer.volLetExt}" />
			<c:param name="imageOrder" value="${imageToCreate.imageOrder}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="false" />
			<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
			<c:param name="imageDocumentFolioStart" value="${volumeExplorer.image.imageId}" />
			<c:param name="modalWindow" value="false"/>
		</c:url>

		<c:url var="TranscribeAndContextualizeDocumentURL" value="/de/docbase/TranscribeAndContextualizeDocument.do">
			<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="false" />
			<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
			<c:param name="imageDocumentFolioStart" value="${volumeExplorer.image.imageId}" />
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="ExplorerVolumeModalURL" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="false" />
			<c:param name="modalWindow" value="true"/>
		</c:url>
	</security:authorize>
	
	<c:url var="nextPage" value="/de/docbase/ChoiceStartFolioDocument.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPage" value="/de/docbase/ChoiceStartFolioDocument.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"><fmt:message key="docbase.choiceStartFolio.previousPage"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"><fmt:message key="docbase.choiceStartFolio.previousPage"/></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == imageToCreate.imageOrder }">
				<a id="nextPage"><fmt:message key="docbase.choiceStartFolio.nextPage"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < imageToCreate.imageOrder }">
				<a id="nextPage" href="${nextPage}" class="nextPage"><fmt:message key="docbase.choiceStartFolio.nextPage"/></a>
			</c:if>
			</div>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"><fmt:message key="docbase.choiceStartFolio.previousPage"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previous" href="${previousPage}" class="previousPage"><fmt:message key="docbase.choiceStartFolio.previousPage"/></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == imageToCreate.imageOrder }">
				<a id="nextPage"><fmt:message key="docbase.choiceStartFolio.nextPage"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < imageToCreate.imageOrder }">
				<a id="next" href="${nextPage}" class="nextPage"><fmt:message key="docbase.choiceStartFolio.nextPage"/></a>
			</c:if>
			</div>
		</div>
		<br />	
		<br />
	
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<div id="chooseDiv">
			<a id="choose" class="choose button_large" href="${TranscribeAndContextualizeDocumentURL}"><fmt:message key="docbase.choiceStartFolio.choseThisStartFolio"/></a>
		</div>
		</security:authorize>
		<div id="gobackvolumeDiv">
			<a id="gobackvolume" class="gobackvolume button_large" href="${ExplorerVolumeModalURL}" title="VOLUME EXPLORER"><fmt:message key="docbase.choiceStartFolio.goBackToVolExplorer"/></a>
		</div>	
	

	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".nextPage").click(function(){
				$j("#modalBox").load($j(this).attr("href"));
				return false;
			});
			
			$j(".previousPage").click(function(){
				$j("#modalBox").load($j(this).attr("href"));
				return false;
			});					
						
			$j("#choose").click(function(e) {
				e.preventDefault();
				$j("#body_left").load($j(this).attr("href"));
				// We fire close event for piro box.
				$j(".piro_close").trigger("click");
				return false;
			});

			$j("#gobackvolume").click(function(){
				$j("#modalBox").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
