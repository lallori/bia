<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

					<c:url var="WhoIsOnlineForumURL" value="/community/WhoIsOnlineForum.json" />
					
					<div id="whoIsOnlineDiv">
						<h1><fmt:message key="community.fragments.whoIsOnlineForum.wHoIsOnline"/></h1>
						<p><fmt:message key="community.fragments.whoIsOnlineForum.inTotalThere"/> <span id="userOnline">${whoIsOnlineHashMap['onlineUsers'].size() + whoIsOnlineHashMap['guestUsers'] }</span> <fmt:message key="community.fragments.whoIsOnlineForum.userOnline"/> <span id="userRegistered">${whoIsOnlineHashMap['onlineUsers'].size()}</span> <fmt:message key="community.fragments.whoIsOnlineForum.registeredAnd"/> <span id="userGuest">${whoIsOnlineHashMap['guestUsers'] }</span> <fmt:message key="community.fragments.whoIsOnlineForum.guest"/></p>
					</div>

					<script type="text/javascript">
					$j(document).ready(function() {
						$j.ajax({ url: '${WhoIsOnlineForumURL}', cache: false, success:function(json) {
							$j("#userOnline").text(json.countOnlineUsers + json.countGuestUsers);
							$j("#userRegistered").text(json.countOnlineUsers);
							$j("#userGuest").text(json.countGuestUsers);
						}});
					});
					</script>