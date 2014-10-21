<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

	<c:url var="ShowUploadPortraitPersonURL" value="/de/peoplebase/ShowUploadPortraitPerson.do">
	</c:url>
	
	<c:url var="DeletePortraitPersonURL" value="/de/peoplebase/DeletePortraitPerson.do">
		<c:param name="personId" value="${person.personId }" />
	</c:url>
	
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId"   value="${person.personId}" />
	</c:url>
	
	<c:url var="GetPortraitPersonInformationURL" value="/src/peoplebase/GetPortraitPersonInformation.json">
		<c:param name="personId" value="${person.personId }" />
	</c:url>

	<form:form id="uploadPortraitPersonForm" action="${ShowUploadPortraitPersonURL}"	method="post" class="edit" enctype="multipart/form-data">
		<div class="listForm">
			<div class="row">
				<div class="col_l">
					<form:label for="link" id="linkLabel" path="link"><fmt:message key=“people.showUploadPortraitPersonModalWindow.link”/></form:label>
				</div>
				<div class="col_l">
					<form:input id="link" name="link" class="input_40c" type="text" path="link"/>
				</div>
			</div>
			<div class="row">
				<div class="col_l">
					<form:label for="browse" id="browseLabel" path="browse"><fmt:message key=“people.showUploadPortraitPersonModalWindow.browse”/></form:label>
				</div>
				<div class="col_l">
					<form:input id="browse" name="browse" class="input_28c" type="file" value="" size="30" path="browse"/>
				</div>
			</div>
		</div>
		<br>
		<form:errors path="browse" cssClass="inputerrors" htmlEscape="false"/>
		<div>
			<input type="hidden" name="personId" value="${person.personId}" />
			<a id="resetPortrait" href="${DeletePortraitPersonURL}" class="button_medium"><fmt:message key=“people.showUploadPortraitPersonModalWindow.resetPortrait”/></a>
			<input id="save" type="submit" class="savePortrait button_small"  value="Save" />
		</div>
	
	</form:form>

	<div id="output"></div>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#resetPortrait").click(function(){
				$j.ajax({ type:"POST", url:$j(this).attr("href"), data:$j(this).serialize(), async:false, success:function(html) {
					$j("#body_left").load('${ShowPersonURL}');
					$j("#uploadPortraitWindow").dialog("close");
				}});
				return false;
			});
			
			var options = { 
				target:        '#output',   // target element(s) to be updated with server response 
				success:       showResponse  // post-submit callback 
		    }; 

		    $j('#uploadPortraitPersonForm').ajaxForm(options);

		    // post-submit callback 
			function showResponse(responseText, statusText, xhr, $form)  {
			    // if the ajaxForm method was passed an Options Object with the dataType 
			    // property set to 'json' then the first argument to the success callback 
			    // is the json data object returned by the server 
			 	$j('#uploadPortraitWindow').html(responseText);
			 	$j.ajax({ url: '${GetPortraitPersonInformationURL}', cache: false, success:function(json) { 
			 		if (json.portraitWidth > 100) {
						$j("#uploadPortraitWindow").dialog("option", "width", json.portraitWidth + 10);
			 		}
			 		if (json.portraitHeight> 100) {
						$j("#uploadPortraitWindow").dialog("option", "height", json.portraitHeight + 100);
			 		}
				}});
			} 
		});
		</script>