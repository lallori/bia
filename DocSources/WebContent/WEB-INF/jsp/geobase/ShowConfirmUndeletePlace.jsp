<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to undelete this record?</h1>
		
		<a id="yes" href="/DocSources/de/docbase/YesDeleteThisRecord.html">YES</a>
	
		<a id="no" href="/DocSources/de/docbase/ActionsMenu.html">NO</a>
			
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				// TO BE IMPLEMENTED...
				Modalbox.hide();
				return false;
			});
		});
	</script>
