<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowExplorerVolume" value="/src/volbase/ShowExplorerVolume.do" />
	
	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber + 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="fancyBox" value="true" />
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber - 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="fancyBox" value="true" />
	</c:url>

	<link rel="stylesheet" type="text/css" href="/DocSources/styles/style_editform.css" />

	<div id="contentFancyCom">
		<h5>VOLUME EXPLORER</h5>
		<hr>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerVolume" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${requestCommand.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="flashVersion" value="${requestCommand.flashVersion}"/>
		</c:url>

		<c:url var="manuscriptViewer" value="/mview/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${page.list[0]}" />
			<c:param name="flashVersion"   value="true" />
		</c:url>
	</security:authorize>
	
		<div id="prevNextButtons">
			<div id="prevButton">
			<c:if test="${page.firstRecordNumber == 1 }">
				<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
			</c:if>
			<c:if test="${page.firstRecordNumber > 1 }">
				<a id="previousPage" class="previousPage" href="${previousPage}"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
			</c:if>
			</div>
			<div id="nextButton">
			<c:if test="${page.firstRecordNumber == page.total }">
				<img src="<c:url value="/images/button_next.png" />" alt="next" />
			</c:if>
			<c:if test="${page.firstRecordNumber < page.total }">
				<a id="nextPage" class="nextPage" href="${nextPage}"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
			</c:if>
			</div>
		</div>

		<div id="flipFullScreenFancyFelCom">
			<iframe scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}"></iframe>
		</div>	

		<div>
			<div id="prevNextButtons">
				<div id="prevButton">
				<c:if test="${page.firstRecordNumber == 1}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${page.firstRecordNumber > 1}">
					<a id="previousPage" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				</div>
				<div id="nextButton">
				<c:if test="${page.firstRecordNumber == page.total }">
					<img src="<c:url value="/images/button_next.png" />" alt="next" />
				</c:if>
				<c:if test="${page.firstRecordNumber < page.total }">
					<a id="nextPage" class="nextPage" href="${nextPage}"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
			</div>
		</div>

		<br/>

		<div id="folioMoveFancyCom">
			<form id="folioCountForm" action="/DocSources/de/volbase/folioCount.do" method="post" class="edit">
				<b>Digitized images in this volume:</b>
					<label for="folioCount" id="folioCount">${page.total}</label>
			</form>
		
			<form:form id="moveToFolioForm" action="${ShowExplorerVolume}" commandName="requestCommand" method="get" cssClass="edit">
				<label for="firstRecord" id="firstRecordLabel">Move to folio</label>
				<input id="firstRecord" name="firstRecord" class="input_4c" type="text" value="" />
				<input id="go" type="image" src="<c:url value="/images/button_go.png" />" alt="Go"/>
				<form:hidden path="volNum" />
				<form:hidden path="volLetExt" value="${requestCommand.volLetExt}" />
				<form:hidden path="total" value="${page.total}" />
				<form:hidden path="flashVersion" value="true" />
				<form:hidden path="fancyBox" value="true"/>
			</form:form>
		</div>

		<div id="transcribeRefresh">
			<a id="transcribeButton" href="#"><img src="<c:url value="/images/button_transcribe.png" />" alt="Transcribe and contextualize it"/></a>
			
			<a id="refreshVolumeExplorer" href="<c:url value="${currentPage}" />"><img src="<c:url value="/images/button_refresh.png" />" alt="Refresh" /></a>
		</div>	
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".previousPage").click(function(){$("#contentFancyCom").load($(this).attr("href"));return false;});					
			$(".nextPage").click(function(){$("#contentFancyCom").load($(this).attr("href"));return false;});
			$("#refreshVolumeExplorer").click(function(){$("#contentFancyCom").load($(this).attr("href"));return false;});
		});
	</script>
