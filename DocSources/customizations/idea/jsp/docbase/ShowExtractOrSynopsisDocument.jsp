<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditExtractOrSynopsisDocumentURL" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocumentModalWindowURL" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="modalWindow"   value="true" />
		</c:url>
		<c:url var="EditDocumentInManuscriptViewerURL" value="/de/mview/EditDocumentInManuscriptViewer.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
		
	<div id="EditExtractOrSynopsisDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.showExtractOrSynopsisDocument.title"/> </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${document.entryId > 0}">
				<a id="EditExtractOrSynopsisDocument" href="${EditExtractOrSynopsisDocumentURL}" class="editBasic" title="<fmt:message key="docbase.showExtractOrSynopsisDocument.editTranscSyn"/>"></a>
				<a id="EditDocumentInModal" href="${EditExtractOrSynopsisDocumentModalWindowURL}" class="editSplitScreen" title="Edit with Split Screen"></a>
				<c:if test="${not empty image}">
					<a id="EditDocumentInManuscriptTranscriber" href="${EditDocumentInManuscriptViewerURL}" class="EditDocumentInManuscriptTranscriber" title="<fmt:message key="docbase.showExtractOrSynopsisDocument.editWithManTransc"/>"></a><span id="loading"/>
				</c:if>
				<c:if test="${empty image}">
					<span class="EditDocumentInManuscriptTranscriberOff" title="Not yet digitized"></span>
				</c:if>
			</c:if>
		</security:authorize>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="docbase.showExtractOrSynopsisDocument.transcription"/></div>
				<div class="value80" id="extract">${document.synExtract.docExtract}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.showExtractOrSynopsisDocument.synopsis"/></div>
				<div class="value80" id="synopsis">${document.synExtract.synopsis}</div>
			</div>
			<div class="row">
				<div class="item">Document Bibliography</div>
				<div class="value80" id="documentBibliography"></div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.showExtractOrSynopsisDocument.documentsReferredTo"/></div> 
				<div class="value80">
			<c:forEach items="${document.docReference}" var="currentDocument">
			<!-- This is a method to have a value near the item with the text People. -->	
				<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do">
					<c:param name="entryId"   value="${currentDocument.documentTo.entryId}" />
				</c:url>
				<a class="linkDocument" href="${CompareDocumentURL}" id="${currentDocument.documentTo.volume.volNum}${currentDocument.documentTo.volume.volLetExt} / ${currentDocument.documentTo.folioNum}${currentDocument.documentTo.folioMod}">#${currentDocument.documentTo.entryId}<input type="hidden" style="display:none;" class="tabId" value="docId${currentDocument.documentTo.entryId}" /></a>
			</c:forEach>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditExtractOrSynopsisDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditExtractOrSynopsisDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#EditDocumentInManuscriptTranscriber").open({windowName: "EditDocumentInManuscriptTranscriber", width: screen.width, height: screen.height, scrollbars: false});
			
			$j("#EditDocumentInModal").click(function(){
				Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 850, height:550}); 
				return false;
			});

			$j("#extract").html($j("#extract").text().replace(/\n\r?/g, '<br />'));

			$j("#synopsis").html($j("#synopsis").text().replace(/\n\r?/g, '<br />'));
			
			$j('#extract').expander({
	                slicePoint: 500,
	                expandText: 'Click here to read more',
	                userCollapseText: 'Click here to hide text'
	        });
			
			$j(".read-less").click(function(){
				$j.scrollTo("#EditExtractOrSynopsisDocumentDiv");
			});
			
			$j(".linkDocument").click(function() {
				var tabName = $j(this).attr("id");
				var numTab = 0;
				var id = $j(this).find(".tabId").val();
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist){
						if(this.text != ""){
							numTab++;
						}
					}
					//Check if exist a tab with the same name or with the same name without id
					if(this.text == tabName || this.text == "DocId#" + id.substring(5, id.length) + " - " + tabName || this.text.substring(this.text.indexOf(" - ") + 3, this.text.length) == tabName){
						if($j(this).find("input").val() == id){
							tabExist = true;
						}else{
							if(this.text.indexOf("#") == -1){
								$j(this).find("span").text("DocId#" + $j(this).find("input").val().substring(5, $j(this).find("input").val().length) + " - " + this.text);
							}
							if(tabName.indexOf("#") == -1){
								tabName = "DocId#" + id.substring(5, id.length) + " - " + tabName;		
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
