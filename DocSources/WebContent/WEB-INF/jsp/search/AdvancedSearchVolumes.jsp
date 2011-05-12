<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<b>ADVANCED SEARCH</b><a href="#" class="helpLink">?</a>
<br /><br />
<div id="multiOpenAccordion">
	<h1><a>WORD SEARCH</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="wordSearchForm" method="post" class="edit">
			<input id="wordSearchForm" name="wordSearch" class="input_15c" type="text" value=""/>
			in
			<select id="wordInSearch" name="wordInSearch" class="selectform_Xlong">
				<option value="Volumes Titles and Notes" selected="selected">Volumes Titles and Notes</option>
				<option value="Volume Titles">Volume Titles</option>
				<option value="Notes">Notes</option>
			</select>
			<a href="#" id="addWordSearch">Add</a>
		</form>
	</div>
	
	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="volumeSearchForm" method="post" class="edit">
			<select id="volumeSearchExactlyBetween" name="volumeSearchExactlyBetween" class="selectform_long">
				<option value="Exactly" selected="selected">Exactly</option>
				<option value="Between">Between</option>
			</select>
			<input id="volumeSearch" name="volumeSearch" class="input_5c" type="text" value="" maxlength="5"/>
			<a href="#" id="addVolumeSearch">Add</a>
		</form>
		
		<div>
			<form id="dateRangeSearchForm" method="post" class="edit">
				<label for="dateRangeSearch" id="dateRangeSearchLabel">Date Range:</label>
				<select id="dateRangeSearch" name="dateRangeSearch" class="selectform">
					<option value="Any" selected="selected">Any</option>
					<option value="After">After</option>
					<option value="Before">Before</option>
					<option value="Between">Between</option>
					<option value="Exactly">Exactly</option>
				</select>
				<input id="dateRangeYearSearch" name="dateRangeYearSearch" class="input_4c" type="text" value="year" maxlength="4"/>
				<select id="dateRangeMonthSearch" name="dateRangeYearSearch" class="selectform">
					<option value="January">January</option>
					<option value="February">February</option>
					<option value="March">March</option>
					<option value="April">April</option>
					<option value="May">May</option>
					<option value="June">June</option>
					<option value="July">July</option>
					<option value="August">August</option>
					<option value="September">September</option>
					<option value="October">October</option>
					<option value="November">November</option>
					<option value="December">December</option>
					<option value="month" selected="selected">month</option>
				</select>
				<input id="dateRangeDaySearch" name="dateRangeDaySearch" class="input_2c" type="text" value="day" maxlength="2"/>
				<a href="#" id="addDateSearch">Add</a>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#multiOpenAccordion").multiAccordion({active: [0]});
	});
</script>
