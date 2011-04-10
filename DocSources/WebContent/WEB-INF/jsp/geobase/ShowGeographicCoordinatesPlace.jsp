<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditGeographicCoordinatesPlaceURL" value="/de/geobase/EditGeographicCoordinatesPlace.do">
			<c:param name="place.placeAllId" value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="GeographicCoordinatesPlaceDiv">
		<div class="title">
			<h5>GEOGRAPHIC COORDINATES</h5>
	 		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a title="Edit Geographic Coordinates" href="${EditGeographicCoordinatesPlaceURL}" class="editButton" id="EditGeoCoorPlace"></a>
			</security:authorize>
		</div>
		<div class="list">	
			<div class="row">
				<div class="item">Latitude</div>
				<div class="value">43 50 00 N</div>
			</div>
			<div class="row">
				<div class="item">Longitude</div>
				<div class="value">0 11 20 00 E</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamesOrNamesVariantsPlace").css('visibility', 'visible');
	        $j("#EditGeographicCoordinatesPlace").css('visibility', 'visible'); 
			$j("#EditExternalLinksPlace").css('visibility', 'visible');

			$j("#EditGeographicCoordinatesPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditGeographicCoordinatesPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>