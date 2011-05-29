<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<script type="text/javascript">
			var $j = jQuery.noConflict();
			${command.showHelp}
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});
			});

			iip = new IIP( "targetframe", {
				server: '/DocSources/mview/ReverseProxyIIPImage.do',
				image: '${image}',
				credit: 'Folio n. ', 
				zoom: 1,
				showNavButtons: 'true',
				render: 'random',
				showNavThumbnail: 'false',
				showHelpButton: 'true'
			});

		</script>
