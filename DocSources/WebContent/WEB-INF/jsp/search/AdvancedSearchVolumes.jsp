<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="multiOpenAccordion">
	<h1><a>WORD SEARCH</a></h1>
	<div>
		<form id="wordSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
			<input id="word" name="word" class="input_15c" type="text" value=""/>
			in
			<select id="wordType" name="wordType" class="selectform_Xlong">
				<option value="Volumes Titles and Notes" selected="selected">Volumes Titles and Notes</option>
				<option value="Volume Titles">Volume Titles</option>
				<option value="Notes">Notes</option>
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Word Search">
		</form>
	</div>
	
	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<form id="volumeSearchForm" method="post" class="edit">
			<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			<select id="volumeType" name="volumeType" class="selectform_long">
				<option value="Exactly" selected="selected">Exactly</option>
				<option value="Between">Between</option>
			</select>
			<input id="volumeSearch" name="volumeSearch" class="input_5c" type="text" value="" maxlength="5"/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Volume">
		</form>
		
		<form id="dateRangeSearchForm" method="post" class="edit">
			<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
			<select id="dateType" name="dateType" class="selectform">
				<option value="Any" selected="selected">Any</option>
				<option value="After">After</option>
				<option value="Before">Before</option>
				<option value="Between">Between</option>
				<option value="Exactly">Exactly</option>
			</select>
			<input id="dateYear" name="dateYear" class="input_4c" type="text" value="year" maxlength="4"/>
			<select id="dateMonth" name="dateMonth" class="selectform">
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
			<input id="dateDay" name="dateDay" class="input_2c" type="text" value="day" maxlength="2"/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Date">
			
		</form>
		
	</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#wordSearchForm").advancedSearchForm();
		$j("#volumeSearchForm").advancedSearchForm();
		$j("#dateRangeSearchForm").advancedSearchForm();
		
		$j("#multiOpenAccordion").multiAccordion({active: [0]});
	});
</script>
