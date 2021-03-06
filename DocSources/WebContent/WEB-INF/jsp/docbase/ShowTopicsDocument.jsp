<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditTopicsDocumentURL" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
	
	<div id="EditTopicsDocumentDiv" class="background">
		<div class="title">
			<h5><fmt:message key="docbase.showTopicsDocument.title"/><a class="helpIcon" title="<fmt:message key="docbase.showTopicsDocument.help.show"></fmt:message>">?</a></h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
			<c:if test="${document.entryId > 0}">
			<a id="EditTopicsDocument" href="${EditTopicsDocumentURL}" class="editButton"></a><span id="loading"/>
			</c:if>
		</security:authorize>
		</div>
		<div class="list">
		<c:forEach items="${topicsDocument}" var="currentTopicAndPlace">
			<c:url var="ShowTopicsRelatedDocumentURL" value="/src/docbase/ShowTopicsRelatedDocument.do">
				<c:param name="topicId" value="${currentTopicAndPlace.topic.topicId}" />
				<c:param name="topicTitle" value="${currentTopicAndPlace.topic.topicTitle}" />
				<c:param name="placeAllId" value="${currentTopicAndPlace.place.placeAllId}" />
				<c:param name="placeName" value="${currentTopicAndPlace.place.placeName}" />
			</c:url>
			<c:url var="ComparePlaceURL" value="/src/geobase/ComparePlace.do">
				<c:param name="placeAllId" value="${currentTopicAndPlace.place.placeAllId}"/>
			</c:url>
			<div class="row">
				<div class="item"><fmt:message key="docbase.showTopicsDocument.topic"/>:</div>
				<div class="value80"><a href="${ShowTopicsRelatedDocumentURL}" class="linkTopic" title="<fmt:message key="docbase.showTopicsDocument.showDocumentsRelatedToThisTopic"/>: ${currentTopicAndPlace.topic.topicTitle} - ${currentTopicAndPlace.place.placeName}">${currentTopicAndPlace.topic.topicTitle}<input type="hidden" value="${currentTopicAndPlace.place.placeName}" class="placeNameTab" style="display:none;" /></a></div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="docbase.showTopicsDocument.topicPlace"/>:</div>
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
				var tabName = $j(this).text() + " " + $j(this).find(".placeNameTab").val();
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
		});
	</script>
