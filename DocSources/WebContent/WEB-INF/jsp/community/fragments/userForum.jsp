<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

 				<div id="userDiv">
					<img src="<c:url value="/images/forum/img_user.png"/>" alt="User" />
					<a href="<c:url value="/community/ShowUserProfileForum.do"/>" id="profile">User Profile</a>
					<a href="<c:url value="/community/ShowMessagesByCategory.do"/>?userMessageCategory=INBOX" id="userMessages">(<span>0</span> new messages)</a>
					<a href="<c:url value="/community/ShowMyForumPost.do"/>" id="viewYourPosts">View your posts</a>
					
					<input type="hidden" value="" id="prevUrl" />
				</div>

				<script type="text/javascript">
					$j(document).ready(function() {
						$j('#profile').click(function (){
							$j("#main").load($j(this).attr("href"));
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
					});
				</script>
