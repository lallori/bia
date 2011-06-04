<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerVolumeModalWindow" value="/src/volbase/ShowExplorerVolume.do">
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
			<c:param name="flashVersion" value="false"/>
			<c:param name="modalWindow" value="true"/>
		</c:url>

	</security:authorize>
	
	<c:url var="manuscriptViewer" value="/src/ShowManuscriptViewer.do">
		<c:param name="imageName"		value="${volumeExplorer.image}" />
		<c:param name="imageProgTypeNum"   value="${volumeExplorer.image.imageProgTypeNum}" /> 
		<c:param name="imageRectoVerso"   value="${volumeExplorer.image.imageRectoVerso}" />
		<c:param name="flashVersion"	value="${command.flashVersion}" />
		<c:param name="showHelp" value="false" />
		<c:param name="showThumbnail" value="false" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do" />
	
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
	
	<div id="ShowVolumeExplorer" class="background">
		<div class="title">
			<h5>VOLUME EXPLORER</h5>
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
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:0"></iframe>
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
		<form:form><form:errors path="imageProgTypeNum" id="folio.errors" cssClass="folioerrors"/></form:form>
	<c:if test="${volumeExplorer.totalRubricario > 0}">
		<br/>
		<br/>
				
		<div id="rubricarioMoveTo">
			<div id="rubricarioCountForm">
				<b>Index of Names folio count:</b> <label for="rubricarioCount" id="rubricarioCount">${volumeExplorer.totalRubricario}</label>
			</div>
		
			<form:form id="moveToRubricarioForm" action="${ShowExplorerVolumeURL}" cssClass="editMoveToRubricarioForm">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="rubricarioLabel">Move to <i>Index of Names</i> folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRucricario" type="text" value="" />
				<input id="goR" type="submit" value="" />
				<form:hidden path="summaryId" />
				<form:hidden path="volNum" value="${volumeExplorer.volNum}"/>
				<form:hidden path="volLetExt" value="${volumeExplorer.volLetExt}"/>
				<form:hidden path="imageType" value="R"/>
				<form:hidden path="imageOrder" />
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="false" />
			</form:form>
		</div>
	</c:if>

		<br/>
		<br/>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${volumeExplorer.totalCarta}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerVolumeURL}" cssClass="editMoveToFolioForm">
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Move to folio</label>
				<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cFolio" type="text" value="" />
				<input id="go" type="submit" value="" />
				<form:hidden path="summaryId" />
				<form:hidden path="volNum" value="${volumeExplorer.volNum}"/>
				<form:hidden path="volLetExt" value="${volumeExplorer.volLetExt}"/>
				<form:hidden path="imageType" value="C"/>
				<form:hidden path="imageOrder" />
				<form:hidden path="total" value="${volumeExplorer.total}" />
				<form:hidden path="totalRubricario" value="${volumeExplorer.totalRubricario}" />
				<form:hidden path="totalCarta" value="${volumeExplorer.totalCarta}" />
				<form:hidden path="totalAppendix" value="${volumeExplorer.totalAppendix}" />
				<form:hidden path="totalOther" value="${volumeExplorer.totalOther}" />
				<form:hidden path="totalGuardia" value="${volumeExplorer.totalGuardia}" />
				<form:hidden path="flashVersion" value="false" />
			</form:form>
		</div>

		<br />
			
		<div>
			<a id="flipItInFullScreen" href="${explorerVolumeModalWindow}" title="VOLUME EXPLORER" class="pirobox" rel="content-full-full"></a>
			<a class="refreshVolumeExplorer" href="${currentPage}"></a>
		</div>

		<div align="center">
			
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

		        $j(".editMoveToRubricarioForm").submit(function (){
		        	var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
		        	// we change selected tab url
		        	$j("#tabs").tabs("url", $j("#tabs").tabs("option", "selected"), formSubmitURL);
		        	// we force tab reload 
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
			});
		</script>
