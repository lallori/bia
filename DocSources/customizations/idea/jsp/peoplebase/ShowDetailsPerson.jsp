<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditDetailsPersonURL" value="/de/peoplebase/EditDetailsPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		<c:url var="UploadPortraitWindowURL" value="/de/peoplebase/ShowUploadPortraitPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
	</security:authorize>
	
	<c:url var="CompareBirthURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.bornPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareDeathURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.deathPlace.placeAllId}" />
	</c:url>
	
	<c:url var="ShowDocumentsPersonURL" value="/src/peoplebase/ShowDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowNamesPersonURL" value="/src/peoplebase/ShowNamesPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>

	<c:url var="ShowSenderDocumentsPersonURL" value="/src/peoplebase/ShowSenderDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowRecipientDocumentsPersonURL" value="/src/peoplebase/ShowRecipientDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="ShowReferringToDocumentsPersonURL" value="/src/peoplebase/ShowReferringToDocumentsPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<div id="personDiv">
		<c:if test="${person.personId == 0}">
			<h2 class="addNew"><fmt:message key="peoplebase.showDetailsPerson.addNewPersonRecord"/></h2>
		</c:if>
		<c:if test="${person.personId != 0}">
		<div id="personTitle">
			<div id="text">		
				<h3>${person.mapNameLf}</h3>
				<c:forEach items="${person.poLink}" var="currentPoLink">
					<c:if test="${currentPoLink.preferredRole}">
						<h4>${currentPoLink.titleOccList.titleOcc}</h4>
					</c:if>
				</c:forEach>			
				<c:if test="${person.activeStart != null}">
					<h7><fmt:message key="peoplebase.showDetailsPerson.activeStart0"/> <span class="h7"> ${person.activeStart}</span></h7>
				</c:if>
				<c:if test="${person.activeStart == null}">
					<h7><fmt:message key="peoplebase.showDetailsPerson.birth"/> <span class="h7">${person.bornYear} ${person.bornMonth} ${person.bornDay}</span></h7>
				</c:if>		
				<c:if test="${person.activeEnd != null}">
					<h7><fmt:message key="peoplebase.showDetailsPerson.activeEnd0"/><span class="h7"> ${person.activeEnd}</span></h7>
				</c:if>
				<c:if test="${person.activeEnd == null}">
					<h7><fmt:message key="peoplebase.showDetailsPerson.death"/> <span class="h7">${person.deathYear} ${person.deathMonth} ${person.deathDay}</span></h7>
				</c:if>
				<%-- Documents Related Section --%>
				<c:if test="${docsRelated != 0 && docsRelated != 1}">
					<p><fmt:message key="peoplebase.showDetailsPerson.documentsRelated"/> <span class="num_docs">${docsRelated}</span>
					<div style="margin:10px 0 0 10px">
						(<a href="${ShowDocumentsPersonURL}" class="all_docs" title="View all documents related to this person "><fmt:message key="peoplebase.showDetailsPerson.viewAll"/></a> | 
						<c:if test="${senderDocsRelated != 0}">
							<a href="${ShowSenderDocumentsPersonURL}" class="sender_docs" title="View the letters SENT BY this person"><fmt:message key="peoplebase.showDetailsPerson.sender"/></a> <span class="num_docs">${senderDocsRelated}</span> |
						</c:if>
						<c:if test="${senderDocsRelated == 0}">
							<p class="no_docs"><fmt:message key="peoplebase.showDetailsPerson.sender"/></p> |
						</c:if>
						<c:if test="${recipientDocsRelated != 0}">
							<a href="${ShowRecipientDocumentsPersonURL}" class="recipient_docs" title="View the letters RECEIVED BY this person"><fmt:message key="peoplebase.showDetailsPerson.recipient"/></a> <span class="num_docs">${recipientDocsRelated}</span> |
						</c:if>
						<c:if test="${recipientDocsRelated == 0}">
							<p class="no_docs"><fmt:message key="peoplebase.showDetailsPerson.recipient"/></p> |
						</c:if>
						<c:if test="${referringDocsRelated != 0}">
							<a href="${ShowReferringToDocumentsPersonURL}" class="referred_docs" title="View the documents in which this person is mentioned"><fmt:message key="peoplebase.showDetailsPerson.referringTo"/></a> <span class="num_docs">${referringDocsRelated}</span>)
						</c:if>
						<c:if test="${referringDocsRelated == 0}">
							<p class="no_docs"><fmt:message key="peoplebase.showDetailsPerson.referringTo"/></p>)
						</c:if>
					</div>
				</c:if>
				<c:if test="${docsRelated == 0}">	
					<p><fmt:message key="peoplebase.showDetailsPerson.documentsRelated"/> <span class="num_docs" title="No documents indexed to this person entry">${docsRelated}</span>
				</c:if>
				<c:if test="${docsRelated == 1}">
					<p><fmt:message key="peoplebase.showDetailsPerson.documentsRelated"/> <span class="num_docs">${docsRelated}</span>
					<div style="margin:10px 0 0 10px;">
						(<a href="${ShowDocumentsPersonURL}" class="all_docs" title="View all the documents related to this person "><fmt:message key="peoplebase.showDetailsPerson.viewIt"/></a>)
					</div>	
				</c:if>
			</div>
			<%-- <div id="EditPortraitPersonDiv">
				<c:url var="ShowPortraitPersonURL" value="/src/peoplebase/ShowPortraitPerson.do">
					<c:param name="personId" value="${person.personId}" />
					<c:param name="time" value="${time}" />
				</c:url>
				<div id="imgPortraitPerson">
					<c:if test="${person.portraitAuthor != null && person.portraitSubject != null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitAuthor} - ${person.portraitSubject}"/>
					</c:if>
					<c:if test="${person.portraitAuthor != null && person.portraitSubject == null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitAuthor}"/>
					</c:if>
					<c:if test="${person.portraitAuthor == null && person.portraitSubject != null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitSubject}"/>
					</c:if>
					<c:if test="${person.portraitAuthor == null && person.portraitSubject == null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145"/>
					</c:if>
				</div>
				<br>				
<!-- 				<p style="text-align:center"><b>Portrait</b></p> -->
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, COMMUNITY_USERS">
				<c:if test="${person.personId != 0}">
					<a id="uploadPortrait" class="button_medium" href="${UploadPortraitWindowURL}"><fmt:message key="peoplebase.showDetailsPerson.uploadPortrait"/></a>
				</c:if>
				</security:authorize>
			</div> --%>
			
		</c:if>
		</div>		
		
		<div id="EditDetailsPersonDiv" class="background">
			<div class="title">
				<h5><fmt:message key="peoplebase.showDetailsPerson.personDetails"/></h5>
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
					<a id="EditDetailsPerson" href="${EditDetailsPersonURL}" class="editButton"></a><span id="loading"/>
				</security:authorize>
			</div>
			
			<div class="listDetails">			
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.name"/></div> <div class="value">${person.mapNameLf}</div>
				</div>
				<br>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.gender"/></div> <div class="value">${person.gender}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.dOfB"/></div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.birthPlace"/></div>
					<c:if test="${person.bornPlace.placeAllId != 53384 && person.bornPlace.placeAllId != 55627 && person.bornPlace.placeAllId != 54332}">
						<div class="value"><a class="linkSearch" href="${CompareBirthURL}">${person.bornPlace.placeNameFull}</a></div>
					</c:if>
					<c:if test="${person.bornPlace.placeAllId == 53384 || person.bornPlace.placeAllId == 55627 || person.bornPlace.placeAllId == 54332 }">
						<div class="value">${person.bornPlace.placeNameFull}</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.activeStart1"/></div> <div class="value">${person.activeStart}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.dOfD"/></div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.deathPlace"/></div>
					<c:if test="${person.deathPlace.placeAllId != 53384 && person.deathPlace.placeAllId != 55627 && person.deathPlace.placeAllId != 54332}">
						<div class="value"><a class="linkSearch" href="${CompareDeathURL}">${person.deathPlace.placeNameFull}</a></div>
					</c:if>
					<c:if test="${person.deathPlace.placeAllId == 53384 || person.deathPlace.placeAllId == 55627 || person.deathPlace.placeAllId == 54332 }">
						<div class="value">${person.deathPlace.placeNameFull}</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="peoplebase.showDetailsPerson.activeEnd1"/></div> <div class="value">${person.activeEnd}</div>
				</div>
			</div>
		</div>
	</div>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {			
			$j("#EditNamesPerson").css('visibility', 'visible');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'visible'); 
			$j("#EditParentsPerson").css('visibility', 'visible');
			$j("#EditChildrenPerson").css('visibility', 'visible');
			$j("#EditSpousesPerson").css('visibility', 'visible');
	        $j("#EditResearchNotesPerson").css('visibility', 'visible'); 
	        
	        var $uploadPortraitWindow = $j('<div id="uploadPortraitWindow" title="UPLOAD PORTRAIT" style="display:none"></div>')
			.dialog({                                                                                                                                                                   
				resizable: false,
				width: 450,
				height: 160, 
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				open: function(event, ui) { 
					$j(this).load($j("#uploadPortrait").attr('href'));
				}
			});                 
	        
	        $j('#uploadPortrait').click(function(){
	        	$j("#uploadPortraitWindow").dialog("option", "width", 450);
	 			$j("#uploadPortraitWindow").dialog("option", "height", 160);
	 			
				$j('#uploadPortraitWindow').dialog('open');
				return false;
			});
	        
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
			
			$j(".all_docs").click(function(){
				var tabName = "All Docs ${person.mapNameLf}";
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
			
			$j(".sender_docs").click(function(){
				var tabName = "Sender Docs ${person.mapNameLf}";
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
			
			$j(".recipient_docs").click(function(){
				var tabName = "Recipient Docs ${person.mapNameLf}";
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
			
			$j(".referred_docs").click(function(){
				var tabName = "Docs Referring To ${person.mapNameLf}";
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
</security:authorize>
<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
<script type="text/javascript">
		$j(document).ready(function() {
			
			$j(".all_docs").click(function(){
				var tabName = "All Docs ${person.mapNameLf}";
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
			
			$j(".sender_docs").click(function(){
				var tabName = "Sender Docs ${person.mapNameLf}";
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
			
			$j(".recipient_docs").click(function(){
				var tabName = "Recipient Docs ${person.mapNameLf}";
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
			
			$j(".referred_docs").click(function(){
				var tabName = "Docs Referring To ${person.mapNameLf}";
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
</security:authorize>
