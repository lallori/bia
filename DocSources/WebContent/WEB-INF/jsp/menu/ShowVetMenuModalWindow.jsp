<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_FELLOWS">
		<div id="VetMenuDiv">
			<h1>What action do you want to perfom on this document?</h1>
			
			<div id="img_vetmenu"></div>
			
			<a id="SetReadtToBeVet" href="#"></a>
		
			<a id="VetThisDoc" href="#"></a>
				
			<a id="UnvetThisDoc" href="#"></a>
			
			<input id="close" type="submit" title="Close Entry Menu window" value=""/>
		</div>
		
		<script>
			$j(document).ready(function() {
				$j("#SetReadtToBeVet").click(function(){
					Modalbox.hide();
					return false;
				});
				$j("#VetThisDoc").click(function(){
					Modalbox.hide();
					return false;
				});
				$j("#UnvetThisDoc").click(function(){
					Modalbox.hide();
					return false;
				});
				$j("#close").click(function(){
					Modalbox.hide();
					return false;
				});
			});
		</script>
	</security:authorize>