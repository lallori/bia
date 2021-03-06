<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteDocumentURL" value="/de/docbase/DeleteDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	<c:url var="CheckDocumentIsDeletableURL" value="/de/docbase/CheckDocumentIsDeletable.json">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1><fmt:message key="docbase.showConfirmDeleteDocument.title.sureToDeleteYN"/></h1>
		
		<a id="yes" class="button_small" href="${DeleteDocumentURL}"><fmt:message key="docbase.showConfirmDeleteDocument.sureToDeleteY"/></a>
	
		<a id="no" class="button_small" href="#"><fmt:message key="docbase.showConfirmDeleteDocument.sureToDeleteN"/></a>
	</div>

	<script>
		$j(document).ready(function() {		
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckDocumentIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						//$j("#DeleteThisRecordDiv").append('<h1>Please remove ALL people, places and topics indexed to this document before trying to deleting it</h1>');
						$j("#DeleteThisRecordDiv").append('<h1>This document is not deletable...if you want to delete it please contact the admin.</h1>');
					} else {
						$j.ajax({ type:"POST", url: '${DeleteDocumentURL}', async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").html(html);
							$j("#body_left").load('${ShowDocumentURL}');
						}});
					}
				}});
				return false;
			});
		});
	</script>
