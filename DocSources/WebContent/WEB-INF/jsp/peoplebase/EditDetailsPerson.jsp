<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<div>
		<form:form id="EditDetailsPersonForm" cssClass="edit" method="post">
			<fieldset>
			<legend><b>PERSON DETAILS</b></legend>
				<div>
					<form:label for="firstName" path="firstName" cssErrorClass="error"><b>First Name:</b></form:label>
					<form:input path="firstName" cssClass="input_5c" />
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
					<select id="gender" name="gender" class="selectform_short">
						<option value="M">M</option><option value="F">F</option><option value="X">X</option><option value="" selected="selected"></option>		
					</select>
				</div>
				
				<div>
					<b>Birth:</b>
					<form:label for="bornYear" path="bornYear" cssErrorClass="error">Year</form:label>
					<form:input path="bornYear" cssClass="input_4c" maxlength="4"/>
					<form:label for="bornMonth" path="bornMonth" cssErrorClass="error">Month</form:label>
					<select id="monthBirth" name="monthBirth" class="selectform">
						<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
					</select>
					<form:label for="bornDay" path="bDay" cssErrorClass="error">Day</form:label>
					<form:input path="bDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				<div>
					<form:label for="bApprox" path="bApprox" cssErrorClass="error">Approx</form:label>
					<form:checkbox path="bApprox" cssClass="checkboxPers1"/>
					<input type="checkbox" name="approxBirth" class="checkboxPers1"/>
					<form:label for="bDateBc" path="bDateBc" cssErrorClass="error">BC?</form:label>
					<form:checkbox path="bDateBc" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<form:label for="bPlace" path="bPlace" cssErrorClass="error">Place</form:label>
					<form:input path="bPlace" cssClass="input_10c"/>
				</div>
				
				<div>
					<form:label for="activeStart" path="activeStart" cssErrorClass="error">Active Start:</form:label>
					<form:input path="activeStart" cssClass="input_10c"/>
		
					<form:label for="bUnsure" path="bUnsure" cssErrorClass="error">Unsure?</form:label>
					<form:checkbox path="bUnsure" cssClass="checkboxPers2"/>
				</div>
				
				<div>
					<b>Death:</b>
					<form:label for="deathYear" path="deathYear" cssErrorClass="error">Year</form:label>
					<form:input path="deathYear" cssClass="input_4c" maxlength="4"/>
					<form:label for="deathMonth" path="deathMonth" cssErrorClass="error">Month</form:label>
					<select id="monthDeath" name="monthDeath" class="selectform">
						<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
					</select>
					<form:label for="deathDay" path="deathDay" cssErrorClass="error">Day</form:label>
					<form:input path="deathDay" cssClass="input_2c" maxlength="2"/>
				</div>
				
				
					<form:hidden path="personId"/>			
				<div>
					<input id="close" type="submit" value="" title="Do not save changes" class="button" />
					<input id="save" type="submit" value="" class="button"/>
				</div>
			</fieldset>	
		</form:form>
	</div>


<script type="text/javascript">
	$j(document).ready(function() {
		$j("#EditNamesPerson").css('visibility', 'hidden');
        $j("#EditTitlesOccupationsPerson").css('visibility', 'hidden'); 
		$j("#EditParentsPerson").css('visibility', 'hidden');
		$j("#EditChildrenPerson").css('visibility', 'hidden');
		$j("#EditSpousesPerson").css('visibility', 'hidden');
        $j("#EditResearchNotesPerson").css('visibility', 'hidden'); 
        
        $j("#EditDetailsPerson").submit(function (){
			$j.post($j(this).attr("action"), $j(this).serialize(), function() {
				// In questa function si definisce la sostituzione del div dove visualizzare il risultato
				// questa function rappresenta 
				alert('done!');
			});
		});
	});
</script>


<script type="text/javascript"> 
    $j(document).ready(function() { 
		$j('#close').click(function() { 
            $j('#EditDetailsVolumeDiv').block({ 
                message: '<h1>Discard changes and close window?</h1>', 
                css: { border: '3px solid #a00' } 
            }); 
        }); 
	s});					  
</script>


<script type="text/javascript">
	$j(document).ready(function() {
		var b = $j('#docIdAutoCompleter').autocomplete({ 
		    serviceUrl:'/DocSources/de/docbase/ajax/FindDocId.json',
		    minChars:3, 
		    delimiter: /(,|;)\s*/, // regex or character
		    maxHeight:400,
		    width:600,
		    zIndex: 9999,
		    deferRequestBy: 0, //miliseconds
		    noCache: false, //default is false, set to true to disable caching
		    onSelect: function(value, data){ $j('#seriesRefNum').val(data); }
		  });
	$j(document).ready(function() {
		var b = $j('#volNumAutoCompleter').autocomplete({ 
		    serviceUrl:'/DocSources/de/docbase/ajax/FindVolNum.json',
		    minChars:3, 
		    delimiter: /(,|;)\s*/, // regex or character
		    maxHeight:400,
		    width:600,
		    zIndex: 9999,
		    deferRequestBy: 0, //miliseconds
		    noCache: false, //default is false, set to true to disable caching
		    onSelect: function(value, data){ $j('#seriesRefNum').val(data); }
		  });
		});
		$j("#EditDetailsPerson").submit(function (){
			$j.post($j(this).attr("action"), $j(this).serialize(), function() {
				// In questa function si definisce la sostituzione del div dove visualizzare il risultato
				// questa function rappresenta 
				alert('done!');
			});
		});
	});
</script>


<script type="text/javascript"> 
    $j(document).ready(function() { 
		$j('#close').click(function() {
            $j('#EditDetailsPersonDiv').block({ message: $j('#question') }); 
			return false;
		});
        
		$j("#EditDetailsPersonForm").submit(function (){
			$j.ajax({ type:"POST", url:$j(this).attr("action"), data:$j(this).serialize(), async:false, success:function(html) { 
				$j("#EditDetailsPersonDiv").html(html);
			}});
			return false;
		});
	s});					  
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
			$j.ajax({ url: '${ShowVolume}', cache: false, success:function(html) { 
				$j("#body_left").html(html);
			}});
				
			return false; 
		}); 
     
	});
</script>