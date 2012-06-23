<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowForumURL" value="/community/ShowForum.do">
		<c:param name="id"          value="${forum.id}" />
		<c:param name="CompleteDOM" value="true"/>
	</c:url>
	
	<div id="ConfirmCreateForum">
		<h1>Forum has been created. Please wait to redirect...</h1>
		<a id="open" href="${ShowForumURL}">OPEN FORUM</a>
	
		<input id="close" type="submit" title="Close Comments Menu window" value="Close"/>
	</div>

	<script>
		$j(document).ready(function() {
			$j("#close").click(function(){
				Modalbox.hide();
				return false;
			});

			$j("#open").click(function() {
				$j(this).open({scrollbars: "yes"});
				return false;
			});
		});
	</script>
