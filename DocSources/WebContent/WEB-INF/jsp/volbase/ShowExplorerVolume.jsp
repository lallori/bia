<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:forEach var="volumeImage" items="${volumeImages.pageList}">
	<c:url var="next" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="flashVersion" value="true" />
		<c:param name="page" value="next" />
	</c:url>

	<c:url var="previous" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="flashVersion" value="true" />
		<c:param name="page" value="previous" />
	</c:url>

	<script type="text/javascript" src="<c:url value="/scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/fancybox/jquery.fancybox-1.3.2.js"/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/fancybox.css"/>" media="screen" />

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="manuscriptViewer" value="/mview/ShowManuscriptViewer.do">
			<c:param name="imageName"   value="${volumeImage}" />
			<c:param name="flashVersion"   value="true" />
		</c:url>
	</security:authorize>
	
		<h5>VOLUME EXPLORER</h5>
		
				<c:if test="${volumeImages.firstPage}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${!volumeImages.firstPage}">
					<a id="previous" href="${previous}"><img src="<c:url value="/images/button_prev.png" />" alt="previous" /></a>
				</c:if>
				<c:if test="${volumeImages.lastPage}">
					<img src="<c:url value="/images/button_next.png" />" alt="next" />
				</c:if>
				<c:if test="${!volumeImages.lastPage}">
					<a id="next" href="${next}"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
		
			<script type="text/javascript">
				$(document).ready(function() {
					$("#previous").click(function(){$("#body_right").load($(this).attr("href"));return false;});					
					$("#next").click(function(){$("#body_right").load($(this).attr("href"));return false;});
				});
			</script>
		<!--  <div id="refresh"><a href="#">Refresh</a></div>-->	
		
		
		<div id="flipDiv">
			<iframe class="iframeFlipVolume" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewer}"></iframe>
		</div>	
		
		<div>
			<div id="flipInFullScreen"><a id="flipItInFullScreen" href="${manuscriptViewer}" style="z-index:5">flip it in fullscreen</a></div>
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
				
				});
			</script>

			<div id="prevNextButtons">
				<div id="prevButton">
				<c:if test="${volumeImages.firstPage}">
					<img src="<c:url value="/images/button_prev.png" />" alt="prev" />
				</c:if>
				<c:if test="${!volumeImages.firstPage}">
					<a href="${previous}"><img src="<c:url value="/images/button_prev.png" />" alt="prev" /></a>
				</c:if>
				</div>
				<div id="nextButton">
				<c:if test="${volumeImages.lastPage}">
					<img src="<c:url value="/images/button_next.png" />" alt="next" />
				</c:if>
				<c:if test="${!volumeImages.lastPage}">
					<a href="${next}"><img src="<c:url value="/images/button_next.png" />" alt="next" /></a>
				</c:if>
				</div>
			</div>
			
			<div id="help"><img src="<c:url value="/images/help.png" />" alt="help" /></div>
		</div>
		
		<br /><br /><br />
		
		<!-- <div style="text-align:center"><a href="#"><img src="<c:url value="/images/button_transcribe.png" />" alt="transcribe and contextualize it" /></a></div> -->
</c:forEach>