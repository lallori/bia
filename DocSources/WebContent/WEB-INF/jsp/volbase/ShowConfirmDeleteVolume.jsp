<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="DeleteVolumeURL" value="/de/volbase/DeleteVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	<c:url var="CheckVolumeIsDeletableURL" value="/de/volbase/CheckVolumeIsDeletable.json">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to delete this record?</h1>
		
		<a id="yes" href="${DeleteVolumeURL}">YES</a>
	
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
				$j.ajax({ type:"GET", url: '${CheckVolumeIsDeletableURL}', async:false, success:function(json) { 
					if (json.isDeletable == 'TRUE') {
						
					} else {
						
					}
				}});

				return false;
			});
		});
	</script>
