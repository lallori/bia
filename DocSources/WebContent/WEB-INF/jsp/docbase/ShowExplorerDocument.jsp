<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerDocumentModalWindow" value="/src/docbase/ShowExplorerDocument.do">
			<c:param name="summaryId" value="${command.summaryId}"/>
			<c:param name="volNum" value="${command.volNum}" />
			<c:param name="volLetExt" value="${command.volLetExt}" />
			<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
			<c:param name="total" value="${documentExplorer.total}" />
			<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
			<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
			<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
			<c:param name="totalOther" value="${documentExplorer.totalOther}" />
			<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
			<c:param name="flashVersion" value="${command.flashVersion}"/>
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="manuscriptViewer" value="/src/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${documentExplorer.image}" />
			<c:param name="flashVersion"   value="${command.flashVersion}" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerDocument" value="/src/docbase/ShowExplorerDocument.do" />
	
	<c:url var="currentPage" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
	</c:url>

	<c:url var="nextPage" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
	</c:url>

	<c:url var="previousPage" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="true" />
	</c:url>
	
		<h5>DOCUMENT EXPLORER</h5>
		<hr id="lineSeparator"/>
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"></a>
			</c:if>
			</div>
		</div>

		<div id="flipDiv">
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:0"></iframe>
		</div>	
		
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${documentExplorer.image.imageOrder == 1}">
				<a id="previousPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${documentExplorer.image.imageOrder == documentExplorer.total }">
				<a id="nextPage"></a>
			</c:if>
			<c:if test="${documentExplorer.image.imageOrder < documentExplorer.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"></a>
			</c:if>
			</div>
		</div>
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="inputerrors"/></form:form>
	<c:if test="${documentExplorer.totalRubricario > 0}">
		<br/>
		<br/>
				
		<div id="rubricarioMoveTo">
			<div id="rubricarioCountForm">
				<b>Rubricario Count:</b> <label for="rubricarioCount" id="rubricarioCount">${documentExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioForm" action="${ShowExplorerDocument}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="rubricarioLabel">Move to rubricario</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRucricario" type="text" value="" />
				<input id="goR" type="image" alt="Go" src="<c:url value="/images/transparent_account.png" />"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="R"/>
				<form:hidden path="imageOrder" />
				<form:hidden path="total" value="${documentExplorer.total}" />
				<form:hidden path="totalRubricario" value="${documentExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${documentExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${documentExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${documentExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${documentExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>
	</c:if>

		<br/>
		<br/>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${documentExplorer.totalCarta}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerDocument}" cssClass="edit">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolio" type="text" value="" />
				<input id="go" type="image" alt="Go" src="<c:url value="/images/transparent_account.png" />"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" />
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="imageOrder" />
				<form:hidden path="total" value="${documentExplorer.total}" />
				<form:hidden path="totalRubricario" value="${documentExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${documentExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${documentExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${documentExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${documentExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>

		<br />
			
		<div>
			<a id="flipItInFullScreen" href="${explorerDocumentModalWindow}" title="DOCUMENT EXPLORER"></a>
			<a id="refreshVolumeExplorer" href="${currentPage}"></a>
		</div>

		<div align="center">
			
		</div>
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#flipItInFullScreen").click(function(){
					Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 750}); 
					return false;
				});

				$j(".previousPage").click(function(){$j("#body_right").load($j(this).attr("href"));return false;});					
				$j(".nextPage").click(function(){$j("#body_right").load($j(this).attr("href"));return false;});
				$j("#refreshVolumeExplorer").click(function(){$j("#body_right").load($j(this).attr("href"));return false;});
				
		        $j("#moveToRubricarioForm").submit(function (){
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#body_right").html(html);
					}});
					return false;
				});
		        $j("#moveToFolioForm").submit(function (){
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
						$j("#body_right").html(html);
					}});
					return false;
				});
			});
		</script>
		