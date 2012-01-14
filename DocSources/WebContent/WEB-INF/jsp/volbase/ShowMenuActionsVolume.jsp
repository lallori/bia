<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteVolumeURL" value="/de/volbase/DeleteVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
	<c:url var="UndeleteVolumeURL" value="/de/volbase/UndeleteVolume.do">
		<c:param name="summaryId"   value="${volume.summaryId}" />
	</c:url>
		
	<div id="ActionsMenuDiv">
		<h1>Use the buttons below to perform one of these predefined actions:</h1>
		
		<c:if test="${!volume.logicalDelete}">
		<a id="deleteVolBase" href="${DeleteVolumeURL}">Delete this volume record</a>
		</c:if>	
		<c:if test="${volume.logicalDelete}">
		<a id="undeleteVolBase" href="${UndeleteVolumeURL}">Undelete this volume record</a>
		</c:if>					
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#deleteVolBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "DELETE THIS VOLUME RECORD", width: 450, height: 150});
				return false;
			});
			$j("#undeleteVolBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "UNDELETE THIS VOLUME RECORD", width: 450, height: 150});
				return false;
			});
		});
	</script>
