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
		<%-- Loading div when saving the form --%>
		<div id="loadingDiv"></div>
			<fieldset>
			<legend><b>PERSON DETAILS</b></legend>
				<div>
					<form:label for="first" path="first" id ="firstLabel" cssErrorClass="error" title="First or Given Name"><u>First Name</u></form:label>
					<form:input path="first" id="first" cssClass="input_20c" />
					<form:label for="sucNum" path="sucNum" id="sucNumLabel" cssErrorClass="error" title="Succession Number. E.g., 'I' in Cosimo I de'Medici">Succes. Number</form:label>
					<form:input path="sucNum" id="sucNum" cssClass="input_5c" maxlength="5"/>
				</div>
				
				<div>
					<form:label for="midPrefix" id="midPrefixLabel" path="midPrefix" cssErrorClass="error" title="Prefix for Middle Display Name">Prefix Pre-Id</form:label>
					<form:input path="midPrefix" id="midPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="middle" id="middleLabel" path="middle" cssErrorClass="error" title="Middle Display Name">Pre-Id</form:label>
					<form:input path="middle" id="middle" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="lastPrefix" id="lastPrefixLabel" path="lastPrefix" cssErrorClass="error" title="Prefix for Last/ID Name e.g. 'degli''">Prefix Lastname</form:label>
					<form:input path="lastPrefix" id="lastPrefix" cssClass="input_5c" maxlength="5"/>
		
					<form:label for="last" id="lastLabel" path="last" cssErrorClass="error" title="Last or Identification Display Name - Required Entry"><u>Lastname</u></form:label>
					<form:input path="last" id="last" cssClass="input_20c" />
				</div>
				
				<div>
					<form:label for="postLastPrefix" id="postLastPrefixLabel" path="postLastPrefix" cssErrorClass="error" title="Prefix for PostLast  Name">Prefix Post-Id</form:label>
					<form:input path="postLastPrefix" id="postLastPrefix" cssClass="input_5c" maxlength="5"/>
					<form:label for="postLast" id="postLastLabel" path="postLast" cssErrorClass="error" title="Post-ID Display Name">Post-Id</form:label>
					<form:input path="postLast" id="postLast" cssClass="input_20c"/>
				</div>
				
				<div>
					<form:label for="gender" id="genderLabel" path="gender" cssErrorClass="error">Gender</form:label>
					 <form:select path="gender" id="gender" cssClass="selectform_short" items="${genders}"/>
				</div>
				
				<hr />
				
				<div>
					<b>Birth:</b>
					<form:label for="bornYear" id="bornYearLabel" path="bornYear" cssErrorClass="error"></form:label>
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
					<form:label for="activeStart" id="activeStartLabel" path="activeStart" cssErrorClass="error"></form:label>
					<form:input id="activeStart" path="activeStart" cssClass="input_10c"/>
		
					<form:label for="bornPlaceUnsure" id="bornPlaceUnsureLabel" path="bornPlaceUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox id="bornPlaceUnsure1" path="bornPlaceUnsure" cssClass="checkboxPers2"/>
				</div>
				
				<hr />
				
				<div>
					<b>Death:</b>
					<form:label for="deathYear" id="deathYearLabel" path="deathYear" cssErrorClass="error"></form:label>
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
					<form:label for="activeEnd" id="activeEndLabel" path="activeEnd" cssErrorClass="error"></form:label>
					<form:input id="activeEnd" path="activeEnd" cssClass="input_10c"/>
					
					<form:label for="deathPlaceUnsure" id="deathPlaceUnsureLabel" path="deathPlaceUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox id="deathPlaceUnsure" path="deathPlaceUnsure" cssClass="checkboxPers2"/>
				</div>
				
				
				<form:hidden path="personId"/>
				<form:hidden path="bornPlaceId"/>
				<form:hidden path="bornPlacePrefered"/>
				<form:hidden path="deathPlaceId"/>
				<form:hidden path="deathPlacePrefered"/>			

				<form:errors path="bornYear" cssClass="inputerrors" htmlEscape="false"/>
				<form:errors path="bornMonth" cssClass="inputerrors" htmlEscape="false"/>
				<form:errors path="bornDay" cssClass="inputerrors" htmlEscape="false"/>
				<form:errors path="deathYear" cssClass="inputerrors" htmlEscape="false"/>
				<form:errors path="deathMonth" cssClass="inputerrors" htmlEscape="false"/>
				<form:errors path="deathDay" cssClass="inputerrors" htmlEscape="false"/>

				<div>
					<input id="close" type="submit" value="Close" title="Do not save changes" class="button" />
					<input id="save" type="submit" value="Save" class="button"/>
				</div>
				<input type="hidden" value="" id="modify" />
			</fieldset>	
		</form:form>
	</div>

	<c:url var="SearchBornPlaceURL" value="/de/geobase/SearchBornPlace.json"/>
	<c:url var="SearchDeathPlaceURL" value="/de/geobase/SearchDeathPlace.json"/>
	
	<c:url var="ShowBirthPlaceDetailsURL" value="/de/geobase/ShowBirthPlaceDetails.json" />
	<c:url var="ShowDeathPlaceDetailsURL" value="/de/geobase/ShowDeathPlaceDetails.json" />

	<script type="text/javascript">
		$j(document).ready(function() {
			$j.scrollTo("#EditDetailsPersonForm");
			
			$j("#EditNamesPerson").css('visibility', 'hidden');
	        $j("#EditTitlesOrOccupationsPerson").css('visibility', 'hidden'); 
			$j("#EditParentsPerson").css('visibility', 'hidden');
			$j("#EditChildrenPerson").css('visibility', 'hidden');
			$j("#EditSpousesPerson").css('visibility', 'hidden');
	        $j("#EditResearchNotesPerson").css('visibility', 'hidden');
	        
	        $j("#save").click(function(){
	        	$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
	        	$j("#loadingDiv").css('visibility', 'visible');
	        });
	        
	        $j("#EditDetailsPersonForm :input").change(function(){
				$j("#modify").val(1); <%-- //set the hidden field if an element is modified --%>
				return false;
			});
	        
	        if($j("#activeStart").val() != ''){
	        	$j("#activeStartLabel").append("Active Start");
	        	$j("#bornYear").attr('class', 'input_4c_disabled');
	        	$j("#bornYear").attr('disabled', 'disabled');
	        	$j("#bornYearLabel").append('<a class="messageStart" title="This field is disabled because you have written the Active Start">Year<a/>');
	        }else{
	        	if($j("#bornYear").val() != ''){
	        		$j("#bornYearLabel").append("Year");
	        		$j("#activeStart").attr('class', 'input_10c_disabled');
	        		$j("#activeStart").attr('disabled', 'disabled');
	        		$j("#activeStartLabel").append('<a class="messageStart" title="This field is disabled because you have written the Birth Year">Active Start<a/>');
	        	}else{
	        		$j("#activeStartLabel").append("Active Start");
	        		$j("#bornYearLabel").append("Year");
	        	}
	        }
	        
	        if($j("#activeEnd").val() != ''){
	        	$j("#activeEndLabel").append("Active End");
	        	$j("#deathYear").attr('class', 'input_4c_disabled');
	        	$j("#deathYear").attr('disabled', 'disabled');
	        	$j("#deathYearLabel").append('<a class="messageEnd" title="This field is disabled because you have written the Active End">Year<a/>');
	        }else{
	        	if($j("#deathYear").val() != ''){
	        		$j("#deathYearLabel").append("Year");
	        		$j("#activeEnd").attr('class', 'input_10c_disabled'); 
					$j("#activeEnd").attr('disabled', 'disabled');
	        		$j("#activeEndLabel").append('<a class="messageEnd" title="This field is disabled because you have written the Death Year">Active End<a/>');
	        	}else{
	        		$j("#activeEndLabel").append("Active End");
	        		$j("#deathYearLabel").append("Year");
	        	}
	        }
	        
	        $j("#bornYear").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#activeStart").val('');
	        		$j("#activeStart").attr('class', 'input_10c_disabled');
	        		$j("#activeStart").attr('disabled', 'disabled');
	        		$j("#activeStartLabel").text("");
	        		$j("#activeStartLabel").append('<a class="messageStart" title="This field is disabled because you have written the Birth Year">Active Start<a/>');
	        		$j('.messageStart').tooltip({
	    				track: true,
	    				fade: 350 
	    			});
	        	}else{
	        		$j("#activeStart").attr('class', 'input_10c');
	        		$j("#activeStart").removeAttr('disabled');
	        		$j(".messageStart").remove();
	        		$j("#activeStartLabel").append("Active Start");
	        	}
	        });
	        
	        $j("#activeStart").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#bornYear").val('');
	        		$j("#bornYear").attr('class', 'input_4c_disabled');
	        		$j("#bornYear").attr('disabled', 'disabled');
	        		$j("#bornYearLabel").text("");
	        		$j("#bornYearLabel").append('<a class="messageStart" title="This field is disabled because you have written the Active Start">Year<a/>');
	        		$j('.messageStart').tooltip({
	    				track: true,
	    				fade: 350 
	    			});
	        	}else{
	        		$j("#bornYear").attr('class', 'input_4c');
	        		$j("#bornYear").removeAttr('disabled');
	        		$j(".messageStart").remove();
	        		$j("#bornYearLabel").append("Year");
	        	}
	        });
	        
	        $j("#deathYear").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#activeEnd").val('');
	        		$j("#activeEnd").attr('class', 'input_10c_disabled');
	        		$j("#activeEnd").attr('disabled', 'disabled');
	        		$j("#activeEndLabel").text("");
	        		$j("#activeEndLabel").append('<a class="messageEnd" title="This field is disabled because you have written the Death Year">Active End<a/>');
	        		$j('.messageEnd').tooltip({
	    				track: true,
	    				fade: 350 
	    			});
	        	}else{
	        		$j("#activeEnd").attr('class', 'input_10c');
	        		$j("#activeEnd").removeAttr('disabled');
	        		$j(".messageEnd").remove();
	        		$j("#activeEndLabel").append("Active End");
	        	}
	        });
	        
	        $j("#activeEnd").change(function(){
	        	if($j(this).val() != ''){
	        		$j("#deathYear").val('');
	        		$j("#deathYear").attr('class', 'input_4c_disabled');
	        		$j("#deathYear").attr('disabled', 'disabled');
	        		$j("#deathYearLabel").text("");
	        		$j("#deathYearLabel").append('<a class="messageEnd" title="This field is disabled because you have written the Active End">Year<a/>');
	        		$j('.messageEnd').tooltip({
	    				track: true,
	    				fade: 350 
	    			});
	        	}else{
	        		$j("#deathYear").attr('class', 'input_4c');
	        		$j("#deathYear").removeAttr('disabled');
	        		$j(".messageEnd").remove();
	        		$j("#deathYearLabel").append("Year");
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
			    	$j.get("${ShowBirthPlaceDetailsURL}", { placeAllId: "" + data }, function(data) {
			    		$j('#bornPlacePrefered').val(data.prefFlag);
			    	});
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
			    	$j.get("${ShowDeathPlaceDetailsURL}", { placeAllId: "" + data }, function(data) {
			    		$j('#deathPlacePrefered').val(data.prefFlag);
			    	});
			    }
			});

			$j("#EditDetailsPersonForm").submit(function (){
				if($j("#bornPlacePrefered").val() == 'V' || $j("#deathPlacePrefered").val() == 'V'){
					$j('#EditDetailsPersonDiv').block({ message: $j('.notPrincipal') });
					return false;
				}else{
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
				}
			});

			$j('#close').click(function(e) {
				$j('.autocomplete').remove();

				if($j("#modify").val() == 1){
	        		// Block is attached to form otherwise this block does not function when we use in transcribe and contextualize document
					$j('#EditDetailsPersonDiv').block({ message: $j('#question') }); ; 
					return false;
	        	}else{
	        		$j.ajax({ url: '${ShowPersonURL}', cache: false, success:function(html) { 
	    			$j("#body_left").html(html);
	    			}});
				}
	            return false;
			});
			
			$j('.messageStart').tooltip({
				track: true,
				fade: 350 
			});
			
			$j('#firstLabel, #sucNumLabel, #midPrefixLabel, #middleLabel, #lastPrefixLabel, #lastLabel, #postLastPrefixLabel, #postLastLabel').tooltip({
				track: true,
				fade: 350 
			});
			
			$j('.messageEnd').tooltip({
				track: true,
				fade: 350 
			}); 
				
		});
	</script>


	<div id="questionPlace" class="notPrincipal" style="display:none; cursor: default">
		<h1>This name place is classified as a Variant Name and will be adjusted to its Preferred Name</h1>
		<input type="button" id="ok" value="Ok" />
	</div>
	
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
			
			$j("#ok").click(function(){
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j(".notPrincipal").hide();
				$j("#EditDetailsPersonDiv").append($j(".notPrincipal"));
				$j(".blockUI").remove();
				$j.ajax({ type:"POST", url:$j("#EditDetailsPersonForm").closest('form').attr("action"), data:$j("#EditDetailsPersonForm").closest('form').serialize(), async:false, success:function(html) { 
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