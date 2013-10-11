<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	<c:url var="GetImageAnnotationURL" value="/src/mview/GetImageAnnotation.json">
		<c:param name="imageId" value="${image.imageId}"></c:param>
		<c:param name="imageName" value="${image.imageName}"></c:param>
	</c:url>
	<c:url var="UpdateAnnotationsURL" value="/src/mview/UpdateAnnotations.json">
		<c:param name="imageId" value="${image.imageId}"></c:param>
		<c:param name="imageName" value="${image.imageName}"></c:param>
	</c:url>
	
		<script type="text/javascript">
			var imageName = "${image.imageName}";
			
			// RR: Added volume informations and insert informations (if needed)
			var volExt = "${image.volLetExt}";
			var insNum = "${image.insertNum}";
			var insExt = "${image.insertLet}";
			var credit = '';
			if (insNum != '')
				credit += '<span style=\'font-size:16px\'><fmt:message key="mview.showManuscriptViewerHtml.insert"/> ' + insNum + (insExt != '' ? ' <fmt:message key="mview.showManuscriptViewerHtml.extension"/> '+insExt : '') + ' &nbsp; - </span>';
				
			var annotations = new Array();
			var annotationId = "${annotationId}";
			if ("${image.imageType}" == 'R') {
				credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.indexOfNames"/>&nbsp;</span>';
			} else if ("${image.imageType}" == 'C') {
				credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.folio"/>&nbsp;</span>';
			} else if ("${image.imageType}" == 'A') {
				credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.allegato"/>&nbsp;</span>';
			} else if ("${image.imageType}" == 'G') {
				credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.guardia"/>&nbsp;</span>';
			} else if ("${image.imageType}" == 'O') {
				//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
				if(imageName.indexOf("COPERTA") != -1){
					credit += '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.coperta"/>&nbsp;</span>';
				}
			} else {
           		credit += ' ';
           	}
		
			credit+= '<span style=\'font-size:22px\'>' + "${image.imageProgTypeNum}";
			if ("${image.missedNumbering}") {
				credit += ' ' + "${image.missedNumbering}";
			}
			if ("${image.imageRectoVerso}" == 'R') {
				credit += '</span>' + ' recto' + '</span>';
			} else if ("${image.imageRectoVerso}" == 'V'){
				credit += '</span>' + ' verso' + '</span>';
			}
			
			//MD:The last control is to verify if the image is a spine
			if(imageName.indexOf("SPI") != -1){
				credit = '<span style=\'font-size:16px\'>' + '<fmt:message key="mview.showManuscriptViewerHtml.spine"/>' + '</span>';
			}
			
			iip = new IIPMooViewer( "targetframe", {
				server: '${IIPImageServerURL}',
				prefix: '${ImagePrefixURL}',
				image: '${image}',
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				annotationId: annotationId,
				credit: credit,
				navigation: true,
				showNavWindow: true,
				showNavImage: true, // this property hide navigation image
				showNavButtons: true,
				winResize: true,
				zoom: 2,
				scale: 0
			});
			
			// RR The if-else condition has been removed because we always want IIPMooViewer instantiated with extended options
			/*
			if(annotationId != null && annotationId != ''){
				iip = new IIPMooViewer( "targetframe", {
					server: '${IIPImageServerURL}',
					prefix: '${ImagePrefixURL}',
					image: '${image}',
					annotationsType: 'remote',
					retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
					updateAnnotationsUrl: '${UpdateAnnotationsURL}',
					annotations: annotations,
					annotationId: annotationId,
					credit: credit,
					navigation: true,
					showNavWindow: true,
					showNavImage: true, // this property hide navigation image
					showNavButtons: true,
					winResize: true,
					zoom: 2,
					scale: 0
				});		
			}else{
				iip = new IIPMooViewer( "targetframe", {
					server: '${IIPImageServerURL}',
					prefix: '${ImagePrefixURL}',
					image: '${image}',
					credit: credit,
					navigation: true,
					showNavWindow: true,
					showNavImage: true, // this property hide navigation image
					showNavButtons: true,
					winResize: true,
					zoom: 2,
					scale: 0
				});		
			}
			*/

		</script>
