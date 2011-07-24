<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="customSearchFilterDiv">
		<div class="customSearchFilterTitle"></div>
			<div id="multiOpenAccordion">
			<h1><a>Word search</a></h1>
			<div>
				<form id="wordSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
					<input type="text" id="word" name="word" class="input_15c" value="" />
					in 
					<select id="wordType" name="wordType" class="selectform_LXlong">
						<option value="SynopsisAndExtract" selected="selected">Synopsis and Extract</option>
						<option value="Synopsis">Document Synopsis</option>
						<option value="Extract">Document Extract</option>				
					</select>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Word Search">
				</form>
			</div>

			<h1><a><i>in</i> Volume</a></h1>
			<div>
				<form id="volumeSearchForm" method="post" class="edit">
					<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
					<select id="volumeType" name="volumeType" class="selectform_long">
						<option value="Exactly" selected="selected">Exactly</option>
						<option value="Between">Between</option>
					</select>
					<input type="text" id="volume"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
					<p class="invisibleVol">and</p>
					<input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Volume">
				</form>
			</div>

			<h1><a>Date Range</a></h1>
			<div>
				<form id="dateSearchForm" method="post" class="edit">
					<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
					<select id="dateType" name="dateType" class="selectform_Llong">
						<option value="After">Written after</option>
						<option value="Before">Written before</option>
						<option value="Between">Written between</option>
					</select>
					<input type="text" id="dateYear" class="input_4c" maxlength="4" value="yyyy"/>
					<select id="dateMonth" name="dateMonth" class="selectform">
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
					</select>
					<input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter" class="addDateRange">
					<input type="hidden" id="category" value="Date">
					<p class="invisible">and</p>
	                <input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/>
	                <select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
	                </select>
	                <input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/>
				</form>
			</div>
		</div>		
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#dateMonth option:eq(0)").text("mm");
		$j("#dateMonth option:eq(0)").attr('selected', 'selected');
		$j("#dateMonthBetween option:eq(0)").text("mm");
		$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

		$j("#volumeType").change(function(){
			if(this.options[1].selected) {
				$j("#volumeBetween").css('visibility','visible'); 
				$j(".invisibleVol").css('visibility','visible'); 
			} else { 
				$j("#volumeBetween").css('visibility','hidden');
				$j(".invisibleVol").css('visibility','hidden');
			}
		});	

		$j("#dateType").change(function(){
			if(this.options[2].selected) { 
				$j("#dateYearBetween").css('visibility','visible');
				$j("#dateMonthBetween").css('visibility','visible');
				$j("#dateDayBetween").css('visibility','visible');
				$j('.invisible').css('visibility','visible');
				$j('.visible').css('visibility','hidden');
				$j('.addDateRange').css('margin-top','53px');
		   } else { 
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
				$j('.addDateRange').css('margin-top','5px');
			}
		});
		
		$j('#dateYear').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateYearBetween').focus(function() {
			if(this.value=='yyyy') {
				this.value='';
			}
		});
		$j('#dateDay').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});
		$j('#dateDayBetween').focus(function() {
			if(this.value=='dd') {
				this.value='';
			}
		});

		$j("#wordSearchForm").advancedSearchForm();
		$j("#volumeSearchForm").advancedSearchForm();
		$j("#dateSearchForm").advancedSearchForm();

		$j('#multiOpenAccordion').multiAccordion({active: [0]});
	});
</script>
