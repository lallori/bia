<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

 				<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
					<c:param name="forumId" value="1"/>
				</c:url>
 				
 				<c:url var="CheckNewMessagesURL" value="/community/CheckNewMessages.json"/>
 				
 				<div id="userDiv">
 					<security:authorize ifAnyGranted="ROLE_COMMUNITY_USERS">
					<img src="<c:url value="/images/forum/img_user.png"/>" alt="User" />
					<a href="<c:url value="/community/ShowUserProfileForum.do"/>" id="profile"><fmt:message key="community.fragments.userForum.userProfile"/></a>
					<a href="<c:url value="/community/ShowMessagesByCategory.do"/>?userMessageCategory=INBOX" id="userMessages"><fmt:message key="community.fragments.userForum.open"/><span id="messagesCount"><fmt:message key="community.fragments.userForum.zero"/></span> <fmt:message key="community.fragments.userForum.newMessages"/></a>
					<a href="<c:url value="/community/ShowMyForumPost.do"/>" id="viewYourPosts">View your posts</a>
					</security:authorize>
					<input type="hidden" value="" id="prevUrl" />
				</div>

				<script type="text/javascript">
					$j(document).ready(function() {
						<security:authorize ifAnyGranted="ROLE_COMMUNITY_USERS">
						$j('#profile').click(function (){
							$j("#main").load($j(this).attr("href"));
							$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
			    				$j("#chronologyDiv").html(json.chronology);
								$j("#selectForum").append(json.selectChronology);
								$j("#chronologyDiv .arrowForum").css('display','');
								$j("#chronologyDiv .forum").css('display','');
			    			}});
							return false;
						});

						$j('#userMessages').click(function (){
							$j("#main").load($j(this).attr("href"));
							return false;
						});

						$j('#viewYourPosts').click(function (){
							$j("#main").load($j(this).attr("href"));
							return false;
						});
						
						setInterval(function() {
							$j.ajax({ type:"GET", url:"${CheckNewMessagesURL}", async:false, success:function(data) {
								if(data.newMessages == 'true'){
									$j("#messagesCount").html(data.numberOfNewMessages);
								}else{
									$j("#messagesCount").html('0');
								}
							}});
						}, 60000); //every 1 minute...
						
						</security:authorize>
					});
				</script>
