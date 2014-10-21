<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditTitleOrOccupationURL" value="/de/peoplebase/EditTitleOrOccupation.do">
			<c:param name="titleOccId"   value="${titleOccsList.titleOccId}" />
		</c:url>
	</security:authorize>

	<div id="EditTitleOccupationDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.showTitleOrOccupation.titlesOccupations”/> </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<a id="EditTitleOccupation" href="${EditTitleOrOccupationURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
		</div>
		<div class="list">
			<div class="row">
				<div class="item37"><fmt:message key=“people.showTitleOrOccupation.titleOccupationName”/></div>
				<div class="value">${titleOccsList.titleOcc}</div>
			</div>
			<div class="row">
				<div class="item37"><fmt:message key=“people.showTitleOrOccupation.role”/></div>
				<div class="value">${titleOccsList.roleCat.roleCatMajor}/${titleOccsList.roleCat.roleCatMinor}</div>
			</div>
		</div>
	</div>


<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_COMMUNITY_USERS">
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#EditTitleOccupation").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditTitleOccupationDiv").load($j(this).attr("href"));
				return false;
			});
		});
	</script>
</security:authorize>