<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>
	
	<c:choose>
		<c:when test="${fromTranscribe == null || !fromTranscribe}">
			<c:set var="transcribe" value="false" />
		</c:when>
		<c:otherwise>
			<c:set var="transcribe" value="true" />
		</c:otherwise>
	</c:choose>
	
	<c:url var="editDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do"/>
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditDetailsDocumentForm" action="${editDetailsDocumentURL}" method="post" cssClass="edit">
	<fieldset>
			<legend><b><fmt:message key="docbase.editDetailsDocument.title.documentDetails"/></b></legend>
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.docid"/>'>?</a>
						<form:label id="entryIdLabel" for="entryId" path="entryId" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.docId"/></form:label>
					</div>
					<div class="col_l"><span class="docId">${command.entryId}</span></div>
					<div class="col_r"><form:label id="volumeLabel" for="volume" path="volume" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.volumeMdp"/></form:label></div>
					<div class="col_r"><form:input id="volume" path="volume" cssClass="input_5c" maxlength="5"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.insert"/>'>?</a>
						<form:label id="insertNumLabel" for="insertNum" path="insertNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.insert"/></form:label>
					</div>
					<div class="col_l">
						<form:input id="insertNum" path="insertNum" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.part"/>'>?</a>
						<form:label id="insertLetLabel" for="insertLet" path="insertLet" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.part"/></form:label>
					</div>
					<div class="col_r">
						<form:input id="insertLet" path="insertLet" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documentstartsatfolio"/>'>?</a>
						<form:label id="folioNumLabel" for="folioNum" path="folioNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.documentStartsAtFolio"/></form:label>
					</div>
					<div class="col_l">
						<form:input id="folioNum" path="folioNum" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.iffolioaddenda"/>'>?</a>
						<form:label id="folioModLabel" for="folioMod" path="folioMod" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.ifFolioAddenda"/></form:label>
					</div>
					<div class="col_r">
						<form:input id="folioMod" path="folioMod" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documentfoliorectoverso"/>'>?</a>
						<form:label id="folioRectoVersoLabel" for="folioRectoVerso" path="folioRectoVerso" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.folioRectoVerso"/></form:label>
					</div>
					<div class="col_l">
						<form:input id="folioRectoVerso" path="folioRectoVerso" maxlength="1" class="${transcribe ? 'input_4c_disabled' : 'input_2c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.transcribefolionum"/>'>?</a>
						<form:label id="transcribeFolioNumLabel" for="transcribeFolioNum" path="transcribeFolioNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.transcribeFolioNum"/></form:label>
					</div>
					<div class="col_l">
						<form:input id="transcribeFolioNum" path="transcribeFolioNum" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.transcribefoliomod"/>'>?</a>
						<form:label id="transcribeFolioModLabel" for="transcribeFolioMod" path="transcribeFolioMod" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.transcribeFolioMod"/></form:label>
					</div>
					<div class="col_r">
						<form:input id="transcribeFolioMod" path="transcribeFolioMod" class="${transcribe ? 'input_4c_disabled' : 'input_5c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.transcribefoliorectoverso"/>'>?</a>
						<form:label id="transcribeFolioRectoVersoLabel" for="transcribeFolioRectoVerso" path="transcribeFolioRectoVerso" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.transcribeFolioRectoVerso"/></form:label>
					</div>
					<div class="col_l">
						<form:input id="transcribeFolioRectoVerso" path="transcribeFolioRectoVerso" maxlength="1" class="${transcribe ? 'input_4c_disabled' : 'input_2c'}" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.unpaginated"/>'>?</a>
						<form:label id="unpagedLabel" for="unpaged" path="unpaged" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.unpaginated"/></form:label>
						<form:checkbox id="unpaged" path="unpaged" disabled="${transcribe ? 'true' : 'false' }" />
					</div>
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.nonconsecutive"/>'>?</a>
						<form:label id="contDiscLabel" for="contDisc" path="contDisc" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.nonconsecutive"/></form:label>
						<form:checkbox id="contDisc" path="contDisc"/>
					</div>
				</div>
			</div>
			
			<div id="volumeErrorClient" class="errorClient" display="none" style="color:red;" />
			<div id="insertErrorClient" class="errorClient" display="none" style="color:red;" />
			<div id="folioErrorClient" class="errorClient" display="none" style="color:red;" />
			<div id="transcribeFolioErrorClient" class="errorClient" display="none" style="color:red;" />
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.documenttypology"/>'>?</a>
					<form:label id="docTypologyLabel" for="docTypology"  path="docTypology" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.documentTypology"/></form:label>
					<form:input id="docTypology" path="docTypology" class="input_45c"/>
				</div>
			</div>

			<hr />
			
			<div class="listForm">
				<b><fmt:message key="docbase.editDetailsDocument.date"/>:</b>
				<br />
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.date"/>'>?</a>
						<form:label id="DocYearLabel" for="docYear" path="docYear" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.year"/></form:label>
					</div>
					<div class="col_l"><form:input id="docYear" path="docYear" class="input_4c" value="" maxlength="4"/></div>
					<div class="col_r"><form:label id="docMonthNumLabel" for="docMonthNum" path="docMonthNum" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.month"/></form:label></div>
					<div class="col_l"><form:select id="docMonthNum" path="docMonthNum" cssClass="selectform_long" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
					<div class="col_r"><form:label  for="docDay" id="docDayLabel" path="docDay" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.date.day"/></form:label></div>
					<div class="col_r"><form:input id="docDay" path="docDay" class="input_2c" maxlength="2"/></div>
				</div>
				<div class="row">
					<div class="col_r">
						<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.moderndating"/>'>?</a>
						<form:label id="yearModernLabel" for="yearModern" path="yearModern" cssErrorClass="error"><fmt:message key="docbase.editDetailsDocument.modernDating"/></form:label>
					</div>
					<div class="col_l"><form:input id="yearModern" path="yearModern" class="input_4c" maxlength="4"/></div>
				</div>
			</div>
			
			<div class="listForm">
				<div class="row">
					<div class="col_l">
						<form:label  id="dateUnsLabel" for="dateUns" path="dateUns"><fmt:message key="docbase.editDetailsDocument.dateUncertain"/></form:label>
						<form:checkbox id="dateUns" path="dateUns"/>
					</div>
					<div class="col_r">
						<form:label  id="dateUndatedLabel" for="dateUndated" path="dateUndated"><fmt:message key="docbase.editDetailsDocument.dateUndated"/></form:label>
						<form:checkbox  id="dateUndated" path="dateUndated"/>
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="listForm">
				<div class="row">
					<a class="helpIcon" title='<fmt:message key="docbase.editDetailsDocument.help.datenotes"/>'>?</a>
					<form:label for="dateNotes" id="dateNotesLabel" path="dateNotes"><fmt:message key="docbase.editDetailsDocument.dateNotes"/></form:label>
					<form:textarea id="dateNotes" path="dateNotes" class="txtarea"/>
				</div>
			</div>
			
			<!-- <form:hidden id="transcribeFolioNum" path="transcribeFolioNum" /> -->
			<!-- <form:hidden id="transcribeFolioMod" path="transcribeFolioMod" /> -->
			<form:hidden id="dateCreated" path="dateCreated" />
			<form:hidden id="entryId" path="entryId" />
			
			<form:errors path="entryId" cssClass="inputErrors" htmlEscape="false"/>
			<form:errors path="docDay" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="docMonthNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="docYear" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="folioNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="folioRectoVerso" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="insertNum" cssClass="inputerrors" htmlEscape="false"/>
			<form:errors path="volume" cssClass="inputerrors" htmlEscape="false"/>

			<div style="margin-top:5px">
				<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input id="save" class="button_small fr" type="submit" value="Save"/>
			</div>
			<c:if test="${fromTranscribe == null || !fromTranscribe}">
				<input type="hidden" value="" id="modify" />
			</c:if>
			<c:if test="${fromTranscribe != null && fromTranscribe}">
				<input type="hidden" value="1" id="modify" />
				<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
					<c:param name="volNum"	value="${document.volume.volNum}" />
					<c:param name="volLetExt"	value="${document.volume.volLetExt}" />
					<c:param name="imageOrder"	value="${command.imageOrder}" />
					<c:param name="total"	value="${command.total}" />
					<c:param name="totalRubricario"	value="${command.totalRubricario}" />
					<c:param name="totalCarta"	value="${command.totalCarta}" />
					<c:param name="totalAppendix"	value="${command.totalAppendix}" />
					<c:param name="totalGuardia"	value="${command.totalGuardia}" />
					<c:param name="totalOther"	value="${command.totalOther}" />
				</c:url>
				<input type="hidden" id="returnToManuscriptViewer" value="${ShowDocumentInManuscriptViewerURL}" />
			</c:if>
		</fieldset>	
		<input type="hidden" name="summaryId" value="${document.volume.summaryId}">
		<input type="hidden" id="folioNumStored" value=""/>

	</form:form>

	<c:url var="ShowDocument" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>

	<c:url var="ShowExplorerVolumeURL" value="/src/volbase/ShowExplorerVolume.do"/>
	
	<c:url var="CompareDocumentURL" value="/src/docbase/CompareDocument.do" />
	
	<c:url var="ShowDocumentAlreadyURL" value="/src/docbase/CompareDocument.do" />
	
	<c:url var="ShowDocumentsAlreadyURL" value="/src/docbase/ShowSameFolioDocuments.do" />
	
	<div id="question" style="display:none; cursor: default"> 
		<h1><fmt:message key="docbase.editDetailsDocument.messages.discardChanges"/></h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<div id="existentDocumentQuestion" style="display:none; cursor: default"> 
		<span class="warningMessage"></span>
		<span class="clickHere"></span>
		<input type="button" id="yesDQ" value="Yes" /> 
		<input type="button" id="noDQ" value="No" /> 
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditDetailsDocumentForm");
			
			$j("#folioNumStored").val($j("#folioNum").val());
			
	        $j("#EditCorrespondentsDocument").css('visibility', 'hidden');
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'hidden');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'hidden');
	        $j(".EditDocumentInManuscriptTranscriberOff").css('visibility', 'hidden');
	        $j("#EditDocumentInModal").css('visibility', 'hidden');
	        $j("#EditFactCheckDocument").css('visibility', 'hidden');
	        $j("#EditTopicsDocument").css('visibility', 'hidden');
	        
	        
	        $j("#EditDetailsDocumentForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
	        
	        <%-- We disable editing an already entered document volume number if not Administrator  --%>
	        <security:authorize ifNotGranted="ROLE_ADMINISTRATORS">
				<c:if test="${command.entryId != 0}"> 
					$j("#volume").attr("disabled","true");
    			</c:if>
			</security:authorize>

			/**
			 * This function open the tab that shows the volume explorer of the volume indicated as a param 
			 * of the url (href) or the one that has the id specificated in idTab (if the url does not contain
			 * the volume reference)
			 * @param volNum the volume number
			 * @param volLetExt the volume extension
			 * @param href the url to call for volume data loading process
			 */
			var openTab = function(volNum,volLetExt,href) {
				var idTab = '' + volNum + volLetExt;
				var numTab = 0;
				var tabExist = false;
				
				$j("#tabs ul li a").each(function() {
					
					if (!tabExist) {
						if (this.text != "") {
							numTab++;
						}
					}
					// We read the volNum associated to the current tab from the 'href' attribute of the anchor.
					// It is expected the 'href' attribute contains this information otherwise we search it
					// from the 'titleTab' identifier.
					var currentVolNum = getURLParameter($j(this).attr('href'), "volNum");
					if (currentVolNum == null && $j(this).find("#titleTab"+idTab).length > 0)
						tabExist = true;
					else if (volNum == currentVolNum) {
						// Check for the volume extension equality
						var currentVolExt = getURLParameter($j(this).attr('href'), "volLetExt");
						if (volLetExt == currentVolExt)
							tabExist = true;
					}
				});
				
				if (!tabExist) {
					// open a new tab
					var msg = '<fmt:message key="docbase.editDetailsDocument.messages.removeTab"/>';
					$j("#tabs").tabs("add", href, "<span id=\"titleTab" + idTab + "\">Explore Volume " + idTab + "</span></span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">" + msg + "</span>");
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				} else {
					// open the existent tab
					$j("#tabs").tabs("select", numTab);
				}
			}
			
			/**
			 * This function returns the value of the parameter (identified with 'name') read from the url provided.
			 * @param url the url to check
			 * @param name the name of the parameter
			 */
			var getURLParameter = function(url, name) {
			    return (RegExp('[\\?&]' + name + '=([^&#]*)').exec(url)||[,null])[1];
			}
			
			/**
			 * This function defines the change-handler of the "volume" input.
			 * If the volume informations correspond to an existent volume it shows that volume in the Volume Explorer (inside a tab).
			 */
			var showVolumeExplorer = function () {
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $j("#volume").val() },
					function(data){
						if (data.summaryId == "") {
							var vol = $j("#volume").val();
							var msg = '<fmt:message key="docbase.editDetailsDocument.error.volumeNotExist"><fmt:param value="' + vol + '" /></fmt:message>';
							displayErrorClientMsg("volume",msg);
							resetErrorClientMsg("folio");
							resetErrorClientMsg("transcribeFolio");
							resetErrorClientMsg("insert");
						} else {
							
							// Launch the insert checking process if not empty
							if ($j("#insertNum").val() && $j("#insertNum").val() != "") {
								$j("#insertNum").change();
							} else {
								// Launch the folio checking process if not empty
								if ($j("#folioNum").val() && $j("#folioNum").val() != "")
									checkRectoVersoOnDigitizedFolio("folio");
								
								// Launch the transcribe folio checking process if not empty
								if ($j("#transcribeFolioNum").val() && $j("#transcribeFolioNum").val() != "")
									checkRectoVersoOnDigitizedFolio("transcribeFolio");
							}
							
							// Open the volume explorer if volume is digitized
							if (data.volumeDigitized) {
	            				var showVolumeExplorerURL = "${ShowExplorerVolumeURL}?volNum=" + data.volNum + "&volLetExt=" + data.volLetExt + "&flashVersion=false";
	                    		openTab(data.volNum,data.volLetExt,showVolumeExplorerURL);
							}
						}
					}
				);
	 		}
			// We attach the change-handler to the "volume" input.
			$j("#volume").change(function() {
				resetErrorClientMsg("volume");
				$j("#save").attr("disabled","true");
				if ($j(this).val() != "") {
					showVolumeExplorer();
				} else {
					// We remove all errors from the client
					resetErrorClientMsg("folio");
					resetErrorClientMsg("transcribeFolio");
					resetErrorClientMsg("insert");
				}
			});
			
			/**
			 * This function enables the save button if (and only if) all the client-error sections are empty.
			 */
			function enableSaveIfNeeded() {
				var saveDisabled = false;
				$j("[id$=ErrorClient]").each(function() {
					if ($j(this).attr("display") == "block")
						saveDisabled = true;
				});
				if (!saveDisabled)
					$j("#save").removeAttr("disabled");
			}
			
			/**
			 * This function removes the warning message from the error section and remove the save button inhibition
			 * (only if all the client error section are empty).
			 *
			 * @param prefix the prefix of the error section; possible values are 'folio', 'transcribeFolio', 'volume' and 'insert'.
			 */
			function resetErrorClientMsg(prefix) {
				if ($j("#" + prefix + "ErrorClient").length > 0) {
					$j("#" + prefix + "ErrorClient").html("");
					$j("#" + prefix + "ErrorClient").attr("display","none");
				}
				// The save button is enabled if there are no other errors
				enableSaveIfNeeded();
			}
			
			/**
			 * This function shows a warning message in the error section.
			 * NOTE: this function does not inhibit the save button.
			 *
			 * @param prefix the prefix of the error section; possible values are 'folio' and 'transcribeFolio'
			 * @param msg the message to show
			 */
			function displayErrorClientMsg(prefix,msg) {
				$j("#" + prefix + "ErrorClient").html(msg);
				$j("#" + prefix + "ErrorClient").attr("display","block");
			}
			
			
			/**
			 * This function checks the correctness of recto/verso information.
			 * If the informations of volume, insert and folio (number + extension) provided in the 'EditDetailsDocumentForm'
			 * correspond to a digitized folio this function checks if that folio has a correct recto/verso information.
			 * NOTE: at least volume and folio number informations must be provided.
			 *
			 * @param prefix the prefix of the inputs; possible values are 'folio' and 'transcribeFolio'.
			 */
			var checkRectoVersoOnDigitizedFolio = function(prefix) {
				 resetErrorClientMsg(prefix);
				
				var rectoVersoSelector = $j("#" + prefix + "RectoVerso");
				var volumeBlank = !$j("#volume").val() || $j("#volume").val() == "";
				var folioNumBlank = !$j("#" + prefix + "Num").val() || $j("#" + prefix + "Num").val() == "";
				var folioModBlank = !$j("#" + prefix + "Mod").val() || $j("#" + prefix + "Mod").val() == "";
				var folioRVBlank = !$j(rectoVersoSelector).val() || $j(rectoVersoSelector).val() == "";

				// The save button is inhibited during the checking process
				$j("#save").attr("disabled","true");
				
				if (volumeBlank) {
					displayErrorClientMsg("volume",'<fmt:message key="docbase.editDetailsDocument.error.volumeMissing"/>');
				} else {
					var rectoVersoInputValue = $j(rectoVersoSelector).val();
					var rectoVersoValidate = rectoVersoInputValue == '' || rectoVersoInputValue == 'R' || rectoVersoInputValue == 'r' || rectoVersoInputValue == 'V' || rectoVersoInputValue == 'v';
					
					if (!rectoVersoValidate) {
						var rectoVersoString = '' + (folioRVBlank ? 'blank' : $j(rectoVersoSelector).val());
						var msg = '<fmt:message key="docbase.editDetailsDocument.error.rectoVersoIncorrect"><fmt:param value="' + rectoVersoString + '" /></fmt:message>';
						displayErrorClientMsg(prefix,msg);
					} else {
					
						$j.get('<c:url value="/de/volbase/CheckFolio.json" />', 
							{ volume: $j("#volume").val(), 
							  insertNum : $j("#insertNum").val(), 
							  insertLet: $j("#insertLet").val(), 
							  folioNum: $j("#" + prefix + "Num").val(), 
							  folioMod: $j("#" + prefix + "Mod").val(), 
							  folioRectoVerso: $j(rectoVersoSelector).val() 
							},
							function(data){
								if (data.volumeDigitized && !data.rectoVersoCheck) {
									var startOrTranscribe = '' + ((prefix == 'folio') ? '<fmt:message key="docbase.editDetailsDocument.error.start"/>' : '<fmt:message key="docbase.editDetailsDocument.error.transcribe"/>');  
									var folioString = '' + (folioNumBlank ? 'blank' : $j("#" + prefix + "Num").val());
									folioString += (folioModBlank ? '' : (' ' + $j("#" + prefix + "Mod").val()));
									folioString += (folioRVBlank ? '' : (' ' + $j(rectoVersoSelector).val()));
									var msg = '<fmt:message key="docbase.editDetailsDocument.error.rectoVersoNotExist"><fmt:param value="' + startOrTranscribe + '" /><fmt:param value="' + folioString + '" /></fmt:message>';
									displayErrorClientMsg(prefix,msg);
								} else {
									// The save button is enabled if there are no other errors
									enableSaveIfNeeded();
								}
							}
						);
					}
				}
			}
			
			
			/**
			 * This function defines the "out of range" check for the folio number.
			 *
			 * @param prefix the prefix of the inputs; possible values are 'folio' and 'transcribeFolio'.
			 */
			var outOfRangeFolioCheck = function(prefix) {
				$j.get('<c:url value="/de/volbase/FindVolume.json" />', { volume: $j("#volume").val() }, function(data) {
					if (data.folioCount != "") {
						var folioNumber = parseInt($j("#" + prefix + "Num").val(),10);
						if (folioNumber > parseInt(data.folioCount,10)) {
							var folioStr = '' + (prefix == 'folio' ? '<fmt:message key="docbase.editDetailsDocument.error.start" />' : '<fmt:message key="docbase.editDetailsDocument.error.transcribe" />')
							var msg = '<fmt:message key="docbase.editDetailsDocument.error.folioHigher"><fmt:param value="' + folioStr + '" /><fmt:param value="' + folioNumber + '" /></fmt:message>';
							displayErrorClientMsg(prefix,msg);
						} else if ($j("#" + prefix + "Num").val() != "") {
							checkRectoVersoOnDigitizedFolio(prefix);
						} else {
							// The save button is enabled if there are no other errors
							enableSaveIfNeeded();
						}
					} else {
						if ($j("#" + prefix + "Num").val() != "") {
							checkRectoVersoOnDigitizedFolio(prefix);
						} else {
							// The save button is enabled if there are no other errors
							enableSaveIfNeeded();
						}
					}
				});
			}
			
			
			/**
			 * This function defines a change handler for the folio number inputs.
			 *
			 * @param prefix the prefix of the inputs; possible values are 'folio' and 'transcribeFolio'.
			 */
			var folioNumChangeHandler = function(prefix) {
				resetErrorClientMsg(prefix);
				var folioSelector = $j("#" + prefix + "Num");
				if ($j(folioSelector).val() && $j(folioSelector).val() != "") {
					$j("#save").attr("disabled","true");
					if ($j("#volume").val() && $j("#volume").val() != "") {
						outOfRangeFolioCheck(prefix);
					} else {
						displayErrorClientMsg("volume",'<fmt:message key="docbase.editDetailsDocument.error.volumeMissing"/>');
					}
				}
			}
			// We attach the change-handler to the "folioNum" input
			$j("#folioNum").change(function() {
				folioNumChangeHandler("folio");
			});
			// We attach the change-handler to the "transcribeFolioNum" input
			$j("#transcribeFolioNum").change(function() {
				folioNumChangeHandler("transcribeFolio");
			});
			
			
			/**
			 * This function defines a change handler for the folio extension inputs.
			 *
			 * @param prefix the prefix of the inputs; possible values are 'folio' and 'transcribeFolio'.
			 */
			var folioModChangeHandler = function(prefix) {
				var folioSelector = $j("#" + prefix + "Num");
				if ($j(folioSelector).val() && $j(folioSelector).val() != "") {
					$j(folioSelector).change();
				}
			}
			// We attach the change-handler to the "folioMod" input
			$j("#folioMod").change(function() {
				folioModChangeHandler("folio");
			});
			// We attach the change-handler to the "transcribeFolioMod" input
			$j("#transcribeFolioMod").change(function() {
				folioModChangeHandler("transcribeFolio");
			});
			
			
			/**
			 * This function defines a change handler for the folio recto/verso inputs.
			 *
			 * @param prefix the prefix of the inputs; possible values are 'folio' and 'transcribeFolio'.
			 */
			var folioRectoVersoChangeHandler = function(prefix) {
				resetErrorClientMsg(prefix);
				var folioSelector = $j("#" + prefix + "Num");
				if ($j(folioSelector).val() && $j(folioSelector).val() != "") {
					checkRectoVersoOnDigitizedFolio(prefix);
				}
			}
			// We attach the change-handler to the "folioRectoVerso" input
			$j("#folioRectoVerso").change(function() {
				folioRectoVersoChangeHandler("folio");
			});
			// We attach the change-handler to the "transcribeFolioRectoVerso" input
			$j("#transcribeFolioRectoVerso").change(function() {
				folioRectoVersoChangeHandler("transcribeFolio");
			});
			
			
			/**
			 * This function defines a change handler for the insert number input.
			 */
			var insertNumChangeHandler = function() {
				resetErrorClientMsg("insert");
				if ($j("#volume").val() && $j("#volume").val() != "" && $j(this).val() && $j(this).val() != "") {
					$j("#save").attr("disabled","true");
					if ($j("#volumeErrorClient").html() == "") {
						// Volume exists
						$j.get('<c:url value="/src/docbase/CheckInsert.json" />', { volume: $j("#volume").val(), insertNum: $j(this).val(), insertLet: $j("#insertLet").val() }, function(data) {
							if (typeof data.error === "undefined") {
								if (data.insertOK == false) {
									var insertNum = $j("#insertNum").val();
									var msg = '<fmt:message key="docbase.editDetailsDocument.error.insertNotExist"><fmt:param value="' + insertNum + '" /></fmt:message>';
									displayErrorClientMsg("insert", msg);
								} else {
									$j("#folioNum").change();
									$j("#transcribeFolioNum").change();
								}
							} else {
								displayErrorClientMsg("insert", data.error);
							}
						});
					}
				}
			}
			// We attach the change-handler to the "insertNum" input
			$j("#insertNum").change(insertNumChangeHandler);
			
			
			/**
			 * This function defines a change handler for the insert extension input.
			 */
			var insertLetChangeHandler = function() {
				if ($j("#insertNum").val() && $j("#insertNum").val() != "") {
					$j("#insertNum").change();
				}
			}
			// We attach the change-handler to the "insertLet" input
			$j("#insertLet").change(insertLetChangeHandler);
			
			
			/*if ($j("#transcribeFolioNum").val().length>0) {
				$j("#EditDetailsDocument").volumeExplorer( {
					summaryId				: "${document.volume.summaryId}",
					transcribeFolioNum		: "${command.transcribeFolioNum}",
					checkVolumeDigitizedURL	: "${checkVolumeDigitizedURL}",
					showExplorerVolumeURL	: "${ShowExplorerVolumeURL}",
					target 					: $j("#body_right") 
				});  
			}*/
			
			
			/**
			 * This function defines the operations of the submit process.
			 */
			var submitHandler = function () {
				// Disabled attributes are removed because disabled inputs are submitted with null values.
				$j("#EditDetailsDocumentForm").find("[disabled='disabled']").each(function(index) {
					$j(this).removeAttr("disabled");
				});
				$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        	
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0) {
						$j("#EditDetailsDocumentDiv").html(html);
					} else {
						<c:choose> 
							<c:when test="${command.entryId == 0}"> 
								$j("#body_left").html(html);
							</c:when> 
							<c:otherwise> 
								$j("#body_left").html(html);
							</c:otherwise> 
						</c:choose> 
					}
				}});
				return false;
			}
			// We attach the submit-handler to the form 'EditDetailsDocumentForm'.
			$j("#EditDetailsDocumentForm").submit(submitHandler);
			
			
			/**
			 * This object contains message and style informations of the "existentDocumentQuestion" popup.
			 */
			var existentDocumentPopupBlock = { 
					message: $j('#existentDocumentQuestion'), 
					css: { 
						border: 'none', 
						padding: '5px',
						width: '250px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
				};
			
			
			/**
			 * This function defines the find document callback used in the save process.
			 */
			var findDocumentCallback = function(data) {
				//In this case we have a document with the same volume and folio and the link open in a tab its record.
				if (data.countAlreadyEntered == 1) {
					var msg = '<fmt:message key="docbase.editDetailsDocument.messages.oneDocExists"/>';
					$j("#existentDocumentQuestion .warningMessage").html("<span>"+ msg +"<br /></span>");
					var clickHereMsg = '<fmt:message key="docbase.editDetailsDocument.messages.clickHereDoc"/>';
					$j("#existentDocumentQuestion .clickHere").html("<span><a class=\"compareDoc\" style=\"color:red\" href=\"${ShowDocumentAlreadyURL}?entryId=" + data.entryId +  "\"><u>"+clickHereMsg+"</u></a><br/></span>");
					
					$j(".compareDoc").click(function() {
						showAlreadyEnteredDocumentsTab(data.volNum+data.volLetExt,data.insertNum+data.insertLet,data.folioNum+data.folioMod+' '+data.folioRectoVerso, $j(this).attr("href"), false);
						return false;
					});
					$j('#EditDetailsDocumentForm').block(existentDocumentPopupBlock); 
					return;
				} else if (data.countAlreadyEntered > 1) {
					var msg = '<fmt:message key="docbase.editDetailsDocument.messages.moreDocsExist"/>';
					$j("#existentDocumentQuestion .warningMessage").html("<span>"+ msg +"<br /></span>");
					var showDocumentsAlreadyURLParams = "?volNum=" + data.volNum +  "&volLetExt=" + data.volLetExt + "&insertNum=" + data.insertNum + "&insertLet=" + data.insertLet + "&folioNum=" + data.folioNum + "&folioMod=" + data.folioMod + "&folioRectoVerso=" + data.folioRectoVerso;
					var clickHereMsg = '<fmt:message key="docbase.editDetailsDocument.messages.clickHereDocs"/>';
					$j("#existentDocumentQuestion .clickHere").html("<span><a class=\"compareDocs\" style=\"color:red\" href=\"${ShowDocumentsAlreadyURL}" + showDocumentsAlreadyURLParams + "\"><u>"+clickHereMsg+"</u></a><br/></span>");
					
					$j(".compareDocs").click(function() {
						showAlreadyEnteredDocumentsTab(data.volNum+data.volLetExt,data.insertNum+data.insertLet,data.folioNum+data.folioMod+' '+data.folioRectoVerso, $j(this).attr("href"), true);
						return false;
					});
					$j('#EditDetailsDocumentForm').block(existentDocumentPopupBlock); 
					return;
				}
				$j("#EditDetailsDocumentForm").submit();
			}
			
			
			/**
			 * This function defines the handler of the "Save" button.
			 */
			var saveHandler = function() {
				if($j("#folioNum").val() == $j("#folioNumStored").val()){
					// An existent document is going to be modified.					
					if ($j("#alreadyDigitized").length > 0) {
						$j("#alreadyDigitized").remove();
					}
					$j("#save").removeAttr("disabled");
					return true;
				}
				
				$j.get('<c:url value="/src/docbase/FindDocument.json" />', 
					{ volume: $j("#volume").val(), 
					  insertNum: $j("#insertNum").val(), 
					  insertLet: $j("#insertLet").val(), 
					  folioNum: $j("#folioNum").val(), 
					  folioMod: $j("#folioMod").val(), 
					  folioRectoVerso: $j("#folioRectoVerso").val() },
					findDocumentCallback
				);
				return false;
			}
			// We attach the save click-handler to the "Save" button.
			$j("#save").click(saveHandler);
			
			
			/**
			 * This function opens a tab with the informations retrieved from the href param provided.
			 * If a tab with a specific name already exists this function selects that tab.
			 *
			 * @param volume the volume (number + extension)
			 * @param insert the insert (number + extension)
			 * @param folio the folio (number + extension + recto/verso)
			 * @param href the url where to retrieve the informations to show
			 * @param tableDoc true if only one doc is retrieved
			 */
			var showAlreadyEnteredDocumentsTab = function(volume,insert,folio,href,tableDoc) {
				var tabName = '<fmt:message key="docbase.editDetailsDocument.messages.vol"/>' + volume + 
					(insert != "" ? " / " + '<fmt:message key="docbase.editDetailsDocument.messages.ins"/> ' + insert : "") + 
					" / " + '<fmt:message key="docbase.editDetailsDocument.messages.folio"/> ' + folio + 
					(tableDoc ? ' <fmt:message key="docbase.editDetailsDocument.messages.document"/> ' : ' <fmt:message key="docbase.editDetailsDocument.messages.documents"/> ');
				var numTab = 0;
				var tabExist = false;
				$j("#tabs ul li a").each(function() {
					if (!tabExist) {
						if (this.text != "") {
							numTab++;
						}
					}
					if (this.text == tabName) {
						tabExist = true;
					}
				});
				if (!tabExist) {
					// the new tab is opened and the CompareDoc Table is loaded
					var msg = '<fmt:message key="docbase.editDetailsDocument.messages.removeTab"/>';
					$j("#tabs").tabs("add", href, tabName + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">" + msg);
					$j("#tabs").tabs("select", $j("#tabs").tabs("length")-1);
				} else {
					// the existent tab is selected
					$j("#tabs").tabs("select", numTab);
				}
				return false;
			}
			
			/**
			 * This function defines the handler of "Close" button.
			 */
			var closeHandler = function() {
				if ($j("#modify").val() == 1) {
	        		// Block is attached to form otherwise this block does not work when we use in transcribe and contextualize document
					$j('#EditDetailsDocumentForm').block({ message: $j('#question'), 
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					}); 
					return false;
	        	} else {
	        		$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
	    				$j("#body_left").html(html);
	    			}});
	    				
	    			return false; 
	        	}	
			}
			// We attach the close click-handler to the "Close" button.
	        $j('#close').click(closeHandler);
	        
			$j('.helpIcon').tooltip({ 
				track: true, 
				fade: 350 
			});
			
			
			/**
			 * This function defines the handler for the "No" button of the "Discarg changes" popup.
			 */
			var discardChangesNoHandler = function() {
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				// Block is attached to the form otherwise this block does not work when we use in transcribe and contextualize document
				$j("#EditDetailsDocumentForm").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}
			// We attach the click-handler to the "No" button of the "Discarg changes" popup.
			$j('#no').click(discardChangesNoHandler); 
	        
			
			/**
			 * This function defines the handler for the "Yes" button of the "Discarg changes" popup.
			 */
			var discardChangesYesHandler = function() {
				if($j("#returnToManuscriptViewer").length > 0){
					window.open($j("#returnToManuscriptViewer").val(),'BIA Manuscript Viewer', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=no');
				}
				$j.ajax({ url: '${ShowDocumentURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
								
				return false; 
			}
			// We attach the click-handler to the "Yes" button of the "Discarg changes" popup.
			$j('#yes').click(discardChangesYesHandler); 
			
			
			/**
			 * This function defines the handler for the "No" button of the "ExistentDocumentQuestion" popup.
			 * ==> The user does not want to create the document.
			 */
			var existentDocumentQuestionNoHandler = function() {
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#existentDocumentQuestion").hide();
				// Block is attached to the form otherwise this block does not work when we use in transcribe and contextualize document
				$j("#EditDetailsDocumentForm").append($j("#existentDocumentQuestion"));
				$j(".blockUI").remove();
				return false; 
			}
			// We attach the click-handler to the "No" button of the "ExistentDocumentQuestion" popup.
			$j('#noDQ').click(existentDocumentQuestionNoHandler);
			
			
			/**
			 * This function defines the handler for the "Yes" button of the "ExistentDocumentQuestion" popup.
			 * ==> The user wants to create the document.
			 */
			var existentDocumentQuestionYesHandler = function() {
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#existentDocumentQuestion").hide();
				// Block is attached to the form otherwise this block does not work when we use in transcribe and contextualize document
				$j("#EditDetailsDocumentForm").append($j("#existentDocumentQuestion"));
				$j(".blockUI").remove();
				// Submitting the "EditDetailsDocumentForm" form.
				$j("#EditDetailsDocumentForm").submit();
				return false;
			}
			// We attach the click-handler to the "Yes" button of the "ExistentDocumentQuestion" popup.
			$j('#yesDQ').click(existentDocumentQuestionYesHandler);
     	
		});
	</script>