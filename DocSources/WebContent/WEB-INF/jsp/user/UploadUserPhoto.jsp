<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="userPhotoUrl" value="/user/showuserphoto.do"/>
		<center>
			<img id="userphoto" src="${userPhotoUrl}"/>
		</center>
		
				<form:form id="uploadPhotoForm" method="post" enctype="multipart/form-data" onsubmit="beginUpload();">
				  	<fieldset>		
						<p>
							<input name="file" type="file" class="inputbox" style="width:200px;" />
						</p>
						<p>	
							<input id="update" type="submit" value="Update" />
						</p>
					</fieldset>
					<input type="hidden" name="idUpload" value="${idUpload}"/>
					<span class="progressbar" id="uploadprogressbar"></span>
				</form:form>

<c:url var="uploadPhotoUrl" value="/common/upload.json"/>
<c:url var="progressImageBarUrl" value="/images/progressbg_red.gif"/>
<script type='text/javascript' src='<c:url value="/scripts/jquery-1.4.2.js"/>'></script>
<script type='text/javascript' src='<c:url value="/scripts/jquery.form-2.47.js"/>'></script>
<script type='text/javascript' src='<c:url value="/scripts/jquery.progressbar.js"/>'></script>
<script type="text/javascript">
	var progress_key = '${idUpload}';
	 
	// this sets up the progress bar
	$(document).ready(function() {
	    $("#uploadprogressbar").progressBar({ barImage: '/DocSourcesProject/images/progressbar.gif' });

		var options = { 
	        target:        '#output2',   // target element(s) to be updated with server response 
	        success:       showResponse  // post-submit callback 
	    }; 
		 
		    // bind to the form's submit event 
	    $('#uploadPhotoForm').submit(function() { 
	        $(this).ajaxSubmit(options); 
		 
	        // always return false to prevent standard browser submit and page navigation 
	        return false; 
	    }); 

	});

	// post-submit callback 
	function showResponse(responseText, statusText, xhr, $form)  { 
	    // for normal html responses, the first argument to the success callback 
	    // is the XMLHttpRequest object's responseText property 
	 
		$("#userphoto").html("<img id='userphoto' src='${userPhotoUrl}'>");
		 
	} 

	// fades in the progress bar and starts polling the upload progress after 1.5seconds
	function beginUpload() {
	    // uses ajax to poll the uploadprogress.php page with the id
	    // deserializes the json string, and computes the percentage (integer)
	    // update the jQuery progress bar
	    // sets a timer for the next poll in 750ms
	    $("#uploadprogressbar").fadeIn();
	 
	    var i = setInterval(function() { 
	        $.getJSON("${uploadPhotoUrl}?idUpload=" + progress_key, function(data) {
	        	if (data.bytes_total == data.bytes_uploaded) {
	                clearInterval(i);
	                return;
	            }

	            var percentage = Math.floor(100 * parseInt(data.bytes_uploaded) / parseInt(data.bytes_total));
	            $("#uploadprogressbar").progressBar(percentage);
	        });
	    }, 1500);
	}
</script>
