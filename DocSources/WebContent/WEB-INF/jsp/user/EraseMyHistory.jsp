<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowMyHistoryURL" value="/user/ShowMyHistory.do"/>

	<div id="yesWindow"> 
	<p style="text-align:center">Your History has been erased</p>
	    <div class="yesNoButtons">
	        <a class="close" title="Close my History Window">Close</a>
	        <a class="goBack" href="${ShowMyHistoryURL}" title="Go back to my History">Go back</a>
	    </div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#lastRecord").css('visibility', 'hidden');
			$j("#nextRecord").css('visibility', 'hidden');
						
			$j(".close").click(function(){
				Modalbox.hide();
				return false;
			});
			$j(".goBack").click(function() {
				Modalbox.show($j(this).attr("href"), {title: "RESEARCH HISTORY", width: 750, height: 415});
				return false;
			});
		});
	</script>