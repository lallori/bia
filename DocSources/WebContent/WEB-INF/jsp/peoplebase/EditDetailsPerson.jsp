<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsPersonURL" value="/de/peoplebase/EditDetailsPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
		<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
			<c:param name="personId"   value="${command.personId}" />
		</c:url>
	</security:authorize>
	<br>
	<div>
		<form:form id="EditDetailsPersonForm" cssClass="edit" method="post">
			<fieldset>
			<legend><b>PERSON DETAILS</b></legend>
				<div>
					<form:label for="first" path="first" cssErrorClass="error"><b>First Name:</b></form:label>
					<form:input path="first" cssClass="input_5c" />
					<form:label for="sucNum" path="sucNum" cssErrorClass="error">Succes. Number:</form:label>
					<form:input path="sucNum" cssClass="input_5c" maxlength="5"/>
				</div>
				
				<div>
					<form:label for="midPrefix" path="midPrefix" cssErrorClass="error">Prefix Pre-Id:</form:label>
					<form:input path="midPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="middle" path="middle" cssErrorClass="error">Pre-Id:</form:label>
					<form:input path="middle" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="lastPrefix" path="lastPrefix" cssErrorClass="error">Prefix Lastname:</form:label>
					<form:input path="lastPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="last" path="last" cssErrorClass="error">Lastname:</form:label>
					<form:input path="last" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="postLastPrefix" path="postLastPrefix" cssErrorClass="error">Prefix Post-Id:</form:label>
					<form:input path="postLastPrefix" cssClass="input_5c" maxlength="5"/>
					<form:label for="postLast" path="postLast" cssErrorClass="error">Post-Id:</form:label>
					<form:input path="postLast" cssClass="input_20c" maxlength="5"/>
				</div>
				
				<div>
					<form:label for="gender" path="gender" cssErrorClass="error">Gender:</form:label>
					 <form:select path="gender" cssClass="selectform_short" items="${genders}"/>
				</div>
				
				<div>
					<b>Birth:</b>
					<form:label for="bornYear" path="bornYear" cssErrorClass="error">Year</form:label>
					<form:input path="bornYear" cssClass="input_4c" maxlength="4"/>
					<form:label for="bornMonth" path="bornMonth" cssErrorClass="error">Month</form:label>
					<form:select id="bornMonth" path="bornMonth" cssClass="selectform"  items="${months}" itemValue="monthNum" itemLabel="monthName"/>
					<form:label for="bornDay" path="bornDay" cssErrorClass="error">Day</form:label>
					<form:input path="bornDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				<div>
					<form:label for="bornApprox" path="bornApprox" cssErrorClass="error">Approx</form:label>
					<form:checkbox path="bornApprox" cssClass="checkboxPers1"/>
					<form:label for="bornDateBc" path="bornDateBc" cssErrorClass="error">BC?</form:label>
					<form:checkbox path="bornDateBc" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<form:label for="bornPlaceDescription" path="bornPlaceDescription" cssErrorClass="error">Place</form:label>
					<form:input id="bornPlaceDescriptionAutoCompleter" path="bornPlaceDescription" cssClass="input_10c"/>
				</div>
				
				<div>
					<form:label for="activeStart" path="activeStart" cssErrorClass="error">Active Start:</form:label>
					<form:input path="activeStart" cssClass="input_10c"/>
		
					<form:label for="bornPlaceUnsure" path="bornPlaceUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox path="bornPlaceUnsure" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<b>Death:</b>
					<form:label for="deathYear" path="deathYear" cssErrorClass="error">Year</form:label>
					<form:input path="deathYear" cssClass="input_4c" maxlength="4"/>
					<form:label for="deathMonth" path="deathMonth" cssErrorClass="error">Month</form:label>
					<form:select id="deathMonth" path="deathMonth" cssClass="selectform"  items="${months}" itemValue="monthNum" itemLabel="monthName"/>
					<form:label for="deathDay" path="deathDay" cssErrorClass="error">Day</form:label>
					<form:input path="deathDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				
					<form:hidden path="personId"/>
					<form:hidden path="bornPlaceId"/>			
				<div>
					<input id="close" type="submit" value="" title="Do not save changes" class="button" />
					<input id="save" type="submit" value="" class="button"/>
				</div>
			</fieldset>	
		</form:form>
	</div>

	<c:url var="searchBornPlaceURL" value="/de/peoplebase/SearchBornPlacePerson.json"/>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
	        
			$j('#close').click(function(e) {
				$j('#EditDetailsPersonDiv').block({ message: $j('#question') }); 
	            return false;
			});
			
			var bornPlaceDescription = $j('#bornPlaceDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchBornPlaceURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $j('#bornPlaceId').val(data); }
			  });

			$j("#EditDetailsPersonForm").submit(function (){
				$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
					if ($j(html).find(".inputerrors").length > 0){
						$j("#EditDetailsPersonDiv").html(html);
					} else {
				<c:choose> 
					<c:when test="${command.personId == 0}"> 
						$j("#body_left").html(html);
					</c:when> 
					<c:otherwise> 
						$j("#EditDetailsPersonDiv").html(html);
					</c:otherwise> 
				</c:choose> 
					}
				}});
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
				$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
					$j("#body_left").html(html);
				}});
	
				return false; 
			}); 
	     
		});
	</script>