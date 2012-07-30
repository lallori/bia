<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="yesWindow"> 
	<p style="text-align:center">Your Marked List has been erased</p>
	    <div>
	        <a class="close" title="Close my History Window">Close</a>
	    </div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".close").click(function(){
				Modalbox.hide();
				return false;
			});
		});
	</script>