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
			<h5>TRANSCRIPTION/SYNOPSIS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${document.entryId > 0}">
				<a id="EditExtractOrSynopsisDocument" href="${EditExtractOrSynopsisDocumentURL}" class="editBasic" title="Edit Transcription/Synopsis"></a>
				<a id="EditDocumentInModal" href="${EditExtractOrSynopsisDocumentModalWindowURL}" class="editSplitScreen" title="Edit with Split Screen"></a>
				<c:if test="${not empty image}">
					<a id="EditDocumentInManuscriptTranscriber" href="${EditDocumentInManuscriptViewerURL}" class="EditDocumentInManuscriptTranscriber" title="Edit with Manuscript Transcirber"></a><span id="loading"/>
				</c:if>
				<c:if test="${empty image}">
					<span class="EditDocumentInManuscriptTranscriberOff" title="Not yet digitized"></span>
				</c:if>
			</c:if>
		</security:authorize>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item">Transcription</div>
				<div class="value80" id="extract">${document.synExtract.docExtract}</div>
			</div>
			<div class="row">
				<div class="item">Synopsis</div>
				<div class="value80" id="synopsis">${document.synExtract.synopsis}</div>
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
			
			$j("#EditDocumentInManuscriptTranscriber").open({width: screen.width, height: screen.height, scrollbars: false});
			
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

		});
	</script>
