<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowActivateVolumeModalURL" value="/digitization/ShowActivateVolumeModal.json"/>

<div id="activateDiv">
	<form:form id="activateForm" method="post" cssClass="edit">
		<input type="submit" id="activate" cssClass="button_large" value="Activate this volume" title="Activate this volume">
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
	</form:form>
</div>

<script>
	$j(document).ready(function() {
		$j("#activateForm").submit(function(){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#activateDiv").html(html);
			}});
			return false;
		});
		
		$j("#close").click(function(){
			Modalbox.hide(); 
			return false;
		});

	});
</script>