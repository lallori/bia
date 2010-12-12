<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="next" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber + 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="fancyBox" value="true" />
	</c:url>

	<c:url var="previous" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber - 1}" />
		<c:param name="flashVersion" value="true" />
		<c:param name="fancyBox" value="true" />
	</c:url>

	<div id="contentFancyCom">
		<h5>VOLUME EXPLORER</h5>
		
		<div id="transcribeButton">
			<a href="#"><img src="/DocSources/images/button_transcribe.png" alt="Transcribe and contextualize it"/></a>
		</div>

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
			<c:if test="${page.firstRecordNumber == 0}">
				<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
			</c:if>
			<c:if test="${page.firstRecordNumber != 0}">
				<a id="previous" href="${previous}"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
			</c:if>
			</div>
			<div id="nextButton">
			<c:if test="${page.firstRecordNumber == (page.total-1) }">
				<img src="<c:url value="/images/button_next.png" />" alt="next" />
			</c:if>
			<c:if test="${page.firstRecordNumber != (page.total-1)}">
				<a id="next" href="${next}"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
			</c:if>
			</div>
			<script type="text/javascript">
				$(document).ready(function() {
					$("#previous").click(function(){$("#contentFancyCom").load($(this).attr("href"));return false;});					
					$("#next").click(function(){$("#contentFancyCom").load($(this).attr("href"));return false;});
				});
			</script>
		</div>

		<div id="refresh">
			<a href="#">Refresh</a>
		</div>	

		<div id="flipFullScreenFancyFelCom">
			<iframe scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}"></iframe>
		</div>	
		
	</div>