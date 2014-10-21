<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowConfirmCreatePersonForumURL" value="/src/peoplebase/ShowConfirmCreatePersonForum.do">
		<c:param name="personId"   value="${requestCommand.personId}" />
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1><fmt:message key="peoplebase.showConfirmCreatePersonForumModalWindow.creationQuestion"/></h1>
		
		<a id="yes" href="${ShowConfirmCreatePersonForumURL}" class="button_small"><fmt:message key="peoplebase.showConfirmCreatePersonForumModalWindow.yes"/></a>
	
		<a id="no" href="#" class="button_small"><fmt:message key="peoplebase.showConfirmCreatePersonForumModalWindow.no"/></a>			
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
	 			$j.ajax({ type:"POST", url:'${ShowConfirmCreatePersonForumURL}', data:null, async:false, success:function(html) { 
					$j("#ConfirmCreateForum").html(html);
				}});
				return false;
			});
		});
	</script>
