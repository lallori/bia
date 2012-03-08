<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPersonURL" value="/de/peoplebase/EditDetailsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="ShowNamesPersonURL" value="/src/peoplebase/ShowNamesPerson.do">
			<c:param name="personId" value="${person.personId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="CompareBirthURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.bornPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareDeathURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.deathPlace.placeAllId}" />
	</c:url>
	
	<c:url var="ShowDocumentsPersonURL" value="/de/peoplebase/ShowDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowSenderDocumentsPersonURL" value="/de/peoplebase/ShowSenderDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowRecipientDocumentsPersonURL" value="/de/peoplebase/ShowRecipientDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowReferringToDocumentsPersonURL" value="/de/peoplebase/ShowReferringToDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<div id="personDiv">
		<div id="personTitle">
			<h3>${person.mapNameLf}</h3>
			<c:forEach items="${person.poLink}" var="currentPoLink">
				<c:if test="${currentPoLink.preferredRole}">
					<h4>${currentPoLink.titleOccList.titleOcc}</h4>
				</c:if>
			</c:forEach>
			
			<c:if test="${person.activeStart != null}">
				<h7>Active Start: ${person.activeStart}</h7>
			</c:if>
			<c:if test="${person.activeStart == null}">
				<h7>Birth: ${person.bornYear} ${person.bornMonth} ${person.bornDay}</h7>
			</c:if>
				
			<c:if test="${person.activeEnd != null}">
				<h7>Active End: ${person.activeEnd}</h7>
			</c:if>
			<c:if test="${person.activeEnd == null}">
				<h7>Death: ${person.deathYear} ${person.deathMonth} ${person.deathDay}</h7>
			</c:if>
			
			<c:if test="${docsRelated != 0 && docsRelated != 1}">
				<p style="margin-left:12px">Documents related to this person entry: <span class="num_docs">${docsRelated}</span> <br />
				(<a href="${ShowDocumentsPersonURL}" class="all_docs" title="Click here to view all documents related to this person "><u>View All</u></a>,
				<a href="${ShowSenderDocumentsPersonURL}" class="sender_docs" title="Click here to view the letters SENT BY this person" style="margin-left:8px"><u>Sender</u></a>,
				<a href="${ShowRecipientDocumentsPersonURL}" class="recipient_docs" title="Click here to view the letters RECEIVED BY this person" style="margin-left:8px"><u>Recipient</u></a>,
				<a href="${ShowReferringToDocumentsPersonURL}" class="referred_docs" title="Click here to view the documents in which this person is mentioned" style="margin-left:8px"><u>Referring To</u></a>)</p>
			</c:if>
			<c:if test="${docsRelated == 0}">
				<p style="margin-left:12px">Documents related to this person entry: <a class="num_docs" title="No documents indexed to this person entry">0</a></p>
			</c:if>
			<c:if test="${docsRelated == 1}">
				<p style="margin-left:12px">Document related to this person entry: <span class="num_docs">${docsRelated}</span> <br />
				(<a href="${ShowDocumentsPersonURL}" class="all_docs" title="Click here to see this documentview all documents related"><u>View it</u></a></p>
			</c:if>
		</div>
		
		<div id="EditDetailsPersonDiv" class="background">
			<div class="title">
				<h5>PERSON DETAILS</h5>
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
					<a id="EditDetailsPerson" href="${EditDetailsPersonURL}" class="editButton"></a><span id="loading"/>
				</security:authorize>
			</div>
						
			<div id="EditPortraitPersonDiv">
				<div id="imgPortraitPerson"></div>
				<p style="text-align:center"><b>Portrait</b></p>
			</div>
			
			<div class="listDetails">
				<div class="row">
					<div class="item">Gender</div> <div class="value">${person.gender}</div>
				</div>
				<div class="row">
					<div class="item">Date of Birth</div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
				</div>
				<div class="row">
					<div class="item">Birth Place</div>
					<c:if test="${person.bornPlace.placeAllId != 53384 && person.bornPlace.placeAllId != 55627 && person.bornPlace.placeAllId != 54332}">
						<div class="value"><a class="linkSearch" href="${CompareBirthURL}">${person.bornPlace.placeNameFull}</a></div>
					</c:if>
					<c:if test="${person.bornPlace.placeAllId == 53384 || person.bornPlace.placeAllId == 55627 || person.bornPlace.placeAllId == 54332 }">
						<div class="value">${person.bornPlace.placeNameFull}</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item">Active Start</div> <div class="value">${person.activeStart}</div>
				</div>
				<div class="row">
					<div class="item">Date of Death</div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
				</div>
				<div class="row">
					<div class="item">Death Place</div>
					<c:if test="${person.deathPlace.placeAllId != 53384 && person.deathPlace.placeAllId != 55627 && person.deathPlace.placeAllId != 54332}">
						<div class="value"><a class="linkSearch" href="${CompareDeathURL}">${person.deathPlace.placeNameFull}</a></div>
					</c:if>
					<c:if test="${person.deathPlace.placeAllId == 53384 || person.deathPlace.placeAllId == 55627 || person.deathPlace.placeAllId == 54332 }">
						<div class="value">${person.deathPlace.placeNameFull}</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item">Active End</div> <div class="value">${person.activeEnd}</div>
				</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {			
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 
	        
	        <c:choose> 
				<c:when test="${person.personId != 0}"> 
	        		$j("#EditNamesPersonDiv").load("${ShowNamesPersonURL}");
	        	</c:when>
	        </c:choose>

			$j("#EditDetailsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsPersonDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j(".linkSearch").click(function() {
				var tabName = $j(this).text();
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
			
			$j(".all_docs").click(function(){
				var tabName = "All Docs ${person.mapNameLf}";
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
			
			$j(".sender_docs").click(function(){
				var tabName = "Sender Docs ${person.mapNameLf}";
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
			
			$j(".recipient_docs").click(function(){
				var tabName = "Recipient Docs ${person.mapNameLf}";
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
			
			$j(".referred_docs").click(function(){
				var tabName = "Docs Referring To ${person.mapNameLf}";
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
</security:authorize>