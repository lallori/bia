<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_FELLOWS,ROLE_DIGITIZATION_TECHNICIANS">
						<li class="entryMenu">
							<a id="entryMenu" href="<c:url value="/de/ShowEntryMenu.do"/>"></a>
						</li>
						<script type="text/javascript">
							$j(document).ready(function() {
								$j("#entryMenu").click( function() {															
									Modalbox.show($j(this).attr("href"), {title: "<fmt:message key="menu.dataEntry.open"/>", width: 750, height: 225});return false;
								});	
							});
						</script>
					</security:authorize>