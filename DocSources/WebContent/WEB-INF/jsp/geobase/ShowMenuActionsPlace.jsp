<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePlaceURL" value="/de/geobase/DeletePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
	<c:url var="UndeletePlaceURL" value="/de/geobase/UndeletePlace.do">
		<c:param name="placeAllId"   value="${place.placeAllId}" />
	</c:url>
		
	<div id="ActionsMenuDiv">
		<h1>Use the buttons below to perform one of these predefined actions:</h1>
		
		<c:if test="${!place.logicalDelete}">
		<a id="deleteGeoBase" href="${DeletePlaceURL}">Delete this place record</a>
		</c:if>	
		<c:if test="${place.logicalDelete}">
		<a id="undeleteGeoBase" href="${UndeletePlaceURL}">Undelete this place record</a>
		</c:if>					
		<input id="close" class="button_small" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#deleteGeoBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "DELETE THIS PLACE RECORD", width: 450, height: 150});
				return false;
			});
			$j("#undeleteGeoBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "UNDELETE THIS PLACE RECORD", width: 450, height: 150});
				return false;
			});
		});
	</script>
