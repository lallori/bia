<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do" />
			
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${person.personId}" />
		</c:url>
		
	</security:authorize>

	<div id="EditParentPersonDiv">
		<form:form id="EditParentsPersonForm" action="${editParentsPersonURL}" method="post" cssClass="edit">
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
		<fieldset>
			<legend>
				<b>PARENTS</b>
			</legend>
				<div class="listForm">
					<div class="row">
						<div class="col_l"><b>Father:</b></div>
					</div>
					<div class="row">
						<div class="col_l"><form:label id="fatherDescriptionLabel" for="fatherDescription" path="fatherDescription" cssErrorClass="error">Name</form:label></div>
						<div class="col_l"><form:input id="fatherAutocompleter" path="fatherDescription" cssClass="input_30c" /></div>
					</div>
				</div>
				
				<div class="listForm">
					<div class="row">
						<div class="col_l"><u>Birth:</u></div>
						<div class="col_r"><form:label id="bornYearLabelFather" for="bornYearFather" path="bornYearFather">Year</form:label></div>
						<div class="col_l"><form:input path="bornYearFather" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" /></div>
						<div class="col_r"><form:label id="bornMonthLabelFather" for="bornMonthNumFather" path="bornMonthNumFather">Month</form:label></div>
						<div class="col_l"><form:select id="bornMonthFather" disabled="disabled" path="bornMonthFather" cssClass="selectform_disabled" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
						<div class="col_r"><form:label id="bornDayLabelFather" for="bornDayFather" path="bornDayFather">Day</form:label></div>
						<div class="col_l"><form:input path="bornDayFather" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" /></div>
					</div>
					<div class="row">
						<div class="col_l"><u>Death:</u></div>
						<div class="col_r"><form:label id="deathYearLabelFather" for="deathYearFather" path="bornYearFather">Year</form:label></div>
						<div class="col_l"><form:input path="deathYearFather" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" /></div>
						<div class="col_r"><form:label id="deathMonthLabelFather" for="deathMonthNumFather" path="deathMonthNumFather">Month</form:label></div>
						<div class="col_l"><form:select id="deathMonthFather" disabled="disabled" path="deathMonthFather" cssClass="selectform_disabled"  items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
						<div class="col_r"><form:label id="deathDayLabelFather" for="deathDayFather" path="deathDayFather">Day</form:label></div>
						<div class="col_l"><form:input path="deathDayFather" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" /></div>
					</div>
				</div>
				
				<div class="listForm">
					<div class="row"><form:label id="bioNotesLabelFather" for="bioNotesFather" path="bioNotesFather">Bio notes:</form:label></div>
					<div class="row"><form:textarea path="bioNotesFather" readonly="true" cssClass="txtarea_disabled" /></div>
				</div>
				
				<hr />
				
				<div class="listForm">
					<div class="row">
						<div class="col_l"><b>Mother:</b></div>
					</div>
					<div class="row">
						<div class="col_l"><form:label id="motherDescriptionLabel" for="motherDescription" path="motherDescription" cssErrorClass="error">Name</form:label></div>
						<div class="col_l"><form:input id="motherAutocompleter" path="motherDescription" cssClass="input_30c" /></div>
					</div>
				</div>
				
				<div class="listForm">
					<div class="row">
						<div class="col_l"><u>Birth:</u></div>
						<div class="col_r"><form:label id="bornYearLabelMother" for="bornYearMother" path="bornYearMother">Year</form:label></div>
						<div class="col_l"><form:input path="bornYearMother" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" /></div>
						<div class="col_r"><form:label id="bornMonthLabelMother" for="bornMonthNumMother" path="bornMonthNumMother">Month</form:label></div>
						<div class="col_l"><form:select id="bornMonthMother" disabled="disabled" path="bornMonthMother" cssClass="selectform_disabled"  items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
						<div class="col_r"><form:label id="bornDayLabelMother" for="bornDayMother" path="bornDayMother">Day</form:label></div>
						<div class="col_l"><form:input path="bornDayMother" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" /></div>
					</div>
					<div class="row">
						<div class="col_l"><u>Death:</u></div>
						<div class="col_r"><form:label id="deathYearLabelMother" for="deathYearMother" path="deathYearMother">Year</form:label></div>
						<div class="col_l"><form:input path="deathYearMother" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" /></div>
						<div class="col_r"><form:label id="deathMonthLabelMother" for="deathMonthNumMother" path="deathMonthNumMother">Month</form:label></div>
						<div class="col_l"><form:select id="deathMonthMother" disabled="disabled" path="deathMonthMother" cssClass="selectform_disabled" items="${months}" itemValue="monthNum" itemLabel="monthName"/></div>
						<div class="col_r"><form:label id="deathDayLabelMother" for="deathDayMother" path="deathDayMother">Day</form:label></div>
						<div class="col_l"><form:input path="deathDayMother" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" /></div>
					</div>
				</div>
				
				<div class="listForm">
					<div class="row"><form:label id="bioNotesLabelMother" for="bioNotesMother" path="bioNotesMother">Bio notes:</form:label></div>
					<div class="row"><form:textarea path="bioNotesMother" readonly="true" cssClass="txtarea_disabled" /></div>
				</div>	

				<div>
					<input id="close" class="button_small fl" type="submit" value="Close" title="do not save changes" />
					<input id="save" class="button_small fr" type="submit" value="Save" />
				</div>
				
				<input type="hidden" value="" id="modify" />
				
				<form:hidden path="fatherRecordId"/>
				<form:hidden path="fatherPersonId"/>
				<form:hidden path="motherRecordId"/>
				<form:hidden path="motherPersonId"/>
				<form:hidden path="personId"/>
				<form:hidden path="genderFather"/>
				<form:hidden path="genderMother"/>
			</fieldset>
	</form:form>
	
	</div>
	
	<c:url var="SearchFatherLinkableToPersonURL" value="/de/peoplebase/SearchFatherLinkableToPerson.json">
		<c:param name="personId" value="${command.personId}" />
	</c:url>

	<c:url var="ShowFatherDetailsURL" value="/de/peoplebase/ShowFatherDetails.json" />
	
	<c:url var="SearchMotherLinkableToPersonURL" value="/de/peoplebase/SearchMotherLinkableToPerson.json">
		<c:param name="personId" value="${command.personId}" />
	</c:url>

	<c:url var="ShowMotherDetailsURL" value="/de/peoplebase/ShowMotherDetails.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditParentsPersonForm");
			
			$j("#EditParentsPersonForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
			
			$j("#EditDetailsPerson").css('visibility', 'hidden');
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
	        
	        
	        $j("#bornMonthFather, #bornYearFather, #bornDayFather, #bornMonthMother, #bornYearMother, #bornDayMother").attr("disabled", "disabled");
			$j("#deathMonthFather, #deathYearFather, #deathDayFather, #deathMonthMother, #deathYearMother, #deathDayMother").attr("disabled", "disabled");
			
			var fatherDescription = $j('#fatherAutocompleter').autocompletePerson({ 
			    serviceUrl:'${SearchFatherLinkableToPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#fatherPersonId').val(data); 
					$j.get("${ShowFatherDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bornYearFather").val(data.bornYear);
						$j("#bornMonthFather").append('<option value="' + data.bornMonth + '" selected="selected">' + data.bornMonth + '</option>');
						$j("#bornDayFather").val(data.bornDay);
						$j("#deathYearFather").val(data.deathYear);
						$j("#deathMonthFather").append('<option value="' + data.deathMonth + '" selected="selected">' + data.deathMonth + '</option>');
						$j("#deathDayFather").val(data.deathDay);
						$j("#bioNotesFather").val(data.bioNotes);
						$j("#genderFather").val(data.gender);
					})
			    }
			  });
			
			var motherDescription = $j('#motherAutocompleter').autocompletePerson({ 
			    serviceUrl:'${SearchMotherLinkableToPersonURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#motherPersonId').val(data); 
					$j.get("${ShowMotherDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bornYearMother").val(data.bornYear);
						$j("#bornMonthMother").append('<option value="' + data.bornMonth + '" selected="selected">' + data.bornMonth + '</option>');
						$j("#bornDayMother").val(data.bornDay);
						$j("#deathYearMother").val(data.deathYear);
						$j("#deathMonthMother").append('<option value="' + data.deathMonth + '" selected="selected">' + data.deathMonth + '</option>');
						$j("#deathDayMother").val(data.deathDay);
						$j("#bioNotesMother").val(data.bioNotes);
						$j("#genderMother").val(data.gender);
					})
			    }
			  });
			
			$j("#fatherAutocompleter").change(function(){
				if($j("#fatherAutocompleter").val() == ''){
					$j("#bornYearFather").val('');
					$j("#bornMonthFather").val('');
					$j("#bornDayFather").val('');
					$j("#deathYearFather").val('');
					$j("#deathMonthFather").val('');
					$j("#deathDayFather").val('');
					$j("#bioNotesFather").val('');
					$j("#genderFather").val('');
					$j("#fatherPersonId").val('');
				}
			});
			
			$j("#motherAutocompleter").change(function(){
				if($j("#motherAutocompleter").val() == ''){
					$j("#bornYearMother").val('');
					$j("#bornMonthMother").val('');
					$j("#bornDayMother").val('');
					$j("#deathYearMother").val('');
					$j("#deathMonthMother").val('');
					$j("#deathDayMother").val('');
					$j("#bioNotesMother").val('');
					$j("#genderMother").val('');
					$j("#motherPersonId").val('');
				}
			});
			
			$j("#EditParentsPersonForm").submit(function (){
				if($j("#genderFather").val() == 'F' || $j("#genderFather").val() == 'X'){
					$j('#EditParentsPersonDiv').block({ message: $j('#questionGenderFather'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					});
					return false;
				}else if($j("#genderMother").val() == 'M' || $j("#genderMother").val() == 'X'){
					$j('#EditParentsPersonDiv').block({ message: $j('#questionGenderMother'),
						css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
					});
					return false;
				}else{
					$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
		        	$j("#loadingDiv").css('visibility', 'visible');
					$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
						$j("#body_left").load('${ShowPersonURL}');
					}})
					return false;
				}
			});
			
			$j('#close').click(function() {
				fatherDescription.killSuggestions();
				motherDescription.killSuggestions();
				if($j("#modify").val() == 1){
	        		$j("#EditParentsPersonDiv").block({ message: $j("#question"),
	        			css: { 
							border: 'none', 
							padding: '5px',
							boxShadow: '1px 1px 10px #666',
							'-webkit-box-shadow': '1px 1px 10px #666'
							} ,
							overlayCSS: { backgroundColor: '#999' }	
	        		});
	        		return false;
				}else{
					$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
						$j("#body_left").html(html);
					}});					
					return false; 
				}
			});

	        /*$j(".deleteIcon").click(function() {
				$j.get(this.href, function(data) {
					if(data.match(/KO/g)){
			            var resp = $j('<div></div>').append(data); // wrap response
					} else {
						$j("#EditParentsPersonDiv").load('${EditParentsPersonURL}');
					}
		        });
				return false;
			});*/				
		});
	</script>
	
	<div id="question" style="display:none; cursor: default"> 
		<h1>Discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<div id="questionGenderFather" class="differentGender" style="display:none; cursor: default">
		<h1>A father can't be a female or a corporate identity</h1>
		<input type="button" id="okFather" class="ok" value="Ok" />
	</div>
	
	<div id="questionGenderMother" class="differentGender" style="display:none; cursor: default">
		<h1>A mother can't be a male or a corporate identity</h1>
		<input type="button" id="okMother" class="ok" value="Ok" />
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditParentsPersonDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});					
				return false; 
			}); 
			
			$j("#okFather").click(function(){
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#questionGenderFather").hide();
				$j("#EditParentPersonDiv").append($j("#questionGenderFather"));
				$j(".blockUI").remove();
				return false;
			});
			
			$j("#okMother").click(function(){
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#questionGenderMother").hide();
				$j("#EditParentPersonDiv").append($j("#questionGenderMother"));
				$j(".blockUI").remove();
				return false;
			});
			
		});
	</script>