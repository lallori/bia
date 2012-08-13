<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditTitleOrOccupationURL" value="/de/peoplebase/EditTitleOrOccupation.do">
			<c:param name="titleOccId"   value="${titleOccList.titleOccId}" />
		</c:url>
	</security:authorize>

	<div id="EditTitleOrOccupationDiv" class="background">
		<div class="title">
			<h5>TITLES / OCCUPATIONS </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
				<a id="EditTitleOrOccupation" href="${EditTitleOrOccupationURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				Title/Occupation Name
				<div class="value60">titleOccList.titleOcc<br>
			</div>
			<div class="row">
				Role
				<div class="value60">titleOccList.<br>
			</div>
		</div>
	</div>


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditTitlesOrOccupationsPerson").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTitleOrOccupationDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>