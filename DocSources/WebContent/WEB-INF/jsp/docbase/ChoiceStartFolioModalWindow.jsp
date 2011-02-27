<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="manuscriptViewer" value="/src/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${volumeExplorer.image}" />
			<c:param name="flashVersion"   value="true" />
		</c:url>

		<c:url var="TranscribeAndContextualizeDocument" value="/de/docbase/TranscribeAndContextualizeDocument.do">
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="total" value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="true" />
			<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
			<c:param name="imageDocumentFolioStart" value="${volumeExplorer.image.imageId}" />
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="ExplorerVolumeModal" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
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
	</security:authorize>
	
	<c:url var="nextPage" value="/de/docbase/ChoiceStartFolioDocument.do">
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
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPage" value="/de/docbase/ChoiceStartFolioDocument.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="imageDocumentToCreate" value="${requestCommand.imageDocumentToCreate}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<div id="modalBox">
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
			<c:if test="${volumeExplorer.image.imageOrder == imageToCreate.imageOrder }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < imageToCreate.imageOrder }">
				<a id="nextPage" href="${nextPage}" class="nextPage"></a>
			</c:if>
			</div>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:100"></iframe>
		
		<div>
			<div id="prevNextButtons">
				<div id="previousPage">
				<c:if test="${volumeExplorer.image.imageOrder == 1}">
					<a id="previousPage"></a>
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder > 1}">
					<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				</div>
				<div id="nextPage">
				<c:if test="${volumeExplorer.image.imageOrder == imageToCreate.imageOrder }">
					<a id="nextPage"></a>
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder < imageToCreate.imageOrder }">
					<a id="next" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
			</div>
		</div>
		
		<br />	
		<br />
			
		<div id="chooseGoBack">
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="transcribeAndContextualizeDocument" href="${TranscribeAndContextualizeDocument}"><img src="/DocSources/images/button_choose.png" alt="next" /></a>
		</security:authorize>

			<a href="${ExplorerVolumeModal}" title="VOLUME EXPLORER" onClick="Modalbox.show(this.href, {onUpdate: function() { alert('Are you sure you want to go back?') } });return false;"><img src="/DocSources/images/button_goBack.png" alt="Go back to Volume Explorer" id="buttonGoBack"/></a>
		</div>
			
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
						
			$j("#transcribeAndContextualizeDocument").click(function(e) {
				e.preventDefault();
				$j("#body_left").load($j(this).attr("href"));
				Modalbox.hide(); 
				return false;
			});
		});
	</script>
