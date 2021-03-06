<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShareDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"		value="${document.entryId}" />
	</c:url>

	<c:url var="checkDocumentDigitizedURL" value="/src/docbase/CheckDocumentDigitized.json">
		<c:param name="entryId"				value="${document.entryId}" />
		<c:param name="volNum"				value="${document.volume.volNum}" />
		<c:param name="volLetExt"			value="${document.volume.volLetExt}" />
		<c:param name="insertNum"			value="${document.insertNum}" />
		<c:param name="insertLet"			value="${document.insertLet}" />
		<c:param name="folioNum"			value="${document.folioNum}" />
		<c:param name="folioMod"			value="${document.folioMod}" />
		<c:param name="folioRectoVerso"		value="${document.folioRectoVerso}" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
		<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"			value="${document.entryId}" />
			<c:param name="flashVersion"	value="false" />
		</c:url>
		
		<c:url var="ShowTranscriptionInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"				value="${document.entryId}" />
			<c:param name="showTranscription"	value="true" />
			<c:param name="flashVersion"		value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowDocumentExplorerURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId"				value="${document.entryId}" />
		<c:param name="volNum"				value="${document.volume.volNum}" />
		<c:param name="volLetExt"			value="${document.volume.volLetExt}" />
		<c:param name="insertNum"			value="${document.insertNum}" />
		<c:param name="insertLet"			value="${document.insertLet}" />
		<c:param name="imageType"			value="C" />
		<c:param name="imageProgTypeNum" 	value="${document.folioNum}" />
		<c:param name="missedNumbering"		value="${document.folioMod}" />
		<c:param name="imageRectoVerso"		value="${document.folioRectoVerso}" />
		<c:param name="flashVersion"		value="false" />
	</c:url>
	
	<c:url var="CompareVolumeURL" value="/src/volbase/CompareVolume.do">
		<c:param name="summaryId"	value="${document.volume.summaryId}" />
	</c:url>
	<%-- Create new Document Record --%>
	<c:if test="${document.volume == null}">
			<h2 class="addNew"><fmt:message key="docbase.showSummaryDocument.addNewDocument"/></h2>
	</c:if>
	<%-- Editing Document Record --%>
	<c:if test="${document.volume != null}">
		<div id="documentTitle">
			<div id="text">
				<h3><fmt:message key="docbase.showSummaryDocument.volume"/>: <a href="${CompareVolumeURL}" class="linkVolume" title="<fmt:message key="docbase.showSummaryDocument.viewVolumeN"/>${document.volume.volNum}${document.volume.volLetExt} <fmt:message key="docbase.showSummaryDocument.file"/>">${document.volume.volNum}${document.volume.volLetExt}</a></h3>
				<!-- Checking if folio is inside inserts or inserts with parts -->
				<!-- 	1) folio is not inside inserts-->
				<c:if test="${document.insertNum == null}">
					<h3><fmt:message key="docbase.showSummaryDocument.folio"/>: ${document.folioNum}${document.folioMod}
						<c:choose>
							<c:when test="${document.folioRectoVerso == 'R'}">
								<fmt:message key="docbase.showSummaryDocument.recto"/>
							</c:when>
							<c:when test="${document.folioRectoVerso == 'V'}">
								<fmt:message key="docbase.showSummaryDocument.verso"/>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</h3>
				</c:if>
				<!-- 	2) folio is inside inserts with no parts -->
				<c:if test="${document.insertNum != null && document.insertLet  == null}">
					<br>
					<br>
					<h3><fmt:message key="docbase.showSummaryDocument.insert"/>: ${document.insertNum}</h3>
					<h3><fmt:message key="docbase.showSummaryDocument.folio"/>: ${document.folioNum}${document.folioMod}
						<c:choose>
							<c:when test="${document.folioRectoVerso == 'R'}">
								<fmt:message key="docbase.showSummaryDocument.recto"/>
							</c:when>
							<c:when test="${document.folioRectoVerso == 'V'}">
								<fmt:message key="docbase.showSummaryDocument.verso"/>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</h3>
					<br>
					<br>
				</c:if>
				<!-- 	3) folio is inside inserts with parts -->
				<c:if test="${document.insertLet  != null}">
					<br>
					<br>
					<h3><fmt:message key="docbase.showSummaryDocument.insert"/>: ${document.insertNum} / ${document.insertLet}</h3>
					<h3><fmt:message key="docbase.showSummaryDocument.folio"/>: ${document.folioNum}${document.folioMod}
						<c:choose>
							<c:when test="${document.folioRectoVerso == 'R'}">
								<fmt:message key="docbase.showSummaryDocument.recto"/>
							</c:when>
							<c:when test="${document.folioRectoVerso == 'V'}">
								<fmt:message key="docbase.showSummaryDocument.verso"/>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</h3>
					<br>
					<br>
				</c:if>
				<c:choose>
					<%-- Recipient empty --%>
					<c:when test="${document.senderPeople.mapNameLf != null} && ${document.recipientPeople.mapNameLf == null}">
				 		<h4><fmt:message key="docbase.showSummaryDocument.from"/>: <span class="h4">${document.senderPeople.mapNameLf}</span></h4>
						<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? ' - (Unsure)':'' }</h7>
				 		<h4><fmt:message key="docbase.showSummaryDocument.to"/>: <span class="h4"><fmt:message key="docbase.showSummaryDocument.notEntered"/></span></h4>
					</c:when>
					<%-- Sender empty --%>
					<c:when test="${document.senderPeople.mapNameLf == null} && ${document.recipientPeople.mapNameLf != null}">
				 		<h4><fmt:message key="docbase.showSummaryDocument.from"/>:<span class="h4"><fmt:message key="docbase.showSummaryDocument.notEntered"/></span></h4>
				 		<h4><fmt:message key="docbase.showSummaryDocument.to"/>: <span class="h4">${document.recipientPeople.mapNameLf}</span></h4>
				 		<h7>${document.recipientPlace.placeNameFull} ${document.recipientPlaceUnsure ? '(Unsure)':'' }</h7>
					</c:when>
					<%-- Sender and Recipient filled in --%>
					<c:otherwise>
				  		<h4><fmt:message key="docbase.showSummaryDocument.from"/>:<span class="h4"> ${document.senderPeople.mapNameLf}</span></h4>
						<h7>${document.senderPlace.placeNameFull} ${document.senderPlaceUnsure ? '(Unsure)':'' }</h7>
				  		<h4><fmt:message key="docbase.showSummaryDocument.to"/>:<span class="h4"> ${document.recipientPeople.mapNameLf}</span></h4>
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
						<img src="<c:url value="/mview/IIPImageServer.do?FIF=${image}&WID=120"/>" title="<fmt:message key="docbase.showSummaryDocument.shouldRegister"/>">
					</security:authorize>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
						<a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}" title="<fmt:message key="docbase.showSummaryDocument.showThisDocumentInMV"/>"></a>
						<a id="ShowTranscriptionInManuscriptViewer" href="#" title="<fmt:message key="docbase.showSummaryDocument.showThisTranscriptionInMV"/>"></a>
						<a id="ShowDocumentInVolumeExplorer" href="${ShowDocumentExplorerURL}" title="<fmt:message key="docbase.showSummaryDocument.showPreviewInRightSplitScreen"/>"></a>
					</security:authorize>
				</div>
			</c:if>
			<c:if test="${empty image}">
				<div id="DocumentImageNotDigitDiv">
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
						<span><fmt:message key="docbase.showSummaryDocument.toBeDigitized"/></span>
					</security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
						<span class="register"><fmt:message key="docbase.showSummaryDocument.toBeDigitized"/></span>
					</security:authorize>
				</div>
			</c:if>
				
		</div>
	</c:if>
	
	<script type="text/javascript">
		$j(document).ready(function() {

			$j("#buttonShareLink").hover(function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").attr({"style": 'display:inline'});
				$j(this).find("span").animate({opacity: 1, top: "-60"}, {queue:false, duration:400});
			}, function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").animate({opacity: 0, top: "-50"}, {queue:false, duration:400, complete: function(){
					$j(this).attr({"style": 'display:none'});
				}});
			});
			
			var $width = navigator.userAgent.indexOf('Chrome') != -1 ? window.innerWidth : screen.width;
			var $height = navigator.userAgent.indexOf('Chrome') != -1 ? window.innerHeight : screen.height;
			$j("#ShowDocumentInManuscriptViewer").open({windowName: "ShowDocumentInManuscriptViewer", width: $width, height: $height});
			
			$j("#ShowDocumentInVolumeExplorer").click(function(){
				var insertTitle = '';
				if (${document.insertNum != null}) {
					insertTitle = ' - <fmt:message key="docbase.showSummaryDocument.insert"/> ${document.insertNum}';
					if (${document.insertLet != null})
						insertTitle += ' ${document.insertLet}';
				}
				var folioTitle = ' - <fmt:message key="docbase.showSummaryDocument.folio"/> ${document.folioNum}';
				if (${document.folioMod != null})
					folioTitle += ' ${document.folioMod}';
				if (${document.folioRectoVerso != null})
					folioTitle += ' ${document.folioRectoVerso}';
					
				var tabName = "<span id='titleTab${document.volume.volNum}${document.volume.volLetExt}'><fmt:message key='docbase.showSummaryDocument.volume'/> ${document.volume.volNum}${document.volume.volLetExt}" + insertTitle + folioTitle + "</span>";
				//Check if already exist a tab with this document in volume explorer
				var numTab = 0;
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					var sameId = $j(this).find("span").find("span");
					//MD: Declare variable toTest for fix problem with IE
					var toTest = "";
					toTest += this.text;
					if(!tabExist){
						if(toTest != ""){
							numTab++;
						}
					}
					if(toTest == tabName || $j(sameId).attr("id") == "titleTab${document.volume.volNum}${document.volume.volLetExt}"){
						tabExist = true;
					}
				});
				
				/*if($j('#titleTab${document.volume.volNum}${document.volume.volLetExt}').length != 0){
					tabExist = true;
				}*/
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab);
					$j('#tabs ul li').eq(numTab).data('loaded', false).find('a').attr('href', $j(this).attr("href"));
					$j("#tabs").tabs("load", numTab);
					return false;
				}
			});
			
			$j(".linkVolume").click(function() {
				var tabN = $j(this).text();
				tabName = 'Volume ' 
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
			
			$j("#ShowTranscriptionInManuscriptViewer").click(function() {
				if (${empty document.transcribeFolioNum}) {
					alert('<fmt:message key="docbase.showSummaryDocument.warn.missingTranscribeFolio"/>');
				} else {
					window.open("${ShowTranscriptionInManuscriptViewerURL}", "ShowTranscriptionInManuscriptViewer", "width="+$width+",height="+$height);
				}
				return false;
			});
		});
	</script>
