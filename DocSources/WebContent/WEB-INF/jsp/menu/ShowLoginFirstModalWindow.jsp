<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="LoginURL" value="/LoginUser.do" />

	<div style="width: auto; min-height: 0px; height: 100px;" id="logInFirstWindow" title="LOG IN FIRST" class="ui-dialog-content ui-widget-content">
		<div><fmt:message key="menu.showLoginFirstModal.loginMessage"/></div>
		<br />
		<input type="button" id="okButton" value="<fmt:message key="menu.showLoginFirstModal.button.yes"/>" class="button_small" />
		<input type="button" id="closeButton" value="<fmt:message key="menu.showLoginFirstModal.button.no"/>" class="button_small" /> 
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			var close = $j("#DialogLoginFirst").parent();
			//$j("#okButton").css("cursor", "pointer");
			//$j("#closeButton").css("cursor", "pointer");
			
			$j("#okButton").click(function(e) {
				window.location = '${LoginURL}';
	            return true;
			});
			
			$j("#closeButton").click(function() {
				$j(close).find(".ui-dialog-titlebar-close").trigger('click');
				return false;
			});
		});
	</script>