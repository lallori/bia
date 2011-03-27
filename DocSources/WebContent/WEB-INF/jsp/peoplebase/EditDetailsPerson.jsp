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
			<input id="firstName" name="firstName" class="input_20c" type="text" value="" />
			<form:label for="sucNum" path="sucNum" cssErrorClass="error">Succes. Number:</form:label>
			<input id="successNumber" name="successNumber" class="input_5c" type="text" value="" maxlength="5"/>
		</div>
		
		<div>
			<form:label for="midPrefix" path="midPrefix" cssErrorClass="error">Prefix Pre-Id:</form:label>
			<input id="prefixPreId" name="prefixPreId" class="input_5c" type="text" value="" maxlength="5"/>

			<form:label for="middle" path="middle" cssErrorClass="error">Pre-Id:</form:label>
			<input id="PreId" name="PreId" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<form:label for="lastPrefix" path="lastPrefix" cssErrorClass="error">Prefix Lastname:</form:label>
			<input id="prefixLname" name="prefixLname" class="input_5c" type="text" value="" maxlength="5"/>

			<form:label for="last" path="last" cssErrorClass="error">Lastname:</form:label>
			<input id="lastname" name="lastname" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<form:label for="postLastPrefix" path="postLastPrefix" cssErrorClass="error">Prefix Post-Id:</form:label>
			<input id="prefixPostId" name="prefixPostId" class="input_5c" type="text" value="" maxlength="5"/>
			<form:label for="postLast" path="postLast" cssErrorClass="error">Post-Id:</form:label>
			<input id="PostId" name="PostId" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<form:label for="gender" path="gender" cssErrorClass="error">Gender:</form:label>
			<select id="gender" name="gender" class="selectform_short">
				<option value="M">M</option><option value="F">F</option><option value="X">X</option><option value="" selected="selected"></option>		
			</select>
		</div>
		
		<div>
			<b>Birth:</b>
			<form:label for="bYear" path="bYear" cssErrorClass="error">Year</form:label>
			<input id="yearBirth" name="yearBirth" class="input_4c" type="text" value="" maxlength="4"/>
			<form:label for="bMonth" path="bMonth" cssErrorClass="error">Month</form:label>
			<select id="monthBirth" name="monthBirth" class="selectform">
				<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
			</select>
			<form:label for="bDay" path="bDay" cssErrorClass="error">Day</form:label>
			<input id="dayBirth" name="dayBirth" class="input_2c" type="text" value="" maxlength="2"/>
		</div>
		
		<div>
			<form:label for="bApprox" path="bApprox" cssErrorClass="error">Approx</form:label>
			<input type="checkbox" name="approxBirth" class="checkboxPers1"/>
			<form:label for="bDateBc" path="bDateBc" cssErrorClass="error">BC?</form:label>
			<input type="checkbox" name="bcBirth" class="checkboxPers2"/>
		</div>
		
		<div>
			<form:label for="bPlace" path="bPlace" cssErrorClass="error">Place</form:label>
			<input id="placeBirth" name="placeBirth" class="input_10c" type="text" value=""/>
		</div>
		
		<div>
			<form:label for="activeStart" path="activeStart" cssErrorClass="error">Active Start:</form:label>
			<input id="activeStartAutoCompleter" name="activeStart" class="input_10c" type="text" value=""/>

			<form:label for="bUnsure" path="bUnsure" cssErrorClass="error">Unsure?</form:label>
			<input type="checkbox" name="unsure" class="checkboxPers2"/>
		</div>
		
		<div>
			<b>Death:</b>
			<form:label for="dYear" path="dYear" cssErrorClass="error">Year</form:label>
			<input id="yearDeath" name="yearDeath" class="input_4c" type="text" value="" maxlength="4"/>
			<form:label for="dMonth" path="dMonth" cssErrorClass="error">Month</form:label>
			<select id="monthDeath" name="monthDeath" class="selectform">
				<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
			</select>
			<form:label for="dDay" path="dDay" cssErrorClass="error">Day</form:label>
			<input id="dayDeath" name="dayDeath" class="input_2c" type="text" value="" maxlength="2"/>
		</div>
		
		
			<input id="summaryId" name="summaryId" type="hidden" value="0"/>			
		<div>
			<input id="close" type="submit" value="" title="Do not save changes" class="button" />
			<input id="save" type="submit" value="" class="button"/>
		</div>
		
		</fieldset>	
		</form:form>
	</div>


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
		$j("#EditDetailsVolume").submit(function (){
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
		$j("#EditDetailsVolume").submit(function (){
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