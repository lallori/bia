<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="editExtractDocumentDialogURL" value="/de/mview/EditExtractDocumentDialog.do"/>
	
	<c:url var="ShowSynopsisDialogURL" value="/src/mview/ShowSynopsisDocumentDialog.do" />

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS, ROLE_COMMUNITY_USERS">
	<div id="ShowExtractDocumentDiv">
		<div id="content">${docExtract}</div>
	</security:authorize>	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<input id="editExtract" class="button_medium" type="submit" value="<fmt:message key="mview.showExtractDocumentDialog.editTranscription.alt"/>"/>
	</security:authorize>
	<input id="showSynopsis" class="button_medium" type="submit" value="<fmt:message key="mview.showExtractDocumentDialog.showSynopsis.alt"/>"/>
	<input id="exitExtract" class="button_small" type="submit" value="<fmt:message key="mview.showExtractDocumentDialog.close.button"/>"/>
    </div>
    
    <input type="hidden" id="extractEntryId" value="${entryId}" />
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#ShowExtractDocumentDiv").dialog("option" , "position" , ['center', 'middle']);
			
// 			$j("#exitExtract").click(function(){
// 				if($j("#ShowSynopsisDocumentDiv").dialog("isOpen")){
// 					$j("#ShowExtractDocumentDiv").dialog("close");
// 					return false;
// 				}else{
// 					$j('#showTranscription').css('visibility','visible');
// 					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
// 					$j("#ShowExtractDocumentDiv").dialog("close");
// 					return false;
// 				}
// 			});
			
			$j("#ui-dialog-title-ShowExtractDocumentDiv").text("<fmt:message key="mview.showExtractDocumentDialog.showTranscriptionWindow.title"/> / <fmt:message key="mview.showExtractDocumentDialog.youAreTranscribingFrom.folio"/>: ${folioNum} - <fmt:message key="mview.showExtractDocumentDialog.youAreTranscribingFrom.volume"/>: ${volNum}");
			
			$j("#showSynopsis").click(function(){
				$j('<div id="ShowSynopsisDocumentDiv"></div>').dialog({                                                                                                                                                                   
					autoOpen: false,
					width: 352,
					minWidth: 350,
					minHeight: 200,
					maxHeight: 500,
					title: '<fmt:message key="mview.showExtractDocumentDialog.showSynopsisWindow.title"/>',
					position: ['center','middle'],                                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
						$(this).load('${ShowSynopsisDialogURL}' + '?entryId=' + $j('#extractEntryId').val());
					},
					//drag: function(event, ui) {$j(this).append(ui.position.left);},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);}, 
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});
				
				$j("#ShowExtractDocumentDiv").dialogExtend("minimize");
				$j("#ShowSynopsisDocumentDiv").dialog("open");
				return false;
			});
			
			$j('#editExtract').click(function() {
					$j("#ShowExtractDocumentDiv").dialog("close");
					if($j("#ShowSynopsisDocumentDiv").length != 0 && $j("#ShowSynopsisDocumentDiv").dialog("isOpen")){
						$j("#ShowSynopsisDocumentDiv").dialog("close");
					}
					$j("#EditExtractDocumentDiv").dialog("open");
					$j("#unvailableTranscribe").css('visibility', 'hidden');
					$j("#alreadyTranscribe").css('visibility', 'hidden');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
					$j("#transcribeAnyway").css('visibility','hidden');
					$j("#notExtract").css('visibility', 'hidden');
					$j("#extractTranscribe").css('visibility', 'hidden');
					$j("#readyToTranscribe").css('visibility', 'hidden');
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
					$j("#transcribeDiv").append($j("#transcribeModeFromShow"));
					$j("#transcribeModeFromShow").css('display', 'inline');
					return false;
			});
		});
	</script>
	
	<span id="transcribeModeFromShow" class="transcribeMessage" style="display: none;"><fmt:message key="mview.showExtractDocumentDialog.youAreTranscribingFrom"/><br /><fmt:message key="mview.showExtractDocumentDialog.youAreTranscribingFrom.folio"/>: ${folioNum} <fmt:message key="mview.showExtractDocumentDialog.youAreTranscribingFrom.volume"/>: ${volNum}</span>
