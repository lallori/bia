<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditMotherPersonURL" value="/de/peoplebase/EditMotherPerson.do" />

		<c:url var="EditParentsPersonURL" value="/de/peoplebase/EditParentsPerson.do">
			<c:param name="personId"   value="${command.childId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditMotherPersonForm" action="${EditMotherPersonURL}" method="post" cssClass="edit">
	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>	
		<fieldset>
			<legend>
				<b><fmt:message key="peoplebase.editMotherPerson.motherCaps"/></b></legend>
				<div>
					<a class="helpIcon" title="<fmt:message key="peoplebase.editMotherPerson.help.name"></fmt:message>">?</a>
					<form:label id="motherDescriptionLabel" for="motherDescription" path="motherDescription" cssErrorClass="error"><fmt:message key="peoplebase.editMotherPerson.name"/></form:label>
					<form:input id="motherAutocompleter" path="motherDescription" cssClass="input_25c" />
				</div>
				
				<div> 
					<b><fmt:message key="peoplebase.editMotherPerson.birth"/></b>
					<form:label id="bornYearLabel" for="bornYear" path="bornYear"><fmt:message key="peoplebase.editMotherPerson.year"/></form:label>
					<form:input path="bornYear" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" />
					<form:label id="bornMonthLabel" for="bornMonthNum" path="bornMonthNum"><fmt:message key="peoplebase.editMotherPerson.month"/></form:label>
					<form:select id="bornMonth" disabled="disabled" path="bornMonth" cssClass="selectform_disabled"  items="${months}" itemValue="monthNum" itemLabel="monthName"/>
					<form:label id="bornDayLabel" for="bornDay" path="bornDay"><fmt:message key="peoplebase.editMotherPerson.day"/></form:label>
					<form:input path="bornDay" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" />
				</div>
				
				<div>
					<b><fmt:message key="peoplebase.editMotherPersonh.deat"/></b>
					<form:label id="deathYearLabel" for="deathYear" path="bornYear"><fmt:message key="peoplebase.editMotherPerson.year"/></form:label>
					<form:input path="deathYear" disabled="disabled" maxlength="4" cssClass="input_4c_disabled" />
					<form:label id="deathMonthLabel" for="deathMonthNum" path="deathMonthNum"><fmt:message key="peoplebase.editMotherPerson.month"/></form:label>
					<form:select id="deathMonth" disabled="disabled" path="deathMonth" cssClass="selectform_disabled"/>
					<form:label id="deathDayLabel" for="deathDay" path="deathDay"><fmt:message key="peoplebase.editMotherPerson.day"/></form:label>
					<form:input path="deathDay" disabled="disabled" maxlength="2" cssClass="input_2c_disabled" />
				</div>
				
				<div>
					<form:label id="bioNotesLabel" for="bioNotes" path="bioNotes"><fmt:message key="peoplebase.editMotherPerson.bioNotes"/></form:label>
				</div>
				<div>
					<form:textarea path="bioNotes" readonly="true" cssClass="txtarea_disabled" />
				</div>
				
				<div>
					<input id="closeMother" class="button_small fl" type="submit" value="Close" title="do not save changes" />
					<input id="save" class="button_small fr" type="submit" value="Save" />
				</div>
				
				<form:hidden path="id"/>
				<form:hidden path="parentId"/>
				<form:hidden path="childId"/>
				<form:hidden path="gender"/>
			</fieldset>	
	</form:form>

	<c:url var="SearchMotherLinkableToPersonURL" value="/de/peoplebase/SearchMotherLinkableToPerson.json">
		<c:param name="personId" value="${command.childId}" />
	</c:url>

	<c:url var="ShowMotherDetailsURL" value="/de/peoplebase/ShowMotherDetails.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#bornMonth, #bornYear, #bornDay").attr("disabled", "disabled");
			$j("#deathMonth, #deathYear, #deathDay").attr("disabled", "disabled");
			
			$j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
			
			var motherDescription = $j('#motherAutocompleter').autocompletePerson({ 
			    serviceUrl:'${SearchMotherLinkableToPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#parentId').val(data); 
					$j.get("${ShowMotherDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bornYear").val(data.bornYear);
						$j("#bornMonth").append('<option value="' + data.bornMonth + '" selected="selected">' + data.bornMonth + '</option>');
						$j("#bornDay").val(data.bornDay);
						$j("#deathYear").val(data.deathYear);
						$j("#deathMonth").append('<option value="' + data.deathMonth + '" selected="selected">' + data.deathMonth + '</option>');
						$j("#deathDay").val(data.deathDay);
						$j("#bioNotes").val(data.bioNotes);
						$j("#gender").val(data.gender);
					})
			    }
			  });

			$j('#closeMother').click(function() {
				$j('.autocomplete').remove();
				$j('#EditParentPersonDiv').block({ message: $j('#question') }); 
				return false;
			});

			$j("#EditMotherPersonForm").submit(function (){
				if($j("#gender").val() == 'M' || $j("#gender").val() == 'X'){
					$j('#EditParentPersonDiv').block({ message: $j('.differentGender') });
					return false;
				}else{
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditParentsPersonDiv").load('${EditParentsPersonURL}');
				}})
				return false;
				}
			});
		});
	</script>

	<div id="question" style="display:none; cursor: default"> 
		<h1><fmt:message key="peoplebase.editMotherPerson.discardChangesQuestion"/></h1> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<div id="questionGender" class="differentGender" style="display:none; cursor: default">
		<h1><fmt:message key="peoplebase.editMotherPerson.motherCantBe"/></h1>
		<input type="button" id="ok" value="Ok" />
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#EditParentPersonDiv").append($j("#question"));
				$j(".blockUI").remove();
				return false; 
			}); 
	        
			$j('#yes').click(function() { 
				$j.ajax({ url: '${EditParentsPersonURL}', cache: false, success:function(html) { 
					$j("#EditParentsPersonDiv").html(html);
				}});
					
				return false; 
			}); 

			$j("#ok").click(function(){
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j(".differentGender").hide();
				$j("#EditParentPersonDiv").append($j(".differentGender"));
				$j(".blockUI").remove();
				return false;
			});
	     
		});
	</script>