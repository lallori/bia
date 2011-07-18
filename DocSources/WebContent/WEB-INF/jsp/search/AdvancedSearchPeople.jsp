<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="customSearchFilterDiv">
	<div class="customSearchFilterTitle"></div>

<div id="multiOpenAccordion">
	<h1><a>Name Parts</a></h1>
	<div>
		<form id="namePartsSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Names are broken down into their component parts in order to help identify individuals who may have been referred to by a variety of names. Each person therefore has various distinct name records in order to track 'Given Names', 'Appellatives', 'Family Names', 'Married Names' and 'Patronymics'. You should enter name parts without modifiers (e.g. Agnolo instead of d'Agnolo or di Agnolo). You can select a specific name type to further limit the name search. Women's 'maiden' names are categorized as 'Family' names.">?</a>
			<input id="nameParts" name="nameParts" class="input_20c" type="text" value=""/>
			in
			<select id="namePartsType" name="namePartsType" class="selectform_Mlong">
				<option value="All Name Types" selected="selected">All Name Types</option>
				<option value="Apellative">Apellative</option>
				<option value="Family">Family</option>
				<option value="Given">Given</option>
				<option value="Maiden">Maiden</option>
				<option value="Married">Married</option>
				<option value="Patronymic">Patronymic</option> 
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Name Parts">
		</form>	
	</div>
	
	<h1><a>Word Search</a></h1>
	<div>
		<form id="wordSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
			<input id="word" name="word" class="input_20c" type="text" value=""/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Word Search">
		</form>
	</div>
	
	<h1><a>Date Range</a></h1>
	<div>
		<form id="dateSearchForm" method="post" class="edit">
			<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
			<select id="dateType" name="dateType" class="selectform_Llong">
				<option value="Any" selected="selected">Any</option>
				<option value="Born after">Born after</option>
				<option value="Died by">Died by</option>
				<option value="Lived between">Lived between</option>
				<option value="Born/Died on">Born/Died on</option>
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
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter" class="addDateRange">
			<input type="hidden" id="category" value="Date">
			<p class="invisible">and</p>
			<input id="dateYearBetween" name="dateYear" class="input_4c" type="text" value="yyyy" maxlength="4" />
            <select id="dateMonthBetween" name="dateMonth" class="selectform">
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
                <option value="mm" selected="selected">mm</option>
            </select>
            <input id="dateDayBetween" name="dateDay" class="input_2c" type="text" value="dd" maxlength="2"/>
		</form>
	</div>

	<h1><a>Gender</a></h1>
	<div>
		<form id="genderSearchForm" method="post" class="edit">
			<a class="helpIcon" title="The subjects of some biographical entries are categorized as 'Groups', with the 'Gender' specified as 'X'. This includes entities such as academies and confraternities (even if their membership was made up of a single gender), and families when they are mentioned collectively in a document.">?</a>
			<select id="genderType" name="genderType" class="selectform_Xlong">
				<option value="Select a Role Category" selected="selected">Select a Role Category</option>
				<option value="ARTISTS and ARTISANS">ARTISTS and ARTISANS</option>
				<option value="Actors/Dancers">Actors/Dancers</option>
				<option value="Architects/Engineers">Architects/Engineers</option>
				<option value="Armorers/Weapon Makers">Armorers/Weapon Makers</option>
				<option value="Cloth Weavers/Embroiderers">Cloth Weavers/Embroiderers</option>
				<option value="Clothing Makers">Clothing Makers</option>
				<option value="Gold/Silver Workers">Gold/Silver Workers</option>
				<option value="Jewelers/Hard Stone Workers">Jewelers/Hard Stone Workers</option>
				<option value="Musicians/Singers/Instrument Makers">Musicians/Singers/Instrument Makers</option>
				<option value="Painters">Painters</option>
				<option value="Printmakers">Printmakers</option>
				<option value="Sculptors">Sculptors</option>
				<option value="Tapestry Weavers">Tapestry Weavers</option>
				<option value="Woodworkers">Woodworkers</option>
				<option value="Artists and Artisans Other">Artists and Artisans Other</option>
				<option value="CORPORATE BODIES">CORPORATE BODIES</option>
				<option value="Religious">Religious</option>
				<option value="Secular">Secular</option>
				<option value="Corporate Bodies Other">Corporate Bodies Other</option>
				<option value="M.3">ECCLESIASTICS</option>
				<option value="49">Beatified/Saints</option>
				<option value="16">Bishops/Archbishops</option>
				<option value="15">Cardinals</option>
				<option value="61">Members of Religious Orders</option>
				<option value="14">Popes</option>
				<option value="47">Ecclesiastics Other</option>
				<option value="M.4">HEADS of STATE</option>
				<option value="2">Emperors-Empresses/Kings-Queens</option>
				<option value="6">Sovereign Dukes-Duchesses/Grand Dukes-Duchesses</option>
				<option value="35">Viceroys-Vicereines/Governors</option>
				<option value="46">Heads of State Other</option>
				<option value="M.10">MILITARY and NAVAL PERSONNEL</option>
				<option value="64">Captains</option>
				<option value="63">Colonels</option>
				<option value="62">Generals/Admirals</option>
				<option value="65">Lieutenants/Ensigns</option>
				<option value="66">Military and Naval Personnel Other</option>
				<option value="M.5">NOBLES</option>
				<option value="39">Barons-Baronesses</option>
				<option value="13">Counts-Countesses</option>
				<option value="11">Dukes-Duchesses/Archdukes-Archduchesses</option>
				<option value="36">Marquises-Marchionesses</option>
				<option value="50">Members Chivalric Orders</option>
				<option value="10">Princes-Princesses</option>
				<option value="42">Nobles OTHER</option>
				<option value="M.6">PROFESSIONS</option>
				<option value="32">Bankers/Merchants</option>
				<option value="51">Lawyers/Notaries</option>
				<option value="30">Medical Practitioners</option>
				<option value="72">Professions OTHER</option>
				<option value="M.7">SCHOLARLY and LITERARY</option>
				<option value="67">Poets/Writers</option>
				<option value="53">Printers/Booksellers</option>
				<option value="31">Scholarly/Learned</option>
				<option value="73">Scholarly and Literary Other</option>
				<option value="M.8">STATE and COURT PERSONNEL</option>
				<option value="54">Civic/Local/Regional Administrators</option>
				<option value="55">Courtiers</option>
				<option value="19">Diplomats</option>
				<option value="68">Judges/Magistrates</option>
				<option value="18">Secretaries/Ministers</option>
				<option value="56">State and Court Personnel Other</option>
				<option value="M.9">UNASSIGNED</option>
				<option value="1">Unassigned</option>
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Gender">
		</form>
	</div>
	
	<h1><a>Place</a></h1>
	<div>
		<form id="placeSearchForm" method="post" class="edit">
			<a class="helpIcon" title="That text will explain...">?</a>
			<input id="place" name="place" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
			<input type="hidden" id="category" value="Place">
		</form>
	</div>
</div>
</div>

<script type="text/javascript">
	$j(document).ready(function() {

		$j('#namePartsSearchForm').advancedSearchForm();
		$j('#wordSearchForm').advancedSearchForm();
		$j('#dateSearchForm').advancedSearchForm();
		$j('#genderSearchForm').advancedSearchForm();
		$j('#placeSearchForm').advancedSearchForm();		

		
		$j('#multiOpenAccordion').multiAccordion({active: [0]});

		$j("#dateType").change(function(){
			if(this.options[3].selected) { 
				$j('#dateYearBetween').css('visibility','visible');
				$j('#dateMonthBetween').css('visibility','visible');
				$j('#dateDayBetween').css('visibility','visible');
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
		 $j('#dateYear').focus(function(){
				  if(this.value=='yyyy')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateYearBetween').focus(function(){
				  if(this.value=='yyyy')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateDay').focus(function(){
				  if(this.value=='dd')
					  {
					  this.value=''
					  }
				 });
		 $j('#dateDayBetween').focus(function(){
				  if(this.value=='dd')
					  {
					  this.value=''
					  }
				 });
		
	});
</script>