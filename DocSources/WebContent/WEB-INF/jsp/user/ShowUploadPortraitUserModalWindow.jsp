<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

	<c:url var="ShowUploadPortraitUserURL" value="/user/ShowUploadPortraitUser.do">
	</c:url>
	
	<c:url var="DeletePortraitUserURL" value="/user/DeletePortraitUser.do">
		<c:param name="account" value="${user.account}" />
	</c:url>
	
	<c:url var="GetPortraitUserInformationURL" value="/user/GetPortraitUserInformation.json">
		<c:param name="account" value="${user.account}" />
	</c:url>
	
	<c:url var="ShowUserProfileURL" value="/user/ShowUserProfile.do"/>

	<form id="uploadPortraitUserForm" action="${ShowUploadPortraitUserURL}"	method="post" class="edit" enctype="multipart/form-data">
		<div class="listForm">
			<div class="row">
				<div class="col_l">
					<label for="link" id="linkLabel">Link</label>
				</div>
				<div class="col_l">
					<input id="link" name="link" class="input_40c" type="text" value="http://" />
				</div>
			</div>
			<div class="row">
				<div class="col_l">
					<label for="browse" id="browseLabel">Browse</label>
				</div>
				<div class="col_l">
					<input id="browse" name="browse" class="input_28c" type="file" value="" size="30" />
				</div>
			</div>
		</div>
		<br>
		<div>
			<input type="hidden" name="account" value="${user.account}" />
			<a id="resetPortrait" href="${DeletePortraitUserURL}" class="button_medium">Reset Portrait</a>
			<input id="save" type="submit" class="savePortrait"  value="Save" />
		</div>
	
	</form>

	<div id="output"></div>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#resetPortrait").click(function(){
				$j.ajax({ type:"POST", url:$j(this).attr("href"), data:$j(this).serialize(), async:false, success:function(html) {
					Modalbox.show('${ShowUserProfileURL}', {title: "USER PREFERENCES", width: 760, height: 470});
					$j("#uploadPortraitUserWindow").dialog("close");
				}});
				return false;
			});
			
			var options = { 
				target:        '#output',   // target element(s) to be updated with server response 
				success:       showResponse  // post-submit callback 
		    }; 

		    $j('#uploadPortraitUserForm').ajaxForm(options);

		    // post-submit callback 
			function showResponse(responseText, statusText, xhr, $form)  {
			    // if the ajaxForm method was passed an Options Object with the dataType 
			    // property set to 'json' then the first argument to the success callback 
			    // is the json data object returned by the server 
			 	$j('#uploadPortraitUserWindow').html(responseText);
			 	$j.ajax({ url: '${GetPortraitUserInformationURL}', cache: false, success:function(json) { 
			 		if (json.portraitWidth > 100) {
						$j("#uploadPortraitUserWindow").dialog("option", "width", json.portraitWidth + 10);
			 		}
			 		if (json.portraitHeight> 100) {
						$j("#uploadPortraitUserWindow").dialog("option", "height", json.portraitHeight + 100);
			 		}
				}});
			} 
		});
		</script>