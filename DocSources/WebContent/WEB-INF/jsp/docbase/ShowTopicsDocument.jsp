<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocumentURL" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditTopicsDocumentDiv" class="background">
		<div class="title">
			<h5>TOPICS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditTopicsDocument" href="${EditTopicsDocumentURL}" class="editButton"></a><span id="loading"/>
		</security:authorize>
		</div>
		<ul>
			<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
				<li><b>Topic:</b> ${currentTopicAndPlace.topic.topicTitle}</li>
				<li><b>Topic Place:</b> ${currentTopicAndPlace.place.placeNameFull}</li>
				<br/>
			</c:forEach>
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

	        $j("#EditTopicsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTopicsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
