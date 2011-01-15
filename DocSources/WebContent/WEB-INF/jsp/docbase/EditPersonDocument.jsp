<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditCorrespondentsOrPeopleDocument" value="/de/docbase/EditCorrespondentsOrPeopleDocument.do">
			<c:param name="entryId" value="${command.entryId}" />
		</c:url>
	</security:authorize>

	<form:form id="EditPersonDocumentForm" cssClass="edit">
		<fieldset>
			<legend>
			<c:if test="${command.personId == 0}"> 
				<b>ADD NEW PERSON</b>
			</c:if>
			<c:if test="${command.personId == 0}">
				<br>
			</c:if> 
			</legend>
			<div>
				<form:label id="personDescriptionLabel" for="personDescription" path="personDescription" cssErrorClass="error">Name:</form:label>
				<form:input id="personDescriptionAutoCompleter" path="personDescription" class="input_25c" />
			</div>
			<div>
				<form:label id="assignUnsureLabel" for="assignUnsure" path="assignUnsure" cssErrorClass="error">Unsure?</form:label>
				<form:checkbox id="assignUnsure" path="assignUnsure" cssClass="checkboxPers2"/>
				<form:label id="portraitLabel" for="portrait" path="portrait" cssErrorClass="error">Portrait</form:label>
				<form:checkbox id="portrait" path="portrait" class="checkboxPers2"/>
			</div>
			<div>
				<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
				<input id="save" type="submit" value="Save" class="button"/>
			</div>
		</fieldset>	

		<form:hidden path="epLinkId"/>
		<form:hidden path="personId"/>
		<form:hidden path="entryId"/>
	</form:form>

	<c:url var="searchPersonLinkableToDocumentUrl" value="/de/docbase/SearchPersonLinkableToDocument.json">
		<c:param name="entryId" value="${command.entryId}" />
	</c:url>

	<script type="text/javascript"> 
	    $(document).ready(function() { 
			var peopleDescription = $('#personDescriptionAutoCompleter').autocompletePerson({ 
			    serviceUrl:'${searchPersonLinkableToDocumentUrl}',
			    minChars:3, 
			    delimiter: /(,|;)\s*/, // regex or character
			    maxHeight:400,
			    width:600,
			    zIndex: 9999,
			    deferRequestBy: 0, //miliseconds
			    noCache: true, //default is false, set to true to disable caching
			    onSelect: function(value, data){ $('#personId').val(data); }
			  });

			$('#close').click(function() { 
	            $('#EditPersonDocumentDiv').block({ 
	                message: '<h1>Discard changes and close window?</h1>', 
	                css: { border: '3px solid #a00' } 
	            })
			});

			$("#EditPersonDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) {
					$("#EditCorrespondentsOrPeopleDocumentDiv").load('${EditCorrespondentsOrPeopleDocument}');
				}})
				return false;
			});
		});					  
	</script>
