<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditGeographicCoordinatesPlaceURL" value="/de/geobase/EditGeographicCoordinatesPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="EditGeoCoorPlaceDiv">
		<div class="title">
			<h5>GEOGRAPHIC COORDINATES<a class="helpIcon" title="Text">?</a></h5>
	 		<c:if test="${place.placeAllId > 0 && place.prefFlag == 'P'}">
	 		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a title="Edit Geographic Coordinates" href="${EditGeographicCoordinatesPlaceURL}" class="editButton" id="EditGeoCoorPlace"></a>
			</security:authorize>
			</c:if>
		</div>
		<div class="list">	
			<div class="row">
				<div class="item">Latitude</div>
				<div class="value">${place.placeGeographicCoordinates.degreeLatitude}° ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</div>
			</div>
			<div class="row">
				<div class="item">Longitude</div>
				<div class="value">${place.placeGeographicCoordinates.degreeLongitude}° ${place.placeGeographicCoordinates.minuteLongitude}' ${place.placeGeographicCoordinates.secondLongitude}'' ${place.placeGeographicCoordinates.directionLongitude}</div>
			</div>
		</div>
	</div>
	
	<br />

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamePlace").css('visibility', 'visible');
	        $j("#EditGeoCoorPlace").css('visibility', 'visible'); 
			$j("#EditExternalLinksPlace").css('visibility', 'visible');

			$j("#EditGeoCoorPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditGeoCoorPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>