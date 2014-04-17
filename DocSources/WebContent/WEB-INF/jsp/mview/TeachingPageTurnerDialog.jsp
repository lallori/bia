<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="PageTurnerDialogURL" value="/src/mview/JumpToFolio.json"/>

	<c:url var="SearchAjaxURL" value="/src/mview/SearchCarta.json"/>
	
	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>

	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
	<c:url var="VolumeSummaryDialogURL" value="/src/mview/ShowSummaryVolumeDialog.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
	</c:url>
	
	<c:url var="currentPage" value="/src/mview/TeachingPageTurnerDialog.do">
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
	
	<div id="PageTurnerVerticalDiv">
	
		<input type="hidden" id="currentEntryId" value="${command.entryId}" />
		<input type="hidden" id="currentImageOrder" value="${command.imageOrder}" />
	
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
	    
	</div>


	<script type="text/javascript">
		$j(document).ready(function() {
		
			console.log('Current image order' + ${command.imageOrder});
			
			var pageTurnerParams = {
				searchUrl: '${SearchAjaxURL}', 
		        getLinkedDocumentUrl:  null,
				imagePrefix: '${ImagePrefixURL}', 
				IIPImageServer: '${IIPImageServerURL}', 
				annotationsType: 'remote',
				retrieveAnnotationsUrl: null,
				updateAnnotationsUrl: null,
				annotations: null,
				showHideAnnotationButton: false,  // Show/Hide Annotation Button is disabled
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
			    textVerso : '<fmt:message key="mview.credits.verso"/>'
			};
			
			$j("#moveToFolioForm").pageTurnerForm(pageTurnerParams);
			$j("#indexNames").pageTurnerPage(pageTurnerParams);
			$j("#previous").pageTurnerPage(pageTurnerParams);
			$j("#next").pageTurnerPage(pageTurnerParams);
			
			// TODO: check if notFound element has to be included in the page
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
			
			$j('#volumeSummary').click(function(){
				if ($dialogVolumeSummary.dialog("isOpen")) {
					$dialogVolumeSummary.dialog("close");;
					return false;
				} else {
					$dialogVolumeSummary.dialog("open");
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
