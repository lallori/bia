<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<!-- The class assigned to href is the default of jqModal -->
	
	<form:form id="EditCorrespondentsDocumentForm" method="post" cssClass="edit">

		<fieldset>
		<legend><b>CORRESPONDENTS/PEOPLE </b></legend>
			<div>
				<form:label id="senderPeopleLabel" for="senderPeople" path="senderPeople" cssErrorClass="error">Sender:</form:label>
				<form:input id="senderPeopleAutoCompleter" path="senderPeople" cssClass="input_25c" />
				<form:label id="senderPeopleUnsureLabel" for="senderPeopleUnsure" path="senderPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="senderPeopleUnsure" path="senderPeopleUnsure" cssClass="checkboxPers2"/>
			</div>
			<div>	
				<form:label id="senderPlaceLabel" for="senderPlace" path="senderPlace" cssErrorClass="error">From:</form:label>
				<form:input id="senderPlaceAutoCompleter" path="senderPlace" cssClass="input_25c" />
				<form:label id="senderPlaceUnsureLabel" for="senderPlaceUnsure" path="senderPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="senderPlaceUnsure" path="senderPlaceUnsure" cssClass="checkboxPers2"/>
			</div>

			<hr />
			
			<div>
				<form:label id="recipientPeopleLabel" for="recipientPeople" path="recipientPeople" cssErrorClass="error">Recipient:</form:label>
				<form:input id="recipientPeopleAutoCompleter" path="recipientPeople" cssClass="input_25c"/>
				<form:label id="recipientPeopleUnsureLabel" for="recipientPeopleUnsure" path="recipientPeopleUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPeopleUnsure" path="recipientPeopleUnsure" cssClass="checkboxPers2"/>
			</div>
			<div>
				<form:label id="recipientPlaceLabel" for="recipientPlace" path="recipientPlace" cssErrorClass="error">From:</form:label>
				<form:input id="recipientPlaceAutoCompleter" name="recipientPlace" cssClass="input_25c" />
				<form:label id="recipientPlaceUnsureLabel" for="recipientPlaceUnsure" path="recipientPlaceUnsure">Unsure?</form:label>
				<form:checkbox id="recipientPlaceUnsure" path="recipientPlaceUnsure" cssClass="checkboxPers2"/>
			</div>
			
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>			
		</fieldset>	
	</form>
		
	<form id="PeopleCorrespondentsDocumentsForm" action="/DocSources/de/peoplebase/PeopleCorrespondentsDocuments.do?summaryId=0&amp;volNum=0&amp;volLeText=" method="post" class="edit">
		<fieldset>	
		
			<div>
				<label for="people" id="peopleLabel">People:</label>
			</div>
			<div>
				<input id="people" name="people" class="input_28c_disabled" type="text" value="Niccolini, Agnolo di Matte" disabled="disabled"/>
				<a href="#"><img src="/DocSources/images/button_cancel_form13.gif" alt="Cancel value" title="Delete this entry"/></a>
				<a id="editValue" href="/DocSources/de/docbase/EditPersonCorrespondentsDocument.html">edit value</a>
			</div>
			
			<div>
				<input id="people" name="people" class="input_28c_disabled" type="text" value="Pippo, Agnolo di Matte" disabled="disabled"/>
				<a href="#"><img src="/DocSources/images/button_cancel_form13.gif" alt="Cancel value" title="Delete this entry"/></a>
				<a id="editValue" href="/DocSources/de/docbase/EditPersonCorrespondentsDocument.html">edit value</a>
			</div>			

			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<a id="AddPersonCorrespondentsDocument" href="/DocSources/de/docbase/AddPersonCorrespondentsDocument.html">Add new Person</a>
			</div>	
		</fieldset>
	</form>
		
		<div id="AddPersonCorrespondentsDocumentDiv"></div>
			
		<script type="text/javascript">
			$(document).ready(function() {
				$("#AddPersonCorrespondentsDocument").click(function(){$("#AddPersonCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
				$("#editValue").click(function(){$("#EditCorrespondentsDocumentDiv").load($(this).attr("href"));return false;});
			});
		</script>	



<script type="text/javascript">
	$(document).ready(function() {
		var a = $('#senderPeopleAutoCompleter').autocomplete({ 
		    serviceUrl:'/DocSources/de/peoplebase/FindPeople.json',
		    minChars:3, 
		    delimiter: /(,|;)\s*/, // regex or character
		    maxHeight:400,
		    width:600,
		    zIndex: 9999,
		    deferRequestBy: 0, //miliseconds
		    noCache: false, //default is false, set to true to disable caching
		    onSelect: function(value, data){ $('#seriesRefNum').val(data); }
		  });
		
		$("#EditDetailsVolume").submit(function (){
			$.post($(this).attr("action"), $(this).serialize(), function() {
				// In questa function si definisce la sostituzione del div dove visualizzare il risultato
				// questa function rappresenta 
				alert('done!');
			});
		});
	});
</script>


<script type="text/javascript"> 
    $(document).ready(function() { 
		$('#close').click(function() { 
            $('#EditDetailsVolumeDiv').block({ 
                message: '<h1>Discard changes and close window?</h1>', 
                css: { border: '3px solid #a00' } 
            }); 
        }); 
	s});					  
</script>
