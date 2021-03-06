<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>

	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	
	<c:url var="CompareBirthURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.bornPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareDeathURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${person.deathPlace.placeAllId}" />
	</c:url>
	
	<c:url var="ShowDocumentsPersonURL" value="/src/peoplebase/ShowDocumentsPerson.do">
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
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<div>
			<a href="${ShowPersonURL}" id="editLink${person.personId}" class="showOrEditCompare button_large">Show or Edit this Person</a>
		</div>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<div>
			<a href="${ShowPersonURL}" id="editLink${person.personId}" class="showCompare button_medium">Show this Person</a>
		</div>
	</security:authorize>

	<div id="personDiv">
		<c:if test="${person.personId == 0}">
		<div id="personTitle">
			<h2><fmt:message key="peoplebase.showDocumentsPerson.documentsIndexedTo"/></h2>
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
					<h7><fmt:message key="peoplebase.comparePerson.activeStartCaps"/> <span class="h7"> ${person.activeStart}</span></h7>
				</c:if>
				<c:if test="${person.activeStart == null}">
					<h7><fmt:message key="peoplebase.comparePerson.birth"/> <span class="h7">${person.bornYear} ${person.bornMonth} ${person.bornDay}</span></h7>
				</c:if>		
				<c:if test="${person.activeEnd != null}">
					<h7><fmt:message key="peoplebase.comparePerson.activeEndCaps"/> <span class="h7"> ${person.activeEnd}</span></h7>
				</c:if>
				<c:if test="${person.activeEnd == null}">
					<h7><fmt:message key="peoplebase.comparePerson.death0"/> <span class="h7">${person.deathYear} ${person.deathMonth} ${person.deathDay}</span></h7>
				</c:if>
				<%-- Documents Related Section --%>
				<c:if test="${docsRelated != 0 && docsRelated != 1}">
					<p>Documents related to this person entry: <span class="num_docs">${docsRelated}</span>
					<div style="margin:10px 0 0 10px">
						(<a href="${ShowDocumentsPersonURL}" class="all_docsCompare ${person.personId}" title="View all documents related to this person">View All</a> | 
						<a href="${ShowSenderDocumentsPersonURL}" class="sender_docsCompare ${person.personId}" title="View the letters SENT BY this person">Sender</a> <span class="num_docs">${senderDocsRelated}</span> | 
						<a href="${ShowRecipientDocumentsPersonURL}" class="recipient_docsCompare ${person.personId}" title="View the letters RECEIVED BY this person">Recipient</a> <span class="num_docs">${recipientDocsRelated}</span> | 
						<a href="${ShowReferringToDocumentsPersonURL}" class="referred_docsCompare ${person.personId}" title="View the documents in which this person is mentioned">Referring To</a> <span class="num_docs">${referringDocsRelated}</span>)
					</div>
				</c:if>
				<c:if test="${docsRelated == 0}">	
					<p><fmt:message key="peoplebase.comparePerson.documentsRelatedToThisPersonEntry"/> <span class="num_docs" title="No documents indexed to this person entry">${docsRelated}</span>
				</c:if>
				<c:if test="${docsRelated == 1}">
					<p><fmt:message key="peoplebase.comparePerson.documentsRelatedToThisPersonEntry"/> <span class="num_docs">${docsRelated}</span>
					<div style="margin-left:28px">
						(<a href="${ShowDocumentsPersonURL}" class="all_docs ${person.personId}" title="View all the documents related to this person">View it</a>)
					</div>	
				</c:if>
			</div>
			<div id="EditPortraitPersonDiv">
				<c:url var="ShowPortraitPersonURL" value="/src/peoplebase/ShowPortraitPerson.do">
					<c:param name="personId" value="${person.personId}" />
					<c:param name="time" value="${time}" />
				</c:url>
				<div id="imgPortraitPerson">
					<img src="${ShowPortraitPersonURL}" width="111" height="145"/>
				</div>				
			</div>	
		</c:if>	
	</div>

	<div id="EditDetailsPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key="peoplebase.comparePerson.personDetails"/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.name"/></div> <div class="value">${person.mapNameLf}</div>
			</div>
			<br>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.gender"/></div> <div class="value">${person.gender}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.birthDate"/></div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.birthPlace"/></div><div class="value"><a class="linkPlaceCompare" href="${CompareBirthURL}">${person.bornPlace.placeNameFull}</a></div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.activeStart"/></div> <div class="value">${person.activeStart}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.deathDate"/></div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.deathPlace"/></div> <div class="value"><a class="linkPlaceCompare" href="${CompareDeathURL}">${person.deathPlace.placeNameFull}</a></div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.activeEnd"/></div> <div class="value">${person.activeEnd}</div>
			</div>
		</div>
	</div>


	<div id="EditNamesPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key="peoplebase.comparePerson.names"/> </h5>
		</div>
		<div class="list">
			<c:forEach items="${person.altName}" var="currentName">
				<div class="row">
					<div class="item">${currentName.nameType}</div> 
					<c:if test="${currentName.nameType == 'Family' }">
						<c:url var="ShowFamilyPersonURL" value="/src/peoplebase/ShowFamilyPerson.do">
							<c:param name="nameId" value="${currentName.nameId}" />
							<c:param name="altName" value="${currentName.altName}" />
						</c:url>
							<div class="value"><a class="linkFamilyCompare ${person.personId}" href="${ShowFamilyPersonURL}">${currentName.namePrefix} ${currentName.altName}</a></div>
					</c:if>
					<c:if test="${currentName.nameType != 'Family' }"> 
						<div class="value">${currentName.namePrefix} ${currentName.altName}</div>
					</c:if>  
				</div>
			</c:forEach>
		</div>	
	</div>


	<div id="EditTitlesOrOccupationsPersonDiv" class="background">
		<div class="title">	
			<h5><fmt:message key="peoplebase.comparePerson.titlesOccupations"/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${person.poLink}" var="currentPoLink">
				<c:url var="ShowTitlesOrOccupationsPeoplePersonURL" value="/src/peoplebase/ShowTitlesOrOccupationsPeoplePerson.do">
					<c:param name="titleOccId" value="${currentPoLink.titleOccList.titleOccId}" />
				</c:url>
				<c:url var="ShowRoleCatPeoplePersonURL" value="/src/peoplebase/ShowRoleCatPeoplePerson.do">
					<c:param name="roleCatId" value="${currentPoLink.titleOccList.roleCat.roleCatId}" />
				</c:url>
				<div class="row">
					<c:if test="${currentPoLink.preferredRole}">
						<div class="value5" title="Preferred Role" id="preferredRoleIcon"></div>
					</c:if>
					<c:if test="${!currentPoLink.preferredRole}">
						<div class="value5"></div>
					</c:if>
					<div class="value60">
						<a class="linkOccupationCompare ${person.personId}" href="${ShowTitlesOrOccupationsPeoplePersonURL}">
							<b>${currentPoLink.titleOccList.titleOcc}</b>
						</a>
						<br>
						<a class="linkOccupationCompare ${person.personId}" href="${ShowRoleCatPeoplePersonURL}">
							${currentPoLink.titleOccList.roleCat.roleCatMinor}
						</a>
					</div> 
					<div class="info">Start ${currentPoLink.startDate} | End ${currentPoLink.endDate}</div>
				</div>
			</c:forEach>
		</div>
	</div>


	<div id="EditParentsPersonDiv" class="background">
		<div class="title">	
			<h5><fmt:message key="peoplebase.comparePerson.parents"/></h5>
		</div>
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.father"/></div> 
				<c:forEach items="${person.parents}" var="currentParent">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentParent.parent.personId}" />
					</c:url>
					<c:if test="${currentParent.parent.gender == 'M'}">
						<div class="value">
							<a class="linkParentCompare ${person.personId}" href="${ComparePersonURL}">
								${currentParent.parent}
								<input type="hidden" style="display:none;" class="tabId" value="peopleId${currentParent.parent.personId}" />
							</a>
						</div> 
						<div class="info"><fmt:message key="peoplebase.comparePerson.born"/> ${currentParent.parent.bornYear} | <fmt:message key="peoplebase.comparePerson.death1"/> ${currentParent.parent.deathYear}</div>
					</c:if>				
				</c:forEach>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="peoplebase.comparePerson.mother"/></div> 
				<c:forEach items="${person.parents}" var="currentParent">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentParent.parent.personId}" />
					</c:url>
					<c:if test="${currentParent.parent.gender == 'F'}">
						<div class="value">
							<a class="linkParentCompare ${person.personId}" href="${ComparePersonURL}">
								${currentParent.parent}
								<input type="hidden" style="display:none;" class="tabId" value="peopleId${currentParent.parent.personId}" />
							</a>
						</div> 
						<div class="info"><fmt:message key="peoplebase.comparePerson.born"/> ${currentParent.parent.bornYear} | <fmt:message key="peoplebase.comparePerson.death1"/> ${currentParent.parent.deathYear}</div>
					</c:if>				
				</c:forEach>
			</div>
		</div>
	</div>

	<div id="EditChildrenPersonDiv" class="background">
		<div class="title">	
			<h5><fmt:message key="peoplebase.comparePerson.children"/></h5>
		</div>
		<div class="list">
			<c:forEach items="${children}" var="currentChild">
				<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
					<c:param name="personId"   value="${currentChild.child.personId}" />
				</c:url>
				<div class="row">
					<div class="value">
						<a class="linkChildCompare ${person.personId}" href="${ComparePersonURL}">
							${currentChild.child}
							<input type="hidden" style="display:none;" class="tabId" value="peopleId${currentChild.child.personId}" />
						</a>
					</div> 
					<div class="info"><fmt:message key="peoplebase.comparePerson.born"/> ${currentChild.child.bornYear} | <fmt:message key="peoplebase.comparePerson.death1"/> ${currentChild.child.deathYear}</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div id="EditSpousesPersonDiv" class="background">
		<div class="title">	
			<h5><fmt:message key="peoplebase.comparePerson.spouses"/></h5>
		</div>
		<div class="list">
			<c:forEach items="${marriages}" var="currentMarriage">
				<div class="row">
					<c:choose>
						<c:when test="${person.personId == currentMarriage.husband.personId}">
							<c:set var="spouse" value="${currentMarriage.wife}" />
						</c:when>
						<c:otherwise>
							<c:set var="spouse" value="${currentMarriage.husband}" />
						</c:otherwise>
					</c:choose>
					<div class="row">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${spouse.personId}" />
						</c:url>
						<div class="value">
							<a class="linkSpouseCompare ${person.personId}" href="${ComparePersonURL}">
								${spouse}
								<input type="hidden" style="display:none;" class="tabId" value="peopleId${spouse.personId}" />
							</a>
						</div>
						<div class="info">
							<fmt:message key="peoplebase.comparePerson.marriageDates"/> ${currentMarriage.startYear} - ${currentMarriage.endYear}
							<c:if test="${not empty currentMarriage.marTerm and currentMarriage.marTerm != 'Unknown'}">
							| ${currentMarriage.marTerm}
							</c:if>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<div id="EditResearchNotesPersonDiv" class="background">
		<div class="title">	
			<h5><fmt:message key="peoplebase.comparePerson.researchNotes"/></h5>
		</div>
	
		<div class="list">
			<div class="row">
				<div class="value">
					${person.bioNotes}
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${person.personId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
				return false;
			});
			
			$j(".all_docsCompare.${person.personId}").click(function(){
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
			
			$j(".all_docs.${person.personId}").click(function(){
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
			
			$j(".sender_docsCompare.${person.personId}").click(function(){
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
			
			$j(".recipient_docsCompare.${person.personId}").click(function(){
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
			
			$j(".referred_docsCompare.${person.personId}").click(function(){
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
			
			$j(".linkSearch").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".linkPlaceCompare").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkOccupationCompare.${person.personId}").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
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
			
			$j(".linkParentCompare.${person.personId}").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".linkChildCompare.${person.personId}").click(function() {
	        	var tabName = $j(this).text();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
			
			$j(".linkFamilyCompare.${person.personId}").click(function() {
	        	var tabName = $j(this).text();
	        	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
	        	if(is_chrome){
	        		//For remove whitespaces at start/end of a string
	        		tabName = tabName.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
	        	}

				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
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
			
			$j(".linkSpouseCompare.${person.personId}").click(function(){
				var tabName = $j(this).text();
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					if(this.text == tabName || this.text == "PersonId#" + id.substring(8, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							//To change name of the tab
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("PersonId#" + $j(this).find("input").val().substring(8, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "PersonId#" + id.substring(8, id.length) + " - " + tabName;		
							}
						}
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					return false;
				}
			});
		});
	</script>
