<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />	
</c:url>

<c:url var="ShowSenderDocumentsPlaceURL" value="/src/geobase/ShowSenderDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowRecipientDocumentsPlaceURL" value="/src/geobase/ShowRecipientDocumentsPlace.do">
	<c:param name="placeAllId" value="${place.placeAllId}" />
</c:url>

<c:url var="ShowTopicsPlaceURL" value="/src/geobase/ShowTopicsPlace.do">
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


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowPlaceURL}" id="editLink${place.placeAllId}" class="showOrEditCompare button_large"><fmt:message key="geobase.comparePlace.showOrEdit"/></a>
	</div>
</security:authorize>
<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowPlaceURL}" id="editLink${place.placeAllId}" class="showCompare button_medium"><fmt:message key="geobase.comparePlace.showThisPlace"/></a>
	</div>
</security:authorize>

<div id="geoCompareDiv">
			<c:if test="${place.placeAllId != 0}">
				<div id="geoTitle">
					<div id="text">
						<h3>${place.placeName}</h3>
						<h4>${place.parentPlace.placeNameFull}</h4>
						<c:if test="${place.plSource == 'TGN' && place.geogKey >= 1000000}">
	            		<h5><fmt:message key="geobase.comparePlace.tngPlace"/></h5>
	        			</c:if>
	        			<c:if test="${place.geogKey >= 1000000  && place.plSource == 'MAPPLACE'}">
	        			<h5><fmt:message key="geobase.comparePlace.tngPlaceUpdated"/></h5>
	        			</c:if>
	        			<c:if test="${place.plSource == 'MAPPLACE' && (place.geogKey >= 100000 && place.geogKey < 400000) }">
						<h5><fmt:message key="geobase.comparePlace.mapPlace"/></h5>
						</c:if>
	        			<c:if test="${place.plSource == 'MAPSITE' || (place.geogKey >= 400000 && place.geogKey < 1000000) }">
						<h5><fmt:message key="geobase.comparePlace.mapSite"/></h5>
						</c:if>
						<h7>${place.plType}</h7>
						<c:if test="${place.plSource == 'TGN' || place.geogKey >= 1000000}">
							<p style="margin:20px 0 5px 10px"><fmt:message key="geobase.comparePlace.toCompareThisPlaceToGetty"/> <a class="link" href="http://www.getty.edu/research/tools/vocabularies/tgn/index.html" target="_blank"><fmt:message key="geobase.comparePlace.clickHere"/></a></p>		
						</c:if>
						<c:if test="${place.prefFlag == 'V'}">
							<br />
							<div style="margin-left:8px">
									<c:forEach items="${placeNames}" var="currentName">
										<c:if test="${currentName.prefFlag == 'P'}">
											<p style="margin:0 0 5px 10px"><font color="red">'${place.placeName}' <fmt:message key="geobase.comparePlace.isAVariantName"/> '${currentName.placeName}'<fmt:message key="geobase.comparePlace.clickOn"/> ${currentName.placeName} <fmt:message key="geobase.comparePlace.andAllTheValue"/></font></p>
										</c:if>
									</c:forEach>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
	
	<div class="background" id="EditDetailsPlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.comparePlace.placeDetails"/></h5>
			
		</div>
	
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.placeId"/></div> 
				<div class="value">${place.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.placeName"/></div>
				<div class="value">${place.placeName}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.placeType"/></div>
				<div class="value">${place.plType}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.placeParent"/></div>
				<div class="value">${place.parentPlace.getPlaceNameFull()}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.placeNote"/></div>
				<div class="value">${place.placesMemo}</div>
			</div>
		</div>

	
	</div>
	</div>
	
	<br />

<div class="background" id="EditNamePlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.comparePlace.nameOrVar"/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${placeNames}" var="currentName">
				<div class="row">
					<c:if test="${currentName.prefFlag == 'P'}">
						<div class="item"><fmt:message key="geobase.comparePlace.principal"/></div>
						<div class="value">${currentName.placeName}</div>
					</c:if>
					<c:if test="${currentName.prefFlag == 'V'}">
						<div class="item"><fmt:message key="geobase.comparePlace.variant"/></div>
						<div class="value">${currentName.placeName}</div>
					</c:if>					
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div class="background" id="EditSendRecipPlaceDiv">
	<div class="title">
		<h5><fmt:message key="geobase.comparePlace.sendersAndRecipients"/></h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<c:if test="${senderPlace != null && senderPlace != 0}">
				<%-- <div class="value">${senderPlace} Senders</div> --%>
				<div class="value"><a id="linkSearch" class="senderCompare" href="${ShowSenderDocumentsPlaceURL}">${senderPlace} <fmt:message key="geobase.comparePlace.senders"/></a></div>
			</c:if>
			<c:if test="${senderPlace == 0 || senderPlace == null}">
				<div class="value"><fmt:message key="geobase.comparePlace.zeroSender"/></div>
			</c:if>
		</div>
		<div class="row">
			<c:if test="${recipientPlace != null && recipientPlace != 0}">
				<div class="value"><a id="linkSearch" class="recipientCompare" href="${ShowRecipientDocumentsPlaceURL}">${recipientPlace} <fmt:message key="geobase.comparePlace.recipients"/></a></div>
			</c:if>
			<c:if test="${recipientPlace == 0 || recipientPlace == null}">
				<div class="value"><fmt:message key="geobase.comparePlace.zeroRecipient"/></div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditTopicsPlaceDiv">
	<div class="title">
		<h5><fmt:message key="geobase.comparePlace.topicsList"/></h5>
	</div>
	<div class="list">	
		<div class="row">
			<c:if test="${topicsPlace != null && topicsPlace != 0}">
				<div class="value"><a id="linkSearch" class="topicsCompare" href="${ShowTopicsPlaceURL}">${docInTopics} <fmt:message key="geobase.comparePlace.documentsOn"/> ${topicsPlace} <fmt:message key="geobase.comparePlace.topics"/></a></div>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
				<div class="value"><fmt:message key="geobase.comparePlace.zeroDocumentOnZeroTopic"/></div>
			</c:if>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditBirthDeathPlaceDiv">
	<div class="title">
		<h5><fmt:message key="geobase.comparePlace.birthAndDeathPlace"/></h5>
	</div>
	
	<div class="list">	
		<div class="row">
			<div class="value"><c:if test="${birthPlace != 0}"><a id="linkSearch" class="birthCompare" href="${ShowBirthPeoplePlaceURL}">${birthPlace} <fmt:message key="geobase.comparePlace.birth"/></a></c:if><c:if test="${birthPlace == 0}"><fmt:message key="geobase.comparePlace.zeroBirth"/></c:if>      <c:if test="${activeStartPlace != 0}"><a id="linkSearch" class="activeStartCompare" href="${ShowActiveStartPeoplePlaceURL}">${activeStartPlace} <fmt:message key="geobase.comparePlace.activeStart"/></a></c:if><c:if test="${activeStartPlace == 0}"><fmt:message key="geobase.comparePlace.zeroActiveStart"/></c:if></div>
		</div>
		<div class="row">
			<div class="value"><c:if test="${deathPlace != 0}"><a id="linkSearch" class="deathCompare" href="${ShowDeathPeoplePlaceURL}">${deathPlace} <fmt:message key="geobase.comparePlace.death"/></a></c:if><c:if test="${deathPlace == 0}"><fmt:message key="geobase.comparePlace.zeroDeath"/></c:if>      <c:if test="${activeEndPlace != 0}"><a id="linkSearch" class="activeEndCompare" href="${ShowActiveEndPeoplePlaceURL}">${activeEndPlace} <fmt:message key="geobase.comparePlace.activeEnd"/></a></c:if><c:if test="${activeEndPlace == 0}"><fmt:message key="geobase.comparePlace.zeroActiveEnd"/></c:if></div>
		</div>
	</div>
</div>

<br />
<br />

<div class="background" id="EditGeoCoorPlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.comparePlace.geographicCoordinates"/></h5>
	 	</div>
		<div class="list">	
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.latitude"/></div>
				<div class="value">${place.placeGeographicCoordinates.degreeLatitude} ${place.placeGeographicCoordinates.minuteLatitude} ${place.placeGeographicCoordinates.secondLatitude} ${place.placeGeographicCoordinates.directionLatitude}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.longitude"/></div>
				<div class="value">${place.placeGeographicCoordinates.degreeLongitude} ${place.placeGeographicCoordinates.minuteLongitude} ${place.placeGeographicCoordinates.secondLongitude} ${place.placeGeographicCoordinates.directionLongitude}</div>
			</div>
		</div>
	</div>
	
	<br />
	
	<div class="background" id="EditExtLinkPlaceDiv">
		<div class="title">
			<h5><fmt:message key="geobase.comparePlace.externalLinks"/></h5>
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
			<h5><fmt:message key="geobase.comparePlace.hierarchy"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.parent"/></div> 
				<div class="value">${place.parentPlace.placeAllId}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.gParent"/></div>
				<div class="value">${place.gParent}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.ggParent"/></div>
				<div class="value">${place.ggp}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.gptwo"/></div>
				<div class="value">${place.gp2}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.parentTGN"/></div>
				<div class="value">${place.plParentTermId}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="geobase.comparePlace.parentGeokey"/></div>
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
			
			$j(".recipientCompare").click(function(){
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
			
			$j(".topicsCompare").click(function(){
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
			
			$j(".birthCompare").click(function(){
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
			
			$j(".deathCompare").click(function(){
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
			
			$j(".activeStartCompare").click(function(){
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
			
			$j(".activeEndCompare").click(function(){
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
		});
	</script>
