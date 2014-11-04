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
	
	<script type="text/javascript" src="<c:url value="/scripts/mview/jquery-ui-1.8.9.custom.min.js"/>"></script>
	
    <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="true" />
	</security:authorize>
	
    <security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="false" />
	</security:authorize>
	
    <c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
		<c:param name="placeAllId" value="${place.placeAllId}" />
	</c:url>
	<c:url var="HomeURL" value="/Home.do">
		<c:param name="placeAllId" value="${place.placeAllId}" />
	</c:url>
	<c:url var="ShowLoginFirstDialogURL" value="/menu/ShowLoginFirstModalWindow.do" />
	
	<div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_EN/all.js#xfbml=1";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
	
	<a href="${ShowPlaceURL}" id="moreInfoButton" class="button_medium" title="Browse The Medici Archive Project Database" target="_blank"><fmt:message key="geobase.sharePlace.moreInfo"/></a>
	
	
	
	<ul id="network">
      <div class="fb-like" data-send="false" data-layout="button_count" data-width="500" data-show-faces="false" style="display:inline;"></div>
	   <div style="display:inline;"><a href="https://twitter.com/share" class="twitter-share-button" data-text=" "><fmt:message key="geobase.sharePlace.tweet"/></a></div>
	   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
	   <div class="g-plusone" data-size="medium" style="display:inline"></div>
	   <script type="text/javascript">
  	   	(function() {
    		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    		po.src = 'https://apis.google.com/js/plusone.js';
    		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  		})();
		</script>
	</ul>
	
	<div id="geoDiv">
		<div id="geoTitle">
			<div id="text">
        		<h3>${place.placeName}</h3>
				<h4>${place.parentPlace.placeNameFull}</h4>
				<c:if test="${place.plSource == 'TGN' && place.geogKey >= 1000000}">
            	<h5><fmt:message key="geobase.sharePlace.tgnPlace"/></h5>
            	</c:if>
        		<c:if test="${place.geogKey >= 1000000  && place.plSource == 'MAPPLACE'}">
        			<h5><fmt:message key="geobase.sharePlace.tgnPlaceUpdated"/></h5>
        		</c:if>
        		<c:if test="${place.plSource == 'MAPPLACE' && (place.geogKey >= 100000 && place.geogKey < 400000) }">
					<h5><fmt:message key="geobase.sharePlace.mapPlaceRecord"/></h5>
				</c:if>
        		<c:if test="${place.plSource == 'MAPSITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
					<h5><fmt:message key="geobase.sharePlace.mapSite"/></h5>
				</c:if>
				<h7>${place.plType}</h7>
				<c:if test="${place.prefFlag != 'V'}">
					<div id="linked">
						<p><fmt:message key="geobase.sharePlace.linkedTo"/></p>
						<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
							${docInTopics} <fmt:message key="geobase.sharePlace.documentsOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topics"/>
						</c:if>
						<c:if test="${topicsPlace == 1}">
							${docInTopics} <fmt:message key="geobase.sharePlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topic"/>
						</c:if>
						<c:if test="${docInTopics == 1 && topicsPlace != 1}">
							${docInTopics} <fmt:message key="geobase.sharePlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topics"/>
						</c:if>
						<c:if test="${topicsPlace == 0 || topicsPlace == null}">
							<fmt:message key="geobase.sharePlace.zeroDocuments"/>
						</c:if>
						<hr />
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							${senderPlace} <fmt:message key="geobase.sharePlace.sendersAnd"/> ${recipientPlace} <fmt:message key="geobase.sharePlace.recipients"/>
						</c:if>
						<c:if test="${senderPlace == 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							${senderPlace} <fmt:message key="geobase.sharePlace.senderAnd"/> ${recipientPlace} <fmt:message key="geobase.sharePlace.recipients"/>
						</c:if>
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace == 1}">
							${senderPlace} <fmt:message key="geobase.sharePlace.sendersAnd"/> ${recipientPlace} <fmt:message key="geobase.sharePlace.recipient"/>
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
							<fmt:message key="geobase.sharePlace.zeroSendersAnd"/> ${recipientPlace} <fmt:message key="geobase.sharePlace.recipients"/>
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace == 1}">
							<fmt:message key="geobase.sharePlace.zeroSendersAnd"/> ${recipientPlace} <fmt:message key="geobase.sharePlace.recipient"/>
						</c:if>
						<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && (recipientPlace == 0 || recipientPlace == null)}">
							${senderPlace} <fmt:message key="geobase.sharePlace.sendersAndZero"/>
						</c:if>
						<c:if test="${senderPlace == 1 && (recipientPlace == 0 || recipientPlace == null)}">
							${senderPlace} <fmt:message key="geobase.sharePlace.senderAndZero"/>
						</c:if>
						<c:if test="${(senderPlace == 0 || senderPlace == null) && (recipientPlace == 0 || recipientPlace == null)}">
							<fmt:message key="geobase.sharePlace.zeroSenderAndZero"/>
						</c:if>
						<hr />
						<c:if test="${birthPlace != 0}">${birthPlace} <fmt:message key="geobase.sharePlace.births"/></c:if><c:if test="${birthPlace == 0}"><fmt:message key="geobase.sharePlace.zeroBirths"/></c:if> <fmt:message key="geobase.sharePlace.and"/> <c:if test="${activeStartPlace != 0}">${activeStartPlace} <fmt:message key="geobase.sharePlace.activeStarts"/></c:if><c:if test="${activeStartPlace == 0}"><fmt:message key="geobase.sharePlace.zeroActiveStarts"/></c:if>
						<br />
						<c:if test="${deathPlace != 0}">${deathPlace} <fmt:message key="geobase.sharePlace.deaths"/></c:if><c:if test="${deathPlace == 0}"><fmt:message key="geobase.sharePlace.zeroDeaths"/></c:if> <fmt:message key="geobase.sharePlace.and"/> <c:if test="${activeEndPlace != 0}">${activeEndPlace} <fmt:message key="geobase.sharePlace.activeEnds"/></c:if><c:if test="${activeEndPlace == 0}"><fmt:message key="geobase.sharePlace.zeroActiveEnds"/></c:if>
					</div>	
				</c:if>						
				<c:if test="${place.prefFlag == 'V'}">
					<br />
					<c:forEach items="${placeNames}" var="currentName">
						<c:if test="${currentName.prefFlag == 'P'}">
							<c:url var="ShowPrincipalPlaceURL" value="/src/geobase/ShowPlace.do">
								<c:param name="placeAllId"	value="${currentName.placeAllId}"/>
							</c:url>
							<p class="textPrincipalName">'${place.placeName}' <fmt:message key="geobase.sharePlace.isAVariant"/> '${currentName.placeName}'.</p>
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
					<span><fmt:message key="geobase.sharePlace.notAttached"/></span>	
				</c:if>
			</div>
		</div>
		
		<div id="EditDetailsPlaceDiv" class="background">
        	<div class="title">
            	<h5><fmt:message key="geobase.sharePlace.placeDetails"/></h5>
            </div>
		
			<div class="listDetails">
				<div class="row">
					<div class="item"><fmt:message key="geobase.sharePlace.placeID"/></div> 
					<div class="value">${place.placeAllId}</div> 
				</div>
				<div class="row">
					<div class="item"><fmt:message key="geobase.sharePlace.placeName"/></div>
					<div class="value">${place.placeName}</div>
				</div>
				<c:if test="${place.termAccent != place.placeName}">
					<div class="row">
						<div style="color: #6D5C4D;display: table-cell;padding: 3px 0;text-align: left;width: 20%;"><fmt:message key="geobase.sharePlace.withAccents"/></div>
						<div class="value">${place.termAccent}</div>
					</div>
				</c:if>
				<div class="row">
					<div class="item"><fmt:message key="geobase.sharePlace.placeType"/></div>
					<div class="value">${place.plType}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="geobase.sharePlace.placeParent"/></div>
					<div class="value">${place.parentPlace.getPlaceNameFull()}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="geobase.sharePlace.placeNotes"/></div>
					<div class="value">${place.placesMemo}</div>
				</div>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditNamePlaceDiv" class="background">
    	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.nameOr"/></h5>
        </div>
        
        <div class="list">
			<c:forEach items="${placeNames}" var="currentName">
				<div class="row">
					<c:if test="${currentName.prefFlag == 'P'}">
						<div class="item"><fmt:message key="geobase.sharePlace.principal"/></div>
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
						<div class="item"><fmt:message key="geobase.sharePlace.variant"/></div>
						<div class="value">${currentName.placeName}</div>
					</c:if>					
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditSendRecipPlaceDiv"class="background">
    	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.sendersAnd"/> </h5>
        </div>
        
        <div class="list">	
			<div class="row">
				<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1}">
					<div class="value">${senderPlace} <fmt:message key="geobase.sharePlace.senders"/></div>
				</c:if>
				<c:if test="${senderPlace == 1 }">
					<div class="value">${senderPlace} <fmt:message key="geobase.sharePlace.sender"/></div>
				</c:if>
				<c:if test="${senderPlace == 0 || senderPlace == null}">
					<div class="value"><fmt:message key="geobase.sharePlace.zeroSender"/></div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
					<div class="value">${recipientPlace} <fmt:message key="geobase.sharePlace.recipients"/></div>
				</c:if>
				<c:if test="${recipientPlace == 1}">
					<div class="value">${recipientPlace} <fmt:message key="geobase.sharePlace.recipient"/></div>
				</c:if>
				<c:if test="${recipientPlace == 0 || recipientPlace == null}">
					<div class="value"><fmt:message key="geobase.sharePlace.zeroRecipient"/></div>
				</c:if>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditTopicsPlaceDiv" class="background">
    	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.topicsList"/> </h5>
        </div>
        
        <div class="list">	
			<div class="row">
			<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
				<div class="value">${docInTopics} <fmt:message key="geobase.sharePlace.documentsOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topics"/></div>
			</c:if>
			<c:if test="${topicsPlace == 1}">
				<div class="value">${docInTopics} <fmt:message key="geobase.sharePlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topic"/></div>
			</c:if>
			<c:if test="${docInTopics == 1 && topicsPlace != 1}">
				<div class="value">${docInTopics} <fmt:message key="geobase.sharePlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.sharePlace.topics"/></div>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
				<div class="value"><fmt:message key="geobase.sharePlace.zeroDocumentOn"/></div>
			</c:if>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	 <div id="EditBirthDeathPlaceDiv" class="background">
     	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.birthAndDeathPlace"/> </h5>
        </div>
        
        <div class="list">	
			<div class="row">
				<div class="value"><c:if test="${birthPlace != 0}">${birthPlace} <fmt:message key="geobase.sharePlace.birth"/></c:if><c:if test="${birthPlace == 0}"><fmt:message key="geobase.sharePlace.zeroBirth"/></c:if>      <c:if test="${activeStartPlace != 0}">${activeStartPlace} <fmt:message key="geobase.sharePlace.activeStart"/></c:if><c:if test="${activeStartPlace == 0}"><fmt:message key="geobase.sharePlace.zeroActiveStart"/></c:if></div>
			</div>
			<div class="row">
				<div class="value"><c:if test="${deathPlace != 0}">${deathPlace} <fmt:message key="geobase.sharePlace.death"/></c:if><c:if test="${deathPlace == 0}"><fmt:message key="geobase.sharePlace.zeroDeath"/></c:if>      <c:if test="${activeEndPlace != 0}">${activeEndPlace} <fmt:message key="geobase.sharePlace.activeEnd"/></c:if><c:if test="${activeEndPlace == 0}"><fmt:message key="geobase.sharePlace.zeroActiveEnd"/></c:if></div>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditGeoCoorPlaceDiv" class="background">
    	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.geographicCoordinates"/></h5>
        </div>
        
        <div class="list">	
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.latitude"/></div>
				<div class="value">${place.placeGeographicCoordinates.degreeLatitude}&#xb0 ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.longitude"/></div>
				<div class="value">${place.placeGeographicCoordinates.degreeLongitude}&#xb0 ${place.placeGeographicCoordinates.minuteLongitude}' ${place.placeGeographicCoordinates.secondLongitude}'' ${place.placeGeographicCoordinates.directionLongitude}</div>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditExtLinkPlaceDiv" class="background">
    	<div class="title">
        	<h5><fmt:message key="geobase.sharePlace.externalLinks"/></h5>
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
        	<h5><fmt:message key="geobase.sharePlace.hierarchy"/></h5>
        </div>
        
        <div class="list">
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.parent"/></div> 
				<div class="value">${place.parentPlace.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.gparent"/></div>
				<div class="value">${place.gParent}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.ggParent"/></div>
				<div class="value">${place.ggp}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.gptwo"/></div>
				<div class="value">${place.gp2}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.parentTGN"/></div>
				<div class="value">${place.plParentTermId}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.sharePlace.parentGeo"/></div>
				<div class="value">${place.plParentSubjectId}</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			
			var showDialogLoginFirst = $j('<div id="DialogLoginFirst"></div>').dialog({
				resizable: false,
				width: 300,
				height: 150, 
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				open: function(event, ui) { 
            		$j(this).load('${ShowLoginFirstDialogURL}');
           		},
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				title: "LOG IN FIRST"
			});
			
			$j("#moreInfoButton").die();
			$j("#moreInfoButton").live('click', function(e){
				if (!${logged}) {
					showDialogLoginFirst.dialog('open');
					return false;
				}
				e.preventDefault();
				if (window.opener != null) {
					if (window.opener.$j("#body_left").length == 1) {
						window.opener.$j("#body_left").load($j(this).attr('href'));
						window.opener.alert('<fmt:message key="home.showRecordAlertMessage"/>');
					} else {
						// Parent window is not yet opened
						window.open("${HomeURL}","_self");
					}
				} else {
					// If there isn't BIA window
					window.open("${HomeURL}","_self");
				}
				return false;
			});
		});
	</script>