<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="explorerVolumeFancyBox" value="/src/volbase/ShowExplorerVolume.do">
			<c:param name="summaryId" value="${requestCommand.summaryId}"/>
			<c:param name="volNum" value="${requestCommand.volNum}" />
			<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
			<c:param name="flashVersion" value="${requestCommand.flashVersion}"/>
			<c:param name="total" value="${page.total}" />
			<c:param name="firstRecord" value="${page.firstRecordNumber}" />
			<c:param name="fancyBox" value="true"/>
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
	</c:url>

	<c:url var="nextPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber + 1}" />
		<c:param name="flashVersion" value="true" />
	</c:url>

	<c:url var="previousPage" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${requestCommand.volNum}" />
		<c:param name="volLetExt" value="${requestCommand.volLetExt}" />
		<c:param name="total" value="${page.total}" />
		<c:param name="firstRecord" value="${page.firstRecordNumber - 1}" />
		<c:param name="flashVersion" value="true" />
	</c:url>
	
		<h5>VOLUME EXPLORER</h5>
		<hr />
		
		<div  id="prevNextButtons">
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
					<a id="nextPage" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
		</div>


		<div id="flipDiv">
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}"></iframe>
		</div>	
		
		<div>
			<div id="prevNextButtons">
				<div id="prevButton">
				<c:if test="${page.firstRecordNumber == 1}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${page.firstRecordNumber > 1}">
					<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				</div>
				<div id="nextButton">
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
				
		<div>
			<b>Digitized images in this volume:</b> <label for="folioCount" id="folioCount">${page.total}</label>
		
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

		<br />
			
		<div>
		<c:if test="${page.total > 0}">
			<a id="flipItInFullScreen" href="${explorerVolumeFancyBox}" style="z-index:5"><img src="<c:url value="/images/fullscreenMode.png" />" alt="Fullscreen Mode" /></a>
		</c:if>

			<a id="refreshVolumeExplorer" href="${currentPage}"><img src="<c:url value="/images/button_refresh.png" />" alt="Refresh" /></a>
		</div>

		<script type="text/javascript">
			$(document).ready(function() {
				$("#flipItInFullScreen").fancybox({
					'overlayOpacity': 0.5,
					'overlayColor'	: '#000',
					'transitionIn'	: 'elastic',
					'transitionOut'	: 'elastic',
					'type'			: 'iframe',
					'width'			: '100%',
					'height'		: '100%',
					'enableEscapeButton': 'true'
				});
				
				$(".previousPage").click(function(){$("#body_right").load($(this).attr("href"));return false;});					
				$(".nextPage").click(function(){$("#body_right").load($(this).attr("href"));return false;});
				$("#refreshVolumeExplorer").click(function(){$("#body_right").load($(this).attr("href"));return false;});
				
		        $("#moveToFolioForm").submit(function (){
					$.ajax({ type:"GET", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						$("#body_right").html(html);
					}});
					return false;
				});
			});
		</script>
		