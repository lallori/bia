<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
				<a id="editValue" href="${DeleteTopicDocument}"><img src="<c:url value="/images/button_cancel_form13.gif"/>" alt="Cancel value" title="Delete this entry"/></a>
				<a id="editValue" href="${EditTopicDocument}">edit value</a>
			</div>
		</c:forEach>
			
			<c:url var="EditTopicDocument" value="/de/docbase/EditTopicDocument.do">
				<c:param name="entryId" value="${currentTopicAndPlace.document.entryId}" />
				<c:param name="eplToId" value="0" />
			</c:url>

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<a id="AddTopicDocument" href="${EditTopicDocument}">Add new Topic</a>
			</div>
			
		</fieldset>	
		<div id="AddTopicDocumentDiv"></div>
	
		<script type="text/javascript">
			$(document).ready(function() {
				$("#AddTopicDocument").click(function(){$("#AddTopicDocumentDiv").load($(this).attr("href"));return false;});
				$("#editValue").click(function(){$("#EditTopicsDocumentDiv").load($(this).attr("href"));return false;});
			});
		</script>
	</form:form>
