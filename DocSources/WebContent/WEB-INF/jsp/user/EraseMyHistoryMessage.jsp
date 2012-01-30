<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="eraseHistoryWindow"> 
		<p style="text-align:center">Are your sure you want to erase your History?</p>
        <div class="yesNoButtons">
             <a class="yes" href="/DocSources/cm/EraseHistoryOK.html" title="Yes, erase all my History">Yes</a>
             <a class="no" href="/DocSources/cm/ResearchHistory.html" title="No, go back to my History">No</a>
        </div>
	</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j(".yes").click(
			function() {
				Modalbox.show($j(this).attr("href"), {title: "ERASE HISTORY", width: 300, height: 100});return false;}
			);
		$j(".no").click(
			function() {
				Modalbox.show($j(this).attr("href"), {title: "RESEARCH HISTORY", width: 750, height: 500});return false;}
			);
	});
</script>

	