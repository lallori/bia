<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowForumURL" value="/community/ShowForum.do?completeDOM=true"/>

				<li class="forumsMenu"><a id="forumsMenu" href="${ShowForumURL}" target="_blank"></a></li>
				<script type="text/javascript">
					$j(document).ready(function() {														
							$j("#forumsMenu").open({scrollbars: "yes"});
							return false;
						});							   	
					});
				</script>						
