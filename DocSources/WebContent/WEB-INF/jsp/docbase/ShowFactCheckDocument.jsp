<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditDetailsDocument" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditExtractOrSynopsisDocument" value="/de/docbase/EditExtractOrSynopsisDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditFactCheckDocument" value="/de/docbase/EditFactCheckDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>

	<div id="EditFactCheckDocumentDiv">
		<h5>FACT CHECK <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"><a id="EditFactCheckDocument" href="${EditFactCheckDocument}">edit</a></security:authorize></h5>
		<hr id="lineSeparator"/>
		<ul>
			<li>${document.factChecks.addLRes}</li>
		</ul>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			 $j("#EditDetailsDocument").attr('href', "${EditDetailsDocument}");
			 $j("#EditFactCheckDocument").attr('href', "${EditFactCheckDocument}");
			 $j("#EditCorrespondentsOrPeopleDocument").attr('href', "${EditCorrespondentsOrPeopleDocument}");
			 $j("#EditTopicsDocument").attr('href', "${EditTopicsDocument}");
			 $j("#EditExtractOrSynopsisDocument").attr('href', "${EditExtractOrSynopsisDocument}");

			 $j("#EditFactCheckDocument").click(function(){$j("#EditFactCheckDocumentDiv").load($j(this).attr("href"));return false;});
			 
			// Loading image on editlink js funct
				$j("#EditDetailsDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
					return false;
				});
				$j("#EditFactCheckDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditFactCheckDocument").load($j(this).attr("href"));
					return false;
				});
				$j("#EditCorrespondentsOrPeopleDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditCorrespondentsOrPeopleDocument").load($j(this).attr("href"));
					return false;
				});
				$j("#EditTopicsDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditTopicsDocument").load($j(this).attr("href"));
					return false;
				});
				$j("#EditExtractOrSynopsisDocument").click(function(){
					$j(this).append('<span id=loading><img src="images/loading.gif"></span>');
					 $j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
					return false;
				});
			 
		});
	</script>
