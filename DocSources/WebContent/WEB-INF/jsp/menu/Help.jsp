<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%--<c:url var="ChoiceAdvancedSearchURL" value="/src/ChoiceAdvancedSearch.do"/> --%>

				<li class="helpMenu"><a id="helpMenu" href="#"></a></li>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#helpMenu").click(function() {															
							Modalbox.show($j(this).attr("href"), {title: "HELP", width: 750, height: 325});return false;
						});							   	
					});
				</script>						
