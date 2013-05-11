<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UndeleteDocumentURL" value="/de/docbase/UndeleteDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1><fmt:message key="docbase.showConfirmUndeleteDocument.title.sureToUndeleteYN"/></h1>
		
		<a id="yes" class="button_small" href="${UndeleteDocumentURL}"><fmt:message key="docbase.showConfirmUndeleteDocument.sureToUndeleteY"/></a>
	
		<a id="no" class="button_small" href="#"><fmt:message key="docbase.showConfirmUndeleteDocument.sureToUndeleteN"/></a>
	</div>

	<script>
		$j(document).ready(function() {
		
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"POST", url: '${UndeleteDocumentURL}', async:false, success:function(html) {
					$j("#DeleteThisRecordDiv").html(html);
					$j("#body_left").load('${ShowDocumentURL}');
				}});
				return false;
			});
		});
	</script>
