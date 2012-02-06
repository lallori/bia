<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
		<script type="text/javascript">
			iip = new IIP( "targetframe", {
				server: '${IIPImageServerURL}',
				prefix: '${ImagePrefixURL}',
				image: '${image}',
				<%-- credit: "${image.imageType == 'C' ? 'folio: ': ''} ${image.imageType == 'R' ? 'Index of Names n.' : ''} ${image.imageProgTypeNum} ${image.missedNumbering} ${image.imageRectoVerso == 'R' ? 'Recto' : 'Verso'}", --%>
				credit: '<span style=\'font-size:12px\'>' +
							"${image.imageType == 'C' ? 'folio &nbsp;&nbsp;': ''} ${image.imageType == 'R' ? 'index of names &nbsp;' : ''}" +
							'<span style=\'font-size:18px\'>' +
							"${image.imageProgTypeNum} ${image.missedNumbering}" + 
							'</span>' +
							"${image.imageRectoVerso == 'R' ? 'recto' : 'verso'}" +
						'</span>',
				navigation: true,
				showNavWindow: true,
				showNavImage: true, // this property hide navigation image
				showNavButtons: true,
				winResize: true,
				zoom: 2,
				scale: 0
			});
			
			
					
		

		</script>
