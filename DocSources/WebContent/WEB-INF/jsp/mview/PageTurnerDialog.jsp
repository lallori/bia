<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="SearchAjaxURL" value="/src/mview/SearchCarta.json"/>
	
	<c:url var="IIPImageServerURL" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>
	
	<c:url var="PersonalNotesDialogURL" value="/src/mview/EditPersonalNotesDialog.do"/>
	
	<c:url var="VolumeSummaryDialogURL" value="/src/mview/ShowSummaryVolumeDialog.do">
		<c:param name="volNum" value="${command.volNum}" />
		<c:param name="volLetExt" value="${command.volLetExt}" />
	</c:url>
	

	<c:url var="currentPage" value="${caller}">
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

	<c:url var="previousPage" value="${caller}">
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
	<div id="prevNextButtons">
	    <div id="prevButton">
		<c:if test="${command.imageOrder == 1}">
	    	<a class="previousPage"><img src="<c:url value="/images/mview/button_prev.png" />" alt="prev" /></a>
		</c:if>
		<c:if test="${command.imageOrder > 1}">
			<a id="previous" href="${previousPage}" class="previousPage"><img src="<c:url value="/images/mview/button_prev.png" />" alt="previous" /></a>
		</c:if>
		</div>
		<div id="folio" title="Warning!" style="display:none"> 
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>You need to save your Extract / Synopsis text before using the page turner.</p> 
		</div> 
		<div id="nextButton">
		<c:if test="${command.imageOrder == command.total}">
			<a id="next" class="nextPage"><img src="<c:url value="/images/mview/button_next.png" />" alt="next" /></a>
		</c:if>
		<c:if test="${command.imageOrder < command.total}">
			<a id="next" href="${nextPage}" class="nextPage"><img src="<c:url value="/images/mview/button_next.png" />" alt="next" /></a>
		</c:if>
		</div>
	</div>

	<a id="volumeSummary" href="#"><img alt="Volume Summary" src="<c:url value="/images/mview/button_volumesummary.png"/>"></a>

	<div id="line"></div>
	
	<div id="rubricarioMoveTo">
		<form:form id="moveToRubricarioForm" method="post" class="edit">
			<label id="imageProgTypeNumLabel" class="rubricarioLabel" for="imageProgTypeNum">Move to <i>Index of Names</i> folio</label>
			<input id="imageProgTypeNum" name="imageProgTypeNum" class="input_4cRucricario" type="text" value="" />
			<input id="goR" type="image" src="<c:url value="/images/mview/go.png" />" alt="Go"/>
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
	<div id="folioMoveTo">
		<form:form id="moveToFolioForm" method="post" class="edit">
			<label for="imageProgTypeNum" id="imageProgTypeNumLabel" class="folioLabel">Move to folio </label>
			<input id="imageProgTypeNum" class="input_4cFolio" type="text" value="" name="imageProgTypeNum" />
			<input id="go" type="image" src="<c:url value="/images/mview/go.png" />" alt="Go"/>
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
	
	<div id="line2"></div>
	
	<div id="personalNotesDiv">
		<a id="personalNotesButton" href="#"><img src="<c:url value="/images/mview/button_personalNotes.png" />" alt="Personal Notes" /></a>
	</div>
	
	<div id="exitDiv">
		<a id="exitButton" href="#" onClick="$j('#exit').dialog('open');"><img src="<c:url value="/images/mview/button_exit.png" />" alt="Exit" /></a>
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

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#moveToFolioForm").pageTurnerForm({searchUrl: '${SearchAjaxURL}', imagePrefix: '${ImagePrefixURL}', IIPImageServer: '${IIPImageServerURL}'});
			$j("#rubricarioMoveTo").pageTurnerForm({searchUrl: '${SearchAjaxURL}', imagePrefix: '${ImagePrefixURL}', IIPImageServer: '${IIPImageServerURL}'});
			$j("#previous").pageTurnerPage({imagePrefix: '${ImagePrefixURL}', IIPImageServer: '${IIPImageServerURL}'});
			$j("#next").pageTurnerPage({imagePrefix: '${ImagePrefixURL}', IIPImageServer: '${IIPImageServerURL}'});
			
			var $dialogPersonalNotes = $j('<div id="DialogPersonalNotesDiv"></div>').dialog({                                                                                                                                                                   
				autoOpen: false,
				width: 352,
				minWidth: 350,
				minHeight: 200,                                                                                                                                                         
				title: 'PERSONAL NOTES',
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
				width: 520,
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
		});
	</script>
