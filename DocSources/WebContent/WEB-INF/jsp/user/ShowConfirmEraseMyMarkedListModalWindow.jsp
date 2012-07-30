<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EraseMyMarkedListURL" value="/user/EraseMyMarkedList.do"/>

	<c:url var="ShowMyMarkedListURL" value="/user/ShowMyMarkedList.do"/>

	<div id="eraseMarkedListWindow"> 
		<p style="text-align:center">Are your sure you want to erase your Marked List?</p>
	    <div class="yesNoButtons">
	        <a class="yes" href="${EraseMyMarkedListURL}" title="Yes, erase all my Marked List">Yes</a>
	        <a class="no" href="${ShowMyMarkedListURL}" title="No, go back to my Marked List">No</a>
	    </div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".yes").click(function() {
				Modalbox.show($j(this).attr("href"), {title: "ERASE MARKED LIST", width: 300, height: 120});
				return false;
			});
			$j(".no").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "MARKED LIST", width: 750, height: 415});
				return false;
			});
		});
	</script>