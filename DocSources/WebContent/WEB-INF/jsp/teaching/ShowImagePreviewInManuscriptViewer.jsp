<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
	<script type="text/javascript">

		iip = new IIPMooViewer( "targetframe", {
			server: '${IIPImageServerURL}',
			prefix: '${ImagePrefixURL}',
			image: '${image}',
			/*annotationsType: 'local',
			retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
			updateAnnotationsUrl: '${UpdateAnnotationsURL}',
			annotations: annotations,
			annotationId: annotationId,*/
			credit: '${image}',
			enableEdit: false,
			navigation: true,
			showNavWindow: true,
			showNavImage: true, // this property hide navigation image
			showNavButtons: true,
			winResize: true,
			zoom: 2,
			scale: 0
		});
		
	</script>