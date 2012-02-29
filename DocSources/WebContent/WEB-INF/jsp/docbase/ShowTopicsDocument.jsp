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
			<c:if test="${document.entryId > 0}">
			<a id="EditTopicsDocument" href="${EditTopicsDocumentURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>
		<div class="list">
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
			<c:url var="ShowTopicsRelatedDocumentURL" value="/de/docbase/ShowTopicsRelatedDocument.do">
				<c:param name="topicId" value="${currentTopicAndPlace.topic.topicId}" />
				<c:param name="topicTitle" value="${currentTopicAndPlace.topic.topicTitle}" />
			</c:url>
			<c:url var="ComparePlaceURL" value="/src/geobase/ComparePlace.do">
				<c:param name="placeAllId" value="${currentTopicAndPlace.place.placeAllId}"/>
			</c:url>
			<div class="row">
				<div class="item">Topic:</div>
				<div class="value80"><a href="${ShowTopicsRelatedDocumentURL}" class="linkTopic">${currentTopicAndPlace.topic.topicTitle}</a></div>
			</div>
			<div class="row">
				<div class="item">Topic Place:</div>
				<c:if test="${currentTopicAndPlace.place.placeAllId != 53384 && currentTopicAndPlace.place.placeAllId != 55627 && currentTopicAndPlace.place.placeAllId != 54332}">
					<div class="value80"><a href="${ComparePlaceURL}" class="linkTopic">${currentTopicAndPlace.place.placeNameFull}</a></div>
				</c:if>
				<c:if test="${currentTopicAndPlace.place.placeAllId == 53384 || currentTopicAndPlace.place.placeAllId == 55627 || currentTopicAndPlace.place.placeAllId == 54332 }">
					<div class="value80">${currentTopicAndPlace.place.placeNameFull}</div>
				</c:if>
			</div>
			<br/>
		</c:forEach>
	</div>
	</div>


	<script type="text/javascript">
		$j(document).ready(function() {			 
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditTopicsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTopicsDocumentDiv").load($j(this).attr("href"));
				return false;
			});
	        
	        $j(".linkTopic").click(function() {
				var tabName = $j(this).text();
				var numTab = 0;
				
				if(tabName.length > 20){
					tabName = tabName.substring(0,17) + "...";
				}
				
				//Check if already exist a tab with this person
				var tabExist = false;
				$j("#tabs ul li a").each(function(){
					if(!tabExist)
						numTab++;
					if(this.text == tabName){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
					return false;
				}else{
					$j("#tabs").tabs("select", numTab-1);
					return false;
				}
			});
		});
	</script>
