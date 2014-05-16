<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div style="width: auto; min-height: 0px; height: 566.4px; overflow: auto;" id="volumeSummaryWindow" title="VOLUME SUMMARY" class="ui-dialog-content ui-widget-content"> 
		<h5>Volume ${volumeSummary.volNum} ${volumeSummary.volLetExt}</h5>
		<h3>${volumeSummary.carteggio}</h3>
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.folioCount"/></div> 
				<div class="value"><b>${volumeSummary.totalCarta}</b></div> 
			</div>
			<div class="row">
				<div class="item"><i><fmt:message key="mview.showSummaryVolumeDialog.indexOfNames"/></i> <fmt:message key="mview.showSummaryVolumeDialog.indexOfNames.count"/></div> 
				<div class="value"><b>${volumeSummary.totalRubricario}</b></div> 
			</div>
<!-- 			<div class="row"> -->
<!-- 				<div class="item"><i>Folio</i> count</div>  -->
<%-- 				<div class="value"><b>${volumeSummary.total}</b></div>  --%>
<!-- 			</div> -->
			<%-- <div class="row">
				<div class="item"><i>Index of Appendix</i> count</div> 
				<div class="value"><b>${volumeSummary.totalAppendix}</b></div> 
			</div>
			<div class="row">
				<div class="item"><i>Index of Guardia</i> count</div> 
				<div class="value"><b>${volumeSummary.totalGuardia}</b></div> 
			</div>
			<div class="row">
				<div class="item"><i>Index of Other</i> count</div> 
				<div class="value"><b>${volumeSummary.totalOther}</b></div> 
			</div>
			<div class="row">
				<div class="item">Missing Folios count</div> 
				<div class="valueMissingFolios">${volumeSummary.totalMissingFolios}</div> 
			</div> --%>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.missingFolios"/></div> 
				<div class="valueMissingFolios"><b>${fn2:toString(volumeSummary.missingFolios)}</b></div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.folioAddenda"/><br /><fmt:message key="mview.showSummaryVolumeDialog.folioAddenda.extension"/></div> 
				<div class="value"><b>${fn2:toString(volumeSummary.misnumberedFolios)}</b></div> 
			</div>
			
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.numeration"/></div> 
				<div class="value">${volumeSummary.cartulazione}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.numerationNotes"/></div> 
				<div class="value">${volumeSummary.noteCartulazioneEng}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.context"/></div>
				<div class="value">${volumeSummary.ccontext}</div>
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.inventarioSommarioDescription"/></div>
				<div class="value">${volumeSummary.inventarioSommarioDescription}</div>
			</div>
			
			<div class="row">
				<div class="item"><u><fmt:message key="mview.showSummaryVolumeDialog.volumeSize"/>:</u></div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.volumeWidth"/></div> 
				<div class="value">${volumeSummary.width}</div> 
			</div>
			<div class="row">
				<div class="item"><fmt:message key="mview.showSummaryVolumeDialog.volumeHeight"/></div> 
				<div class="value">${volumeSummary.height}</div> 
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			var close = $j("#ui-dialog-title-DialogVolumeSummaryDiv").parent();
			$j(close).find(".ui-dialog-titlebar-close").css("display", "inline");
			//$j(".ui-dialog-titlebar-close").css("display", "inline");
		});
	</script>