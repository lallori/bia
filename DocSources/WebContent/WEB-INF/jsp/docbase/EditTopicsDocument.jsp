<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditTopicsDocumentURL" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
		<c:url var="AddTopicURL" value="/de/docbase/EditTopicDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="eplToId"  value="0" />
		</c:url>
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>
	</security:authorize>
		
	<form:form id="EditTopicsDocumentForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b><fmt:message key="docbase.editTopicsDocument.title.topics"/></b></legend>
		<c:forEach items="${topicsDocument}" var="currentTopicAndPlace">
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
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><input id="topic_${currentTopicAndPlace.eplToId}" name="topic_${currentTopicAndPlace.eplToId}" class="input_35c_disabled" type="text" value="${currentTopicAndPlace}" disabled="disabled" /></div>
					<div class="col_l"><a class="deleteIcon" title="Delete this entry" href="${DeleteTopicDocument}"></a></div>
					<div class="col_l"><a class="editValue" class="editValue" href="${EditTopicDocument}" title="Edit this entry"></a></div>
					<div class="col_l">
						<c:if test="${not empty currentTopicAndPlace.topic}">
						<a class="topicDescription" title="TOPIC DESCRIPTION" href="${ShowTopicDescription}"></a>
						</c:if>
					</div>
				</div>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="AddNewValue" class="button_small fr" type="submit" value="Add" title="Add new Topic" />		
			</div>
			
		</fieldset>	
		<div id="EditTopicDocumentDiv"></div>
		
		</form:form>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j.scrollTo("#EditTopicsDocumentForm");
				
		        $j("#EditDetailsDocument").css('visibility', 'hidden'); 
		        $j("#EditCorrespondentsDocument").css('visibility', 'hidden'); 
		        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
		        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
		        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
		        $j("#EditDocumentInModal").css('visibility', 'hidden');
		        $j("#EditFactCheckDocument").css('visibility', 'hidden');
		        
// 		        $j(".deleteIcon").click(function() {
// 					$j.get(this.href, function(data) {
// 						if(data.match(/KO/g)){
// 				            var resp = $j('<div></div>').append(data); // wrap response
// 						} else {
// 							$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
// 						}
// 			        });
// 					return false;
// 				});

				$j(".deleteIcon").click(function() {
				var temp = $j(this);
				$j("#EditTopicsDocumentDiv").block({ message: $j(".question"),
					css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
				});

				$j('.no').click(function() {
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j(".question").hide();
					$j("#EditTopicsDocumentDiv").append($j(".question"));
					$j(".blockUI").remove();
					$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
					return false; 
				}); 

				$j('.yes').click(function() { 
					$j.get(temp.attr("href"), function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditTopicsDocumentDiv").load('${EditTopicsDocumentURL}');
						return false;
					}
					
					return false; 
				}); 	
									     
				});
				return false;
			});

				$j(".editValue").click(function() {
					$j("#EditTopicDocumentDiv").load($j(this).attr("href"));
					return false;
				});

				$j(".topicDescription").click(function() {
					Modalbox.show(this.href, {title: this.title, width: 750});
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditTopicDocumentDiv").load("${AddTopicURL}");
					return false;
				});

				$j('#close').click(function() {
					$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
						$j("#body_left").html(html);
					}});

					return false;
				});
			});
		</script>
	
	<div class="question" style="display:none; cursor: default"> 
		<h1><fmt:message key="docbase.editTopicsDocument.deleteThisTopic"/></h1> 
		<input type="button" class="yes" value="Yes" /> 
		<input type="button" class="no" value="No" /> 
	</div>
