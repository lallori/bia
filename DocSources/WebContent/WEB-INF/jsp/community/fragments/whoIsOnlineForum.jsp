<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

					<c:url var="WhoIsOnlineForumURL" value="/community/WhoIsOnlineForum.json" />
					
					<div id="whoIsOnlineDiv">
						<h1>WHO IS ONLINE</h1>
						<p>In total there is <span id="userOnline">${whoIsOnlineHashMap['onlineUsers'].size() + whoIsOnlineHashMap['guestUsers'] }</span> user online: <span id="userRegistered">${whoIsOnlineHashMap['onlineUsers'].size()}</span> registered and <span id="userGuest">${whoIsOnlineHashMap['guestUsers'] }</span> guest (based on users active over the past 30 minutes)</p>
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