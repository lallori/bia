<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="editExtractDocumentDialogURL" value="/de/mview/EditExtractDocumentDialog.do"/>
	
	<div id="ShowSynopsisDocumentDiv">
		<div id="content">${synopsis}</div>
		
<%-- 	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS"> --%>
<!-- 		<input id="editExtract" class="button_medium" type="submit" value="Edit Extract"/> -->
<%-- 	</security:authorize> --%>
	<input id="exitSynopsis" class="button_small" type="submit" value="Close"/>
    </div>
    
    <input type="hidden" id="synopsisEntryId" value="${entryId}" />
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#ShowSynopsisDocumentDiv").dialog("option" , "position" , ['center', 'middle']);
			
			$j("#exitSynopsis").click(function(){
				if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					return false;
				}else{
					$j('#showTranscription').css('visibility','visible');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					return false;
				}
			});
			
			$j("#ui-dialog-title-ShowSynopsisDocumentDiv").text("SYNOPSIS / Start Folio: ${folioNum} - MDP: ${volNum}");
			
// 			$j('#editExtract').click(function() {
// 					$j("#ShowExtractDocumentDiv").dialog("close");
// 					$j("#EditExtractDocumentDiv").dialog("open");
// 					$j("#unvailableTranscribe").css('visibility', 'hidden');
// 					$j("#alreadyTranscribe").css('visibility', 'hidden');
// 					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
// 					$j("#transcribeAnyway").css('visibility','hidden');
// 					$j("#notExtract").css('visibility', 'hidden');
// 					$j("#extractTranscribe").css('visibility', 'hidden');
// 					$j("#readyToTranscribe").css('visibility', 'hidden');
// 					$j("#choiceThisFolioStart").css('visibility', 'hidden');
// 					$j("#transcribeDiv").append($j("#transcribeModeFromShow"));
// 					$j("#transcribeModeFromShow").css('display', 'inline');
// 					return false;
// 			});
			
			
		});
	</script>
	
	<span id="transcribeModeFromShow" class="transcribeMessage" style="display: none;">You are transcribing from<br />Folio: ${folioNum} MDP: ${volNum}</span>
