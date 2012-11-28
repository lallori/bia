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
	
		<c:url var="EditGeographicCoordinatesPlaceURL" value="/de/geobase/EditGeographicCoordinatesPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowTopicsPlaceURL" value="/src/geobase/ShowTopicsPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowSenderDocumentsPlaceURL" value="/src/geobase/ShowSenderDocumentsPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>

		<c:url var="ShowRecipientDocumentsPlaceURL" value="/src/geobase/ShowRecipientDocumentsPlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowBirthPeoplePlaceURL" value="/src/geobase/ShowBirthPeoplePlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowDeathPeoplePlaceURL" value="/src/geobase/ShowDeathPeoplePlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowActiveStartPeoplePlaceURL" value="/src/geobase/ShowActiveStartPeoplePlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
		
		<c:url var="ShowActiveEndPeoplePlaceURL" value="/src/geobase/ShowActiveEndPeoplePlace.do">
			<c:param name="placeAllId" value="${place.placeAllId}" />
		</c:url>
			<%-- Creating a New Place Record --%>
			<c:if test="${place.placeAllId == 0}">
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
			</c:if>
			<%-- Editing Place Records --%>
			<div id="geoDiv">
			<c:if test="${place.placeAllId != 0}">
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
						<c:if test="${place.plSource == 'TGN' || place.geogKey >= 1000000}">
						<p style="margin:20px 0 5px 10px">To compare this place data to the Getty TGN source <a class="link" href="http://www.getty.edu/research/tools/vocabularies/tgn/index.html" target="_blank">click here</a></p>		
						</c:if>
						<c:if test="${place.prefFlag != 'V'}">
							<div id="linked">
								<p>Linked to this place entry:</p>
								<c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}">
									<a id="linkSearch" class="topics placeText_left" href="${ShowTopicsPlaceURL}">${docInTopics} Documents on ${topicsPlace} Topics</a>
								</c:if>
								<c:if test="${topicsPlace == 1}">
									<a class="topics placeText_left" href="${ShowTopicsPlaceURL}">${docInTopics} Document on ${topicsPlace} Topic</a>
								</c:if>
								<c:if test="${docInTopics == 1 && topicsPlace != 1}">
									<a class="topics placeText_left" href="${ShowTopicsPlaceURL}">${docInTopics} Document on ${topicsPlace} Topics</a>
								</c:if>
								<c:if test="${topicsPlace == 0 || topicsPlace == null}">
									0 Documents on 0 Topics
								</c:if>
								<hr />
								<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
									<a class="sender placeText_left" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Senders</a> and <a id="linkSearch" class="recipient placeText" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipients</a>
								</c:if>
								<c:if test="${senderPlace == 1 && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
									<a class="sender placeText_left" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Sender</a> and <a id="linkSearch" class="recipient placeText" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipients</a>
								</c:if>
								<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && recipientPlace == 1}">
									<a class="sender placeText_left" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Senders</a> and <a id="linkSearch" class="recipient placeText" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipient</a>
								</c:if>
								<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
									0 Senders and <a id="linkSearch" class="recipient placeText" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipients</a>
								</c:if>
								<c:if test="${(senderPlace == 0 || senderPlace == null) && recipientPlace == 1}">
									0 Senders and <a id="linkSearch" class="recipient placeText" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipient</a>
								</c:if>
								<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1 && (recipientPlace == 0 || recipientPlace == null)}">
									<a class="sender placeText_left" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Senders</a> and 0 Recipient
								</c:if>
								<c:if test="${senderPlace == 1 && (recipientPlace == 0 || recipientPlace == null)}">
									<a class="sender placeText_left" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Sender</a> and 0 Recipient
								</c:if>
								<c:if test="${(senderPlace == 0 || senderPlace == null) && (recipientPlace == 0 || recipientPlace == null)}">
									0 Sender and 0 Recipient
								</c:if>
								<hr />
								<c:if test="${birthPlace != 0}"><a class="birth placeText_left" href="${ShowBirthPeoplePlaceURL}">${birthPlace} Births</a></c:if><c:if test="${birthPlace == 0}">0 Births</c:if> and <c:if test="${activeStartPlace != 0}"><a class="activeStart placeText" href="${ShowActiveStartPeoplePlaceURL}">${activeStartPlace} Active Starts</a></c:if><c:if test="${activeStartPlace == 0}">0 Active Starts</c:if>
								<br />
								<c:if test="${deathPlace != 0}"><a class="death placeText_left" href="${ShowDeathPeoplePlaceURL}">${deathPlace} Deaths</a></c:if><c:if test="${deathPlace == 0}">0 Deaths</c:if> and <c:if test="${activeEndPlace != 0}"><a class="activeEnd placeText" href="${ShowActiveEndPeoplePlaceURL}">${activeEndPlace} Active Ends</a></c:if><c:if test="${activeEndPlace == 0}">0 Active Ends</c:if>
							</div>	
						</c:if>						
						<c:if test="${place.prefFlag == 'V'}">
							<br />
							<div style="margin-left:8px">
									<c:forEach items="${placeNames}" var="currentName">
										<c:if test="${currentName.prefFlag == 'P'}">
											<c:url var="ShowPrincipalPlaceURL" value="/src/geobase/ShowPlace.do">
												<c:param name="placeAllId"	value="${currentName.placeAllId}"/>
											</c:url>
											<p style="margin:0"><font color="red">'${place.placeName}' is a Variant Name for '${currentName.placeName}'. Click here to visualize <a class="linkPlace" href="${ShowPrincipalPlaceURL}">${currentName.placeName}</a> and all the values and fields connected to it.</font></p>
										</c:if>
									</c:forEach>
							</div>
						</c:if>
					</div>
					<div id="placeImageDiv">
						<c:if test="${linkGoogleMaps != null}">
							<a href="${linkGoogleMaps}" target="_blank" title="Show on Google Maps"><img src="<c:url value="/images/1024/img_googleMap.jpg"/>" alt="Place" class="shadow"></a>
						</c:if>
						<c:if test="${linkGoogleMaps == null }">
							<span>Not attached to Google Maps</span>
							<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
							<c:if test="${place.prefFlag == 'P'}">
								<a class="PlaceMap" href="${EditGeographicCoordinatesPlaceURL}">Assign Geo Coordinates</a>
							</c:if>
							</security:authorize>	
						</c:if>
					</div>
					
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


	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditDetailsPlace").css('visibility', 'visible');
			$j("#EditNamePlace").css('visibility', 'visible');
	        $j("#EditGeoCoorPlace").css('visibility', 'visible'); 
			$j("#EditExtLinkPlace").css('visibility', 'visible');
			
			$j(".PlaceMap").click(function(){
				$j("#EditGeoCoorPlaceDiv").load($j(this).attr("href"));
				$j.scrollTo("#EditGeoCoorPlaceDiv");
				return false;
			});

// 			$j("#EditDetailsPlace").click(function(){
// 				$j(this).next().css('visibility', 'visible');
// 				$j("#EditDetailsPlaceDiv").load($j(this).attr("href"));
// 				return false;
// 			});

			$j(".topics").click(function(){
				var tabName = "Topics ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".birth").click(function(){
				var tabName = "Birth ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".death").click(function(){
				var tabName = "Death ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".activeStart").click(function(){
				var tabName = "Active Start ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".activeEnd").click(function(){
				var tabName = "Active End ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".sender").click(function(){
				var tabName = "Senders ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".recipient").click(function(){
				var tabName = "Recipients ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>