<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ImagePreviewURL" value="/teaching/ShowImagePreview.do">
		<c:param name="imageId" value="${command.imageId}"/>
	</c:url>

	<div id="ShowImagePreviewDiv">
		
		<div id="flipDiv">
			<iframe class="iframeFlipImage" scrolling="no" marginheight="0" marginwidth="0" src="${ImagePreviewURL}" style="z-index:0"></iframe>
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

			console.log('HERE');
		});
	</script>