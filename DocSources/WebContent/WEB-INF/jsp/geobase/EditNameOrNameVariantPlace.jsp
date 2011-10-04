<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesOrNamesVariantsPlaceURL" value="/de/geobase/EditNamesOrNamesVariantsPlace.do">
			<c:param name="placeAllId" value="${command.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<form:form id="EditNamePlaceForm" method="post" class="edit">
	<fieldset>
	<legend><b>NAME or NAME VARIANTS</b></legend>
		<div>
			<form:label for="namePlace" id="namePlaceLabel" path="plName">Name</form:label>
			<form:input id="namePlace" path="plName" cssClass="input_30c" type="text" />
		</div>
		
		<div>
			<form:label for="nameType" id="nameTypeLabel" path="plType">Name Type</form:label>
			<form:select id="nameType" path="plType" cssClass="input_14c" type="text" items="${placeTypes}"/>
		</div>
		
		<hr />
		
		<div>
			<b>Geographic Coordinates</b><br /><br />
			<form:label for="latitudeName" id="latitudeNameLabel" path="latitude">Latitude</form:label>
			<form:input id="latitudeName" path="latitude" cssClass="input_10c" type="text" />
			<form:label for="longitudeName" id="longitudeNameLabel" path="longitude">Longitude</form:label>
			<form:input id="longitudeName" path="longitude" class="input_10c" type="text"/>
		</div>
		
		<div>
			<input id="close" type="submit" value="Close" title="Do not save changes" />
			<input id="save" type="submit" value="Save" />
		</div>
		
	</fieldset>	
	</form:form>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamesOrNamesVariantsPlace").css('visibility', 'visible');
	        $j("#EditGeographicCoordinatesPlace").css('visibility', 'visible'); 
			$j("#EditExternalLinksPlace").css('visibility', 'visible');

			$j("#EditNamesOrNamesVariantsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamesOrNamesVariantsPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>