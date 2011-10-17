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
	<div>
		<form:form id="EditDetailsPersonForm" cssClass="edit" method="post">
			<fieldset>
			<legend><b>PERSON DETAILS</b></legend>
				<div>
					<form:label for="first" path="first" id ="firstLabel" cssErrorClass="error"><u>First Name</u></form:label>
					<form:input path="first" id="first" cssClass="input_20c" />
					<form:label for="sucNum" path="sucNum" id="sucNumLabel" cssErrorClass="error">Succes. Number</form:label>
					<form:input path="sucNum" id="sucNum" cssClass="input_5c" maxlength="5"/>
				</div>
				
				<div>
					<form:label for="midPrefix" id="midPrefixLabel" path="midPrefix" cssErrorClass="error">Prefix Pre-Id</form:label>
					<form:input path="midPrefix" id="midPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="middle" id="middleLabel" path="middle" cssErrorClass="error">Pre-Id</form:label>
					<form:input path="middle" id="middle" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="lastPrefix" id="lastPrefixLabel" path="lastPrefix" cssErrorClass="error">Prefix Lastname</form:label>
					<form:input path="lastPrefix" id="lastPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="last" id="lastLabel" path="last" cssErrorClass="error"><u>Lastname</u></form:label>
					<form:input path="last" id="last" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="postLastPrefix" id="postLastPrefixLabel" path="postLastPrefix" cssErrorClass="error">Prefix Post-Id</form:label>
					<form:input path="postLastPrefix" id="postLastPrefix" cssClass="input_5c" maxlength="5"/>
					<form:label for="postLast" id="postLastLabel" path="postLast" cssErrorClass="error">Post-Id</form:label>
					<form:input path="postLast" id="postLast" cssClass="input_20c" maxlength="5"/>
				</div>
				
				<div>
					<form:label for="gender" id="genderLabel" path="gender" cssErrorClass="error">Gender</form:label>
					 <form:select path="gender" id="gender" cssClass="selectform_short" items="${genders}"/>
				</div>
				
				<hr />
				
				<div>
					<b>Birth:</b>
					<form:label for="bornYear" id="bornYearLabel" path="bornYear" cssErrorClass="error">Year</form:label>
					<form:input id="bornYear" path="bornYear" cssClass="input_4c" maxlength="4"/>
					<form:label for="bornMonth" id="bornMonthLabel" path="bornMonth" cssErrorClass="error">Month</form:label>
					<form:select id="bornMonth" path="bornMonth" cssClass="selectform_long"  items="${months}" itemValue="monthNum" itemLabel="monthName"/>
					<form:label for="bornDay" id="bornDayLabel" path="bornDay" cssErrorClass="error">Day</form:label>
					<form:input id="bornDay" path="bornDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				<div>
					<form:label for="bornApprox" id="bornApproxLabel" path="bornApprox" cssErrorClass="error">Approx</form:label>
					<form:checkbox path="bornApprox" id="bornApprox1" cssClass="checkboxPers1"/>
					<form:label for="bornDateBc" id="bornDateBcLabel" path="bornDateBc" cssErrorClass="error">BC?</form:label>
					<form:checkbox id="bornDateBc1" path="bornDateBc" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<form:label for="bornPlaceDescription" id="bornPlaceDescriptionLabel" path="bornPlaceDescription" cssErrorClass="error">Place</form:label>
					<form:input id="bornPlaceAutoCompleter" path="bornPlaceDescription" cssClass="input_25c"/>
				</div>
				
				<div>
					<form:label for="activeStart" id="activeStartLabel" path="activeStart" cssErrorClass="error">Active Start</form:label>
					<form:input id="activeStart" path="activeStart" cssClass="input_10c"/>
		
					<form:label for="bornPlaceUnsure" id="bornPlaceUnsureLabel" path="bornPlaceUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox id="bornPlaceUnsure1" path="bornPlaceUnsure" cssClass="checkboxPers2"/>
				</div>
				
				<hr />
				
				<div>
					<b>Death:</b>
					<form:label for="deathYear" id="deathYearLabel" path="deathYear" cssErrorClass="error">Year</form:label>
					<form:input id="deathYear" path="deathYear" cssClass="input_4c" maxlength="4"/>
					<form:label id="deathMonthLabel" for="deathMonth" path="deathMonth" cssErrorClass="error">Month</form:label>
					<form:select id="deathMonth" path="deathMonth" cssClass="selectform_long"  items="${months}" itemValue="monthNum" itemLabel="monthName"/>
					<form:label for="deathDay" id="deathDayLabel" path="deathDay" cssErrorClass="error">Day</form:label>
					<form:input id="deathDay" path="deathDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				<div>
					<form:label for="deathApprox" id="deathApproxLabel" path="deathApprox" cssErrorClass="error">Approx</form:label>
					<form:checkbox path="deathApprox" id="deathApprox" cssClass="checkboxPers1"/>
					<form:label for="deathDateBc" id="deathDateBcLabel" path="deathDateBc" cssErrorClass="error">BC?</form:label>
					<form:checkbox id="deathDateBc" path="deathDateBc" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<form:label for="deathPlaceDescription" id="deathPlaceDescriptionLabel" path="deathPlaceDescription" cssErrorClass="error">Place</form:label>
					<form:input id="deathPlaceAutoCompleter" path="deathPlaceDescription" cssClass="input_25c"/>
				</div>
				
				<div>
					<form:label for="activeEnd" id="activeEndLabel" path="activeEnd" cssErrorClass="error">Active End</form:label>
					<form:input id="activeEnd" path="activeEnd" cssClass="input_10c"/>
					
					<form:label for="deathPlaceUnsure" id="deathPlaceUnsureLabel" path="deathPlaceUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox id="deathPlaceUnsure" path="deathPlaceUnsure" cssClass="checkboxPers2"/>
				</div>
				
				
					<form:hidden path="personId"/>
					<form:hidden path="bornPlaceId"/>
					<form:hidden path="deathPlaceId"/>			
				<div>
					<input id="close" type="submit" value="Close" title="Do not save changes" class="button" />
					<input id="save" type="submit" value="Save" class="button"/>
				</div>
			</fieldset>	
		</form:form>
	</div>

	<c:url var="SearchBornPlaceURL" value="/de/geobase/SearchBornPlace.json"/>
	<c:url var="SearchDeathPlaceURL" value="/de/geobase/SearchDeathPlace.json"/>

	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden');
	        
	        if($j("#activeStart").val() != ''){
	        	$j("#bornYear, #bornYearLabel, #bornMonth, #bornMonthLabel, #bornDay, #bornDayLabel").css('visibility', 'hidden');
	        }else{
	        	$j("#activeStart, #activeStartLabel").css('visibility', 'hidden');
	        }
	        
	        if($j("#activeEnd").val() != ''){
	        	$j("#deathYear, #deathYearLabel, #deathMonth, #deathMonthLabel, #deathDay, #deathDayLabel").css('visibility', 'hidden');
	        }else{
	        	$j("#activeEnd, #activeEndLabel").css('visibility', 'hidden');
	        }
	        
	        $j("#bornYear").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#activeStart").val('');
	        		$j("#activeStart, #activeStartLabel").css('visibility', 'hidden');
	        	}else{
	        		$j("#activeStart, #activeStartLabel").css('visibility', 'visible');
	        	}
	        });
	        
	        $j("#activeStart").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#bornYear, #bornMonth, #bornDay").val('');
	        		$j("#bornYear, #bornYearLabel, #bornMonth, #bornMonthLabel, #bornDay, #bornDayLabel").css('visibility', 'hidden');
	        	}else{
	        		$j("#bornYear, #bornYearLabel, #bornMonth, #bornMonthLabel, #bornDay, #bornDayLabel").css('visibility', 'visible');
	        	}
	        });
	        
	        $j("#deathYear").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#activeEnd").val('');
	        		$j("#activeEnd, #activeEndLabel").css('visibility', 'hidden');
	        	}else{
	        		$j("#activeEnd, #activeEndLabel").css('visibility', 'visible');
	        	}
	        });
	        
	        $j("#activeEnd").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#deathYear, #deathMonth, #deathDay").val('');
	        		$j("#deathYear, #deathYearLabel, #deathMonth, #deathMonthLabel, #deathDay, #deathDayLabel").css('visibility', 'hidden');
	        	}else{
	        		$j("#deathYear, #deathYearLabel, #deathMonth, #deathMonthLabel, #deathDay, #deathDayLabel").css('visibility', 'visible');
	        	}
	        });
	        
			$j('#bornPlaceAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${SearchBornPlaceURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#bornPlaceId').val(data); 
			    }
			});

			$j('#deathPlaceAutoCompleter').autocompletePlace({ 
			    serviceUrl:'${SearchDeathPlaceURL}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ 
			    	$j('#deathPlaceId').val(data); 
			    }
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

			$j('#close').click(function(e) {
				$j('#EditDetailsPersonDiv').block({ message: $j('#question') }); 
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
				$j("#EditDetailsPersonDiv").append($j("#question"));
				$j(".blockUI").remove();
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