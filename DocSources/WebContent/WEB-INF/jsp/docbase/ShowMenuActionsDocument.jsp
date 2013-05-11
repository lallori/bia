<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteDocumentURL" value="/de/docbase/DeleteDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	<c:url var="UndeleteDocumentURL" value="/de/docbase/UndeleteDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
		
	<div id="ActionsMenuDiv">
		<h1>Use the buttons below to perform one of these predefined actions:</h1>
		
		<c:if test="${!document.logicalDelete}">
		<a id="deleteDocBase" href="${DeleteDocumentURL}">Delete this document record</a>
		</c:if>	
		<c:if test="${document.logicalDelete}">
		<a id="undeleteDocBase" href="${UndeleteDocumentURL}">Undelete this document record</a>
		</c:if>					
		<input id="close" class="button_small" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#deleteDocBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "DELETE THIS DOCUMENT RECORD", width: 450, height: 150});
				return false;
			});
			$j("#undeleteDocBase").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "UNDELETE THIS DOCUMENT RECORD", width: 450, height: 150});
				return false;
			});
		});
	</script>
