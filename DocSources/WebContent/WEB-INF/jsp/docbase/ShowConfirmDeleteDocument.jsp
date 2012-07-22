<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteDocumentURL" value="/de/docbase/DeleteDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	<c:url var="CheckDocumentIsDeletableURL" value="/de/docbase/CheckDocumentIsDeletable.json">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	<c:url var="ShowDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${command.entryId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeleteDocumentURL}">YES</a>
	
		<a id="no" href="#">NO</a>
			
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
		
		<form:form id=""></form:form>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.show($j(this).attr("href"), {title: "DOCUMENT ACTIONS MENU", width: 750, height: 150});
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckDocumentIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove people and topics indexed to this document before deleting it.<p></h1>');
					} else {
						$j.ajax({ type:"POST", url: '${DeleteDocumentURL}', async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").html(html);
							$j("#body_left").load('${ShowDocumentURL}');
						}});
					}
				}});
				return false;
			});
		});
	</script>
