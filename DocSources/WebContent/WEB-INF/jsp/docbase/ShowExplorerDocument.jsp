<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerDocumentModalWindowURL" value="/src/docbase/ShowExplorerDocument.do">
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
			<c:param name="flashVersion" value="false"/>
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${documentExplorer.image}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerDocumentURL" value="/src/docbase/ShowExplorerDocument.do" />
	
	<c:url var="currentPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
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
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder + 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>

	<c:url var="previousPageURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder - 1}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>
	
	<div id="ShowDocumentExplorer" class="background">
		<div class="title">
		<h5>VOLUME EXPLORER</h5>
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
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="folioerrors"/></form:form>
	<c:if test="${documentExplorer.totalRubricario > 0}">
		<br/>
		<br/>
				
		<div id="rubricarioMoveTo">
			<div id="rubricarioCountForm">
				<b>Index of Names Count:</b> <label for="rubricarioCount" id="rubricarioCount">${documentExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioForm" action="${ShowExplorerDocumentURL}" cssClass="editMoveToRubricarioForm">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="rubricarioLabel">Move to <i>Index of Names</i> folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRucricario" type="text" value="" />
				<input id="goR" type="submit" value="" />
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
				<form:hidden path="flashVersion" value="false" />
			</form:form>
		</div>
	</c:if>

		<br/>
		<br/>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${documentExplorer.totalCarta}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerDocumentURL}" cssClass="editMoveToFolioForm">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolio" type="text" value="" />
				<input id="go" type="submit" value="" />
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
				<form:hidden path="flashVersion" value="false" />
			</form:form>
		</div>

		<br />
			
		<div>
			<a id="flipItInFullScreen" href="${explorerDocumentModalWindowURL}" title="DOCUMENT EXPLORER" class="pirobox" rel="content-full-full"></a>
			<a class="refreshVolumeExplorer" href="${currentPageURL}"></a>
		</div>
	</div>
		<script type="text/javascript">
			$j(document).ready(function() {
				$j('.piro_overlay,.piro_html').remove(); // trick to resolve scroll bug with pirobox

				$j().piroBox_ext({
					piro_speed : 700,
					bg_alpha : 0.5,
					piro_scroll : true
				});

				$j(".previousPage").click(function(){
					// we change selected tab url, 
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					// we force tab reload 
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
				
				$j(".nextPage").click(function(){
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
				
				$j(".refreshVolumeExplorer").click(function(){
					$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), $j(this).attr("href"));
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
				
		        $j(".editMoveToRubricarioForm").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), formSubmitURL);
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
		        
		        $j(".editMoveToFolioForm").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), formSubmitURL);
					$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
					return false;
				});
			});
		</script>
		