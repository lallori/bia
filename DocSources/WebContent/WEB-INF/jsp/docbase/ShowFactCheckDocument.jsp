<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditFactCheckDocumentURL" value="/de/docbase/EditFactCheckDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>

	<div id="EditFactCheckDocumentDiv" class="background">
		<div class="title">
			<h5>FACT CHECK<a class="helpIcon" title="<fmt:message key="docbase.factcheck.show"></fmt:message>">?</a></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<c:if test="${document.entryId > 0}">
			<a id="EditFactCheckDocument" href="${EditFactCheckDocumentURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				<div class="valueHilight">${document.factChecks.addLRes}</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditFactCheckDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditFactCheckDocumentDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
