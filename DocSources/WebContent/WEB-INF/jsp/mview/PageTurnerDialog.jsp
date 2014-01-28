<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ContextPathURL" value="/"/>

	<c:url var="PageTurnerDialogURL" value="/src/mview/JumpToFolio.json"/>

	<c:url var="SearchAjaxURL" value="/src/mview/SearchCarta.json"/>
	
	<c:url var="LinkedDocumentUrl"	value="/src/mview/GetLinkedDocument.json"/>
	
	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>

	<c:url var="GetLinkedDocumentURL" value="/src/mview/GetLinkedDocument.json"/>

	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
	<c:url var="PersonalNotesDialogURL" value="/src/mview/EditPersonalNotesDialog.do"/>
	
	<c:url var="VolumeSummaryDialogURL" value="/src/mview/ShowSummaryVolumeDialog.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
	</c:url>
	
	<c:url var="GetLinkedDocumentURL" value="/src/mview/GetLinkedDocument.json">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${command.imageOrder}" />
		<c:param name="imageName" value="${image}" />
		<c:param name="imageType" value="${image.imageType}" />
	</c:url>
	
	<c:url var="GetLinkedDocumentURLUpdate" value="/src/mview/GetLinkedDocument.json" />

	<c:url var="currentPage" value="${caller}">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${command.imageOrder}" />
		<c:param name="total" value="${command.total}" />
		<c:param name="totalRubricario" value="${command.totalRubricario}" />
		<c:param name="totalCarta" value="${command.totalCarta}" />
		<c:param name="totalAppendix" value="${command.totalAppendix}" />
		<c:param name="totalOther" value="${command.totalOther}" />
		<c:param name="totalGuardia" value="${command.totalGuardia}" />
	</c:url>

	<c:url var="nextPage" value="/src/mview/SearchCarta.json">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${command.imageOrder + 1}" />
		<c:param name="total" value="${command.total}" />
		<c:param name="totalRubricario" value="${command.totalRubricario}" />
		<c:param name="totalCarta" value="${command.totalCarta}" />
		<c:param name="totalAppendix" value="${command.totalAppendix}" />
		<c:param name="totalOther" value="${command.totalOther}" />
		<c:param name="totalGuardia" value="${command.totalGuardia}" />
		<c:param name="nextPage" value="true" />
	</c:url>

	<c:url var="previousPage" value="/src/mview/SearchCarta.json">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${command.imageOrder - 1}" />
		<c:param name="total" value="${command.total}" />
		<c:param name="totalRubricario" value="${command.totalRubricario}" />
		<c:param name="totalCarta" value="${command.totalCarta}" />
		<c:param name="totalAppendix" value="${command.totalAppendix}" />
		<c:param name="totalOther" value="${command.totalOther}" />
		<c:param name="totalGuardia" value="${command.totalGuardia}" />
		<c:param name="previousPage" value="true" />
	</c:url>
	
	<c:url var="indexOfNamesURL" value="/src/mview/SearchCarta.json">
		<c:param name="entryId" value="${command.entryId}" />
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageType" value="R" />
		<c:param name="imageOrder" value="${1}" />
		<c:param name="total" value="${command.total}" />
		<c:param name="totalRubricario" value="${command.totalRubricario}" />
		<c:param name="totalCarta" value="${command.totalCarta}" />
		<c:param name="totalAppendix" value="${command.totalAppendix}" />
		<c:param name="totalOther" value="${command.totalOther}" />
		<c:param name="totalGuardia" value="${command.totalGuardia}" />
		<c:param name="modeEdit" value="${command.modeEdit}" />
	</c:url>
	
	<c:url var="exploreDocumentURL" value="/src/volbase/ShowExplorerVolume.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
		<c:param name="imageOrder" value="${command.imageOrder + 1}" />
		<c:param name="total" value="${command.total}" />
		<c:param name="totalRubricario" value="${command.totalRubricario}" />
		<c:param name="totalCarta" value="${command.totalCarta}" />
		<c:param name="totalAppendix" value="${command.totalAppendix}" />
		<c:param name="totalOther" value="${command.totalOther}" />
		<c:param name="totalGuardia" value="${command.totalGuardia}" />
		<c:param name="flashVersion" value="false" />
	</c:url>
	
	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	
	<c:url var="ShowExtractDialogURL" value="/src/mview/ShowExtractDocumentDialog.do" />

	<c:url var="EditExtractDialogUrl" value="/de/mview/EditExtractDocumentDialog.do" />

	<c:url var="EditSynopsisDialogUrl" value="/de/mview/EditSynopsisDocumentDialog.do" />
	
	<c:url var="ShowDocumentsAlreadyURL" value="/src/docbase/ShowSameFolioDocuments.do" />
	
	<c:url var="GetImageAnnotationURL" value="/src/mview/GetImageAnnotation.json" />
		
	<c:url var="UpdateAnnotationsURL" value="/src/mview/UpdateAnnotations.json" />
		
<div id="PageTurnerVerticalDiv">

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
	<div id="transcribeDiv">
	
		<!--  Rubricario (Index of Names), Guardie, or Coperte - you can't trascribe these kind of items -->
		<span id="unvailableTranscribe" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.transcriptionAvailableForFoliosOnly"/></span>
		
		<!--  This document has already been transcribed, you can decide whether see its transcription or see its record-->
		<a id="alreadyTranscribe" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentAlreadyTranscribed"/></a>
		<a id="lettersHere" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentOnStartFolio"/></a>
		<a id="transcriptionsHere" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.transcriptionHere"/></a>
		<a id="showTranscription" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showTranscription.alt"/>" style="display: none;"><fmt:message key="mview.pageTurnerDialog.showTranscription"/></a>
		<a id="showAlreadyTranscribed" href="${ShowDocumentURL}" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showThisRecord.alt"/>" style="display: none;"><fmt:message key="mview.pageTurnerDialog.showThisRecord"/></a>
		<a id="showAlreadyTranscribedDocs" href="${ShowDocumentsAlreadyURL}" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showThisRecords.alt"/>" style="display: none; cursor:pointer;"><fmt:message key="mview.pageTurnerDialog.showThisRecords"/></a>
		<a id="transcribeAnyway" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.transcribeAnyway.alt"/>" style="display: none; cursor: pointer;"><fmt:message key="mview.pageTurnerDialog.transcribeAnyway"/></a>
		
		<!--  This document has not been transcribed-->
		<a id="readyToTranscribe" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.readyToTranscribe.alt"/>" style="display: none; cursor: pointer"><fmt:message key="mview.pageTurnerDialog.readyToTranscribe"/> </a>
		<a id="choiceThisFolioStart" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.chooseThisAsStartFolio.alt"/>" style="display: none; cursor: pointer"><fmt:message key="mview.pageTurnerDialog.setThisAs"/> <font style="font-style:italic;"><fmt:message key="mview.pageTurnerDialog.startFolio"/></font></a>
		
		<!--  This document has a record in the database but it is not transcribed-->
		<a id="notExtract" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentEnteredButNotTranscribed"/></a>
		<a id="extractTranscribe" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.transcribeThisDocument.alt"/>" style="display: none; cursor: pointer;" ><fmt:message key="mview.pageTurnerDialog.transcribeThisDocument"/></a>
		
		
		<input type="hidden" id="currentEntryId" value="${command.entryId}" />
		<input type="hidden" id="currentImageOrder" value="${command.imageOrder}" />
    </div>
	</security:authorize>
	
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<div id="transcribeDiv">
	
		<!--  Rubricario (Index of Names), Guardie, or Coperte - you can't trascribe these kind of items -->
		<span id="unvailableTranscribe" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.transcriptionAvailableForFoliosOnly"/></span>
		
		<!--  This document has already been transcribed, you can decide whether see its transcription or see its record-->
		<a id="alreadyTranscribe" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentAlreadyTranscribed"/></a>
		<a id="lettersHere" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentOnStartFolio"/></a>
		<a id="transcriptionsHere" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.transcriptionHere"/></a>
		<a id="showTranscription" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showTranscription.alt"/>" style="display: none;"><fmt:message key="mview.pageTurnerDialog.showTranscription"/></a>
		<a id="showAlreadyTranscribed" href="${ShowDocumentURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecord.alt"/>"  class="transcribe button_medium" style="display: none;"><fmt:message key="mview.pageTurnerDialog.showThisRecord"/></a>
		<a id="showAlreadyTranscribedDocs" href="${ShowDocumentsAlreadyURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecords.alt"/>" class="transcribe button_medium" style="display: none; cursor:pointer;"><fmt:message key="mview.pageTurnerDialog.showThisRecords"/></a>
		
		<!--  This document has a record in the database but it is not transcribed-->
		<a id="notExtract" class="transcribeMessage" style="display: none;"><fmt:message key="mview.pageTurnerDialog.documentEnteredButNotTranscribed"/></a>
		
		
		<input type="hidden" id="currentEntryId" value="${command.entryId}" />
		<input type="hidden" id="currentImageOrder" value="${command.imageOrder}" />
    </div>
	</security:authorize>

	<div id="line"></div>

	<div id="prevNextButtons">
	    <div id="prevButton">
		<c:if test="${command.imageOrder == 1}">
	    	<a id="previous" title="<fmt:message key="mview.pageTurnerDialog.previousFolio"/>"></a>
		</c:if>
		<c:if test="${command.imageOrder > 1}">
			<a id="previous" href="${previousPage}" title="<fmt:message key="mview.pageTurnerDialog.previousFolio"/>"></a>
		</c:if>
		</div>
		<div id="folio" title="Warning!" style="display:none"> 
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><fmt:message key="mview.pageTurnerDialog.saveBeforeUsingPageTurner.alert"/></p> 
		</div> 
		<div id="nextButton">
		<c:if test="${command.imageOrder == command.total}">
			<a id="next" title="<fmt:message key="mview.pageTurnerDialog.previousFolio"/>"></a>
		</c:if>
		<c:if test="${command.imageOrder < command.total}">
			<a id="next" href="${nextPage}" title="<fmt:message key="mview.pageTurnerDialog.nextFolio"/>"></a>
		</c:if>
		</div>
	</div>
	
	<div id="folioMoveTo">
		<div id="insertErrorClient" class="errorClient" display="none" style="color:red;" ></div>
		<div id="folioErrorClient" class="errorClient" display="none" style="color:red;" ></div>
		<form:form id="moveToFolioForm" method="post" class="edit" action="${PageTurnerDialogURL}">
			<div class="goToPage">
				<span><b>Go To page</b></span> 
				
				<c:if test="${hasInsert}">
					<a class="helpIcon" title="Specify the insert number in the first input text and the insert extension in the second one (only if needed)">?</a>
					<label for="insertNum" id="insertNumLabel" class="folioLabel">Insert:</label>
					<div class="labels">
						<input id="insertNum" name="insertNum" class="input_4c" type="text" value="" />
						<input id="insertLet" name="insertLet" class="input_4c" type="text" value="" />
					</div>
				</c:if>
				
				<a class="helpIcon" title="Specify the folio number in the first input text and the folio extension in the second one (only if needed)">?</a>
				<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Folio:</label>
				<div class="labels">
					<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4c" type="text" value="" />
					<input id="missedNumbering" name="missedNumbering" class="input_4c" type="text" value="" />
				</div>
				<input id="go" class="button_mini" type="submit" value="Go" onclick="return validateForm();"/>
			</div>
			
			<form:hidden path="entryId" />
			<form:hidden path="volNum" />
			<form:hidden path="volLetExt" />
			<form:hidden path="imageType" value="C"/>
			<form:hidden path="imageOrder" />
			<form:hidden path="total" value="${command.total}" />
			<form:hidden path="totalRubricario" value="${command.totalRubricario}" />
			<form:hidden path="totalCarta" value="${command.totalCarta}" />
			<form:hidden path="totalAppendix" value="${command.totalAppendix}" />
			<form:hidden path="totalOther" value="${command.totalOther}" />
			<form:hidden path="totalGuardia" value="${command.totalGuardia}" />
			<form:hidden path="modeEdit" value="${command.modeEdit}" />
			<form:hidden id="formSubmitting" path="formSubmitting" value="${command.formSubmitting}" />
		</form:form>
	</div>
	
	
	<a id="volumeSummary" href="#" title="<fmt:message key="mview.pageTurnerDialog.volumeSummary.icon.title"/>"></a>
    
    <c:if test="${command.totalRubricario > 0}">
        <a id="indexNames" href="${indexOfNamesURL}" title="<fmt:message key="mview.pageTurnerDialog.indexOfNames.icon.title"/>" style="cursor: pointer;"></a>
    </c:if>
    
	<a id="personalNotesButton" href="#" title="<fmt:message key="mview.pageTurnerDialog.personalNotes.button"/>" style="cursor: pointer;"></a>
    
    <div id="line2"></div>
    
	<a id="exitButton" href="#" class="button_small" style="cursor: pointer;"><fmt:message key="mview.pageTurnerDialog.exit.button"/></a>
	<input type="hidden" id="editExtractModify" value="" />
	<input type="hidden" id="editSynopsisModify" value="" />
	
</div>

	<div id="exit" title="Alert" style="display:none">
		<c:if test="${command.modeEdit == true}">
			<p id="closeMessage"><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><fmt:message key="mview.pageTurnerDialog.exitWithoutSaving.alert"/></p>
		</c:if> 
		<c:if test="${command.modeEdit == false}">
			<p id="closeMessage"><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><fmt:message key="mview.pageTurnerDialog.exit.alert"/></p>
		</c:if> 
	</div>



	<script type="text/javascript">
		$j(document).ready(function() {
			
			/**
			 * This function displays the given sections (messages or buttons).
			 * Possible sections are:
			 * 		#alreadyTranscribe 			-> message
			 *		#choiceThisFolioStart 		-> button 
			 *		#extractTranscribe			-> button
			 *		#lettersHere				-> message
			 *		#notExtract					-> message
			 *		#readyToTranscribe			-> button
			 *		#showAlreadyTranscribed		-> button
			 *		#showAlreadyTranscribedDocs	-> button
			 *		#showTranscription			-> button
			 *		#transcribeAnyway			-> button
			 *		#transcriptionsHere			-> message
			 *		#unvailableTranscribe		-> message
			 *
			 * @params sections the sections to show (listed in a js-array)
			 */
			function display(sections) {
				var notDisplayedSections = new Array(
					"#alreadyTranscribe",
					"#choiceThisFolioStart",
					"#extractTranscribe",
					"#lettersHere",
					"#notExtract",
					"#readyToTranscribe",
					"#showAlreadyTranscribed",
					"#showAlreadyTranscribedDocs",
					"#showTranscription",
					"#transcribeAnyway",
					"#transcriptionsHere",
					"#unvailableTranscribe"
				);
				
				if (typeof sections !== "undefined") {
					var sec;
					if (Object.prototype.toString.apply(sections) !== '[object Array]') {
						sec = new Array();
						sec[0] = sections;
					} else {
						sec = sections;
					}
					
					for(i = 0; i < sec.length; i++) {
						var selector = $j(sec[i]);
						if (typeof selector !== "undefined") {
							var idx = notDisplayedSections.indexOf(sec[i]);
							if (idx > -1) {
								notDisplayedSections.splice(idx, 1);
							}
							$j(selector).css('display', 'block');
						}
					}
				}
				
				for (i = 0; i < notDisplayedSections.length; i++) {
					var selector = $j(notDisplayedSections[i]);
					if (typeof selector !== "undefined") {
						$j(selector).css('display', 'none');
					}
				}
			};
			
			var annotations = new Array();
			
			var pageTurnerParams = {
				searchUrl: '${SearchAjaxURL}', 
		        getLinkedDocumentUrl:  '${LinkedDocumentUrl}',
				imagePrefix: '${ImagePrefixURL}', 
				IIPImageServer: '${IIPImageServerURL}', 
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				textVolume: '<fmt:message key="mview.credits.volume"/>',
			    textExtension: '<fmt:message key="mview.credits.extension"/>',
			    textInsert: '<fmt:message key="mview.credits.insert"/>',
			    textIndexOfNames: '<fmt:message key="mview.credits.indexOfNames"/>',
			    textFolio: '<fmt:message key="mview.credits.folio"/>',
			    textAttachment: '<fmt:message key="mview.credits.attachment"/>',
			    textGuardia : '<fmt:message key="mview.credits.guardia"/>',
			    textCoperta : '<fmt:message key="mview.credits.coperta"/>',
			    textSpine : '<fmt:message key="mview.credits.spine"/>',
			    textRecto : '<fmt:message key="mview.credits.recto"/>',
			    textVerso : '<fmt:message key="mview.credits.verso"/>',
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			};
			
			$j("#moveToFolioForm").pageTurnerForm(pageTurnerParams);
			$j("#indexNames").pageTurnerPage(pageTurnerParams);
			$j("#previous").pageTurnerPage(pageTurnerParams);
			$j("#next").pageTurnerPage(pageTurnerParams);
			
			var $dialogPersonalNotes = $j('<div id="DialogPersonalNotesDiv"></div>').dialog({
				autoOpen: false,
				width: 352,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: '<fmt:message key="mview.pageTurnerDialog.personalNotesWindow.title"/>',
				position: ['right','top'],
				closeOnEscape: false,
				maximized:false,
				open: function(event, ui) { 
            		$(this).load('${PersonalNotesDialogURL}');
           		},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			}).dialogExtend({"minimize" : true});
			
			var $dialogVolumeSummary = $j('<div id="DialogVolumeSummaryDiv"></div>').dialog({
				resizable: false,
				width: 550,
				height: 600, 
				title: '<fmt:message key="mview.pageTurnerDialog.volumeSummaryDialog.title"/>',
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				open: function(event, ui) { 
            		$(this).load('${VolumeSummaryDialogURL}');
           		},
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				}
			});
			
			$j("#exit").dialog({
				resizable: false,
				height:150,
				modal: true,
				autoOpen : false,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				buttons: {
					YES : function() {
						$j(this).dialog('close');
						window.close();
					},
					NO: function() {
						$j(this).dialog('close');
					}
					
				}
			});

			$j("#notFound").dialog({
				resizable: false,
				height:140,
				modal: true,
				autoOpen : false,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				}
			});
			
			var $dialogShowExtract = $j('<div id="ShowExtractDocumentDiv"></div>')
			.dialog({
				autoOpen: ${not empty command.showExtract ? command.showExtract : false},
				width: 420,
				minWidth: 420,
				minHeight: 200, 
				maxHeight: 500,
				title: '<fmt:message key="mview.pageTurnerDialog.showTranscriptionWindow.title"/>',
				position: ['center','middle'],
				closeOnEscape: false,
				maximized:false,
				
				open: function(event, ui) { 
					$j(".ui-dialog-titlebar-close").hide(); 
					$(this).load('${ShowExtractDialogURL}' + '?entryId=' + $j('#currentEntryId').val());
				},
				//drag: function(event, ui) {$j(this).append(ui.position.left);},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);}, 
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			}).dialogExtend({"minimize" : true});
			
			var $dialogExtract = $j('<div id="EditExtractDocumentDiv"></div>')
			.dialog({
				autoOpen: false,
				width: 355,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: '<fmt:message key="mview.pageTurnerDialog.editTranscriptionWindow.title"/>',
				position: ['center','middle'],
				closeOnEscape: false,
				maximized:false,
				
				open: function(event, ui) { 
					$j(".ui-dialog-titlebar-close").hide(); 
					$(this).load('${EditExtractDialogUrl}' + '?entryId=' + $j('#currentEntryId').val());
				},
				//drag: function(event, ui) {$j(this).append(ui.position.left);},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);}, 
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			}).dialogExtend({"minimize" : true});
			
			var $dialogSynopsis = $j('<div id="EditSynopsisDocumentDiv"></div>')
			.dialog({
				autoOpen: false,
				width: 352,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: '<fmt:message key="mview.pageTurnerDialog.editSynopsisWindow.title"/>',
				position: [$j("#EditExtractDocumentDiv").dialog("option" , "width") + 8 , "middle"],
				closeOnEscape: false,
				maximized:false,
				
				open: function(event, ui) { 
					$j(".ui-dialog-titlebar-close").hide(); 
					$(this).load('${EditSynopsisDialogUrl}' + '?entryId=' + $j('#currentEntryId').val());
				},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			}).dialogExtend({"minimize" : true});
			
			$j('#exitButton').click(function() {
				$j('#exit').dialog('open');
				if($j("#editExtractModify").val() == 1 || $j("editSynopsisModify").val() == 1){
					$j("#closeMessage").text("<fmt:message key="mview.pageTurnerDialog.exitWithoutSaving.alert"/>");
				}else{
					$j("#closeMessage").text("<fmt:message key="mview.pageTurnerDialog.exit.alert"/>");
				}
				return false;
			});
			
			$j('#showTranscription').click(function() {
				if ($dialogShowExtract.dialog("isOpen")) {
					$dialogShowExtract.load('${ShowExtractDialogURL}' + '?entryId=' + $j('#currentEntryId').val());
					$dialogShowExtract.dialogExtend("restore");
				} else {
					$dialogShowExtract.dialog("open");
				}
				$j('#showTranscription').css('display','none');
				$j("#showAlreadyTranscribed").css('display', 'block');
				return false;
			});
			
			$j('#extractTranscribe').click(function() {
				if ($dialogExtract.dialog("isOpen")) {
					$dialogExtract.dialog("close");
					return false;
				} else {
					$dialogExtract.dialog("open");
					return false;
				}
			});
			
			$j('#volumeSummary').click(function(){
				if ($dialogVolumeSummary.dialog("isOpen")) {
					$dialogVolumeSummary.dialog("close");;
					return false;
				} else {
					$dialogVolumeSummary.dialog("open");
					return false;
				}
			});

			$j('#personalNotesButton').click(function() {
				if ($dialogPersonalNotes.dialog("isOpen")) {
					$dialogPersonalNotes.dialog("option", "zindex", 1014).dialogExtend("restore");
					return false;
				} else {
					$dialogPersonalNotes.dialog("open");
					return false;
				}
			});
			
			
			
			$j.ajax({
				type: "GET",
				url: "${GetLinkedDocumentURL}",
				async: false,
				success: function(data) {
					// We set currentImage
					currentImage = data.imageId;
					$j("#currentImageOrder").val(data.imageOrder);
					if (transcribing == false) {
						if ($dialogExtract.dialog("isOpen") || $j("#EditExtractDocumentForm").length != 0) {
							display();
							$j("#transcribeDiv").append($j("#transcribeMode"));
							$j("#transcribeMode").css('display', 'inline');
						} else {
							if (data.error == 'wrongType' || data.imageType == 'R') {
								display("#unvailableTranscribe");
							} else if (data.linkedDocumentOnStartFolio == true || data.linkedDocumentOnTranscribeFolio == true) {
								if (data.isExtract == false) {
									$j("#currentEntryId").val(data.entryId);
									display(new Array("#notExtract","#extractTranscribe"));
								} else {
									if (data.countAlreadyEntered == 1) {
										var isOpenExtractDoc = $j("#ShowExtractDocumentDiv").dialog("isOpen") && $j("#extractEntryId").val() == $j('#currentEntryId').val();
										$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
										$j("#currentEntryId").val(data.entryId);
										
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This is a start folio of one letter and it is already transcribed
											display(new Array("#alreadyTranscribe", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of one letter
											display(new Array("#lettersHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else {
											// This folio has one transcription attached
											display(new Array("#transcriptionsHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										}
									} else if (data.countAlreadyEntered > 1) {
										$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This folio is a start folio of one or more letters and has one of more transcriptions attached
											display(new Array("#alreadyTranscribe","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of more than one letter
											display(new Array("#lettersHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else {
											// This folio has more than one transcription attached
											display(new Array("#transcriptionsHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										}
									}
								}
								
							} else if (data.linkedDocumentOnStartFolio == false && data.linkedDocumentOnTranscribeFolio == false) {
								display();
								<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
									$j("#readyToTranscribe").css('display', 'block');
								</security:authorize>
							} else {
								display();
							}
						}
					} else {
						if ($j("#EditExtractDocumentForm").length != 0) {
							display();
							$j("#transcribeDiv").append($j("#transcribeMode"));
							$j("#transcribeMode").css('display', 'inline');
						} else if (data.linkedDocumentOnStartFolio == true || data.linkedDocumentOnTranscribeFolio == true) { 							
							//In this case we choose the start folio to transcribe
							$j("#choiceThisFolioStart").css('opacity', '0.5');
							$j("#transcribeDiv").append($j("#transcribeMode"));
							$j("#transcribeMode").css('display', 'block');
							
							if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
								display(new Array("#alreadyTranscribe", "#choiceThisFolioStart"));
							} else if (data.linkedDocumentOnStartFolio == true) {
								display(new Array("#lettersHere", "#choiceThisFolioStart"));
							} else {
								display(new Array("#transcriptionsHere", "#choiceThisFolioStart"));
							}
						} else if (data.linkedDocumentOnStartFolio == false && data.linkedDocumentOnTranscribeFolio == false) {
							display("#choiceThisFolioStart");
							$j("#choiceThisFolioStart").css('opacity', '1');
							$j("#transcribeDiv").append($j("#transcribeMode"));
							$j("#transcribeMode").css('display', 'block');
						}
					}
				},
				error: function(data) {
					alert('Server error!');
				}
			});
			
			$j('#readyToTranscribe').click(function() {
				$j("#choiceThisFolioStart").css('display', 'block');
				$j("#readyToTranscribe").css('display', 'none');
				transcribing=true;
				imageDocumentToCreate=currentImage;
				return false;
			});
			
			$j('#transcribeAnyway').click(function() {
				if($j("#alreadyTranscribe").css('display') == 'block'){
					$j("#showTranscription").css('display', 'none');
				}
				$j("#choiceThisFolioStart").css('display', 'block');
				$j("#transcribeAnyway").css('display', 'none');
				transcribing=true;
				imageDocumentToCreate=currentImage;
				return false;
			});
			
			$j('#choiceThisFolioStart').click(function() {
// 				if($j("#alreadyTranscribe").css('display') != 'block'){
					$j("#choiceThisFolioStart").css('display', 'block');
					$j("#readyToTranscribe").css('display', 'none');
					imageDocumentFolioStart=currentImage;
					var contextPath ="${ContextPathURL}";
					var urlToExplore;
					var volLetExt;
					if("${command.volLetExt}" == ""){
						volLetExt = "";
					}
					else{
						volLetExt = "${command.volLetExt}";
					}
					urlToExplore = contextPath + "src/volbase/ShowExplorerVolume.do?volNum=" + ${command.volNum} + "&volLetExt=" + volLetExt + "&imageOrder=" + $j("#currentImageOrder").val() + "&total=" + ${command.total} + "&totalRubricario=" + ${command.totalRubricario} + "&totalCarta=" + ${command.totalCarta} + "&totalAppendix=" + ${command.totalAppendix} + "&totalOther=" + ${command.totalOther} + "&totalGuardia=" + ${command.totalGuardia} + "&flashVersion=false&showHelp=false&showThumbnail=false";
					var urlToTranscribe = contextPath + "de/docbase/TranscribeAndContextualizeDocument.do?imageDocumentToCreate=" + imageDocumentToCreate + "&imageDocumentFolioStart=" + imageDocumentFolioStart + "&imageOrder=" + $j("#currentImageOrder").val() + "&total=" + ${command.total} + "&totalRubricario=" + ${command.totalRubricario} + "&totalCarta=" + ${command.totalCarta} + "&totalAppendix=" + ${command.totalAppendix} + "&totalOther=" + ${command.totalOther} + "&totalGuardia=" + ${command.totalGuardia};
					window.opener.$j("#body_left").load(urlToTranscribe);
					$j("#choiceThisFolioStart").css('display', 'none');
					//To open volume explorer in a tab
					var tabName = "<span id='titleTab${command.volNum}" + volLetExt + "'>Volume ${command.volNum}" + volLetExt + "</span>";
					var numTab = 0;
					var hrefRemove = "";
					
					//Check if already exist a tab with this person
					var tabExist = false;
					window.opener.$j("#tabs ul li a").each(function(){
						var toTest = "";
						toTest += this.text;
						if(!tabExist){
							if(toTest != ""){
								numTab++;
							}
						}
							
						if(this.text == tabName || toTest.indexOf("Volume ${command.volNum}" + volLetExt) != -1){
							tabExist = true;
							hrefRemove = $j(this).attr("href");
						}
					});
	// 				if(tabExist){
	// 					alert(hrefRemove);
	// 					window.opener.$j("#tabs").tabs("remove", hrefRemove);
	// 				}
								
					if(!tabExist){
						window.opener.$j( "#tabs" ).tabs( "add" , urlToExplore, tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
						window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					}else{
						window.opener.$j("#tabs").tabs("select", numTab);
						window.opener.$j('#tabs ul li').eq(numTab).data('loaded', false).find('a').attr('href', urlToExplore);
						window.opener.$j("#tabs").tabs("load", numTab);
					}
					window.blur();
					window.opener.focus();
					window.close();
// 				}
				return false;
			});
			
			$j('#showAlreadyTranscribed').click(function() {
				window.opener.$j("#body_left").load($j('#showAlreadyTranscribed').attr('href'));
				window.blur();
				window.opener.focus();
				window.close();
				return false;
			});
			
			$j('#showAlreadyTranscribedDocs').click(function() {
				tabName = "" + ${command.volNum} + " / " + ${image.imageProgTypeNum} + " Documents"
				window.opener.$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				window.blur();
				window.opener.focus();
				window.close();
				window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
				
				return false;
			});
			
					
			$j("#exitExtract").live('click', function(){
				if ($j("#ShowSynopsisDocumentDiv").length != 0 && $j("#ShowSynopsisDocumentDiv").dialog("isOpen")) {
					$j("#ShowExtractDocumentDiv").dialog("close");
					return false;
				} else {
					$j("#ShowExtractDocumentDiv").dialog("close");
					$j.ajax({
						type:"GET",
						url:"${GetLinkedDocumentURLUpdate}",
						async:false,  
						data: { 
							volNum: '${command.volNum}', 
							volLetExt: '${command.volLetExt}', 
							imageOrder: $j("#currentImageOrder").val()
						}, 
						success: function(data) {
							// We set currentImage
							currentImage = data.imageId;
							$j("#currentImageOrder").val(data.imageOrder);
							if (data.error == 'wrongType' || data.imageType == 'R') {
								display("#unvailableTranscribe");
							} else if (data.linkedDocumentOnStartFolio == true || data.linkedDocumentOnTranscribeFolio == true) {
								if (data.isExtract == false) {
									$j("#currentEntryId").val(data.entryId);
									display(new Array("#notExtract", "#extractTranscribe"));
								} else {
									if (data.countAlreadyEntered == 1) {
										var isOpenExtractDoc = $j("#ShowExtractDocumentDiv").dialog("isOpen") && $j("#extractEntryId").val() == $j('#currentEntryId').val();
										$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
										$j("#currentEntryId").val(data.entryId);
										
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This is a start folio of one letter and it is already transcribed
											display(new Array("#alreadyTranscribe", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of one letter
											display(new Array("#lettersHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else {
											// This folio has one transcription attached
											display(new Array("#transcriptionsHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										}
									} else if (data.countAlreadyEntered > 1) {
										$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This folio is a start folio of one or more letters and has one of more transcriptions attached
											display(new Array("#alreadyTranscribe","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of more than one letter
											display(new Array("#lettersHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else {
											// This folio has more than one transcription attached
											display(new Array("#transcriptionsHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										}
									}
								}
							}
						},
						error: function(data) {
							alert('Server error');
						}
					});
					return false;
				}
			});
			
			$j("#exitSynopsis").live('click', function(){
				if ($j("#ShowExtractDocumentDiv").length != 0 && $j("#ShowExtractDocumentDiv").dialog("isOpen")) {
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					return false;
				} else {
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					$j.ajax({
						type:"GET",
						url:"${GetLinkedDocumentURLUpdate}",
						async:false,  
						data: { 
							volNum: '${command.volNum}', 
							volLetExt: '${command.volLetExt}', 
							imageOrder: $j("#currentImageOrder").val()
						}, 
						success: function(data) {
							// We set currentImage
							currentImage = data.imageId;
							$j("#currentImageOrder").val(data.imageOrder);
							if (data.error == 'wrongType' || data.imageType == 'R') {
								display("#unvailableTranscribe");
							} else if (data.linkedDocumentOnStartFolio == true || data.linkedDocumentOnTranscribeFolio == true) {
								if (data.isExtract == false) {
									$j("#currentEntryId").val(data.entryId);
									display(new Array("#notExtract", "#extractTranscribe"));
								} else {
									if (data.countAlreadyEntered == 1) {
										var isOpenExtractDoc = $j("#ShowExtractDocumentDiv").dialog("isOpen") && $j("#extractEntryId").val() == $j('#currentEntryId').val();
										$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
										$j("#currentEntryId").val(data.entryId);
										
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This is a start folio of one letter and it is already transcribed
											display(new Array("#alreadyTranscribe", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of one letter
											display(new Array("#lettersHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										} else {
											// This folio has one transcription attached
											display(new Array("#transcriptionsHere", isOpenExtractDoc ? "#showAlreadyTranscribed" : "#showTranscription", "#transcribeAnyway"));
										}
									} else if (data.countAlreadyEntered > 1) {
										$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
										if (data.linkedDocumentOnStartFolio == true && data.linkedDocumentOnTranscribeFolio == true) {
											// This folio is a start folio of one or more letters and has one of more transcriptions attached
											display(new Array("#alreadyTranscribe","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else if (data.linkedDocumentOnStartFolio == true) {
											// This folio is a start folio of more than one letter
											display(new Array("#lettersHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										} else {
											// This folio has more than one transcription attached
											display(new Array("#transcriptionsHere","#showAlreadyTranscribedDocs","#transcribeAnyway"));
										}
									}
								}
							}
						},
						error: function(data) {
							alert('Server error');
						}
					});
					return false;
				}
			});
			
			
			// "Jump To" form -> validation process
			
			/**
			 * This function checks if the parameter provided is a number.
			 *
			 * @param n the string to be checked
			 * @return true if 'n' is a number
			 */
			isNumber = function(n) {
				return !isNaN(parseFloat(n)) && isFinite(n);
			};
		
			/**
			 * This function performs the validation of the 'jump to' form.
			 */
			validateForm = function() {
				resetAllErrors();
				if (isNumber($j('#imageProgTypeNum').val()) == false) {
					displayErrorClientMsg('folio', '<fmt:message key="mview.pageTurnerDialog.onlyNumberAllowedFolio"/>');
					return false;
				}
				var volExt = '' + '${command.volLetExt}';
				if (${hasInsert} == true) {
					$j.get('<c:url value="/src/mview/CheckInsert.json" />', 
						{	volNum: ${command.volNum},
					    	volLetExt: volExt, 
					    	insertNum: $j('#insertNum').val(), 
					    	insertLet: $j("#insertLet").val()
					    },
					    function(data) {
					    	checkInsertCallback(data);
					    }
					);
				} else {
					$j.get('<c:url value="/src/mview/CheckFolio.json" />', 
						{	volNum: ${command.volNum},
							volLetExt: volExt,
							insertNum : $j("#insertNum").val(), 
							insertLet: $j("#insertLet").val(), 
							folioNum: $j("#imageProgTypeNum").val(), 
							folioMod: $j("#missedNumbering").val()
						},
						function(data) {
							checkFolioCallback(data);
						}
					);
				}
				return false;
			};
			
			/**
			 * This callback is called during the insert validation.
			 *
			 * @param data the data provided to the callback
			 */
			var checkInsertCallback = function(data) {
				var volExt = '' + '${command.volLetExt}';
				if (typeof data.error === "undefined") {
					if (data.insertOK == false) {
						var insNum = $j("#insertNum").val();
						var insLet = $j('#insertLet').val();
						var msg = '<fmt:message key="mview.pageTurnerDialog.missingInsert"><fmt:param value="' + insNum + (insLet != '' ? ' ' + insLet : '' ) + '" /></fmt:message>';
						displayErrorClientMsg("insert", msg);
					} else {
						$j.get('<c:url value="/src/mview/CheckFolio.json" />', 
							{	volNum: ${command.volNum},
								volLetExt: volExt,
								insertNum : $j("#insertNum").val(), 
								insertLet: $j("#insertLet").val(), 
								folioNum: $j("#imageProgTypeNum").val(), 
								folioMod: $j("#missedNumbering").val()
							},
							function(data) {
								checkFolioCallback(data);
							}
						);
					}
				} else {
					if (data.error == 'error.manuscriptviewer.incorrectvolume') {
						var msg = '<fmt:message key="mview.pageTurnerDialog.incorrectVolume"></fmt:message>';
						displayErrorClientMsg("insert", msg);
					} else {
						displayErrorClientMsg("insert", data.error);
					}
				}
			}
			
			/**
			 * This callback is called during the folio validation.
			 *
			 * @param data the data provided to the callback
			 */
			var checkFolioCallback = function(data) {
				if (typeof data.error === "undefined") {
					var folioNumBlank = !$j("#imageProgTypeNum").val() || $j("#imageProgTypeNum").val() == "";
					var folioModBlank = !$j("#missedNumbering").val() || $j("#missedNumbering").val() == "";
					if (data.folioOK == false) {
						var folioString = '' + (folioNumBlank ? 'blank' : $j("#imageProgTypeNum").val());
						folioString += (folioModBlank ? '' : (' ' + $j("#missedNumbering").val()));
						var msg = '<fmt:message key="mview.pageTurnerDialog.missingFolio"><fmt:param value="' + folioString + '" /></fmt:message>';
						displayErrorClientMsg('folio',msg);
					} else {
						$j('#formSubmitting').val(true);
						$j("#moveToFolioForm").submit();
					}
				} else {
					if (data.error == 'error.manuscriptviewer.incorrectfolio') {
						var msg = '<fmt:message key="mview.pageTurnerDialog.incorrectFolio"></fmt:message>';
						displayErrorClientMsg('folio', msg);
					} else {
						displayErrorClientMsg('folio', data.error);
					}
				}
			}
			
			/**
			 * This function removes the warning message from the error section.
			 *
			 * @param prefix the prefix of the error section; possible values are 'folio' and 'insert'.
			 */
			 var resetErrorClientMsg = function(prefix) {
				if ($j("#" + prefix + "ErrorClient").length > 0) {
					$j("#" + prefix + "ErrorClient").html("");
					$j("#" + prefix + "ErrorClient").attr("display","none");
				}
			}
			
			/**
			 * This function shows a warning message in the error section.
			 *
			 * @param prefix the prefix of the error section; possible values are 'folio' and 'insert'
			 * @param msg the message to show
			 */
			 var displayErrorClientMsg = function(prefix,msg) {
				$j("#" + prefix + "ErrorClient").html(msg);
				$j("#" + prefix + "ErrorClient").attr("display","block");
			}
			 
			 /**
			  * This function removes all the warning messages from the error section.
			  */
			 var resetAllErrors = function() {
				resetErrorClientMsg('insert');
				resetErrorClientMsg('folio'); 
			 }
			
			$j('#insertNum,#insertLet,#imageProgTypeNum,#missedNumbering').change(function() {
				resetAllErrors();
			});
			
		});
	</script>
