<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div style="width: auto; min-height: 0px; height: 566.4px;" id="volumeSummaryWindow" class="ui-dialog-content ui-widget-content"> 
		<h5>Volume ${volumeSummary.volNum} ${volumeSummary.volLetExt}</h5>
		<h3>${volumeSummary.carteggio}</h3>
		<div class="list">
			<div class="row">
				<div class="item">Folio count</div> 
				<div class="value"><b>454</b></div> 
			</div>
			<div class="row">
				<div class="item"><i>Index of Names</i> count</div> 
				<div class="value"><b>21</b></div> 
			</div>
			<div class="row">
				<div class="item">Missing Folios</div> 
				<div class="valueMissingFolios">33</div> 
			</div>
			<div class="row">
				<div class="item">Missed Numbering Folios (bis, ter, quater, etc.)</div> 
				<div class="value"><b>63bis, 119bis, 133bis, 149bis, 181bis, 212bis, 255bis, 309bis, 362bis, 381bis., 119bis, 133bis, 149bis, 181bis, 212bis, 255bis, 309bis, 362bis, 381bis63bis, 119bis, 133bis, 149bis, 181bis, 212bis, 255bis, 309bis, 362bis, 381bis.</b></div> 
			</div>
			<hr>
			<div class="row">
				<div class="item">Cartulazione</div> 
				<div class="value">${volumeSummary.cartulazione}</div> 
			</div>
			<div class="row">
				<div class="item">Note alla Cartulazione</div> 
				<div class="value">${volumeSummary.noteCartulazione}</div> 
			</div>
			<hr>
			<div class="row">
				<div class="item"><u>Volume Size:</u></div> 
			</div>
			<div class="row">
				<div class="item">Width (mm)</div> 
				<div class="value">230</div> 
			</div>
			<div class="row">
				<div class="item">Height (mm)</div> 
				<div class="value">295</div> 
			</div>
		</div>
	</div>