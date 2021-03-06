<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	
	<style type="text/css">
		#documentTitle {
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
	
    <c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId" value="${document.entryId}" />
	</c:url>
	<c:url var="HomeURL" value="/Home.do">
		<c:param name="entryId" value="${document.entryId}" />
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
	
	<a href="${ShowDocumentURL}" id="moreInfoButton" class="button_medium" title="Browse The ${fn2:getApplicationProperty("project.name")} Database"><fmt:message key="docbase.shareDocument.moreInfo"/></a>
	<ul id="network">
	   <div class="fb-like" data-send="false" data-layout="button_count" data-width="500" data-show-faces="false" style="display:inline;"></div>
	   <div style="display:inline;"><a href="https://twitter.com/share" class="twitter-share-button" data-text=" "><fmt:message key="docbase.shareDocument.tweet"/></a></div>
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
	
	
	<div id="documentTitle">
		<div id="text">
			<h3><fmt:message key="docbase.shareDocument.volume"/>: <a href="${CompareVolumeURL}" class="linkVolume" title="View <fmt:message key="docbase.shareDocument.volume"/> n.${document.volume.volNum}${document.volume.volLetExt} file">${document.volume.volNum}${document.volume.volLetExt}</a></h3>
<!-- 		Checking if folio is inside inserts or inserts with parts -->
<!-- 		1) folio is not inside inserts-->
			<c:if test="${document.insertNum == null}">
				<h3><fmt:message key="docbase.shareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
			</c:if>
<!-- 		2) folio is inside inserts with no parts -->
			<c:if test="${document.insertNum != null && document.insertLet  == null}">
				<br>
				<br>
				<h3><fmt:message key="docbase.shareDocument.insert"/>: ${document.insertNum}</h3><h3><fmt:message key="docbase.shareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
				<br>
				<br>
			</c:if>
<!-- 		3) folio is inside inserts with parts -->
			<c:if test="${document.insertLet  != null}">
				<br>
				<br>
				<h3><fmt:message key="docbase.shareDocument.insert"/>: ${document.insertNum} / ${document.insertLet}</h3><h3><fmt:message key="docbase.shareDocument.folio"/>: ${document.folioNum}${document.folioMod}</h3>
				<br>
				<br>
			</c:if>
			<c:choose>
				<%-- Recipient empty --%>
				<c:when test="${document.senderPeople.mapNameLf != null} && ${document.recipientPeople.mapNameLf == null}">
			 		<h4><fmt:message key="docbase.shareDocument.sender"/>: <span class="h4">${document.senderPeople.mapNameLf}</span></h4>
					<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? ' - (Unsure)':'' }</h7>
			 		<h4><fmt:message key="docbase.shareDocument.recipient"/>: <span class="h4"><fmt:message key="docbase.shareDocument.SendOrRecipNotEntered"/></span></h4>
				</c:when>
				<%-- Sender empty --%>
				<c:when test="${document.senderPeople.mapNameLf == null} && ${document.recipientPeople.mapNameLf != null}">
			 		<h4><fmt:message key="docbase.shareDocument.sender"/>:<span class="h4"><fmt:message key="docbase.shareDocument.SendOrRecipNotEntered"/></span></h4>
			 		<h4><fmt:message key="docbase.shareDocument.recipient"/>: <span class="h4">${document.recipientPeople.mapNameLf}</span></h4>
			 		<h7>${document.recipientPlace.placeNameFull} ${document.recipientPlaceUnsure ? '(Unsure)':'' }</h7>
				</c:when>
				<%-- Sender and Recipient filled in --%>
				<c:otherwise>
			  		<h4><fmt:message key="docbase.shareDocument.sender"/>:<span class="h4"> ${document.senderPeople.mapNameLf}</span></h4>
					<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? '(Unsure)':'' }</h7>
			  		<h4><fmt:message key="docbase.shareDocument.recipient"/>:<span class="h4"> ${document.recipientPeople.mapNameLf}</span></h4>
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
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<div id="DocumentImageDigitDiv">
					<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120&"/>">
				</div>
			</security:authorize>
			<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<div id="DocumentImageNotDigitDiv">
					<span><fmt:message key="docbase.shareDocument.documentDigitized"/></span>
				</div>
			</security:authorize>
		</c:if>
		<c:if test="${empty image}">
			<div id="DocumentImageNotDigitDiv">
				<span><fmt:message key="docbase.shareDocument.toBeDigitized"/></span>
			</div>
		</c:if>
	</div>
	
	<div id="EditExtractOrSynopsisDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.shareDocument.title.transcriptionSynopsis"/> </h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="docbase.shareDocument.transcription"/></div>
				<div class="value80" id="extract">${document.synExtract.docExtract}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.shareDocument.synopsis"/></div>
				<div class="value80" id="synopsis">${document.synExtract.synopsis}</div>
			</div>
		</div>
	</div>
	
	<br /><br />
			

	<div id="EditDetailsDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.shareDocument.title.documentDetails"/> </h5>
		</div>

		
		
		<div class="listDetails">
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.docId"/></div> <div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.volume"/></div> <div class="value">${document.volume.volNum}${document.volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.insert"/>/<fmt:message key="docbase.shareDocument.documentDetails.part"/></div> <div class="value">${document.insertNum} / ${document.insertLet}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.documentStartsAtFolio"/> </div> <div class="value">${document.folioNum} / ${document.folioMod}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.paginated"/></div> <div class="value">${document.unpaged ? 'Yes' : 'NO'}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.documentTypology"/></div> <div class="value">${document.docTypology}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.modernDate"/></div> <div class="valueHilight">${document.yearModern}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.recordedYear"/></div> <div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.dateUncertain"/></div> <div class="value">${document.dateUns ? 'Yes' : 'NO'}</div>
			</div>
			<div class="row">
				<div class="item60"><fmt:message key="docbase.shareDocument.documentDetails.undated"/></div> <div class="value">${document.undated ? 'Yes' : 'NO'}</div>
			</div>
		</div>
		<div class="list">
			<div class="row">
				<div class="item37"><fmt:message key="docbase.shareDocument.documentDetails.dateNotes"/></div> <div class="value50">${document.dateNotes}</div>
			</div>
		</div>
	</div>
	
	<br /><br />
	
	<div id="EditFactChecksDocumentDiv" class="background">
			<div class="title">
				<h5><fmt:message key="docbase.shareDocument.title.factCheck"/> </h5>
			</div>
			<div class="list">
				<div class="row">
					<div class="valueHilight">${document.factChecks.addLRes}</div>
				</div>
			</div>
		</div>
		
		<br /><br />
	
	<div id="EditCorrespondentsDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.shareDocument.title.correspondentsPeople"/> </h5>
		</div>

		<div class="list">
			<div class="row">
<!-- 				Entries like "person name lost" or "to be entered" should be not clickable -->
				<c:if test="${document.senderPeople.personId != 9285 && document.senderPeople.personId != 3905 && document.senderPeople.personId != 198}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.sender"/></div> <div class="value80"><p class="linkSearch" href="${CompareSenderURL}">${document.senderPeople.mapNameLf}</p></div>
				</c:if>
				<c:if test="${document.senderPeople.personId == 9285 || document.senderPeople.personId == 3905 || document.senderPeople.personId == 198}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.sender"/></div> <div class="value80">${document.senderPeople.mapNameLf}</div>
				</c:if>
			</div>
			<div class="row">		
				<c:if test="${document.senderPlace.placeAllId != 53384 && document.senderPlace.placeAllId != 55627 && document.senderPlace.placeAllId != 54332}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.placeFrom"/></div> <div class="value80"><p class="linkSearch" href="${CompareFromURL}">${document.senderPlace.placeNameFull}}</p></div>
				</c:if>
				<c:if test="${document.senderPlace.placeAllId == 53384 || document.senderPlace.placeAllId == 55627 || document.senderPlace.placeAllId == 54332 }">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.placeFrom"/></div> <div class="value80">${document.senderPlace.placeNameFull}</div>
				</c:if>
			</div>	
			 <div class="row">
				<c:if test="${document.recipientPeople.personId != 9285 && document.recipientPeople.personId != 3905 && document.recipientPeople.personId != 198}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.recipient"/></div> <div class="value80"><p class="linkSearch" href="${CompareRecipientURL}">${document.recipientPeople.mapNameLf}</p></div>
				</c:if>
				<c:if test="${document.recipientPeople.personId == 9285 || document.recipientPeople.personId == 3905 || document.recipientPeople.personId == 198}">
					<div class="item">
						<fmt:message key="docbase.shareDocument.correspondentsPeople.recipient"/>
					</div> 
					<div class="value80">${document.recipientPeople.mapNameLf}</div>
				</c:if>
			</div>
			<div class="row">
				<c:if test="${document.recipientPlace.placeAllId != 53384 && document.recipientPlace.placeAllId != 55627 && document.recipientPlace.placeAllId != 54332}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.placeTo"/></div> <div class="value80"><p class="linkSearch" href="${CompareToURL}">${document.recipientPlace.placeNameFull}</p></div>
				</c:if>
				<c:if test="${document.recipientPlace.placeAllId == 53384 || document.recipientPlace.placeAllId == 55627 || document.recipientPlace.placeAllId == 54332}">
					<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.placeTo"/></div> <div class="value80">${document.recipientPlace.placeNameFull}</div>
				</c:if>
			</div>	
			<br>
			<div class="row">
				<div class="item"><fmt:message key="docbase.shareDocument.correspondentsPeople.people"/></div> 
				
			<c:forEach items="${document.epLink}" var="currentPeople">
			<!-- This is a method to have a value near the item with the text People. -->	
					<c:if test="${currentPeople.docRole!= 'S' && currentPeople.docRole != 'R'}">
					<c:url var="ComparePersonURL" value="/src/peoplebase/ComparePerson.do">
						<c:param name="personId"   value="${currentPeople.person.personId}" />
					</c:url>
					<c:if test="${currentPeople.person.personId != 9285 && currentPeople.person.personId != 3905 && currentPeople.person.personId != 198}">
					<div class="value80"><p class="linkSearch" href="${ComparePersonURL}">${currentPeople.person.mapNameLf}</p></div>
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

	<br /><br />
	
	<div id="EditTopicsDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.shareDocument.title.topics"/> </h5>
		</div>
		<div class="list">
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
			<div class="row">
				<div class="item"><fmt:message key="docbase.shareDocument.topic"/>:</div>
				<div class="value80"> ${currentTopicAndPlace.topic.topicTitle}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.shareDocument.topicPLace"/>:</div>
				<div class="value80"> ${currentTopicAndPlace.place.placeNameFull}</div>
			</div>
			<br/>
		</c:forEach>
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
	
