<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditFatherPersonForm" method="post" cssClass="edit">
		<fieldset>
			<legend>
				<b>FATHER</b></legend>
				<div>
					<form:label id="fatherDescriptionLabel" for="fatherDescription" path="fatherDescription" cssErrorClass="error">Name:</form:label>
					<form:input id="fatherAutocompleter" path="fatherDescription" cssClass="input_25c" />
				</div>
				
				<div> 
					<b>Birth:</b>
					<form:label id="bornYearLabel" for="bornYear" path="bornYear">Year</form:label>
					<form:input path="bornYear" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" />
					<form:label id="bornMonthLabel" for="bornMonthNum" path="bornMonthNum">Month</form:label>
					<form:select id="bornMonthNum" disabled="disabled" path="bornMonthNum" cssClass="selectform_disabled"/>
					<form:label id="bornDayLabel" for="bornDay" path="bornDay">Day</form:label>
					<form:input path="bornDay" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" />
				</div>
				
				<div>
					<b>Death:</b>
					<form:label id="deathYearLabel" for="deathYear" path="bornYear">Year</form:label>
					<form:input path="deathYear" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" />
					<form:label id="deathMonthLabel" for="deathMonthNum" path="deathMonthNum">Month</form:label>
					<form:select id="deathMonthNum" disabled="disabled" path="deathMonthNum" cssClass="selectform_disabled"/>
					<form:label id="deathDayLabel" for="deathDay" path="deathDay">Day</form:label>
					<form:input path="deathDay" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" />
				</div>
				
				<div>
					<form:label id="bioNotesLabel" for="bioNotes" path="bioNotes">Bio notes:</form:label>
				</div>
				<div>
					<form:textarea path="bioNotes" readonly="true" cssClass="txtarea_disabled" />
				</div>
				
				<div>
					<input id="closeFather" type="submit" value="" title="do not save changes" class="button" />
					<input id="save" type="submit" value="" class="button"/>
				</div>
				
				<form:hidden path="fatherId"/>
				<form:hidden path="personId"/>
			</fieldset>	
	</form:form>

	<c:url var="SearchFatherLinkableToPersonURL" value="/de/peoplebase/SearchFatherLinkableToPerson.json">
		<c:param name="personId" value="${command.personId}" />
	</c:url>

	<c:url var="ShowFatherDetailsURL" value="/de/peoplebase/ShowFatherDetails.json" />
	
	<script type="text/javascript">
		$j(document).ready(function() {
			var fatherDescription = $j('#fatherAutocompleter').autocompletePerson({ 
			    serviceUrl:'${SearchFatherLinkableToPersonURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#fatherId').val(data); 
					$j.get("${ShowFatherDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bornYear").val(data.bornYear);
						$j("#bornMonthNum").append('<option value="' + data.bornMonth + '" selected="selected">' + data.bornMonth + '</option>');
						$j("#bornDay").val(data.bornDay);
						$j("#deathYear").val(data.deathYear);
						$j("#deathMonthNum").val(data.deathMonth);
						$j("#deathDay").val(data.deathDay);
						$j("#bioNotes").val(data.bioNotes);
					})
			    }
			  });

			$j('#closeFather').click(function() {
				$j('#EditParentPersonDiv').block({ message: $j('#question') }); 
				return false;
			});
		});
	</script>

	<div id="question" style="display:none; cursor: default"> 
		<h1>discard changes?</h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${EditParentsPersonURL}', cache: false, success:function(html) { 
					$j("#EditParentsPersonDiv").html(html);
				}});
					
				return false; 
			}); 
	     
		});
	</script>