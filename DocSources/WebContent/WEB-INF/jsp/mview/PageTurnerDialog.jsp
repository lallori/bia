<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ContextPathURL" value="/"/>

	<c:url var="PageTurnerDialogURL" value="/src/mview/PageTurnerDialog.do"/>

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
		<span id="unvailableTranscribe" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.transcriptionAvailableForFoliosOnly"/></span>
		
		<!--  This document has already been transcribed, you can decide whether see its transcription or see its record-->
		<a id="alreadyTranscribe" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.documentAlreadyTranscribed"/></a>
		<a id="showTranscription" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showTranscription.alt"/>" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.showTranscription"/></a>
		<a id="showAlreadyTranscribed" href="${ShowDocumentURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecord.alt"/>"  class="transcribe button_medium" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.showThisRecord"/></a>
		<a id="showAlreadyTranscribedDocs" href="${ShowDocumentsAlreadyURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecords.alt"/>" class="transcribe button_medium" style="visibility: hidden; cursor:pointer;"><fmt:message key="mview.pageTurnerDialog.showThisRecords"/></a>
		<a id="transcribeAnyway" href="#" title="<fmt:message key="mview.pageTurnerDialog.transcribeAnyway.alt"/>" class="transcribe button_medium" style="visibility:hidden; cursor: pointer;"><fmt:message key="mview.pageTurnerDialog.transcribeAnyway"/></a>
		
		<!--  This document has not been transcribed-->
		<a id="readyToTranscribe" href="#" title="<fmt:message key="mview.pageTurnerDialog.readyToTranscribe.alt"/>" class="transcribe button_medium" style="visibility: hidden; cursor: pointer"><fmt:message key="mview.pageTurnerDialog.readyToTranscribe"/> </a>
		<a id="choiceThisFolioStart" href="#" title="<fmt:message key="mview.pageTurnerDialog.chooseThisAsStartFolio.alt"/>" class="transcribe button_medium" style="visibility: hidden; cursor: pointer"><fmt:message key="mview.pageTurnerDialog.setThisAs"/> <font style="font-style:italic;"><fmt:message key="mview.pageTurnerDialog.startFolio"/></font></a>
		
		<!--  This document has a record in the database but it is not transcribed-->
		<a id="notExtract" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.documentEnteredButNotTranscribed"/></a>
		<a id="extractTranscribe" href="#" title="<fmt:message key="mview.pageTurnerDialog.transcribeThisDocument.alt"/>" class="transcribe button_medium" style="visibility: hidden; cursor: pointer;" ><fmt:message key="mview.pageTurnerDialog.transcribeThisDocument"/></a>
		
		
		<input type="hidden" id="currentEntryId" value="${command.entryId}" />
		<input type="hidden" id="currentImageOrder" value="${command.imageOrder}" />
    </div>
	</security:authorize>
	
	<security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<div id="transcribeDiv">
	
		<!--  Rubricario (Index of Names), Guardie, or Coperte - you can't trascribe these kind of items -->
		<span id="unvailableTranscribe" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.transcriptionAvailableForFoliosOnly"/></span>
		
		<!--  This document has already been transcribed, you can decide whether see its transcription or see its record-->
		<a id="alreadyTranscribe" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.documentAlreadyTranscribed"/></a>
		<a id="showTranscription" href="#" class="transcribe button_medium" title="<fmt:message key="mview.pageTurnerDialog.showTranscription.alt"/>" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.showTranscription"/></a>
		<a id="showAlreadyTranscribed" href="${ShowDocumentURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecord.alt"/>"  class="transcribe button_medium" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.showThisRecord"/></a>
		<a id="showAlreadyTranscribedDocs" href="${ShowDocumentsAlreadyURL}" title="<fmt:message key="mview.pageTurnerDialog.showThisRecords.alt"/>" class="transcribe button_medium" style="visibility: hidden; cursor:pointer;"><fmt:message key="mview.pageTurnerDialog.showThisRecords"/></a>
		
		<!--  This document has a record in the database but it is not transcribed-->
		<a id="notExtract" class="transcribeMessage" style="visibility: hidden;"><fmt:message key="mview.pageTurnerDialog.documentEnteredButNotTranscribed"/></a>
		
		
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
		<form:form id="moveToFolioForm" method="post" class="edit" action="${PageTurnerDialogURL}">
			<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel"><fmt:message key="mview.pageTurnerDialog.folioToMove"/> </label>
			<input id="imageProgTypeNum" class="input_4cFolio" type="text" value="" name="imageProgTypeNum" />
			<input id="go" class="button_mini" type="submit" value="Go"/>
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
			var annotations = new Array();
			
			$j("#moveToFolioForm").pageTurnerForm({
				searchUrl: '${SearchAjaxURL}', 
		        getLinkedDocumentUrl:  '${GetLinkedDocumentURL}',
				imagePrefix: '${ImagePrefixURL}', 
				IIPImageServer: '${IIPImageServerURL}', 
				getLinkedDocumentUrl: '${LinkedDocumentUrl}',
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});

			$j("#indexNames").pageTurnerPage({
				searchUrl: '${SearchAjaxURL}',
		        getLinkedDocumentUrl:  '${GetLinkedDocumentURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				getLinkedDocumentUrl: '${LinkedDocumentUrl}',
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			$j("#previous").pageTurnerPage({
				searchUrl: '${SearchAjaxURL}',
		        getLinkedDocumentUrl:  '${GetLinkedDocumentURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				getLinkedDocumentUrl: '${LinkedDocumentUrl}',
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			$j("#next").pageTurnerPage({
				searchUrl: '${SearchAjaxURL}',
		        getLinkedDocumentUrl:  '${GetLinkedDocumentURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				getLinkedDocumentUrl: '${LinkedDocumentUrl}',
				annotationsType: 'remote',
				retrieveAnnotationsUrl: '${GetImageAnnotationURL}',
				updateAnnotationsUrl: '${UpdateAnnotationsURL}',
				annotations: annotations,
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			
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
				autoOpen: false,
				width: 370,
				minWidth: 370,
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
				$j('#showTranscription').css('visibility','hidden');
				$j("#showAlreadyTranscribed").css('visibility', 'visible');
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
			
			
			
			$j.ajax({ type:"GET", url:"${GetLinkedDocumentURL}", async:false, success:function(data) {
				// We set currentImage
				currentImage = data.imageId;
				$j("#currentImageOrder").val(data.imageOrder);
				if(transcribing == false){
					if($dialogExtract.dialog("isOpen") || $j("#EditExtractDocumentForm").length != 0){
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#alreadyTranscribe").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
						$j("#showTranscription").css('visibility', 'hidden');
						$j("#transcribeAnyway").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#readyToTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'hidden');
						$j("#transcribeDiv").append($j("#transcribeMode"));
						$j("#transcribeMode").css('display', 'inline');
					}else{
						if (data.error == 'wrongType' || data.imageType == 'R') {
							$j("#unvailableTranscribe").css('visibility', 'visible');
							$j("#alreadyTranscribe").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").css('visibility', 'hidden');
							$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
							$j("#showTranscription").css('visibility', 'hidden');
							$j("#transcribeAnyway").css('visibility', 'hidden');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						} else if (data.linkedDocument == 'true') {
							if(data.isExtract == 'false'){
								$j("#notExtract").css('visibility', 'visible');
								$j("#extractTranscribe").css('visibility', 'visible');
								$j("#currentEntryId").val(data.entryId);
								$j("#alreadyTranscribe").css('visibility', 'hidden');
								$j("#showAlreadyTranscribed").css('visibility', 'hidden');
								$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
								$j("#showTranscription").css('visibility', 'hidden');
								$j("#transcribeAnyway").css('visibility', 'hidden');
								$j("#unvailableTranscribe").css('visibility', 'hidden');
								$j("#readyToTranscribe").css('visibility', 'hidden');
								$j("#choiceThisFolioStart").css('visibility', 'hidden');
							}else{
								if(data.countAlreadyEntered == 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
									$j("#currentEntryId").val(data.entryId);
									if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
										if($j("#extractEntryId").val() == $j('#currentEntryId').val()){
											$j("#showTranscription").css('visibility', 'hidden');
											$j("#showAlreadyTranscribed").css('visibility', 'visible');
										}
									}
								}else if(data.countAlreadyEntered > 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
								}
							}
							
						} else if (data.linkedDocument == 'false') {
							<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
							$j("#readyToTranscribe").css('visibility', 'visible');
							</security:authorize>
							$j("#alreadyTranscribe").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").css('visibility', 'hidden');
							$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
							$j("#showTranscription").css('visibility', 'hidden');
							$j("#transcribeAnyway").css('visibility', 'hidden');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#unvailableTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						} else {
							$j("#unvailableTranscribe").css('visibility', 'hidden');
							$j("#alreadyTranscribe").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").css('visibility', 'hidden');
							$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
							$j("#showTranscription").css('visibility', 'hidden');
							$j("#transcribeAnyway").css('visibility', 'hidden');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						}
					}
				}else{
					if($j("#EditExtractDocumentForm").length != 0){
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#alreadyTranscribe").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
						$j("#showTranscription").css('visibility', 'hidden');
						$j("#transcribeAnyway").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#readyToTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'hidden');
						$j("#transcribeDiv").append($j("#transcribeMode"));
						$j("#transcribeMode").css('display', 'inline');
					}else if(data.linkedDocument == 'true'){ 							
						//In this case we choose the start folio to transcribe
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#alreadyTranscribe").css('visibility', 'visible');
						$j("#showTranscription").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
						$j("#transcribeAnyway").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#readyToTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'visible');
						$j("#choiceThisFolioStart").css('opacity', '0.5');
						$j("#transcribeDiv").append($j("#transcribeMode"));
						$j("#transcribeMode").css('visibility','visible');
					}else if(data.linkedDocument == 'false'){
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#alreadyTranscribe").css('visibility', 'hidden');
						$j("#showTranscription").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
						$j("#transcribeAnyway").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#readyToTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'visible');
						$j("#choiceThisFolioStart").css('opacity', '1');
						$j("#transcribeDiv").append($j("#transcribeMode"));
						$j("#transcribeMode").css('visibility','visible');
					}
				}
			}});

			
			$j('#readyToTranscribe').click(function() {
				$j("#choiceThisFolioStart").css('visibility', 'visible');
				$j("#readyToTranscribe").css('visibility', 'hidden');
				transcribing=true;
				imageDocumentToCreate=currentImage;
				return false;
			});
			
			$j('#transcribeAnyway').click(function() {
				if($j("#alreadyTranscribe").css('visibility') == 'visible'){
					$j("#showTranscription").css('visibility','hidden');
				}
				$j("#choiceThisFolioStart").css('visibility', 'visible');
				$j("#transcribeAnyway").css('visibility', 'hidden');
				transcribing=true;
				imageDocumentToCreate=currentImage;
				return false;
			});
			
			$j('#choiceThisFolioStart').click(function() {
// 				if($j("#alreadyTranscribe").css('visibility') != 'visible'){
					$j("#choiceThisFolioStart").css('visibility', 'visible');
					$j("#readyToTranscribe").css('visibility', 'hidden');
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
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
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
				if($j("#ShowSynopsisDocumentDiv").length != 0 && $j("#ShowSynopsisDocumentDiv").dialog("isOpen")){
					$j("#ShowExtractDocumentDiv").dialog("close");
					return false;
				}else{
					$j("#ShowExtractDocumentDiv").dialog("close");
					$j.ajax({ type:"GET", url:"${GetLinkedDocumentURLUpdate}", async:false,  data: {volNum: '${command.volNum}', volLetExt: '${command.volLetExt}', imageOrder: $j("#currentImageOrder").val()}, success:function(data) {
						// We set currentImage
						currentImage = data.imageId;
						$j("#currentImageOrder").val(data.imageOrder);
						if (data.error == 'wrongType' || data.imageType == 'R') {
							$j("#unvailableTranscribe").css('visibility', 'visible');
							$j("#alreadyTranscribe").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").css('visibility', 'hidden');
							$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
							$j("#showTranscription").css('visibility', 'hidden');
							$j("#transcribeAnyway").css('visibility', 'hidden');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						} else if (data.linkedDocument == 'true') {
							if(data.isExtract == 'false'){
								$j("#notExtract").css('visibility', 'visible');
								$j("#extractTranscribe").css('visibility', 'visible');
								$j("#currentEntryId").val(data.entryId);
								$j("#alreadyTranscribe").css('visibility', 'hidden');
								$j("#showAlreadyTranscribed").css('visibility', 'hidden');
								$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
								$j("#showTranscription").css('visibility', 'hidden');
								$j("#transcribeAnyway").css('visibility', 'hidden');
								$j("#unvailableTranscribe").css('visibility', 'hidden');
								$j("#readyToTranscribe").css('visibility', 'hidden');
								$j("#choiceThisFolioStart").css('visibility', 'hidden');
							}else{
								if(data.countAlreadyEntered == 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
									$j("#currentEntryId").val(data.entryId);
									if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
										if($j("#extractEntryId").val() == $j('#currentEntryId').val()){
											$j("#showTranscription").css('visibility', 'hidden');
											$j("#showAlreadyTranscribed").css('visibility', 'visible');
										}
									}
								}else if(data.countAlreadyEntered > 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
								}
							}
						}}});
					return false;
				}
			});
			
			$j("#exitSynopsis").live('click', function(){
				if($j("#ShowExtractDocumentDiv").length != 0 && $j("#ShowExtractDocumentDiv").dialog("isOpen")){
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					return false;
				}else{
					$j("#ShowSynopsisDocumentDiv").dialog("close");
					$j.ajax({ type:"GET", url:"${GetLinkedDocumentURLUpdate}", async:false, data: {volNum: '${command.volNum}', volLetExt: '${command.volLetExt}', imageOrder: $j("#currentImageOrder").val()}, success:function(data) {
						// We set currentImage
						currentImage = data.imageId;
						$j("#currentImageOrder").val(data.imageOrder);
						if (data.error == 'wrongType' || data.imageType == 'R') {
							$j("#unvailableTranscribe").css('visibility', 'visible');
							$j("#alreadyTranscribe").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").css('visibility', 'hidden');
							$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
							$j("#showTranscription").css('visibility', 'hidden');
							$j("#transcribeAnyway").css('visibility', 'hidden');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						} else if (data.linkedDocument == 'true') {
							if(data.isExtract == 'false'){
								$j("#notExtract").css('visibility', 'visible');
								$j("#extractTranscribe").css('visibility', 'visible');
								$j("#currentEntryId").val(data.entryId);
								$j("#alreadyTranscribe").css('visibility', 'hidden');
								$j("#showAlreadyTranscribed").css('visibility', 'hidden');
								$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
								$j("#showTranscription").css('visibility', 'hidden');
								$j("#transcribeAnyway").css('visibility', 'hidden');
								$j("#unvailableTranscribe").css('visibility', 'hidden');
								$j("#readyToTranscribe").css('visibility', 'hidden');
								$j("#choiceThisFolioStart").css('visibility', 'hidden');
							}else{
								if(data.countAlreadyEntered == 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'hidden');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
									$j("#currentEntryId").val(data.entryId);
									if($j("#ShowExtractDocumentDiv").dialog("isOpen")){
										if($j("#extractEntryId").val() == $j('#currentEntryId').val()){
											$j("#showTranscription").css('visibility', 'hidden');
											$j("#showAlreadyTranscribed").css('visibility', 'visible');
										}
									}
								}else if(data.countAlreadyEntered > 1){
									$j("#alreadyTranscribe").css('visibility', 'visible');
									$j("#showAlreadyTranscribed").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").css('visibility', 'visible');
									$j("#showTranscription").css('visibility', 'hidden');
									$j("#transcribeAnyway").css('visibility', 'visible');
									$j("#notExtract").css('visibility', 'hidden');
									$j("#extractTranscribe").css('visibility', 'hidden');
									$j("#unvailableTranscribe").css('visibility', 'hidden');
									$j("#readyToTranscribe").css('visibility', 'hidden');
									$j("#choiceThisFolioStart").css('visibility', 'hidden');
									$j("#showAlreadyTranscribedDocs").attr("href", data.showLinkedDocument);
								}
							}
						}}});
					return false;
				}
			});
			
		});
	</script>
