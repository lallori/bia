<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTopicsDocument" value="/de/docbase/EditTopicsDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
		<c:url var="AddTopicUrl" value="/de/docbase/EditTopicDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
			<c:param name="eplToId"  value="0" />
		</c:url>
		<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.document.entryId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditTopicsDocumentForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>TOPICS</b></legend>
		<c:forEach items="${command.document.eplToLink}" var="currentTopicAndPlace">
			<c:url var="EditTopicDocument" value="/de/docbase/EditTopicDocument.do">
				<c:param name="entryId" value="${command.document.entryId}" />
				<c:param name="eplToId" value="${currentTopicAndPlace.eplToId}" />
			</c:url>

			<c:url var="DeleteTopicDocument" value="/de/docbase/DeleteTopicDocument.do" >
				<c:param name="entryId" value="${command.document.entryId}" />
				<c:param name="eplToId" value="${currentTopicAndPlace.eplToId}" />
			</c:url>

			<div>
      			<input id="topic_${currentTopicAndPlace.eplToId}" name="topic_${currentTopicAndPlace.eplToId}" class="input_28c_disabled" type="text" value="${currentTopicAndPlace}" disabled="disabled" />
				<a class="deleteValue" id="deleteValue" href="${DeleteTopicDocument}"><img src="<c:url value="/images/button_cancel_form13.gif"/>" alt="Cancel value" title="Delete this entry"/></a>
				<a class="editValue" id="editValue" href="${EditTopicDocument}">edit value</a>
			</div>
		</c:forEach>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<a id="AddTopicDocument" href="${AddTopicUrl}">Add new Topic</a>
			</div>
			
		</fieldset>	
		<div id="EditTopicDocumentDiv"></div>
	
		<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId"   value="${command.entryId}" />
		</c:url>

		<script type="text/javascript">
			$j(document).ready(function() {
		        $j("#EditDetailsDocument").removeAttr("href"); 
		        $j("#EditCorrespondentsOrPeopleDocument").removeAttr("href"); 
		        $j("#EditExtractOrSynopsisDocument").removeAttr("href"); 
		        $j("#EditFactCheckDocument").removeAttr("href");
		        
		        $j('#close').click(function() {
					$j('#EditTopicsDocumentDiv').block({ message: $j('#question') }); 
					return false;
				});
	      
				$j('#no').click(function() { 
					$j.unblockUI();
					$j(".blockUI").fadeOut("slow");
					$j('#question').hide();
					$j('#EditFactCheckDocumentDiv').append($j("#question"));
					$j(".blockUI").remove();
					return false; 
				}); 
		        
				$j('#yes').click(function() { 
					$j.ajax({ url: '${ShowDocument}', cache: false, success:function(html) { 
						$j("#body_left").html(html);
					}});
						
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

				$j("#AddTopicDocument").click(function(){
					$j("#EditTopicDocumentDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>
