<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="ChoiceStartFolioDocument" value="/de/docbase/ChoiceStartFolioDocument.do">
			<c:param name="summaryId" 				value="${volumeExplorer.summaryId}"/>
			<c:param name="volNum" 					value="${volumeExplorer.volNum}" />
			<c:param name="volLetExt" 				value="${volumeExplorer.volLetExt}" />
			<c:param name="imageOrder" 				value="${volumeExplorer.image.imageOrder}" />
			<c:param name="imageDocumentToCreate" 	value="${volumeExplorer.image.imageId}" />
			<c:param name="total" 					value="${volumeExplorer.total}" />
			<c:param name="totalRubricario" 		value="${volumeExplorer.totalRubricario}" />
			<c:param name="totalCarta" 				value="${volumeExplorer.totalCarta}" />
			<c:param name="totalAppendix" 			value="${volumeExplorer.totalAppendix}" />
			<c:param name="totalOther" 				value="${volumeExplorer.totalOther}" />
			<c:param name="totalGuardia" 			value="${volumeExplorer.totalGuardia}" />
			<c:param name="flashVersion" 			value="false"/>
			<c:param name="modalWindow"  			value="true"/>
		</c:url>

		<c:url var="manuscriptViewer" value="/src/ShowManuscriptViewer.do">
			<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
			<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder}" />
			<c:param name="flashVersion" value="${command.flashVersion}" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do" />	

	<c:url var="currentPage" value="/src/volbase/ShowExplorerVolume.do">
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
		<c:param name="flashVersion" value="false" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
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
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="summaryId" value="${volumeExplorer.summaryId}"/>
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${volumeExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${volumeExplorer.total}" />
		<c:param name="totalRubricario" value="${volumeExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${volumeExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${volumeExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${volumeExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${volumeExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<div id="modalBox">
		<h6><fmt:message key="volbase.showExplorerVolumeModalWindow.title.volumeExplorer"/></h6>
		<div id="prevNextButtons">
		<c:if test="${volumeExplorer.total > 0}">
			<div id="previousPage">
			<c:if test="${volumeExplorer.image.imageOrder == 1}">
				<a id="previousPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.previousFolio"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.previousFolio"/></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
				<a id="nextPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.nextFolio"/></a>
			</c:if>
			<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.nextFolio"/></a>
			</c:if>
			</div>
		</c:if>
		<c:if test="${volumeExplorer.image.imageType == 'C'}"> 
			<div id="transcribe">
				<a  href="${ChoiceStartFolioDocument}" class="transcribe button_large" title="<fmt:message key="volbase.showExplorerVolumeModalWindow.help.transcribe"/>"><fmt:message key="volbase.showExplorerVolumeModalWindow.transcribe"/></a>
			</div>
		</c:if>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:100"></iframe>

		<div>
			<div id="prevNextButtons">
				<div id="previousPage">
				<c:if test="${volumeExplorer.image.imageOrder == 1}">
					<a id="previousPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.previousFolio"/></a>
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder > 1}">
					<a id="previousPage" href="${previousPage}" class="previousPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.previousFolio"/></a>
				</c:if>
				</div>
				<div id="nextPage">
				<c:if test="${volumeExplorer.image.imageOrder == volumeExplorer.total }">
					<a id="nextPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.nextFolio"/></a>
				</c:if>
				<c:if test="${volumeExplorer.image.imageOrder < volumeExplorer.total }">
					<a id="nextPage" href="${nextPage}" class="nextPage"><fmt:message key="volbase.showExplorerVolumeModalWindow.nextFolio"/></a>
				</c:if>
				</div>
				<c:if test="${volumeExplorer.image.imageType == 'C'}"> 
					<div id="transcribe">
						<a href="${ChoiceStartFolioDocument}" class="transcribe" title="<fmt:message key="volbase.showExplorerVolumeModalWindow.help.transcribe"/>"><fmt:message key="volbase.showExplorerVolumeModalWindow.transcribe"/></a>
					</div>
				</c:if>
				<br />
			</div>
		</div>

		<div>
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="folioerrors"/></form:form>
		</div>
		<br />
		
	<div id="volumeExplorerButtons">
	<c:if test="${volumeExplorer.totalRubricario > 0}">
					
		<div id="rubricarioModalMoveTo">
			<div id="rubricarioModalCountForm">
				<b><fmt:message key="volbase.showExplorerVolumeModalWindow.indexOfNames"/>:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioModalForm" action="${ShowExplorerVolumeURL}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="rubricarioLabelModal">Move to <i>Index of Names</i> folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRubricarioModal" type="text" value="" />
				<input id="goRModal" class="button_mini" type="submit" value="Go" />
				<form:hidden path="summaryId" />
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="R"/>
				<form:hidden path="imageOrder" value="${volumeExplorer.image.imageOrder}"/>
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion"  value="${command.flashVersion}" />
				<form:hidden path="modalWindow" value="true"/>
			</form:form>
		</div>
	</c:if>
	
		<br/>
		
		
		<div id="folioModalMoveTo">

			<div id="folioModalCountForm"> 
				<b><fmt:message key="volbase.showExplorerVolumeModalWindow.folioCount"/>:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalCarta}</label>
			</div>

			<form:form id="moveToFolioModalForm" action="${ShowExplorerVolumeURL}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabelModal">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolioModal" type="text" value="" />
				<input class="openmodalbox" id="goModal" class="button_mini" type="submit" value="Go"/>
				<form:hidden path="summaryId" />
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="imageOrder" value="${volumeExplorer.image.imageOrder}"/>
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion"  value="${command.flashVersion}" />
				<form:hidden path="modalWindow" value="true"/>
			</form:form>
		</div>
		
		<br />
		
		
	
		</div>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#moveToRubricarioModalForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
                	$j("#modalBox").html(html);
				}});
				return false;
			});

	       $j("#moveToFolioModalForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#modalBox").html(html);
				}});
				return false;
			});

	        $j(".previousPage").click(function(){
				$j("#modalBox").load($j(this).attr("href"));
				return false;
			});					
			
			$j(".nextPage").click(function(){
				$j("#modalBox").load($j(this).attr("href"));
				return false;
			});
			
			$j(".transcribe").click(function() { 
				$j("#modalBox").load($j(this).attr("href")); 
				return false;
			});
		});
	</script>
