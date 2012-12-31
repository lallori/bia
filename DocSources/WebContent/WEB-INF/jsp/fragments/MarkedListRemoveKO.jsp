<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="DeleteThisRecordDiv">
		<h1>The ${category} hasn't been removed in your marked list.</h1>
		
		<input id="close" type="submit" title="Close Actions Menu window" value="Close" style="margin:20px 0 0 90px;"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				$j("#DialogMarkedListRemove").dialog("close");
				$j("#DialogMarkedListRemove").remove();
				return false;
			});
		});
	</script>