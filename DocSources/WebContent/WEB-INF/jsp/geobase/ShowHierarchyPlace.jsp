<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/geobase/EditDetailsPlace.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div class="background" id="EditHierarchyPlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.showHierarchyPlace.hierarchy"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.parent"/></div> 
				<div class="value">${place.parentPlace.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.gParent"/></div>
				<div class="value">${place.gParent}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.ggParent"/></div>
				<div class="value">${place.ggp}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.gpTwo"/></div>
				<div class="value">${place.gp2}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.parentTGN"/></div>
				<div class="value">${place.plParentTermId}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.showHierarchyPlace.parentGeo"/></div>
				<div class="value">${place.plParentSubjectId}</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamesOrNamesVariantsPlace").css('visibility', 'visible');
	        $j("#EditGeographicCoordinatesPlace").css('visibility', 'visible'); 
			$j("#EditExternalLinksPlace").css('visibility', 'visible');

			$j("#EditDetailsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>