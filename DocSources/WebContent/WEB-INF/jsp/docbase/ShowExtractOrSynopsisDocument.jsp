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
			<h5>EXTRACT/SYNOPSIS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditExtractOrSynopsisDocument" href="${EditExtractOrSynopsisDocumentURL}" class="editButton"></a>
			<a id="EditDocumentInModal" href="${EditExtractOrSynopsisDocumentModalWindowURL}" class="editFullscreen" title="EXTRACT/SYNOPSIS"></a>
			<a id="EditDocumentInManuscriptViewer" href="${EditDocumentInManuscriptViewerURL}" class="EditExtractOrSynopsisDocument"></a><span id="loading"/>
		</security:authorize>
		</div>
		
		<ul>
			<li><b>Extract:</b></li>
			<li>${document.synExtract.docExtract}</li>
		</ul>
		<ul>
			<li><b>Synopsis:</b></li>
			<li>${document.synExtract.synopsis}</li>
		</ul>
	</div>
	
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditExtractOrSynopsisDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditExtractOrSynopsisDocumentDiv").load($j(this).attr("href"));
				return false;
			});
			
			$j("#EditDocumentInManuscriptViewer").open({width: screen.width, height: screen.height, scrollbars: false});
			
			$j("#EditDocumentInModal").click(function(){
				Modalbox.show($j(this).attr("href"), {title: $j(this).attr("title"), width: 850, height:550}); 
				return false;
			});

		});
	</script>
