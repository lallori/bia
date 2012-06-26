<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPlaceURL" 		value="/de/geobase/EditDetailsPlace.do">
			<c:param name="placeAllId"   		value="${place.placeAllId}" />
			<c:param name="plSource" 	 		value="${place.plSource}" />
			<c:param name="parentPlaceAllId"	value="${place.parentPlace.placeAllId}" />
		</c:url>
	</security:authorize>
	
	<div id="geoDiv">
			<%-- Creating a New Place Record --%>
			<c:if test="${place.placeAllId == 0}">
				<div id="geoTitle">
					<c:if test="${place.plSource == 'TGN'}">
	            		<h2 class="addNew">ADD New - TGN Place Record</h2>
	            		<p style="margin:0 0 5px 15px">Get this place data through the Getty TGN source <a class="link" href="http://www.getty.edu/research/tools/vocabularies/tgn/index.html" target="_blank">click here</a></p>		
					</c:if>
					<c:if test="${place.plSource == 'MAPPLACE'}">
						<h2 class="addNew">ADD New -  MAP Place Record</h2>
					</c:if>
					<c:if test="${place.plSource == 'MAPSITE'}">
						<h2 class="addNew">ADD New -  MAP SITE or SUBSITE Place Record</h2>
					</c:if>
				</div>
			</c:if>
			<%-- Editing Place Records --%>
			<c:if test="${place.placeAllId != 0}">
				<div id="geoTitle">
					<div id="placeImageDiv">
						<c:if test="${linkGoogleMaps != null}">
							<a href="${linkGoogleMaps}" target="_blank" title="Show on Google Maps"><img src="<c:url value="/images/1024/img_googleMap.jpg"/>" alt="Place" class="shadow"></a>
						</c:if>
						<c:if test="${linkGoogleMaps == null }">
							<span>Not attached to Google Maps</span>
							<c:if test="${place.prefFlag == 'P'}">
								<a class="PlaceMap" href="#">Assign Geo Coordinates</a>
							</c:if>
							<img src="<c:url value="/images/1024/img_place.png" />" alt="Place">
						</c:if>
					</div>
        			<h3>${place.placeName}</h3>
					<h4>${place.placeNameFull}</h4>
					<c:if test="${place.plSource == 'TGN' && place.geogKey >= 1000000}">
            		<h5>TGN Place record</h5>
        			</c:if>
        			<c:if test="${place.geogKey >= 1000000  && place.plSource == 'MAPPLACE'}">
        			<h5>TGN Place record (updated by MAP)</h5>
        			</c:if>
        			<c:if test="${place.plSource == 'MAPPLACE' && (place.geogKey >= 100000 && place.geogKey < 400000) }">
					<h5>MAP Place record</h5>
					</c:if>
        			<c:if test="${place.plSource == 'MAPSITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
					<h5>MAP Site or Subsite record</h5>
					</c:if>
					<h7>${place.plType}</h7>
					<c:if test="${place.plSource == 'TGN' || place.geogKey >= 1000000}">
						<p style="margin:20px 0 5px 10px">To compare this place data to the Getty TGN source <a class="link" href="http://www.getty.edu/research/tools/vocabularies/tgn/index.html" target="_blank">click here</a></p>		
					</c:if>
					<c:if test="${place.prefFlag == 'V'}">
						<br />
						<div style="margin-left:8px">
								<c:forEach items="${placeNames}" var="currentName">
									<c:if test="${currentName.prefFlag == 'P'}">
										<p style="margin:0 0 5px 10px"><font color="red">'${place.placeName}' is a Variant Name for '${currentName.placeName}'. Click on the 'Principal' name to visualize ${currentName.placeName} and all the values and fields connected to it.</font></p>
									</c:if>
								</c:forEach>
						</div>
					</c:if>
				</div>
			</c:if>
		
		<div class="background" id="EditDetailsPlaceDiv">
			<div class="title">
				<h5>PLACE DETAILS</h5>
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditDetailsPlace" href="${EditDetailsPlaceURL}" class="editButton"></a><span id="loading" />
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
				<c:if test="${place.termAccent != place.placeName}">
					<div class="row">
						<div style="color: #6D5C4D;display: table-cell;padding: 3px 0;text-align: left;width: 20%;">(with accents)</div>
						<div class="value">${place.termAccent}</div>
					</div>
				</c:if>
				<div class="row">
					<div class="item">Place type</div>
					<div class="value">${place.plType}</div>
				</div>
				<div class="row">
					<div class="item">Place Parent</div>
					<div class="value">${place.parentPlace.getPlaceNameFull()}</div>
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

// 			$j("#EditDetailsPlace").click(function(){
// 				$j(this).next().css('visibility', 'visible');
// 				$j("#EditDetailsPlaceDiv").load($j(this).attr("href"));
// 				return false;
// 			});
		});
	</script>
</security:authorize>