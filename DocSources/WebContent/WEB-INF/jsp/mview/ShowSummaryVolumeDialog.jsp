<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div style="width: auto; min-height: 0px; height: 566.4px;" id="volumeSummaryWindow" title="VOLUME SUMMARY" class="ui-dialog-content ui-widget-content"> 
		<h5>Volume ${volumeSummary.volNum} ${volumeSummary.volLetExt}</h5>
		<h3>${volumeSummary.carteggio}</h3>
		<div class="list">
			<div class="row">
				<div class="item">Folio count</div> 
				<div class="value"><b>${volumeSummary.totalCarta}</b></div> 
			</div>
			<div class="row">
				<div class="item"><i>Index of Names</i> count</div> 
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
				<div class="item">Missing Folios</div> 
				<div class="valueMissingFolios"><b>${fn2:toString(volumeSummary.missingFolios)}</b></div> 
			</div>
			<div class="row">
				<div class="item">Folio addenda<br />(bis, ter, quater, etc.)</div> 
				<div class="value"><b>${fn2:toString(volumeSummary.misnumberedFolios)}</b></div> 
			</div>
			
			<div class="row">
				<div class="item">Cartulazione</div> 
				<div class="value">${volumeSummary.cartulazione}</div> 
			</div>
			<div class="row">
				<div class="item">Note alla Cartulazione</div> 
				<div class="value">${volumeSummary.noteCartulazioneEng}</div> 
			</div>
			<div class="row">
				<div class="item">Context</div>
				<div class="value">${volumeSummary.ccontext}</div>
			</div>
			<div class="row">
				<div class="item">Inventario Sommario Description</div>
				<div class="value">${volumeSummary.inventarioSommarioDescription}</div>
			</div>
			
			<div class="row">
				<div class="item"><u>Volume Size:</u></div> 
			</div>
			<div class="row">
				<div class="item">Width (mm)</div> 
				<div class="value">${volumeSummary.width}</div> 
			</div>
			<div class="row">
				<div class="item">Height (mm)</div> 
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