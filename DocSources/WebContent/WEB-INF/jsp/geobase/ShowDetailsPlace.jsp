<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" value="/de/geobase/EditDetailsPlace.do">
			<c:param name="placeAllId"   value="${place.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div id="geoDiv">
	<div id="geoTitle">
	
	<c:if test="${place.geoIdEncoding == 'TGN_GEOKEY' || place.geogKey >= 1000000}">
		<h4>Adding TGN Place Record</h4>
		<div align="center">
			<p>To get this data throught the TGN <a href="http://www.getty.edu/research/conducting_research/vocabularies/tgn/" target="_blank">click here</a></p>
		</div>
	</c:if>
	
	<c:if test="${place.geoIdEncoding == 'MAP_PLACE' || (place.geogKey >= 100000 && place.geogKey < 400000) }">
		<h4>MAP Place Record</h4>
	</c:if>
	
	<c:if test="${place.geoIdEncoding == 'MAP_SITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
		<h4>MAP Site or Subsite</h4>
	</c:if>
	</div>
	
	<div class="background" id="EditDetailsPlaceDiv">
		<div class="title">
			<h5>PLACE DETAILS</h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a title="Edit TGN Place Details" href="${EditDetailsPlaceURL}" class="editButton" id="EditDetailsPlace"></a><span id="loading" />
			</security:authorize>
		</div>
	
		<div class="list">
			<div class="row">
				<div class="item">Place ID</div> 
				<div class="value">${place.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item">Place name</div>
				<div class="value">${place.placeName}</div>
			</div>
			<div class="row">
				<div class="item">Place type</div>
				<div class="value">${place.plType}</div>
			</div>
			<div class="row">
				<div class="item">Place Parent</div>
				<div class="value">${place.plParent}</div>
			</div>
			<div class="row">
				<div class="item">Place Notes</div>
				<div class="value">${place.placesMemo}</div>
			</div>
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
			$j("#EditExtLinkPlace").css('visibility', 'visible');

			$j("#EditDetailsPlace").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsPlaceDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>