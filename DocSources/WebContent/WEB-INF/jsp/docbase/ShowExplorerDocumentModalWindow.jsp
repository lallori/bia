<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="ChoiceStartFolioDocument" value="/de/docbase/ChoiceStartFolioDocument.do">
			<c:param name="entryId" 				value="${documentExplorer.entryId}"/>
			<c:param name="volNum" 					value="${documentExplorer.volNum}" />
			<c:param name="volLetExt" 				value="${documentExplorer.volLetExt}" />
			<c:param name="imageOrder" 				value="${documentExplorer.image.imageOrder}" />
			<c:param name="imageDocumentToCreate" 	value="${documentExplorer.image.imageId}" />
			<c:param name="total" 					value="${documentExplorer.total}" />
			<c:param name="totalRubricario" 		value="${documentExplorer.totalRubricario}" />
			<c:param name="totalCarta" 				value="${documentExplorer.totalCarta}" />
			<c:param name="totalAppendix" 			value="${documentExplorer.totalAppendix}" />
			<c:param name="totalOther" 				value="${documentExplorer.totalOther}" />
			<c:param name="totalGuardia" 			value="${documentExplorer.totalGuardia}" />
			<c:param name="flashVersion" 			value="false"/>
			<c:param name="modalWindow"  			value="true"/>
		</c:url>
		
		<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
			<c:param name="entryId" value="${documentExplorer.entryId}"/>
			<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
			<c:param name="imageRectoVerso"   value="${documentExplorer.image.imageRectoVerso}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerDocumentURL" value="/src/docbase/ShowExplorerDocument.do" />
	
	<c:url var="nextPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}"/>
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
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId" value="${documentExplorer.entryId}"/>
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
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<div id="modalBox">
		<h6>VOLUME EXPLORER</h6>
		<div id="prevNextButtons">
		<c:if test="${documentExplorer.total > 0}">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage">Previous folio</a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPageURL}" class="previousPage">Previous folio</a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage">Next folio</a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPageURL}" class="nextPage">Next folio</a>
			</c:if>
			</div>
		</c:if>
		<c:if test="${documentExplorer.image.imageType == 'C'}"> 
			<div id="transcribe">
				<a  href="${ChoiceStartFolioDocument}" class="transcribe" title="Transcribe this document">Transcribe this document</a>
			</div>
		</c:if>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage">Previous folio</a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPageURL}" class="previousPage">Previous folio</a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage">Next folio</a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPageURL}" class="nextPage">Next folio</a>
			</c:if>
			</div>
			<c:if test="${documentExplorer.image.imageType == 'C'}"> 
				<div id="transcribe">
					<a  href="${ChoiceStartFolioDocument}" class="transcribe" title="Transcribe this document">Transcribe this document</a>
				</div>
			</c:if>
		<br />
		</div>
		

		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="folioerrors"/></form:form>
		<br />
	<c:if test="${documentExplorer.totalRubricario > 0}">
						
		<div id="rubricarioModalMoveTo">
			<div id="rubricarioModalCountForm">
				<b>Index of Names Count:</b> <label for="rubricarioCount" id="rubricarioCount">${documentExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioModalForm" action="${ShowExplorerDocumentURL}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="rubricarioLabelModal">Move to <i>Index of Names</i> folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRubricarioModal" type="text" value="" />
				<input id="goRModal" type="submit" value="Go" />
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="R"/>
				<form:hidden path="imageOrder" value="${documentExplorer.image.imageOrder}"/>
				<form:hidden path="entryId" value="${documentExplorer.entryId}" />
				<form:hidden path="total" value="${documentExplorer.total}" />
				<form:hidden path="totalRubricario" value="${documentExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${documentExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${documentExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${documentExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${documentExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="false" />
				<form:hidden path="modalWindow" value="true"/>
			</form:form>
		</div>
	</c:if>
	
		<br/>
		
		
		<div id="folioModalMoveTo">

			<div id="folioModalCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${documentExplorer.totalCarta}</label>
			</div>

			<form:form id="moveToFolioModalForm" action="${ShowExplorerDocumentURL}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabelModal">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolioModal" type="text" value="" />
				<input class="openmodalbox" id="goModal" type="submit" value="Go"/>
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
				<form:hidden path="modalWindow" value="true"/>
			</form:form>
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
