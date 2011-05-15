<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditSpousePersonURL" value="/de/peoplebase/EditSpousePerson.do"/>

		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditSpousePersonForm" action="${EditSpousePersonURL}" method="post" cssClass="edit">
		<fieldset>
			<c:if test="${empty command.marriageId}"> 
				<b>ADD NEW SPOUSE</b>
			</c:if>
			<c:if test="${command.marriageId > 0}">
				<b>Edit SPOUSE</b>
			</c:if>
			<%-- We manage fields for husband --%>
			<c:if test="${command.personId == command.wifeId}">
				<div>
					<form:label id="husbandDescriptionLabel" for="husbandDescription" path="husbandDescription" cssErrorClass="error">Name:</form:label>
					<form:input id="spouseDescriptionAutoCompleter" path="husbandDescription" cssClass="input_25c" />
					<form:hidden path="husbandId"/>
					<form:hidden path="personId"/>
				</div>
			</c:if> 
			<%-- We manage fields for wife --%>
			<c:if test="${command.personId == command.husbandId}">
				<div>
					<form:label id="wifeDescriptionLabel" for="wifeDescription" path="wifeDescription" cssErrorClass="error">Name:</form:label>
					<form:input id="spouseDescriptionAutoCompleter" path="wifeDescription" cssClass="input_25c" />
					<form:hidden path="wifeId"/>
					<form:hidden path="personId"/>
				</div>
			</c:if> 

			<div>
				<form:label id="startYearLabel" for="startYear" path="startYear">Start Year:</</form:label>
				<form:input path="startYear" cssClass="input_4c"/>
				<form:label id="endYearLabel" for="endYear" path="endYear">End Year:</form:label>
				<form:input path="endYear" cssClass="input_4c"/>
				<form:label id="marriageTermLabel" for="marriageTerm" path="marriageTerm">Reason:</form:label>
				<form:select cssClass="selectform" path="marriageTerm" items="${marriageTerms}"/>
			</div>
			
			<div>
				<input id="closeSpouse" type="submit" value="" title="do not save changes" class="button" />
				<input type="submit" value="" id="save">
			</div>
		</fieldset>	
	</form:form>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			var spouseDescription = $j('#spouseDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${SearchFatherLinkableToPersonURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#parentId').val(data); 
					$j.get("${ShowFatherDetailsURL}", { personId: "" + data }, function(data) {
						$j("#bioNotes").val(data.bioNotes);
					})
			    }
			  });

			$j('#closeSpouse').click(function() {
				$j('#EditSpousePersonDiv').block({ message: $j('#question') }); 
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
			$j.ajax({ url: '${EditSpousesPersonURL}', cache: false, success:function(html) { 
				$j("#EditSpousesPersonDiv").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>