<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowConfirmCreatePlaceForumURL" value="/src/geobase/ShowConfirmCreatePlaceForum.do">
		<c:param name="placeAllId"   value="${requestCommand.placeAllId}" />
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1><fmt:message key="geobase.showConfirmCreatePlaceForumModalWindow.thisPlaceNotLinked"/></h1>
		
		<a id="yes" href="${ShowConfirmCreatePlaceForumURL}" class="button_small"><fmt:message key="geobase.showConfirmCreatePlaceForumModalWindow.yes"/></a>
	
		<a id="no" href="#" class="button_small"><fmt:message key="geobase.showConfirmCreatePlaceForumModalWindow.no"/></a>
			
	</div>

	<script>
		$j(document).ready(function() {
			$j("#MB_content").css("height", "70px");
			
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});
			
			$j("#no").click(function() {			
				Modalbox.hide();
				return false;
			});

			$j("#yes").click(function() {
	 			$j.ajax({ type:"POST", url:'${ShowConfirmCreatePlaceForumURL}', data:null, async:false, success:function(html) { 
					$j("#ConfirmCreateForum").html(html);
				}});
				return false;
			});
		});
	</script>
