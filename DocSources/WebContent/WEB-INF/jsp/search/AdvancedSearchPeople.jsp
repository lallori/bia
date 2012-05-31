<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="customSearchFilterDiv">
	<h1 class="advSearchTitle">Create your custom search filter</h1>
<div id="accordion">
	<h1 id="nameParts"><a>Name Parts</a></h1>
	<div class="people">
		<form id="namePartsSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Names are broken down into their component parts in order to help identify individuals who may have been referred to by a variety of names. Each person therefore has various distinct name records in order to track 'Given Names', 'Appellatives', 'Family Names', 'Married Names' and 'Patronymics'. You should enter name parts without modifiers (e.g. Agnolo instead of d'Agnolo or di Agnolo). You can select a specific name type to further limit the name search. Women's 'maiden' names are categorized as 'Family' names.">?</a>
			<input id="nameParts" name="nameParts" class="input_20c" type="text" value=""/>
			in
			<select id="namePartsType" name="namePartsType" class="selectform_SXlong">
				<option value="All Name Types" selected="selected">All Name Types</option>
				<option value="Appellative">Appellative</option>
				<option value="Family">Family</option>
				<option value="Given">Given</option>
				<option value="Maiden">Maiden</option>
				<option value="Married">Married</option>
				<option value="Patronymic">Patronymic</option> 
			</select>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
			<input type="hidden" id="category" value="Name Parts">
		</form>	
	</div>
	
	<h1 id="titleOccupation"><a>Title/Occupation</a></h1>
	<div class="people">
		<form id="roleCategorySearchForm" method="post" class="edit">
			<a class="helpIcon" title="The subjects of some biographical entries are categorized as 'Groups', with the 'Gender' specified as 'X'. This includes entities such as academies and confraternities (even if their membership was made up of a single gender), and families when they are mentioned collectively in a document.">?</a>
		
			<select id="roleCategorySelect" name="roleCategorySelect" class="selectform_XXXlong">
                    <option value="Select a Role Category" selected="selected">Select a Role Category</option>
                        <optgroup label="ARTISTS and ARTISANS">
                        	<option value="ARTISTS and ARTISANS">All Artists and Artisans</option>
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
                        </optgroup>
                        <optgroup label="CORPORATE BODIES">
                        	<option value="CORPORATE BODIES">All Corporate Bodies</option>
                        	<option value="Religious">Religious</option>
                        	<option value="Secular">Secular</option>
                        	<option value="Corporate Bodies Other">Corporate Bodies Other</option>
                        </optgroup>
                        <optgroup label="ECCLESIASTICS">
                        	<option value="ECCLESIASTICS">All Ecclesiastics</option>
                        	<option value="Beatified/Saints">Beatified/Saints</option>
                        	<option value="Bishops/Archbishops">Bishops/Archbishops</option>
                        	<option value="Cardinals">Cardinals</option>
                        	<option value="Members of Religious Orders">Members of Religious Orders</option>
                        	<option value="Popes">Popes</option>
                        	<option value="Ecclesiastics Other">Ecclesiastics Other</option>
                        </optgroup>
                        <optgroup label="HEADS of STATE">
                        	<option value="HEADS of STATE">All Heads of State</option>
                        	<option value="Emperors-Empresses/Kings-Queens">Emperors-Empresses/Kings-Queens</option>
                        	<option value="Sovereign Dukes-Duchesses/Grand Dukes-Duchesses">Sovereign Dukes-Duchesses/Grand Dukes-Duchesses</option>
                        	<option value="Viceroys-Vicereines/Governors">Viceroys-Vicereines/Governors</option>
                        	<option value="Heads of State Other">Heads of State Other</option>
                        </optgroup>
                        <optgroup label="MILITARY and NAVAL PERSONNEL">
                        	<option value="MILITARY and NAVAL PERSONNEL">All Military and Naval Personnel</option>
                        	<option value="Captains">Captains</option>
                        	<option value="Colonels">Colonels</option>
                        	<option value="Generals/Admirals">Generals/Admirals</option>
                        	<option value="Lieutenants/Ensigns">Lieutenants/Ensigns</option>
                        	<option value="Military and Naval Personnel Other">Military and Naval Personnel Other</option>
                        </optgroup>
                        <optgroup label="NOBLES">
                        	<option value="NOBLES">All Nobles</option>
                        	<option value="Barons-Baronesses">Barons-Baronesses</option>
                        	<option value="Counts-Countesses">Counts-Countesses</option>
                        	<option value="Dukes-Duchesses/Archdukes-Archduchesses">Dukes-Duchesses/Archdukes-Archduchesses</option>
                        	<option value="Marquises-Marchionesses">Marquises-Marchionesses</option>
                        	<option value="Members Chivalric Orders">Members Chivalric Orders</option>
                        	<option value="Princes-Princesses">Princes-Princesses</option>
                        	<option value="Nobles OTHER">Nobles OTHER</option>
                        </optgroup>
                        <optgroup label="PROFESSIONS">
                        	<option value="PROFESSIONS">All Professions</option>
                        	<option value="Bankers/Merchants">Bankers/Merchants</option>
                        	<option value="Lawyers/Notaries">Lawyers/Notaries</option>
                        	<option value="Medical Practitioners">Medical Practitioners</option>
                        	<option value="Professions OTHER">Professions OTHER</option>
                        </optgroup>
                        <optgroup label="SCHOLARLY and LITERARY">
                        	<option value="SCHOLARLY and LITERARY">All Scholarly and Literary</option>
                        	<option value="Poets/Writers">Poets/Writers</option>
                        	<option value="Printers/Booksellers">Printers/Booksellers</option>
                        	<option value="Scholarly/Learned">Scholarly/Learned</option>
                        	<option value="Scholarly and Literary Other">Scholarly and Literary Other</option>
                        </optgroup>
                        <optgroup label="STATE and COURT PERSONNEL">
                        	<option value="STATE and COURT PERSONNEL">All State and Court Personnel</option>
                        	<option value="Civic/Local/Regional Administrators">Civic/Local/Regional Administrators</option>
                        	<option value="Courtiers">Courtiers</option>
                        	<option value="Diplomats">Diplomats</option>
                        	<option value="Judges/Magistrates">Judges/Magistrates</option>
                        	<option value="Secretaries/Ministers">Secretaries/Ministers</option>
                        	<option value="State and Court Personnel Other">State and Court Personnel Other</option>
                        </optgroup>
                        <optgroup label="UNASSIGNED">
                        	<option value="Unassigned">Unassigned</option>
                        </optgroup>
            </select>
            <input type="hidden" id="roleCategory" name="roleCategory" type="text" value=""/>
            <input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
			<input type="hidden" id="category" value="Role Category">
		</form>
		
		<br />
		<span style="margin:0 0 0 26px;">Word search in Title/Occupation</span>
		
		<form id="occupationWordSearchForm" method="post" class="edit">
			<a class="helpIcon" title="...">?</a>
			<input id="occupationWord" name="occupationWord" class="input_20c" type="text" value=""/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
			<input type="hidden" id="category" value="OccupationWord">
		</form>
		<br />
		
		<span style="margin:0 0 0 26px;">Match the exact Title/Occupation</span>
		
		<form id="occupationSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Use this autocomplater textfield to find a particular title or occupation name. Plase note that a most of titles or occupations  are written in their own language, so try using the  appropriate language rather than English or Italian.">?</a>
			<input id="occupation" name="occupation" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="occupationAdd" disabled="disabled">
			<input type="hidden" id="category" value="Occupation">
			<input type="hidden" id="occupationId" value="">
		</form>
	</div>
	
	<h1 id="placeSearch"><a>Person Details and Vital Statistics</a></h1>
	<div class="people">
		<form id="genderSearchForm" method="post" class="edit">
			<a class="helpIcon" title="...">?</a>
			<label for="gender" id="genderLabel">Gender</label>
			<select id="genderSelect" name="genderSelect" class="selectform_short">
				<option value="M" selected="selected">M</option>
                <option value="F">F</option>
                <option value="X">X</option>
			</select>
			<input type="hidden" id="gender" name="gender" type="text" value=""/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="genderAdd">
			<input type="hidden" id="category" value="Gender">
		</form>
		
		<form id="placeSearchForm" method="post" class="edit">
			<a class="helpIcon" title="That text will explain...">?</a>
			<select id="placeType" name="placeType" class="selectform_MXlong">
				<option value="Birth/Death Place" selected="selected">Birth/Death Place</option>
				<option value="Birth Place">Birth Place</option>
				<option value="Death Place">Death Place</option>
			</select>
			<input id="place" name="place" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="placeAdd" disabled="disabled">
			<input type="hidden" id="category" value="Place">
			<input type="hidden" id="placeId" value="">
		</form>
		
		<form id="dateSearchForm" method="post" class="edit">
			<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
			<select id="dateType" name="dateType" class="selectform_Llong">
				<option value="Born after" selected="selected" >Born after</option>
				<option value="Dead by">Dead by</option>
				<option value="Lived between">Lived between</option>
				<option value="Born/Died on">Born/Died on</option>
			</select>
			<input id="dateYear" name="dateYear" class="input_4c" type="text" value="yyyy" maxlength="4"/>
			<select id="dateMonth" name="dateMonth" class="selectform">
			<c:forEach items="${months}" var="month">
				<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
			</c:forEach>
			</select>
			<input id="dateDay" name="dateDay" class="input_2c" type="text" value="dd" maxlength="2"/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter" class="addDateRange">
			<input type="hidden" id="category" value="Date">
			<p class="invisible">and</p>
			<input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" />
            <select id="dateMonthBetween" name="dateMonthBetween" class="selectform">
            <c:forEach items="${months}" var="month">
				<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
			</c:forEach>
            </select>
            <input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2"/>
		</form>
	</div>
	
	<h1 id="researchNotes"><a><i>in </i>Research Notes</a></h1>
	<div class="people">
		<form id="researchNotesSearchForm" method="post" class="edit">
			<a class="helpIcon" title="Search here for words (in English) that appear in the Research Notes field.">?</a>
			<input id="researchNotes" name="researchNotes" class="input_20c" type="text" value=""/>
			<input type="submit" id="addSearchFilter" value="Add" title="Add this to your search filter">
			<input type="hidden" id="category" value="Research Notes">
		</form>
	</div>	
</div>
</div>

<c:url var="searchPlaceURL" value="/src/SearchPlace.json"/>
<c:url var="searchTitleOrOccupationURL" value="/src/SearchTitleOrOccupation.json"/>

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#dateMonth option:eq(0)").text("mm");
		$j("#dateMonth option:eq(0)").attr('selected', 'selected');
		$j("#dateMonthBetween option:eq(0)").text("mm");
		$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

		$j('#namePartsSearchForm').advancedSearchForm();
		$j('#wordSearchForm').advancedSearchForm();
		$j('#dateSearchForm').advancedSearchForm();
		$j('#roleCategorySearchForm').advancedSearchForm();
		$j('#occupationWordSearchForm').advancedSearchForm();
		$j('#occupationSearchForm').advancedSearchForm();
		$j('#genderSearchForm').advancedSearchForm();
		$j('#placeSearchForm').advancedSearchForm();	
		$j('#researchNotesSearchForm').advancedSearchForm();
		
		$j('#accordion').accordion({
			active: false, 
			autoHeight: false,
			collapsible:true
			});
		$j("#dateType").change(function(){
			if(this.options[2].selected) { 
				$j('#dateYearBetween').css('visibility','visible');
				$j('#dateMonthBetween').css('visibility','visible');
				$j('#dateDayBetween').css('visibility','visible');
				$j('.invisible').css('visibility','visible');
				$j('.visible').css('visibility','hidden');
		   } else { 
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
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
		 
		 $j("#dateSearchForm :input").keyup(function(){
			if($j("#dateType option:selected").val() == 'Lived between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			} 
		 });
		 
		 $j("#dateType").change(function(){
			if($j("#dateType option:selected").val() == 'Lived between'){
				if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
					$j(".addDateRange").attr("disabled", "disabled");
				else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
					
			}else{
				$j(".addDateRange").attr("disabled");
				$j(".addDateRange").removeAttr("disabled");
				$j(".addDateRange").prop("disabled", false);
			}
		 });
		 
		 $j("#dateSearchForm").submit(function(){
				$j('#dateType option[value="Born after"]').attr('selected', 'selected');
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
			});
			
		 
		 $j("#roleCategorySelect").change(function(){
			 if($j(this).val() != "Select a Role Category")
			 	$j("#roleCategory").val($j(this).val());
			 else
				 $j("#roleCategory").val("");
			 return false;
		 });
		 
		 //This is for set the field when the page is loaded
		 $j("#gender").val($j("#genderSelect").find("option:selected").val());
		 
		 $j("#genderSelect").change(function(){
			 $j("#gender").val($j(this).val());
			 return false;
		 });
		 
		 var $placeAutocomplete = $j("#place").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 450,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".placeAdd").removeAttr("disabled");
					$j('#placeId').val(data);
					$j(".placeAdd").attr("disabled");
					$j(".placeAdd").prop("disabled", false);
				}
			});	
		 
		 $j("#place").blur(function(){
			$placeAutocomplete.killSuggestions();
			return false;
		 });
		 
		$j("#place").keyup(function(){
			if($j("#placeId").val() != '')
				$j(".placeAdd").attr("disabled","disabled");
		});
			
		$j("#placeSearchForm").submit(function(){
			$j("#placeId").val("");
			$j(".placeAdd").attr("disabled","disabled");
		});
		 
		var $occupationAutocomplete = $j("#occupation").AutocompleteTitle({
			 	serviceUrl:'${searchTitleOrOccupationURL}',
			    minChars:3, 
			    delimiter: null, // /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j(".occupationAdd").removeAttr("disabled");
			    	$j('#occupationId').val(data);
			    	$j(".occupationAdd").attr("disabled");
			    	$j(".occupationAdd").prop("disabled", false);
			    }
		});
		
		$j("#occupation").blur(function(){
			$occupationAutocomplete.killSuggestions();
			return false;
		});
		
		$j("#occupation").keyup(function(){
			if($j("#occupationId").val() != '')
				$j(".occupationAdd").attr("disabled","disabled");
		});
		
		$j("#occupationSearchForm").submit(function(){
			$j("#occupationId").val("");
			$j(".occupationAdd").attr("disabled","disabled");
		});
		 
		$j('#nameParts').click(function(){
			 $j.scrollTo({top:'0px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "0px"}, "high");
			 return false;
		});
		$j('#dateRange').click(function(){
			 $j.scrollTo({top:'113px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "70px"}, "high");
			 return false;
		});
		$j('#titleOccupation').click(function(){
			 $j.scrollTo({top:'140px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "100px"}, "high");
			 return false;
		});
		$j('#placeSearch').click(function(){
			 $j.scrollTo({top:'168px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "125px"}, "high");
			 return false;
		});
		$j('#researchNotes').click(function(){
			 $j.scrollTo({top:'195px',left:'0px'}, 800 );
			 $j("#yourSearchFilterDiv").animate({"top": "150px"}, "high");
			 return false;
		});
	});
</script>