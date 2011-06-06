<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form id="searchFilterForm" method="post">
	<p><u>Custom Search Filter</u></p>
	<br />
	<div id="wordSearchDiv"></div>
	<div id="volumeSearchDiv"></div>
	<div id="dateSearchDiv"></div>
	<div id="extractSearchDiv"></div>
	<div id="synopsisSearchDiv"></div>
	<div id="topicsSearchDiv"></div>
	<div id="personSearchDiv"></div>
	<div id="placeSearchDiv"></div>
	<div id="senderSearchDiv"></div>
	<div id="fromSearchDiv"></div>
	<div id="recipientSearchDiv"></div>
	<div id="toSearchDiv"></div>
	<div id="refersToSearchDiv"></div>	
	<br>
	<br>
	<input type="submit" title="Search" value="" id="advsearch">
	<a class="saveAsButton" href="#"></a>
</form>

	<script type="text/javascript">
		$j(document).ready(function() {
		});
	</script>
