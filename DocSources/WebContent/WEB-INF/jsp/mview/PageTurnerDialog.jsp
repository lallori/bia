<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="PageTurnerDialogURL" value="/src/mview/PageTurnerDialog.do"/>

	<c:url var="SearchAjaxURL" value="/src/mview/SearchCarta.json"/>
	
	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>

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

<div id="EditPersonalNotesDiv">
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div id="transcribeDiv">
		<span id="unvailableTranscribe" class="transcribeMessage" style="visibility: hidden;">Transcribe is available only for carta.</span>
		<span id="alreadyTranscribe" class="transcribeMessage" style="visibility: hidden;">This document has already been transcribed</span>
		<a id="showAlreadyTranscribed" href="#" title="Show this document record" class="transcribe" style="visibility: hidden;">Show this document record</a>
		<a id="readyToTranscribe" href="#" title="Transcribe this document" class="transcribe" style="visibility: hidden;">Transcribe this document</a>
		<a id="choiceThisFolioStart" href="#" title="Transcribe this document" class="transcribe" style="visibility: hidden;">Choose this as "Start folio"</a>
	</div>
	</security:authorize>	  
	<div id="line3"></div>

	<div id="prevNextButtons" class="transcribe">
	    <div id="prevButton">
		<c:if test="${command.imageOrder == 1}">
	    	<a id="previous" class="previousPage">Previous folio</a>
		</c:if>
		<c:if test="${command.imageOrder > 1}">
			<a id="previous" class="previousPage" href="${previousPage}" >Previous folio</a>
		</c:if>
		</div>
		<div id="folio" title="Warning!" style="display:none"> 
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>You need to save your Extract / Synopsis text before using the page turner.</p> 
		</div> 
		<div id="nextButton">
		<c:if test="${command.imageOrder == command.total}">
			<a id="next" class="nextPage">Next folio</a>
		</c:if>
		<c:if test="${command.imageOrder < command.total}">
			<a id="next" class="nextPage" href="${nextPage}">Next folio</a>
		</c:if>
		</div>
	</div>

	<div>
		<a id="volumeSummary" href="#">Volume Summary</a>
	</div>

	<div id="line" class="transcribe"></div>
	
	<div id="rubricarioMoveTo" class="transcribe">
		<form:form id="moveToRubricarioForm" method="post" class="edit" action="${PageTurnerDialogURL}">
			<label id="imageProgTypeNumLabel" class="rubricarioLabel" for="imageProgTypeNum">Move to <i>Index of Names</i> folio</label>
			<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRucricario" type="text" value="" />
			<input id="goR" type="submit"  value="Go"/>
			<form:hidden path="entryId" />
			<form:hidden path="volNum" />
			<form:hidden path="volLetExt" />
			<form:hidden path="imageType" value="R"/>
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
	<div id="folioMoveTo" class="transcribe">
		<form:form id="moveToFolioForm" method="post" class="edit" action="${PageTurnerDialogURL}">
			<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Move to folio </label>
			<input id="imageProgTypeNum" class="input_4cFolio" type="text" value="" name="imageProgTypeNum" />
			<input id="go" type="submit" value="Go"/>
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
	
	<div id="line2" class="transcribe"></div>
	
	<div id="personalNotesDiv">
		<a id="personalNotesButton" href="#" class="transcribe">Personal Notes</a>
	</div>
	
	<div id="exitDiv">
		<a id="exitButton" href="#" class="transcribe" onClick="$j('#exit').dialog('open');">Exit</a>
	</div>
</div>

	<div id="exit" title="Alert" style="display:none">
		<c:if test="${command.modeEdit == true}">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to Exit without saving your Extract / Synopsis text?</p>
		</c:if> 
		<c:if test="${command.modeEdit == false}">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to Exit ?</p>
		</c:if> 
	</div>

	<div id="notFound" title="Alert" style="display:none">
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>This folio is not present/missing/not available. Check the volume summary.</p>
	</div>

	<form:form id="transcribeForm" class="edit">
		<input type="hidden" id="transcribeImage" value="" />
		<input type="hidden" id="startImage" value="" />
	</form:form>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#moveToFolioForm").pageTurnerForm({
				searchUrl: '${SearchAjaxURL}', 
				imagePrefix: '${ImagePrefixURL}', 
				IIPImageServer: '${IIPImageServerURL}', 
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			
			$j("#rubricarioMoveTo").pageTurnerForm({
				searchUrl: '${SearchAjaxURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			$j("#previous").pageTurnerPage({
				searchUrl: '${SearchAjaxURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			$j("#next").pageTurnerPage({
				searchUrl: '${SearchAjaxURL}',
				imagePrefix: '${ImagePrefixURL}',
				IIPImageServer: '${IIPImageServerURL}',
				<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				canTranscribe: 'true'
				</security:authorize>
			});
			
			var $dialogPersonalNotes = $j('<div id="DialogPersonalNotesDiv"></div>').dialog({                                                                                                                                                                   
				autoOpen: false,
				width: 352,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: 'Personal Notes',
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
				height:140,
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
			
			$j('#exitButton').click(function() {
				$j('#exit').dialog('open');
				return false;
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
					$dialogPersonalNotes.dialog("close");
					return false;
				} else {
					$dialogPersonalNotes.dialog("open")
					return false;
				}
			});
			

			$j.ajax({ type:"GET", url:"${GetLinkedDocumentURL}", async:false, success:function(data) {
				// We set currentImage
				currentImage = data.imageId;
				if (data.error == 'wrongType') {
					$j("#unvailableTranscribe").css('visibility', 'visible');
					$j("#alreadyTranscribe").css('visibility', 'hidden');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
					$j("#readyToTranscribe").css('visibility', 'hidden');
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
				} else if (data.linkedDocument == 'true') {
					$j("#alreadyTranscribe").css('visibility', 'visible');
					$j("#showAlreadyTranscribed").css('visibility', 'visbile');
					$j("#unvailableTranscribe").css('visibility', 'hidden');
					$j("#readyToTranscribe").css('visibility', 'hidden');
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
				} else if (data.linkedDocument == 'false') {
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
					$j("#readyToTranscribe").css('visibility', 'visible');
					</security:authorize>
					$j("#alreadyTranscribe").css('visibility', 'hidden');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
					$j("#unvailableTranscribe").css('visibility', 'hidden');
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
				} else {
					$j("#unvailableTranscribe").css('visibility', 'hidden');
					$j("#alreadyTranscribe").css('visibility', 'hidden');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
					$j("#readyToTranscribe").css('visibility', 'hidden');
					$j("#choiceThisFolioStart").css('visibility', 'hidden');
				}
			}});

			
			$j('#readyToTranscribe').click(function() {
				$j("#choiceThisFolioStart").css('visibility', 'visible');
				$j("#readyToTranscribe").css('visibility', 'hidden');
				transcribing=true;
				imageDocumentToCreate=currentImage;
				return false;
			});
			
			$j('#choiceThisFolioStart').click(function() {
				$j("#choiceThisFolioStart").css('visibility', 'visible');
				$j("#readyToTranscribe").css('visibility', 'hidden');
				imageDocumentFolioStart=currentImage;
				var urlToTranscribe = "/DocSources/de/docbase/TranscribeAndContextualizeDocument.do?imageDocumentToCreate=" + imageDocumentToCreate + "&imageDocumentFolioStart=" + imageDocumentFolioStart;
				window.opener.$j("#body_left").load(urlToTranscribe);
				$j("#choiceThisFolioStart").css('visibility', 'hidden');
				window.blur();
				window.opener.focus();
				return false;
			});
			
			$j('#showAlreadyTranscribed').click(function() {
				window.opener.$j("#body_left").load($j('#showAlreadyTranscribed').attr('href'));
				window.blur();
				window.opener.focus();
				return false;
			});
		});
	</script>
