<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
		<script type="text/javascript">
			var credit = '';	
			var imageName = "${image.imageName}";
			if ("${image.imageType}" == 'R') {
				credit += '<span style=\'font-size:16px\'>' + 'index of names &nbsp;';
			} else if ("${image.imageType}" == 'C') {
				credit += '<span style=\'font-size:16px\'>' + 'folio &nbsp; &nbsp;';
			} else if ("${image.imageType}" == 'A') {
				credit += '<span style=\'font-size:16px\'>' + 'allegato &nbsp; &nbsp;';
			} else if ("${image.imageType}" == 'G') {
				credit += '<span style=\'font-size:16px\'>' + 'guardia &nbsp; &nbsp;';
			} else if ("${image.imageType}" == 'O') {
				//MD: Is it correct the imageType 'O' for "costola" and "coperta"?
				if(imageName.indexOf("COPERTA") != -1){
					credit += '<span style=\'font-size:16px\'>' + 'coperta &nbsp; &nbsp;';
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
				credit = '<span style=\'font-size:16px\'>' + 'SPINE' + '</span>';
			}

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

		</script>
