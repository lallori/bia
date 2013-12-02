<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>
	
	<c:url var="ShareDocumentURL" value="/de/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	
	<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="flashVersion"   value="false" />
	</c:url>

	<c:url var="ShowDocumentExplorerURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="volNum"   value="${document.volume.volNum}" />
		<c:param name="volLetExt"   value="${document.volume.volLetExt}" />
		<c:param name="folioNum"   value="${document.folioNum}" />
		<c:param name="folioMod"   value="${document.folioMod}" />
		<c:param name="imageType"   value="C" />
		<c:param name="imageProgTypeNum"   value="${document.folioNum}" />
		<c:param name="flashVersion"   value="true" />
	</c:url>
	
	<c:url var="CompareVolumeURL" value="/src/volbase/CompareVolume.do">
		<c:param name="summaryId"   value="${document.volume.summaryId}" />
	</c:url>
	
	<c:url var="CompareSenderURL" value="/src/peoplebase/ComparePerson.do">
		<c:param name="personId"   value="${document.senderPeople.personId}" />
	</c:url>

	<c:url var="CompareRecipientURL" value="/src/peoplebase/ComparePerson.do">
		<c:param name="personId"   value="${document.recipientPeople.personId}" />
	</c:url>
	
	<c:url var="CompareFromURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${document.senderPlace.placeAllId}" />
	</c:url>
	
	<c:url var="CompareToURL" value="/src/geobase/ComparePlace.do">
		<c:param name="placeAllId" value="${document.recipientPlace.placeAllId}" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowDocumentURL}" id="editLink${document.entryId}" class="showOrEditCompare button_large"><fmt:message key="docbase.compareDocument.showOrEditThisDocument"/></a>
	</div>
	</security:authorize>
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div>
		<a href="${ShowDocumentURL}" id="editLink${document.entryId}" class="showCompare button_medium"><fmt:message key="docbase.compareDocument.showThisDocument"/></a>
	</div>
	</security:authorize>
	
	<div id="documentDiv">
		<%-- Comparing Document Record --%>
		<c:if test="${document.volume != null}">
		<div id="documentTitle">
			<div id="text">
				<h3><fmt:message key="docbase.compareDocument.volume"/>: <a href="${CompareVolumeURL}" class="linkVolumeCompare${document.entryId}" title="View Volume n.${document.volume.volNum}${document.volume.volLetExt} file">${document.volume.volNum}${document.volume.volLetExt}</a></h3>
	<!-- 		Checking if folio is inside inserts or inserts with parts -->
	<!-- 		1) folio is not inside inserts-->
				<c:if test="${document.insertNum == null}">
					<h3><fmt:message key="docbase.compareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
				</c:if>
	<!-- 		2) folio is inside inserts with no parts -->
				<c:if test="${document.insertNum != null && document.insertLet  == null}">
					<br>
					<br>
					<h3><fmt:message key="docbase.compareDocument.insert"/>: ${document.insertNum}</h3><h3><fmt:message key="docbase.compareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
					<br>
					<br>
				</c:if>
	<!-- 		3) folio is inside inserts with parts -->
				<c:if test="${document.insertLet  != null}">
					<br>
					<br>
					<h3><fmt:message key="docbase.compareDocument.insert"/>: ${document.insertNum} / ${document.insertLet}</h3><h3><fmt:message key="docbase.compareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
					<br>
					<br>
				</c:if>
				<c:choose>
					<%-- Recipient empty --%>
					<c:when test="${document.senderPeople.mapNameLf != null} && ${document.recipientPeople.mapNameLf == null}">
				 		<h4><fmt:message key="docbase.compareDocument.from"/>: <span class="h4">${document.senderPeople.mapNameLf}</span></h4>
						<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? ' - (Unsure)':'' }</h7>
				 		<h4><fmt:message key="docbase.compareDocument.to"/>: <span class="h4">(Not Entered)</span></h4>
					</c:when>
					<%-- Sender empty --%>
					<c:when test="${document.senderPeople.mapNameLf == null} && ${document.recipientPeople.mapNameLf != null}">
				 		<h4><fmt:message key="docbase.compareDocument.from"/>:<span class="h4">(Not Entered)</span></h4>
				 		<h4><fmt:message key="docbase.compareDocument.to"/>: <span class="h4">${document.recipientPeople.mapNameLf}</span></h4>
				 		<h7>${document.recipientPlace.placeNameFull} ${document.recipientPlaceUnsure ? '(Unsure)':'' }</h7>
					</c:when>
					<%-- Sender and Recipient filled in --%>
					<c:otherwise>
				  		<h4><fmt:message key="docbase.compareDocument.from"/>:<span class="h4"> ${document.senderPeople.mapNameLf}</span></h4>
						<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? '(Unsure)':'' }</h7>
				  		<h4><fmt:message key="docbase.compareDocument.to"/>:<span class="h4"> ${document.recipientPeople.mapNameLf}</span></h4>
						<h7>${document.recipientPlace.placeNameFull} ${document.recipientPlaceUnsure ? '(Unsure)':'' }</h7>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${document.yearModern == null && (document.docYear != null || document.docMonthNum != null || document.docDay != null)}">
						<h5>${document.docYear} ${document.docMonthNum} ${document.docDay} ${document.dateUns ? '(Unsure)':'' }</h5>
					</c:when>
					<c:when test="${document.yearModern != null}">
						<h5>${document.yearModern} ${document.docMonthNum} ${document.docDay} ${document.dateUns ? '(Unsure)':'' }</h5>
					</c:when>
				</c:choose>
			
			</div>
			<c:if test="${not empty image}">
					<div id="DocumentImageDigitDiv">
						<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
							<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>">
						</security:authorize>
						<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
							<span class="register">To see this Document image  you must register</span>
							<img src="<c:url value="/images/1024/img_document.png"/>" alt="Document" width="120px" height="160px" style="opacity:0.3;-moz-opacity: 0.3;filter:alpha(opacity=50);">
						</security:authorize>
					</div>
				</c:if>
			<c:if test="${empty image}">
				<div id="DocumentImageNotDigitDiv">
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
						<span><fmt:message key="docbase.compareDocument.toBeDigitized"/></span>
					</security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
						<span class="register"><fmt:message key="docbase.compareDocument.toBeDigitized"/></span>
					</security:authorize>
				</div>
			</c:if>
		</div>
		</c:if>
		
		<div id="EditExtractOrSynopsisDocumentDiv" class="background">
			<div class="title">
				<h5><fmt:message key="docbase.compareDocument.transcriptionSynopsis"/> </h5>
			</div>
	
			<div class="list">
				<div class="row">
					<div class="item"><fmt:message key="docbase.compareDocument.transcription"/></div>
					<div class="value80" id="extract">${document.synExtract.docExtract}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="docbase.compareDocument.synopsis"/></div>
					<div class="value80" id="synopsis">${document.synExtract.synopsis}</div>
				</div>
				<div class="row">
					<div class="item"><fmt:message key="docbase.compareDocument.documentsReferredTo"/></div> 
					<div class="value80">
						<c:forEach items="${document.docReference}" var="currentDocument">
							<div class="inlineList">#${currentDocument.documentTo.entryId}</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<br />
	
		<div id="EditDetailsDocumentDiv" class="background">
			<div class="title">
				<h5>DOCUMENT DETAILS </h5>
			</div>
			<div class="listDetails">
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.docId"/></div>
					<div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.volume"/></div>
					<div class="value">${document.volume.volNum}${document.volume.volLetExt}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.insertPart"/></div>
					<div class="value">
						<c:if test="${not empty document.insertNum}">
							${document.insertNum}
							<c:if test="${not empty document.insertLet}">
								/ ${document.insertLet}
							</c:if>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.documentStartsAtFolio"/></div>
					<div class="value">
						${document.folioNum}
						<c:if test="${not empty document.folioMod}">
							/ ${document.folioMod}
						</c:if>
						<c:choose>
							<c:when test="${document.folioRectoVerso == 'R'}">
								/ <fmt:message key="docbase.compareDocument.folioRecto"/>
							</c:when>
							<c:when test="${document.folioRectoVerso == 'V'}">
								/ <fmt:message key="docbase.compareDocument.folioVerso"/>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</div> 
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.documentTranscribeFolio"/></div>
					<div class="value">
						${document.transcribeFolioNum}
						<c:if test="${not empty document.transcribeFolioMod}">
							/ ${document.transcribeFolioMod}
						</c:if>
						<c:choose>
							<c:when test="${document.transcribeFolioRectoVerso == 'R'}">
								/ <fmt:message key="docbase.compareDocument.folioRecto"/>
							</c:when>
							<c:when test="${document.transcribeFolioRectoVerso == 'V'}">
								/ <fmt:message key="docbase.compareDocument.folioVerso"/>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</div> 
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.notpaginated"/></div>
					<div class="value">${document.unpaged ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.nonconsecutive"/></div>
					<div class="value">${document.contDisc ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.documentTypology"/></div>
					<div class="value">${document.docTypology}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.modernDate"/></div>
					<div class="valueHilight">${document.yearModern}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.recorderYear"/></div>
					<div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.dateUncertain"/></div>
					<div class="value">${document.dateUns ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item60"><fmt:message key="docbase.compareDocument.undated"/></div>
					<div class="value">${document.undated ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item60">Istituto di Conservazione</div>
					<div class="value">Archivio di Stato di Mantova</div>
				</div>
				<div class="row">
					<div class="item60">Fondo</div>
					<div class="value">Archivio Gonzaga</div>
				</div>
				<div class="row">
					<div class="item60">Serie</div>
					<div class="value">Corrispondenza, Copialettere, Originali, Minute, Autografi</div>
				</div>
			</div>
			<div class="list">
				<div class="row">
					<div class="item37"><fmt:message key="docbase.compareDocument.dateNotes"/></div>
					<div class="value50">${document.dateNotes}</div>
				</div>
			</div>
		</div>
		
		<br />
		<br />
	
		<div id="EditFactCheckDocumentDiv" class="background">
			<div class="title">
				<h5><fmt:message key="docbase.compareDocument.factCheck"/> </h5>
			</div>
	
			<div class="list">
				<div class="row">
					<div class="valueHilight">${document.factChecks.addLRes}</div>
				</div>
			</div>
		</div>
		
		<br />
		<br />
	
		<div id="EditCorrespondentsOrPeopleDocumentDiv" class="background">
			<div class="title">
				<h5><fmt:message key="docbase.compareDocument.correspondentsAndPeople"/> </h5>
			</div>
			<div class="list">
				<div class="row">
	<!-- 				Entries like "person name lost" or "to be entered" should be not clickable -->
					<c:if test="${document.senderPeople.personId != 9285 && document.senderPeople.personId != 3905 && document.senderPeople.personId != 198}">
						<div class="item"><fmt:message key="docbase.compareDocument.sender"/></div> <div class="value80"><a class="linkPeopleCompare" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${document.senderPeople.personId}" /></a></div>
					</c:if>
					<c:if test="${document.senderPeople.personId == 9285 || document.senderPeople.personId == 3905 || document.senderPeople.personId == 198}">
						<div class="item"><fmt:message key="docbase.compareDocument.sender"/></div> <div class="value80">${document.senderPeople.mapNameLf}</div>
					</c:if>
				</div>
				<div class="row">
					<c:if test="${document.senderPlace.placeAllId != 53384 && document.senderPlace.placeAllId != 55627 && document.senderPlace.placeAllId != 54332}">
						<div class="item"><fmt:message key="docbase.compareDocument.senderFrom"/></div> <div class="value80"><a class="linkPlaceCompare" href="${CompareFromURL}">${document.senderPlace.placeNameFull} </a></div>
					</c:if>
					<c:if test="${document.senderPlace.placeAllId == 53384 || document.senderPlace.placeAllId == 55627 || document.senderPlace.placeAllId == 54332 }">
						<div class="item"><fmt:message key="docbase.compareDocument.senderFrom"/></div> <div class="value80">${document.senderPlace.placeNameFull} </div>
					</c:if>
					<div class="row">
						<div class="item37"><fmt:message key="docbase.compareDocument.senderNotes"/></div> <div class="value80">${document.sendNotes}</div>
					</div>
				</div>	
				<div class="row">
					<c:if test="${document.recipientPeople.personId != 9285 && document.recipientPeople.personId != 3905 && document.recipientPeople.personId != 198}">
						<div class="item"><fmt:message key="docbase.compareDocument.recipient"/></div> <div class="value80"><a class="linkPeopleCompare" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${document.recipientPeople.personId}" /></a></div>
					</c:if>
					<c:if test="${document.recipientPeople.personId == 9285 || document.recipientPeople.personId == 3905 || document.recipientPeople.personId == 198}">
						<div class="item"><fmt:message key="docbase.compareDocument.recipient"/></div> <div class="value80">${document.recipientPeople.mapNameLf}</div>
					</c:if>
				</div>
				<div class="row">
					<c:if test="${document.recipientPlace.placeAllId != 53384 && document.recipientPlace.placeAllId != 55627 && document.recipientPlace.placeAllId != 54332}">
						<div class="item"><fmt:message key="docbase.compareDocument.recipientTo"/></div> <div class="value80"><a class="linkPlaceCompare" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</a></div>
					</c:if>
					<c:if test="${document.recipientPlace.placeAllId == 53384 || document.recipientPlace.placeAllId == 55627 || document.recipientPlace.placeAllId == 54332}">
						<div class="item"><fmt:message key="docbase.compareDocument.recipientTo"/></div> <div class="value80">${document.recipientPlace.placeNameFull}</div>
					</c:if>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.compareDocument.recipientNotes"/></div> <div class="value80">${document.recipNotes}</div>
				</div>
				<br>
				<div class="row">
					<div class="item"><fmt:message key="docbase.compareDocument.people"/></div> 
					
				<c:forEach items="${document.epLink}" var="currentPeople">
				<!-- This is a method to have a value near the item with the text People. -->	
						<c:if test="${currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
						<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
							<c:param name="personId"   value="${currentPeople.person.personId}" />
						</c:url>
						<c:if test="${currentPeople.person.personId != 9285 && currentPeople.person.personId != 3905 && currentPeople.person.personId != 198}">
						<div class="value80"><a class="linkPeopleCompare" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}<input type="hidden" style="display:none;" class="tabId" value="peopleId${currentPeople.person.personId}" /></a></div>
						</c:if>
						<c:if test="${currentPeople.person.personId == 9285 || currentPeople.person.personId == 3905 || currentPeople.person.personId == 198}">
						<div class="value80">${currentPeople.person.mapNameLf}</div>
						</c:if>
						
	
					</div>
					<div class="row">
						<div class="item">&nbsp</div>
					</c:if>
				</c:forEach>
				</div>
			</div>
		</div>
	
	<br />
	<br />

	<div id="EditTopicsDocumentDiv" class="background">
	<div class="title">
		<h5><fmt:message key="docbase.compareDocument.topics"/> </h5>
		</div>
	
		<div class="list">
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
			<div class="row">
				<div class="item"><fmt:message key="docbase.compareDocument.topic"/>:</div>
				<div class="value80"> ${currentTopicAndPlace.topic.topicTitle}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.compareDocument.topicPlace"/>:</div>
				<div class="value80"> ${currentTopicAndPlace.place.placeNameFull}</div>
			</div>
			<br/>
		</c:forEach>
	</div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function(){
			$j("#editLink${document.entryId}").click(function(){
				$j("#body_left").load($j(this).attr("href"));
				var selected = $j("#tabs").tabs('option', 'selected');
				$j("#tabs").tabs('remove', selected);
				return false;
			});
			
// 			$j("#tabs ul li.ui-tabs-selected a").addClass("docId${document.entryId}");

			$j(".linkVolumeCompare${document.entryId}").click(function() {
				var tabN = $j(this).text();
				tabName = 'Volume  ' 
				tabName += tabN;
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				//Check if already exist a tab with this Volume
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
			
			$j(".linkPeopleCompare").click(function() {
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
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).text() + "</span><input type=\"hidden\" value=\"" + id + "\" /></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
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
			
			$j('#documentDiv > #EditExtractOrSynopsisDocumentDiv > .list > .row > #extract').expander({
                slicePoint: 500,
                expandText: 'Click here to read more',
                userCollapseText: 'Click here to hide text'
       		});
		
			$j(".read-less").click(function(){
				$j.scrollTo("#documentDiv > #EditExtractOrSynopsisDocumentDiv");
			});
		});
	</script>
