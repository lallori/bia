<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowConfirmCreateDocumentForumURL" value="/src/docbase/ShowConfirmCreateDocumentForum.do">
		<c:param name="entryId"   value="${requestCommand.entryId}" />
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1><fmt:message key="docbase.showConfirmCreateDocumentModal.title.sureToCreateYN"/></h1>
		
		<a id="yes" href="${ShowConfirmCreateDocumentForumURL}" class="button_small"><fmt:message key="docbase.showConfirmCreateDocumentModal.sureToCreateY"/></a>
	
		<a id="no" href="#" class="button_small"><fmt:message key="docbase.showConfirmCreateDocumentModal.sureToCreateN"/></a>
			
	</div>

	<script>
		$j(document).ready(function() {
			$j("#MB_content").css("height", "70px");
			
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
	 			$j.ajax({ type:"POST", url:'${ShowConfirmCreateDocumentForumURL}', data:null, async:false, success:function(html) { 
					$j("#ConfirmCreateForum").html(html);
				}});
				return false;
			});
		});
	</script>
