<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="UndeleteVolumeURL" value="/de/volbase/UndeleteVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	<c:url var="ShowVolumeURL" value="/src/volbase/ShowVolume.do">
		<c:param name="summaryId"   value="${command.summaryId}" />
	</c:url>
	
	<div id="DeleteThisRecordDiv">
		<h1>Are you sure you want to undelete this record?</h1>
		
		<a id="yes" href="${DeleteVolumeURL}">YES</a>
	
		<a id="no" href="#">NO</a>
			
		<input id="close" type="submit" title="Close Undelete Menu window" value="Close"/>
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
				$j.ajax({ type:"POST", url: '${UndeleteVolumeURL}', async:false, success:function(html) {
					$j("#DeleteThisRecordDiv").html(html);
					$j("#body_left").load('${ShowVolumeURL}');
				}});

				return false;
			});
		});
	</script>
