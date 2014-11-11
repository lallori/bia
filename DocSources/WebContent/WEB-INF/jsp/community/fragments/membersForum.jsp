<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div id="membersDiv">
	<img src="<c:url value="/images/forum/img_members.png"/>" alt="members" />
	<a href="<c:url value="/community/ShowMembersForum.do"/>?letter=All" id="members"><fmt:message key="community.fragments.membersForum.members"/></a>
</div>

<script type="text/javascript">
$j(document).ready(function() {
	$j("#members").die();
	$j("#members").live('click', function(){
		$j("#main").load($j(this).attr('href'));
		alert($j("#messagesTable").length);
		if($j(".paginateActive").length > 0 && $j("#messagesTable").length == 0)
			$j("#prevUrl").val($j(".paginateActive").attr('href'));
		else
			$j("#prevUrl").val("${ShowForumURL}");
		return false;
	});
});
</script>

				