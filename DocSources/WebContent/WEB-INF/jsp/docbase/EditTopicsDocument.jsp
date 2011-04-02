<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="AddTopicUrl" value="/de/docbase/EditTopicDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="eplToId"  value="0" />
		</c:url>
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditTopicsDocumentForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>TOPICS</b></legend>
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
			<c:url var="EditTopicDocument" value="/de/docbase/EditTopicDocument.do">
				<c:param name="entryId" value="${document.entryId}" />
				<c:param name="eplToId" value="${currentTopicAndPlace.eplToId}" />
			</c:url>

			<c:url var="DeleteTopicDocument" value="/de/docbase/DeleteTopicDocument.do" >
				<c:param name="entryId" value="${document.entryId}" />
				<c:param name="eplToId" value="${currentTopicAndPlace.eplToId}" />
			</c:url>

			<c:url var="ShowTopicDescription" value="/src/docbase/ShowTopicDescription.do">
				<c:param name="topicId" value="${currentTopicAndPlace.topic.topicId}" />
			</c:url>
			<div>
      			<input id="topic_${currentTopicAndPlace.eplToId}" name="topic_${currentTopicAndPlace.eplToId}" class="input_28c_disabled" type="text" value="${currentTopicAndPlace}" disabled="disabled" />
				<a class="deleteIcon" title="Delete this entry" href="${DeleteTopicDocument}"></a>
				<a class="editValue" class="editValue" href="${EditTopicDocument}">edit value</a>
				<c:if test="${not empty currentTopicAndPlace.topic}">
				<a class="topicDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>
				</c:if>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="" title="do not save changes" class="button" />
				<a id="AddNewValue" title="Add new Topic" href="${AddTopicUrl}"></a>
			</div>
			
		</fieldset>	
		<div id="EditTopicDocumentDiv"></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
		        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
		        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'hidden'); 
		        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
		        $j("#EditDocumentInManuscriptViewer").css('visibility', 'hidden');
		        $j("#EditDocumentInModal").css('visibility', 'hidden');
		        $j("#EditFactCheckDocument").css('visibility', 'hidden');
		        
		        $j('#close').click(function() {
					$j('#EditTopicsDocumentDiv').block({ message: $j('#question') }); 
					return false;
				});

		        $j(".deleteValue").click(function() {
					$j.get(this.href, function(data) {
						if(data.match(/KO/g)){
				            var resp = $j('<div></div>').append(data); // wrap response
						} else {
							$j("#EditTopicsDocumentDiv").load('${EditTopicsDocument}');
						}
			        });
					return false;
				});

				$j(".editValue").click(function() {
					$j("#EditTopicDocumentDiv").load($j(this).attr("href"));
					return false;
				});

				$j("#topicDescription").click(function() {
					Modalbox.show(this.href, {title: this.title, width: 750});
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditTopicDocumentDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>

<div id="question" style="display:none; cursor: default"> 
	<h1>discard changes?</h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>