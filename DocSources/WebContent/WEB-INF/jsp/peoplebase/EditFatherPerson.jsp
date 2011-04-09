<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditNamesPersonForm" method="post" cssClass="edit">
		<fieldset>
			<legend><b>FATHER</b></legend>
				<div>
					<label id="nameFatherLabel" for="nameFather">Name:</label>
					<input type="text" value="Pippo de' Pippi" class="input_25c" name="nameFather" id="fatherAutoCompleter"><!-- Autocompleter soltanto con nomi di maschi -->
				</div>
				
				<div> 
					<b>Birth:</b>
					<label id="yearBirthLabel" for="yearBirth">Year</label>
					<input type="text" disabled="disabled" maxlength="4" value="1564" class="input_4c_disabled" name="yearBirth" id="yearBirth">
					<label id="monthBirthLabel" for="monthBirth">Month</label>
					<select disabled="disabled" class="selectform_disabled" name="monthBirth" id="monthBirth">
						<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option selected="selected" value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option>
					</select>
					<label id="dayBirthLabel" for="dayBirth">Day</label>
					<input type="text" disabled="disabled" maxlength="2" value="12" class="input_2c_disabled" name="dayBirth" id="dayBirth">
				</div>
				
				<div>
					<b>Death:</b>
					<label id="yearDeathLabel" for="yearDeath">Year</label>
					<input type="text" disabled="disabled" maxlength="4" value="1615" class="input_4c_disabled" name="yearDeath" id="yearDeath">
					<label id="monthDeathLabel" for="monthDeath">Month</label>
					<select disabled="disabled" class="selectform_disabled" name="monthDeath" id="monthDeath">
						<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option selected="selected" value="November">November</option><option value="December">December</option>
					</select>
					<label id="dayDeathLabel" for="dayDeath">Day</label>
					<input type="text" maxlength="2" value="05" class="input_2c_disabled" name="dayDeath" id="dayDeath">
				</div>
				
				<div><label id="bioNotesLabel" for="bioNotes">Bio notes:</label></div>
				<div><textarea disabled="disabled" class="txtarea_disabled" name="bioNotes" id="bioNotes"></textarea></div>
				
				
				<div>
					<input type="submit" title="Do not save changes" value="" id="close">
					<input type="submit" value="" id="save">
				</div>
				
			</fieldset>	
		<script type="text/javascript">
			$j(document).ready(function() {
		        $j('#close').click(function() {
					$j('#EditNamesPersonDiv').block({ message: $j('#question') }); 
					return false;
				});

				$j("#AddNewValue").click(function(){
					$j("#EditNamePersonDiv").load($j(this).attr("href"));
					return false;
				});
			});
		</script>
	</form:form>

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
			$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>