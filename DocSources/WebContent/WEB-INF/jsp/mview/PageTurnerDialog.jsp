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
		
<%-- 	<c:url var="EditExtractDialogUrl" value="/de/mview/EditExtractDocumentDialog.do" > --%>
<%-- 		<c:param name="entryId" value="${command.entryId}" /> --%>
<%-- 	</c:url> --%>

	<c:url var="EditExtractDialogUrl" value="/de/mview/EditExtractDocumentDialog.do" />
	
<%-- 	<c:url var="EditSynopsisDialogUrl" value="/de/mview/EditSynopsisDocumentDialog.do" > --%>
<%-- 		<c:param name="entryId" value="${command.entryId}" /> --%>
<%-- 	</c:url> --%>

	<c:url var="EditSynopsisDialogUrl" value="/de/mview/EditSynopsisDocumentDialog.do" />

<div id="EditPersonalNotesDiv">
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<div id="transcribeDiv">
		<span id="unvailableTranscribe" class="transcribeMessage" style="visibility: hidden;">Transcription is available for folios only.</span>
		<a id="alreadyTranscribe" class="transcribeMessage" style="visibility: hidden;">This document has already been transcribed</a>
		<a id="notExtract" class="transcribeMessage" style="visibility: hidden;"><font color="green">This document has been entered but not transcribed</font>
		<a id="extractTranscribe" href="#" style="visibility: hidden; cursor: pointer;" title="Transcribe extract" class="transcribe">Transcribe this document</a>
		<a id="showAlreadyTranscribed" href="${ShowDocumentURL}" title="Show this document record"  style="visibility: hidden; cursor: pointer" class="transcribe">Show this document record</a>
		<a id="readyToTranscribe" href="#" title="Transcribe this document" class="transcribe" style="visibility: hidden; cursor: pointer">Transcribe this document</a>
		<a id="choiceThisFolioStart" href="#" title="Transcribe this document" class="transcribe" style="visibility: hidden; cursor: pointer">Choose this as "Start folio"</a>
		<input type="hidden" id="currentEntryId" value="${command.entryId}" />
		<input type="hidden" id="currentImageOrder" value="${command.imageOrder}" />
	</div>
	</security:authorize>	  
	<div id="line3"></div>

	<div id="prevNextButtons" class="transcribe">
	    <div id="prevButton">
		<c:if test="${command.imageOrder == 1}">
	    	<a id="previous" title="Previous Folio"></a>
		</c:if>
		<c:if test="${command.imageOrder > 1}">
			<a id="previous" href="${previousPage}" title="Previous Folio"></a>
		</c:if>
		</div>
		<div id="folio" title="Warning!" style="display:none"> 
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>You need to save your Extract / Synopsis text before using the page turner.</p> 
		</div> 
		<div id="nextButton">
		<c:if test="${command.imageOrder == command.total}">
			<a id="next" title="Previous Folio"></a>
		</c:if>
		<c:if test="${command.imageOrder < command.total}">
			<a id="next" href="${nextPage}" title="Next Folio"></a>
		</c:if>
		</div>
	</div>
	
	<c:if test="${command.totalRubricario > 0}">
		<div>
    		<a id="indexNames" href="${indexOfNamesURL}" class="transcribe" title="Index of Names" style="cursor: pointer;"></a>
    	</div>
    </c:if>

	<div>
		<a id="volumeSummary" href="${VolumeSummaryDialogURL}" class="transcribe" title="Volume Summary" style="cursor: pointer;"></a>
	</div>

	<div id="line" class="transcribe"></div>
	
	<div id="folioMoveTo" class="transcribe">
		<form:form id="moveToFolioForm" method="post" class="edit" action="${PageTurnerDialogURL}">
			<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">folio </label>
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
		<a id="personalNotesButton" href="#" class="transcribe" title="Personal Notes" style="cursor: pointer;"></a>
	</div>
	
	<div id="exitDiv">
		<a id="exitButton" href="#" class="transcribe" onClick="$j('#exit').dialog('open');" style="cursor: pointer;">Exit</a>
	</div>
</div>

	<div id="exit" title="Alert" style="display:none">
		<c:if test="${command.modeEdit == true}">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to close your Manuscript Trascriber/Viewer without saving your work?</p>
		</c:if> 
		<c:if test="${command.modeEdit == false}">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure you want to close your Manuscript Trascriber/Viewer?</p>
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

			$j("#indexNames").pageTurnerPage({
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
				title: 'VOLUME SUMMARY',
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
			
			var $dialogExtract = $j('<div id="EditExtractDocumentDiv"></div>')
			.dialog({                                                                                                                                                                   
				autoOpen: false,
				width: 352,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: 'EDIT EXTRACT',
				position: ['left','middle'],                                                                                                                                                       
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
				title: 'EDIT SYNOPSIS',
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
				if($dialogExtract.dialog("isOpen") || $j("#EditExtractDocumentForm").length != 0){
					$j("#unvailableTranscribe").css('visibility', 'hidden');
					$j("#alreadyTranscribe").css('visibility', 'hidden');
					$j("#showAlreadyTranscribed").css('visibility', 'hidden');
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
							$j("#unvailableTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
						}else{
							$j("#alreadyTranscribe").css('visibility', 'visible');
							$j("#showAlreadyTranscribed").css('visibility', 'visible');
							$j("#notExtract").css('visibility', 'hidden');
							$j("#extractTranscribe").css('visibility', 'hidden');
							$j("#unvailableTranscribe").css('visibility', 'hidden');
							$j("#readyToTranscribe").css('visibility', 'hidden');
							$j("#choiceThisFolioStart").css('visibility', 'hidden');
							$j("#showAlreadyTranscribed").attr("href", data.showLinkedDocument);
						}
					} else if (data.linkedDocument == 'false') {
						<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
						$j("#readyToTranscribe").css('visibility', 'visible');
						</security:authorize>
						$j("#alreadyTranscribe").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'hidden');
					} else {
						$j("#unvailableTranscribe").css('visibility', 'hidden');
						$j("#alreadyTranscribe").css('visibility', 'hidden');
						$j("#showAlreadyTranscribed").css('visibility', 'hidden');
						$j("#notExtract").css('visibility', 'hidden');
						$j("#extractTranscribe").css('visibility', 'hidden');
						$j("#readyToTranscribe").css('visibility', 'hidden');
						$j("#choiceThisFolioStart").css('visibility', 'hidden');
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
			
			$j('#choiceThisFolioStart').click(function() {
				$j("#choiceThisFolioStart").css('visibility', 'visible');
				$j("#readyToTranscribe").css('visibility', 'hidden');
				imageDocumentFolioStart=currentImage;
				var urlToTranscribe = "/DocSources/de/docbase/TranscribeAndContextualizeDocument.do?imageDocumentToCreate=" + imageDocumentToCreate + "&imageDocumentFolioStart=" + imageDocumentFolioStart;
				var urlToExplore;
				var volLetExt;
				if("${command.volLetExt}" == ""){
					volLetExt = "";
				}
				else{
					volLetExt = "${command.volLetExt}";
				}
				urlToExplore = "/DocSources/src/volbase/ShowExplorerVolume.do?volNum=" + ${command.volNum} + "&volLetExt=" + volLetExt + "&imageOrder=" + $j("#currentImageOrder").val() + "&total=" + ${command.total} + "&totalRubricario=" + ${command.totalRubricario} + "&totalCarta=" + ${command.totalCarta} + "&totalAppendix=" + ${command.totalAppendix} + "&totalOther=" + ${command.totalOther} + "&totalGuardia=" + ${command.totalGuardia} + "&flashVersion=false&showHelp=false&showThumbnail=false";
				window.opener.$j("#body_left").load(urlToTranscribe);
				$j("#choiceThisFolioStart").css('visibility', 'hidden');
				//To open volume explorer in a tab
				var tabName = "<span id='titleTab${command.volNum}" + volLetExt + "'>Explore Volume ${command.volNum}" + volLetExt + "</span>";
				var numTab = 0;
				
				//Check if already exist a tab with this person
				var tabExist = false;
				window.opener.$j("#tabs ul li a").each(function(){
					var toTest = "";
					toTest += this.text;
					if(!tabExist)
						numTab++;
					if(this.text == tabName || toTest.indexOf("Volume ${command.volNum}" + volLetExt) != -1){
						tabExist = true;
					}
				});
				
				if(!tabExist){
					window.opener.$j( "#tabs" ).tabs( "add" , urlToExplore, tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
					window.opener.$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				}else{
					window.opener.$j("#tabs").tabs("select", numTab-1);
					window.opener.$j("#tabs").tabs("url", numTab-1, urlToExplore);
					window.opener.$j("#tabs").tabs("load", numTab-1);
				}
				window.blur();
				window.opener.focus();
				window.close();
				return false;
			});
			
			$j('#showAlreadyTranscribed').click(function() {
				window.opener.$j("#body_left").load($j('#showAlreadyTranscribed').attr('href'));
				window.blur();
				window.opener.focus();
				window.close();
				return false;
			});
		});
	</script>
