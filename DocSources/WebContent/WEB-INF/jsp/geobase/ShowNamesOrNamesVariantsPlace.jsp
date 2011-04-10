<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditNamesOrNamesVariantsPlaceURL" value="/de/geobase/EditNamesOrNamesVariantsPlace.do">
			<c:param name="place.placeAllId" value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="EditNamesOrNamesVariantsPlaceDiv">
		<div class="title">
			<h5>NAME or NAME VARIANTS</h5>
		 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditNamePlace" href="${EditNamesOrNamesVariantsPlaceURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">Principal</div> 
				<div class="value">Firenze</div> 
			</div>
			<div class="row">
				<div class="item">Variant</div>
				<div class="value">Fiorenza</div>
			</div>
			<div class="row">
				<div class="item">Variant</div>
				<div class="value">Florencia</div>
			</div>
			<div class="row">
				<div class="item">Variant</div>
				<div class="value">Fiorentia</div>
			</div>
			<div class="row">
				<div class="item">Variant</div>
				<div class="value">Fiorentine</div>
			</div>
			<div class="row">
				<div class="item">Variant</div>
				<div class="value">Florenz</div>
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

			$j("#EditNamesOrNamesVariantsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditNamesOrNamesVariantsPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>