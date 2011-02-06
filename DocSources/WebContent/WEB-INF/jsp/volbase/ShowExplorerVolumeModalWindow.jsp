<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerVolumeModal" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${requestCommand.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="flashVersion" value="${requestCommand.flashVersion}"/>
			<c:param name="total" value="${page.total}" />
			<c:param name="firstRecord" value="${page.firstRecordNumber}" />
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="ChoiceStartFolioDocument" value="/de/docbase/ChoiceStartFolioDocument.do">
			<c:param name="summaryId" value="${requestCommand.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="flashVersion" value="${requestCommand.flashVersion}"/>
			<c:param name="firstRecord" value="${page.firstRecordNumber}" />
			<c:param name="total" value="${page.total}" />
			<c:param name="imageDocumentToCreate" value="${page.list[0].imageId}" />
			<c:param name="modalWindow" value="true"/>
		</c:url>

		<c:url var="manuscriptViewer" value="/mview/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${page.list[0]}" />
			<c:param name="flashVersion"   value="true" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowExplorerVolume" value="/src/volbase/ShowExplorerVolume.do" />
	
	<c:url var="currentPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber + 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber - 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="modalWindow" value="true"/>
	</c:url>
	
	<div id="modalBox">
		<div id="prevNextButtons">
			<div id="previousPage">
			<c:if test="${page.firstRecordNumber == 1}">
				<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
			</c:if>
			<c:if test="${page.firstRecordNumber > 1}">
				<a id="previousPage" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
			</c:if>
			</div>
			<div id="nextPage">
			<c:if test="${page.firstRecordNumber == page.total }">
				<img src="<c:url value="/images/button_next.png" />" alt="next" />
			</c:if>
			<c:if test="${page.firstRecordNumber < page.total }">
				<a id="nextPage" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
			</c:if>
			</div>
		</div>
		
		<iframe class="iframeFlipVolumeFullCom" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}" style="z-index:100"></iframe>
		
		<div>
			<div id="prevNextButtons">
				<div id="previousPage">
				<c:if test="${page.firstRecordNumber == 1}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${page.firstRecordNumber > 1}">
					<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				</div>
				<div id="nextPage">
				<c:if test="${page.firstRecordNumber == page.total }">
					<img src="<c:url value="/images/button_next.png" />" alt="next" />
				</c:if>
				<c:if test="${page.firstRecordNumber < page.total }">
					<a id="next" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
			</div>
		</div>
		
		<br/>
		<br/>
				
		<div id="rubricarioMoveTo">
			<div id="rubricarioCountForm">
				<b>Rubricario Count:</b> <label for="folioCount" id="folioCount">${page.total}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerVolume}" commandName="requestCommand" method="get" cssClass="edit">
				<label for="firstRecord" id="firstRecordLabel">Move to rubricario</label>
				<input id="firstRecord" name="firstRecord" class="input_4c" type="text" value="" />
				<input id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" value="${requestCommand.volLetExt}" />
				<form:hidden path="total" value="${page.total}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>
	
		<br/>
		<br/>
		
		<div id="folioMoveTo">
			<div id="folioCountForm"> 
				<b>Folio Count:</b> <label for="folioCount" id="folioCount">${page.total}</label>
			</div>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerVolume}" commandName="requestCommand" method="get" cssClass="edit">
				<label for="firstRecord" id="firstRecordLabel">Move to folio</label>
				<input id="firstRecord" name="firstRecord" class="input_4c" type="text" value="" />
				<input id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" value="${requestCommand.volLetExt}" />
				<form:hidden path="total" value="${page.total}" />
				<form:hidden path="flashVersion" value="true" />
			</form:form>
		</div>
			
		<div id="transcribe">
			<a id="transcribeDocument" href="${ChoiceStartFolioDocument}" title="FIND THE DOCUMENT START FOLIO"><img src="/DocSources/images/button_transcribe.png" alt="Transcribe this document" /></a>
		</div>
			
		<div id="CloseButtonRight"><input value="Close" class="modalBox-close" onClick="Modalbox.hide(); return false;" type="submit"><br /><span>(or click the overlay)</span></div>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".simplemodal-close").click( function(e) {$j.modal.close();});
			$j(".previousPage").click(function(){$j("#modalBox").load($j(this).attr("href"));return false;});					
			$j(".nextPage").click(function(){$j("#modalBox").load($j(this).attr("href"));return false;});
			$j("#transcribeDocument").click(function() { 
				Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 750}); 
				return false;
			});
		});
	</script>
	