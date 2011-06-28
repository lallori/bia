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
				credit: 'Folio n. ${documentExplorer.image.imageProgTypeNum} ${documentExplorer.image.imageRectoVerso == ImageRectoVerso.R ? 'Recto' : 'Verso'}', 
				scale: 18.0,
				navigation: true,
				showNavWindow: true,
				showNavImage: false, // this property hide navigation image
				showNavButtons: true,
				winResize: true,
				zoom: 1
			});

		</script>
