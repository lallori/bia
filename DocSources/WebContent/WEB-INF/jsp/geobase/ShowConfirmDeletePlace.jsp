<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeletePlaceURL" value="/de/geobase/DeletePlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>
	<c:url var="CheckPlaceIsDeletableURL" value="/de/geobase/CheckPlaceIsDeletable.json">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>
	<c:url var="ShowPlaceURL" value="/src/geobase/ShowPlace.do">
		<c:param name="placeAllId"   value="${command.placeAllId}" />
	</c:url>

	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeletePlaceURL}">YES</a>
	
		<a id="no" href="#">NO</a>
			
		<input id="close" type="submit" title="Close Actions Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
				$j.ajax({ type:"GET", url: '${CheckPlaceIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'false') {
						$j("#DeleteThisRecordDiv").html("");
						$j("#DeleteThisRecordDiv").append('<h1>Please remove people and documents indexed to this place before deleting it.<p></h1>');

						if (json.documentsInTopicsPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.documentsInTopicsPlaceURL + ' <span class=\"num_docs\"> ' + json.topicsPlace + '</span><p>');
						}
						
						if(json.senderDocumentsPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.senderDocumentsPlaceURL + ' <span class=\"num_docs\"> ' + json.senderDocumentsPlace + '</span><p>');
						}
				
						if(json.recipientDocumentsPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.recipientDocumentsPlaceURL + ' <span class=\"num_docs\"> ' + json.recipientDocumentsPlace + '</span><p>');
						}
		
						if(json.birthPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.birthPlaceURL + ' <span class=\"num_docs\"> ' + json.birthPlace + '</span><p>');
						}

						if(json.activeStartPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.activeStartPlaceURL + ' <span class=\"num_docs\"> ' + json.activeStartPlace + '</span><p>');
						}

						if(json.deathPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.deathPlaceURL + ' <span class=\"num_docs\"> ' + json.deathPlace + '</span><p>');
						}

						if(json.activeEndPlace>0) {
							$j("#DeleteThisRecordDiv").append(json.activeEndPlaceURL + ' <span class=\"num_docs\"> ' + json.activeEndPlace + '</span><p>');
						}
				
					} else {
						$j.ajax({ type:"POST", url:$j(this).attr("href"), data:$j(this).serialize(), async:false, success:function(html) {
							$j("#DeleteThisRecordDiv").load(html);
							$j("#body_left").load('${ShowPlaceURL}');
						}})
					}
				}});
				return false;
			});
		});
	</script>
