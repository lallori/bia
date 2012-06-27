<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<style type="text/css">
		#geoTitle {
			margin:10px 0 20px 5px;
		}
	</style>
	
	<a href="#" class="moreInfo" title="Browse The Medici Archive Project Database"></a><!-- Questo pulsante chiude la finestra "pop up" e a lo stesso tempo riporta questo documento nell body_left del main  -->

	<ul id="network">
		<li><a href="#"></a></li>
		<li><a href="#"></a></li>
           <li><a href="#"></a></li>
	</ul>
	
	<div id="geoDiv">
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
			<div id="text">
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
			</div>
		</div>
		
		<div id="EditDetailsPlaceDiv" class="background">
        	<div class="title">
            	<h5>PLACE DETAILS</h5>
            </div>
		
			<div class="listDetails">
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
	
	<br /><br />
	
	<div id="EditNamePlaceDiv" class="background">
    	<div class="title">
        	<h5>NAME or NAME VARIANTS</h5>
        </div>
        
        <div class="list">
			<c:forEach items="${placeNames}" var="currentName">
				<div class="row">
					<c:if test="${currentName.prefFlag == 'P'}">
						<div class="item">Principal</div>
						<c:if test="${currentName.placeAllId != place.placeAllId}">
							<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
								<c:param name="placeAllId" value="${currentName.placeAllId}" />
							</c:url>
							<div class="value"><p class="linkSearch" href="${ShowPlaceURL}">${currentName.placeName}</a></div>
						</c:if>
						<c:if test="${currentName.placeAllId == place.placeAllId}">
							<div class="value">${currentName.placeName}</div>
						</c:if>
					</c:if>
					<c:if test="${currentName.prefFlag == 'V'}">
						<div class="item">Variant</div>
						<div class="value">${currentName.placeName}</div>
					</c:if>					
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditSendRecipPlaceDiv"class="background">
    	<div class="title">
        	<h5>SENDERS and RECIPIENTS </h5>
        </div>
        
        <div class="list">	
			<div class="row">
				<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1}">
					<div class="value">${senderPlace} Senders</div>
				</c:if>
				<c:if test="${senderPlace == 1 }">
					<div class="value">${senderPlace} Sender</div>
				</c:if>
				<c:if test="${senderPlace == 0 || senderPlace == null}">
					<div class="value">0 Sender</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
					<div class="value">${recipientPlace} Recipients</div>
				</c:if>
				<c:if test="${recipientPlace == 1}">
					<div class="value">${recipientPlace} Recipient</div>
				</c:if>
				<c:if test="${recipientPlace == 0 || recipientPlace == null}">
					<div class="value">0 Recipient</div>
				</c:if>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditTopicsPlaceDiv" class="background">
    	<div class="title">
        	<h5>TOPICS LIST </h5>
        </div>
        
        <div class="list">	
			<div class="row">
			<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
				<div class="value">${docInTopics} Documents on ${topicsPlace} Topics</div>
			</c:if>
			<c:if test="${topicsPlace == 1}">
				<div class="value">${docInTopics} Document on ${topicsPlace} Topic</div>
			</c:if>
			<c:if test="${docInTopics == 1 && topicsPlace != 1}">
				<div class="value">${docInTopics} Document on ${topicsPlace} Topics</div>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
				<div class="value">0 Document on 0 Topic</div>
			</c:if>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	 <div id="EditBirthDeathPlaceDiv" class="background">
     	<div class="title">
        	<h5>BIRTH and DEATH PLACE </h5>
        </div>
        
        <div class="list">	
			<div class="row">
				<div class="value"><c:if test="${birthPlace != 0}">${birthPlace} Birth</c:if><c:if test="${birthPlace == 0}">0 Birth</c:if>      <c:if test="${activeStartPlace != 0}">${activeStartPlace} Active Start</c:if><c:if test="${activeStartPlace == 0}">0 Active Start</c:if></div>
			</div>
			<div class="row">
				<div class="value"><c:if test="${deathPlace != 0}">${deathPlace} Death</c:if><c:if test="${deathPlace == 0}">0 Death</c:if>      <c:if test="${activeEndPlace != 0}">${activeEndPlace} Active End</c:if><c:if test="${activeEndPlace == 0}">0 Active End</c:if></div>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditGeoCoorPlaceDiv" class="background">
    	<div class="title">
        	<h5>GEOGRAPHIC COORDINATES</h5>
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
	
	<br /><br />
	
	<div id="EditExtLinkPlaceDiv" class="background">
    	<div class="title">
        	<h5>EXTERNAL LINKS</h5>
        </div>
        
        <div class="list">
			<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">	
				<div class="row">
					<div class="value"><p id="linkSearch"  href="${currentExternalLink.externalLink}" target="_blank">${currentExternalLink.description}</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br /><br />
	
	 <div id="EditHierarchyPlaceDiv" class="background">
     	<div class="title">
        	<h5>HIERARCHY</h5>
        </div>
        
        <div class="list">
			<div class="row">
				<div class="item">Parent</div> 
				<div class="value">${place.parentPlace.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item">GParent</div>
				<div class="value">${place.gParent}</div>
			</div>
			<div class="row">
				<div class="item">GGParent</div>
				<div class="value">${place.ggp}</div>
			</div>
			<div class="row">
				<div class="item">GP2</div>
				<div class="value">${place.gp2}</div>
			</div>
			<div class="row">
				<div class="item">Parent_TGN_id</div>
				<div class="value">${place.plParentTermId}</div>
			</div>
			<div class="row">
				<div class="item">Parent_GEOKEY</div>
				<div class="value">${place.plParentSubjectId}</div>
			</div>
		</div>
	</div>

