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
	
	<a href="http://bia.medici.org" id="moreInfoButton" class="button_medium" title="Browse The Medici Archive Project Database" target="_blank">More info</a>
	
	<ul id="network">
       <span class='st_facebook_large' displayText='Facebook'></span>
<!--        <span class='st_twitter_large' displayText='Tweet'></span> -->
	   <a href="https://twitter.com/share" class="twitter-share-button" data-text=" " data-lang="it" data-size="large" data-count="none">Tweet</a>
	   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
       <span class='st_googleplus_large' displayText='Google +'></span>
	</ul>
	
	<div id="geoDiv">
		<div id="geoTitle">
			<div id="text">
        		<h3>${place.placeName}</h3>
				<h4>${place.parentPlace.placeNameFull}</h4>
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
				<c:if test="${place.prefFlag != 'V'}">
					<div id="linked">
						<p>Linked to this place entry:</p>
						<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
							${docInTopics} Documents on ${topicsPlace} Topics
						</c:if>
						<c:if test="${topicsPlace == 1}">
							${docInTopics} Document on ${topicsPlace} Topic
						</c:if>
						<c:if test="${docInTopics == 1 && topicsPlace != 1}">
							${docInTopics} Document on ${topicsPlace} Topics
						</c:if>
						<c:if test="${topicsPlace == 0 || topicsPlace == null}">
							0 Documents on 0 Topics
						</c:if>
						<hr />
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							${senderPlace} Senders and ${recipientPlace} Recipients
						</c:if>
						<c:if test="${senderPlace == 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							${senderPlace} Sender and ${recipientPlace} Recipients
						</c:if>
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace == 1}">
							${senderPlace} Senders and ${recipientPlace} Recipient
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							0 Senders and ${recipientPlace} Recipients
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace == 1}">
							0 Senders and ${recipientPlace} Recipient
						</c:if>
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && (recipientPlace == 0 || recipientPlace == null)}">
							${senderPlace} Senders and 0 Recipient
						</c:if>
						<c:if test="${senderPlace == 1 && (recipientPlace == 0 || recipientPlace == null)}">
							${senderPlace} Sender and 0 Recipient
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && (recipientPlace == 0 || recipientPlace == null)}">
							0 Sender and 0 Recipient
						</c:if>
						<hr />
						<c:if test="${birthPlace != 0}">${birthPlace} Births</c:if><c:if test="${birthPlace == 0}">0 Births</c:if> and <c:if test="${activeStartPlace != 0}">${activeStartPlace} Active Starts</c:if><c:if test="${activeStartPlace == 0}">0 Active Starts</c:if>
						<br />
						<c:if test="${deathPlace != 0}">${deathPlace} Deaths</c:if><c:if test="${deathPlace == 0}">0 Deaths</c:if> and <c:if test="${activeEndPlace != 0}">${activeEndPlace} Active Ends</c:if><c:if test="${activeEndPlace == 0}">0 Active Ends</c:if>
					</div>	
				</c:if>						
				<c:if test="${place.prefFlag == 'V'}">
					<br />
					<c:forEach items="${placeNames}" var="currentName">
						<c:if test="${currentName.prefFlag == 'P'}">
							<c:url var="ShowPrincipalPlaceURL" value="/src/geobase/ShowPlace.do">
								<c:param name="placeAllId"	value="${currentName.placeAllId}"/>
							</c:url>
							<p class="textPrincipalName">'${place.placeName}' is a Variant Name for '${currentName.placeName}'.</p>
<%-- 							<a href="${ShowPrincipalPlaceURL}" class="button_medium" id="buttonPrincipalName">Click here</a> --%>
<%-- 							<p class="textPrincipalName">to visualize <b>${currentName.placeName}</b> and all the values and fields connected to it.</font></p> --%>
						</c:if>
					</c:forEach>
				</c:if>
			</div>
			<div id="placeImageDiv">
				<c:if test="${linkGoogleMaps != null}">
					<a href="${linkGoogleMaps}" target="_blank" title="Show on Google Maps"><img src="<c:url value="/images/1024/img_googleMap.jpg"/>" alt="Place" class="shadow"></a>
				</c:if>
				<c:if test="${linkGoogleMaps == null }">
					<span>Not attached to Google Maps</span>	
				</c:if>
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
				<div class="value">${place.placeGeographicCoordinates.degreeLatitude}&#xb0 ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</div>
			</div>
			<div class="row">
				<div class="item">Longitude</div>
				<div class="value">${place.placeGeographicCoordinates.degreeLongitude}&#xb0 ${place.placeGeographicCoordinates.minuteLongitude}' ${place.placeGeographicCoordinates.secondLongitude}'' ${place.placeGeographicCoordinates.directionLongitude}</div>
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

