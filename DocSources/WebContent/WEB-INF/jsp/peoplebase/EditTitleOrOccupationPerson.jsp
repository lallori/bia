<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitlesOrOccupationsPersonURL" value="/de/peoplebase/EditTitlesOrOccupationsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>
	<br>
	<form:form id="EditTitleOrOccupationPersonForm" method="post" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${empty command.prfLinkId}"> 
				<b>ADD NEW TITLE</b>
			</c:if>
			<c:if test="${command.prfLinkId > 0}">
				<b>Edit TITLE</b>
			</c:if>
			</legend>
			<div>
				<form:label id="titleOrOccupationDescriptionLabel" for="titleOrOccupationDescription" path="titleOrOccupationDescription" cssErrorClass="error">New Title &amp; Occ:</form:label>
				<form:input id="titleAutocomplete" path="titleOrOccupationDescription" cssClass="input_34c"/>
			</div>
			<div>
				<form:label id="preferredRoleLabel" for="preferredRole" path="preferredRole" cssErrorClass="error">Preferred role:</form:label>
				<form:checkbox id="preferredRole1" path="preferredRole" cssClass="checkboxPers2"/>
			</div>
			<hr>
			<div>
				<b>Start:</b>
				<br>
				<form:label id="startYearLabel" for="startYear" path="startYear" cssErrorClass="error">Year</form:label>
				<form:input path="startYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="startMonthNumLabel" for="startMonthNum" path="startMonthNum" cssErrorClass="error">Month</form:label>
				<form:select id="startMonthNum" path="startMonthNum" cssClass="selectform" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
				<form:label id="startDayLabel" for="startDay" path="startDay" cssErrorClass="error">Day</form:label>
				<form:input path="startDay" cssClass="input_2c" maxlength="2"/>
				<form:label id="startApproxLabel" for="startApprox" path="startApprox" cssErrorClass="error">Approx</form:label>
				<form:checkbox path="startApprox" />
				<form:label id="startUnsLabel" for="startUns" path="startUns" cssErrorClass="error">Uns</form:label>
				<form:checkbox path="startUns" />
			</div>
			<hr>
			<div>
				<b>End:</b>
				<br>
				<form:label id="endYearLabel" for="endYear" path="endYear" cssErrorClass="error">Year</form:label>
				<form:input path="endYear" cssClass="input_4c" maxlength="4"/>
				<form:label id="endMonthNumLabel" for="endMonthNum" path="endMonthNum" cssErrorClass="error">Month</form:label>
				<form:select id="endMonthNum" path="endMonthNum" cssClass="selectform" items="${months}" itemValue="monthNum" itemLabel="monthName"/>
				<form:label id="endDayLabel" for="endDay" path="endDay" cssErrorClass="error">Day</form:label>
				<form:input path="endDay" cssClass="input_2c" maxlength="2"/>
				<form:label id="endApproxLabel" for="endApprox" path="endApprox" cssErrorClass="error">Approx</form:label>
				<form:checkbox path="endApprox" />
				<form:label id="endUnsLabel" for="endtUns" path="endUns" cssErrorClass="error">Uns</form:label>
				<form:checkbox path="endUns" />
			</div>
			
			<form:hidden path="titleOccId" />
			<form:hidden path="personId" />
			<form:hidden path="prfLinkId" />
			
			<div>
				<input id="closeTitle" type="submit" value="" title="do not save changes" class="button" />
				<input type="submit" value="" id="save">
			</div>
			
		</fieldset>	
	</form:form>
	
	<c:url var="SearchTitleOrOccupationURL" value="/de/peoplebase/SearchTitleOrOccupation.json">
		<c:param name="personId" value="${command.personId}"></c:param>
	</c:url>

	<script type="text/javascript">
		$j(document).ready(function() {
			var titleOrOccupationDescription = $j('#titleAutocomplete').AutocompleteTitle({ 
			    serviceUrl:'${SearchTitleOrOccupationURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#titleOccId').val(data); }
			});

			$j('#closeTitle').click(function() {
				$j("#EditTitleOrOccupationPersonDiv").block({ message: $j("#question") });
				return false;
			});

			$j("#EditTitleOrOccupationPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditTitlesOrOccupationsPersonDiv").load('${EditTitlesOrOccupationsPersonURL}');
				}})
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
			$j("#question").hide();
			$j("#EditTitleOrOccupationPersonDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditTitlesOrOccupationsPersonURL}', cache: false, success:function(html) { 
				$j("#EditTitlesOrOccupationsPersonDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>
