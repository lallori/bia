<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="geoDiv">
	<div id="geoTitle">
	
	<c:if test="${place.plSource == 'TGN' || place.geogKey >= 1000000}">
		<h4>Adding TGN Place Record</h4>
		<div align="center">
			<p>To get this data through the TGN <a href="http://www.getty.edu/research/conducting_research/vocabularies/tgn/" target="_blank">click here</a></p>
		</div>
	</c:if>
	
	<c:if test="${place.plSource == 'MAPPLACE' || (place.geogKey >= 100000 && place.geogKey < 400000) }">
		<h4>MAP Place Record</h4>
	</c:if>
	
	<c:if test="${place.plSource == 'MAPSITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
		<h4>MAP Site or Subsite</h4>
	</c:if>
	</div>
	
	<div class="background" id="EditDetailsPlaceDiv">
		<div class="title">
			<h5>PLACE DETAILS</h5>
			
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

<div class="background" id="EditNamePlaceDiv">
		<div class="title">
			<h5>NAME or NAME VARIANTS</h5>
		</div>
		
		<div class="list">
			<c:forEach items="${placeNames}" var="currentName">
				<div class="row">
					<c:if test="${currentName.prefFlag == 'P'}">
						<div class="item">Principal</div>
						<div class="value">${currentName.placeName}</div>
					</c:if>
					<c:if test="${currentName.prefFlag == 'V'}">
						<div class="item">Variant</div>
						<div class="value">${currentName.placeName}</div>
					</c:if>					
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div class="background" id="EditSendRecipPlaceDiv">
	<div class="title">
		<h5>SENDERS and RECIPIENTS </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value">${place.senderDocuments.size()} Senders</div>
		</div>
		<div class="row">
			<div class="value">${place.recipientDocuments.size()} Recipients</div>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditTopicsPlaceDiv">
	<div class="title">
		<h5>TOPICS LIST </h5>
	</div>
	<div class="list">	
		<div class="row">
			<div class="value">6253 Documents on 44 Topics</div>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5>BIRTH and DEATH PLACE </h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value">${place.bornedPeople.size()} Birth</div>
		</div>
		<div class="row">
			<div class="value">${place.deathPeople.size()} Death</div>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditGeoCoorPlaceDiv">
		<div class="title">
			<h5>GEOGRAPHIC COORDINATES</h5>
	 	</div>
		<div class="list">	
			<div class="row">
				<div class="item">Latitude</div>
				<div class="value">${place.placeGeographicCoordinates.degreeLatitude} ${place.placeGeographicCoordinates.minuteLatitude} ${place.placeGeographicCoordinates.secondLatitude} ${place.placeGeographicCoordinates.directionLatitude}</div>
			</div>
			<div class="row">
				<div class="item">Longitude</div>
				<div class="value">${place.placeGeographicCoordinates.degreeLongitude} ${place.placeGeographicCoordinates.minuteLongitude} ${place.placeGeographicCoordinates.secondLongitude} ${place.placeGeographicCoordinates.directionLongitude}</div>
			</div>
		</div>
	</div>
	
	<br />
	
	<div class="background" id="EditExtLinkPlaceDiv">
		<div class="title">
			<h5>EXTERNAL LINKS</h5>
 		</div>
		
		<div class="list">
			<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">	
				<div class="row">
					<div class="value"><a id="linkSearch"  href="${currentExternalLink.externalLink}" target="_blank">${currentExternalLink.description}</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div class="background" id="EditHierarchyPlaceDiv">
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
