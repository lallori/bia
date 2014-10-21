<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditSpousePersonURL" value="/de/peoplebase/EditSpousePerson.do"/>

		<c:url var="EditSpousesPersonURL" value="/de/peoplebase/EditSpousesPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>

	<br />
	<form:form id="EditSpousePersonForm" action="${EditSpousePersonURL}" method="post" cssClass="edit">
		<fieldset>
			<c:if test="${command.marriageId == 0}"> 
				<legend><b><fmt:message key="peoplebase.editSpousePerson.addNew”/></b></legend>
			</c:if>
			<c:if test="${command.marriageId > 0}">
				<legend><b><fmt:message key="peoplebase.editSpousePerson.edit”/></b></legend>
			</c:if>
			<%-- We manage fields for husband --%>
			<c:if test="${command.personId == command.wifeId}">
				<div class="listForm">
					<div class="row">
						<div class="col_l"><a class="helpIcon" title="<fmt:message key="peoplebase.editSpousePerson.help.name"></fmt:message>">?</a><form:label id="husbandDescriptionLabel" for="husbandDescription" path="husbandDescription" cssErrorClass="error"><fmt:message key="peoplebase.editSpousePerson.name”/></form:label></div>
						<div class="col_l">
							<form:input id="spouseDescriptionAutoCompleter" path="husbandDescription" cssClass="input_25c" />
							<form:hidden path="husbandId"/>
							<form:hidden path="wifeId"/>
						</div>
					</div>
				</div>
			</c:if> 
			<%-- We manage fields for wife --%>
			<c:if test="${command.personId == command.husbandId}">
				<div class="listForm">
					<div class="row">
						<div class="col_l"><form:label id="wifeDescriptionLabel" for="wifeDescription" path="wifeDescription" cssErrorClass="error"><fmt:message key="peoplebase.editSpousePerson.name”/></form:label></div>
						<div class="col_l">
							<form:input id="spouseDescriptionAutoCompleter" path="wifeDescription" cssClass="input_25c" />
							<form:hidden path="wifeId"/>
							<form:hidden path="husbandId"/>
						</div>
					</div>
				</div>
			</c:if> 
			
			<div class="listForm">
				<div class="row">
					<div class="col_l"><form:label id="startYearLabel" for="startYear" path="startYear"><fmt:message key="peoplebase.editSpousePerson.startYear”/></form:label></div>
					<div class="col_l"><form:input path="startYear" cssClass="input_4c"/></div>
					<div class="col_r"><form:label id="endYearLabel" for="endYear" path="endYear"><fmt:message key="peoplebase.editSpousePerson.endYear”/></form:label></div>
					<div class="col_l"><form:input path="endYear" cssClass="input_4c"/></div>
					<div class="col_r"><form:label id="marriageTermLabel" for="marriageTerm" path="marriageTerm"><fmt:message key="peoplebase.editSpousePerson.reason”/></form:label></div>
					<div class="col_l"><form:select cssClass="selectform_long" path="marriageTerm" items="${marriageTerms}"/></div>
				</div>
			</div>
			
			<div>
				<input id="closeSpouse" class="button_small fl" type="submit" value="Close" title="do not save changes" />
				<input type="submit" value="Save" id="save" class="button_small fr">
			</div>
			<input type="hidden" value="" id="modify" />
		</fieldset>	
		
		<form:hidden path="marriageId" />
		<form:hidden path="personId" />
		<form:hidden path="gender"/>
	</form:form>
	
	<c:url var="SearchSpouseLinkableToPersonURL" value="/de/peoplebase/SearchSpouseLinkableToPerson.json">
		<c:param name="personId" value="${command.personId}" />
	</c:url>
	
	<c:url var="ShowSpouseDetailsURL" value="/de/peoplebase/ShowSpouseDetails.json" />
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditSpousePersonForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
			
			var spouseDescription = $j('#spouseDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${SearchSpouseLinkableToPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){
			    	$j.get("${ShowSpouseDetailsURL}", { personId: "" + data }, function(data) {
			    		if(data.gender == 'F'){
			    			if($j("#gender").val() == 'F'){
			    				$j('#EditSpousesPersonDiv').block({ message: $j('.differentGender'),
			    					css: { 
										border: 'none', 
										padding: '5px',
										boxShadow: '1px 1px 10px #666',
										'-webkit-box-shadow': '1px 1px 10px #666'
										} ,
										overlayCSS: { backgroundColor: '#999' }	
			    				});
			    				$j('#spouseDescriptionAutoCompleter').val('');
								return false;
			    			}
				    		$j('#wifeId').val(data.personId);
				    	}
						if(data.gender == 'M'){
							if($j("#gender").val() == 'M'){
								$j('#EditSpousesPersonDiv').block({ message: $j('.differentGender'),
									css: { 
										border: 'none', 
										padding: '5px',
										boxShadow: '1px 1px 10px #666',
										'-webkit-box-shadow': '1px 1px 10px #666'
										} ,
										overlayCSS: { backgroundColor: '#999' }	
								});
								$j('#spouseDescriptionAutoCompleter').val('');
								return false;
							}
				    		$j('#husbandId').val(data.personId);
				    	}
						
					})
			    }
			  });

			$j('#closeSpouse').click(function() {
				spouseDescription.killSuggestions();
				if($j("#modify").val() == 1){
					$j('#EditSpousePersonDiv').block({ message: $j('#question'),
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
					$j.ajax({ url: '${EditSpousesPersonURL}', cache: false, success:function(html) { 
						$j("#EditSpousesPersonDiv").html(html);
					}});
						
					return false;
				}
			});
			
			$j("#EditSpousePersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#EditSpousesPersonDiv").load('${EditSpousesPersonURL}');
				}})
				return false;
			});

		});
	</script>
	
	<div id="questionGender" class="differentGender" style="display:none; cursor: default">
		<h1><fmt:message key="peoplebase.editSpousePerson.wrongGender”/></h1>
		<input type="button" id="ok" value="Ok" />
	</div>

<div id="question" style="display:none; cursor: default"> 
	<h1><fmt:message key="peoplebase.editSpousePerson.discardChangesQuestion”/></h1> 
	<input type="button" id="yes" value="Yes" /> 
	<input type="button" id="no" value="No" /> 
</div>

<script type="text/javascript">
	$j(document).ready(function() {
		
		$j('.helpIcon').tooltip({ 
			track: true, 
			fade: 350 
		});
		
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#EditSpousePersonDiv").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j.ajax({ url: '${EditSpousesPersonURL}', cache: false, success:function(html) { 
				$j("#EditSpousesPersonDiv").html(html);
			}});
				
			return false; 
		}); 
		
		$j("#ok").click(function(){
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j(".differentGender").hide();
			$j("#EditSpousesPersonDiv").append($j(".differentGender"));
			$j(".blockUI").remove();
			return false;
		});
     
	});
</script>