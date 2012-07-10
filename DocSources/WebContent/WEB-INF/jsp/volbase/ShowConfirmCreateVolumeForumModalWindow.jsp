<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowConfirmCreateVolumeForumURL" value="/src/volbase/ShowConfirmCreateVolumeForum.do">
		<c:param name="summaryId"   value="${requestCommand.summaryId}" />
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1>This volume has not forum linked. Do you want to create a forum record?</h1>
		
		<a id="yes" href="${ShowConfirmCreateVolumeForumURL}" class="button_small">YES</a>
	
		<a id="no" href="#" class="button_small">NO</a>			
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
	 			$j.ajax({ type:"POST", url:'${ShowConfirmCreateVolumeForumURL}', data:null, async:false, success:function(html) { 
					$j("#ConfirmCreateForum").html(html);
				}});
				return false;
			});
		});
	</script>
