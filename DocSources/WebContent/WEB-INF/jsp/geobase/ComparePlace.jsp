<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />	
</c:url>

<c:url var="ShowSenderDocumentsPlaceURL" value="/de/geobase/ShowSenderDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowRecipientDocumentsPlaceURL" value="/de/geobase/ShowRecipientDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowTopicsPlaceURL" value="/de/geobase/ShowTopicsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowBirthPeoplePlaceURL" value="/de/geobase/ShowBirthPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowDeathPeoplePlaceURL" value="/de/geobase/ShowDeathPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveStartPeoplePlaceURL" value="/de/geobase/ShowActiveStartPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowActiveEndPeoplePlaceURL" value="/de/geobase/ShowActiveEndPeoplePlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div>
		<a href="${ShowPlaceURL}" id="editLink${place.placeAllId}" class="showOrEditCompare">Show or Edit this Place</a>
	</div>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_COMMUNITY_USERS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_GUESTS">
	<div>
		<a href="${ShowPlaceURL}" id="editLink${place.placeAllId}" class="showCompare">Show this Place</a>
	</div>
</security:authorize>

<div id="geoCompareDiv">
			<c:if test="${place.placeAllId != 0}">
				<div id="geoTitle">
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
				</div>
			</c:if>
	
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
			<c:if test="${senderPlace != null && senderPlace != 0}">
				<%-- <div class="value">${senderPlace} Senders</div> --%>
				<div class="value"><a id="linkSearch" class="senderCompare" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} Senders</a></div>
			</c:if>
			<c:if test="${senderPlace == 0 || senderPlace == null}">
				<div class="value">0 Sender</div>
			</c:if>
		</div>
		<div class="row">
			<c:if test="${recipientPlace != null && recipientPlace != 0}">
				<div class="value"><a id="linkSearch" class="recipientCompare" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} Recipients</a></div>
			</c:if>
			<c:if test="${recipientPlace == 0 || recipientPlace == null}">
				<div class="value">0 Recipient</div>
			</c:if>
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
			<c:if test="${topicsPlace != null && topicsPlace != 0}">
				<div class="value"><a id="linkSearch" class="topicsCompare" href="${ShowTopicsPlaceURL}">${docInTopics} Documents on ${topicsPlace} Topics</a></div>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
				<div class="value">0 Document on 0 Topic</div>
			</c:if>
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
			<div class="value"><c:if test="${birthPlace != 0}"><a id="linkSearch" class="birthCompare" href="${ShowBirthPeoplePlaceURL}">${birthPlace} Birth</a></c:if><c:if test="${birthPlace == 0}">0 Birth</c:if>      <c:if test="${activeStartPlace != 0}"><a id="linkSearch" class="activeStartCompare" href="${ShowActiveStartPeoplePlaceURL}">${activeStartPlace} Active Start</a></c:if><c:if test="${activeStartPlace == 0}">0 Active Start</c:if></div>
		</div>
		<div class="row">
			<div class="value"><c:if test="${deathPlace != 0}"><a id="linkSearch" class="deathCompare" href="${ShowDeathPeoplePlaceURL}">${deathPlace} Death</a></c:if><c:if test="${deathPlace == 0}">0 Death</c:if>      <c:if test="${activeEndPlace != 0}"><a id="linkSearch" class="activeEndCompare" href="${ShowActiveEndPeoplePlaceURL}">${activeEndPlace} Active End</a></c:if><c:if test="${activeEndPlace == 0}">0 Active End</c:if></div>
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
	
	<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${place.placeAllId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
				return false;
			});
			
			$j(".senderCompare").click(function(){
				var tabName = "Senders ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".recipientCompare").click(function(){
				var tabName = "Recipients ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".topicsCompare").click(function(){
				var tabName = "Topics ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".birthCompare").click(function(){
				var tabName = "Birth ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".deathCompare").click(function(){
				var tabName = "Death ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".activeStartCompare").click(function(){
				var tabName = "Active Start ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".activeEndCompare").click(function(){
				var tabName = "Active End ${place.placeName}";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>
