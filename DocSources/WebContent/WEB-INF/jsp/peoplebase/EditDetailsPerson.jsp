<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditDetailsPersonForm" method="post">
	</form:form>
<!-- The class assigned to href is the default of jqModal -->
	
	<div id="createdby"><h6>CREATED BY KH 11/28/1996</h6></div>
	
	<div>
	<form id="EditDetailsPersonForm" action="/DocSources/de/peoplebase/EditDetailsPerson.do?summaryId=0&amp;volNum=0&amp;volLeText=" method="post" class="edit">
	<fieldset>
	<legend><b>PERSON DETAILS</b></legend>
		<div>
			<label for="firstName" id="firstNameLabel"><b>First Name:</b></label>
			<input id="firstName" name="firstName" class="input_20c" type="text" value="" />
			<label for="successNumber" id="successNumberLabel">Sucess. Number:</label>
			<input id="successNumber" name="successNumber" class="input_5c" type="text" value="" maxlength="5"/>
		</div>
		
		<div>
			<label for="prefixPreId" id="prefixPreIdLabel">Prefix Pre-Id:</label>
			<input id="prefixPreId" name="prefixPreId" class="input_5c" type="text" value="" maxlength="5"/>

			<label for="PreId" id="PreIdLabel">Pre-Id:</label>
			<input id="PreId" name="PreId" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<label for="prefixLname" id="prefixLnameLabel">Prefix Lastname:</label>
			<input id="prefixLname" name="prefixLname" class="input_5c" type="text" value="" maxlength="5"/>

			<label for="lastname" id="lastnameLabel">Lastname:</label>
			<input id="lastname" name="lastname" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<label for="prefixPostId" id="prefixPostIdLabel">Prefix Post-Id:</label>
			<input id="prefixPostId" name="prefixPostId" class="input_5c" type="text" value="" maxlength="5"/>
			<label for="PostId" id="PostIdLabel">Post-Id:</label>
			<input id="PostId" name="PostId" class="input_20c" type="text" value="" />
		</div>
		
		<div>
			<label for="gender" id="genderLabel">Gender:</label>
			<select id="gender" name="gender" class="selectform_short">
				<option value="M">M</option><option value="F">F</option><option value="X">X</option><option value="" selected="selected"></option>		
			</select>
		</div>
		
		<div>
			<b>Birth:</b>
			<label for="yearBirth" id="yearBirthLabel">Year</label>
			<input id="yearBirth" name="yearBirth" class="input_4c" type="text" value="" maxlength="4"/>
			<label for="monthBirth" id="monthBirthLabel">Month</label>
			<select id="monthBirth" name="monthBirth" class="selectform">
				<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
			</select>
			<label for="dayBirth" id="dayBirthLabel">Day</label>
			<input id="dayBirth" name="dayBirth" class="input_2c" type="text" value="" maxlength="2"/>
		</div>
		
		<div>
			<label for="approxBirth" id="approxBirthLabel">Approx</label>
			<input type="checkbox" name="approxBirth" class="checkboxPers1"/>
			<label for="bcBirth" id="bcBirthLabel">BC?</label>
			<input type="checkbox" name="bcBirth" class="checkboxPers2"/>
		</div>
		
		<div>
			<label for="placeBirth" id="placeBirthLabel">Place:</label>
			<input id="placeBirth" name="placeBirth" class="input_10c" type="text" value=""/>
		</div>
		
		<div>
			<label for="activeStart" id="activeStartLabel">Active Start:</label>
			<input id="activeStartAutoCompleter" name="activeStart" class="input_10c" type="text" value=""/>

			<label for="unsure" id="unsureLabel">Unsure?</label>
			<input type="checkbox" name="unsure" class="checkboxPers2"/>
		</div>
		
		<div>
			<b>Death:</b>
			<label for="yearDeath" id="yearDeathLabel">Year</label>
			<input id="yearDeath" name="yearDeath" class="input_4c" type="text" value="" maxlength="4"/>
			<label for="monthDeath" id="monthDeathLabel">Month</label>
			<select id="monthDeath" name="monthDeath" class="selectform">
				<option value="January">January</option><option value="February">February</option><option value="March">March</option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option><option value="" selected="selected"></option>
			</select>
			<label for="dayDeath" id="dayDeathLabel">Day</label>
			<input id="dayDeath" name="dayDeath" class="input_2c" type="text" value="" maxlength="2"/>
		</div>
		
		
			<input id="summaryId" name="summaryId" type="hidden" value="0"/>
			<input id="resIdNo" name="resIdNo" type="hidden" value=""/>
			<input id="seriesRefNum" name="seriesRefNum" type="hidden" value=""/>
			<input id="dateCreated" name="dateCreated" type="hidden" value="11/03/2010 11:51:57"/>
			
	<div>
		<input id="close" type="submit" value="" title="Do not save changes" class="button" />
		<input id="save" type="submit" value="" class="button"/>
	</div>
		
		</fieldset>	
		</form>
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
