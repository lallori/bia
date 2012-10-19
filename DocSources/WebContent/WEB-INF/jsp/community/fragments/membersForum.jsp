<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div id="membersDiv">
	<img src="<c:url value="/images/forum/img_members.png"/>" alt="members" />
	<a href="<c:url value="/community/ShowMembersForum.do"/>?letter=All" id="members">Members</a>
</div>

<script type="text/javascript">
$j(document).ready(function() {
	$j("#members").click(function(){
		$j("#main").load($j(this).attr('href'));
		return false;
	});
});
</script>

				