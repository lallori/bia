<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="activateDiv">
	<a id="deactivate" class="button_large" href="#">Deactivate this volume</a>
	<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
</div>

<script>
	$j(document).ready(function() {
		$j("#close").click(
					function(){
							Modalbox.hide(); return false;
							});
		$j("#deactivate").click(
					function(){
							Modalbox.hide(); return false;
							});
		});
</script>