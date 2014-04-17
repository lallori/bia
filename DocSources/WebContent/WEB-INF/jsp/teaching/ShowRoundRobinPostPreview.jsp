<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="postTablePreviewMsg" class="postTable">
		<div class="post">
			<div class="title">
				<h2>${extendedPost.post.subject}</h2>
			</div>
			<table class="by" style="width: 100%;">
				<tr>
					<td width="40%"><p>by <a href="#" class="link">${extendedPost.post.user.account}</a>&#xbb <span class="date"><fmt:formatDate value="${extendedPost.post.lastUpdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p></td>
					<td width="20%">
						<c:choose>
							<c:when test="${extendedPost.post.updater == null || extendedPost.post.user.account == extendedPost.post.updater.account}">
							</c:when>
							<c:otherwise>
								<a title='<fmt:message key="community.forum.topic.editedByAdministrator" />' href="#" class="linkUpdater">${extendedPost.post.updater.account}</a>
							</c:otherwise>
						</c:choose>
					</td>
					<td width="40%">
						<div class="postLocation" style="margin-left: 10px;">
							<div class="folioDetailsContainer">
								<c:choose>
									<c:when test="${extendedPost.getVolumeFragment() != null}">
										Volume&nbsp;<span class="volumeFragment">${extendedPost.getVolumeFragment()}</span>
										<c:if test="${extendedPost.getInsertFragment() != null}">
											-&nbsp;Insert&nbsp;<span class="insertFragment">${extendedPost.getInsertFragment()}</span>
										</c:if>
										-&nbsp;Folio&nbsp;<span class="folioFragment">${extendedPost.getFolioFragment()}</span>
									</c:when>
									<c:otherwise>
										No folio details
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<div>${extendedPost.post.text}</div>
		</div>
		<div class="postProfile">
			<ul>
				<li>
					<c:if test="${extendedPost.post.user.portrait}">
						<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
							<c:param name="account" value="${extendedPost.post.user.account}" />
							<c:param name="time" value="${time}" />
						</c:url>
						<img src="${ShowPortraitUserURL}" class="avatar"/>
					</c:if>
					<c:if test="${!extendedPost.post.user.portrait}">
						<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
					</c:if>
					<a href="#" id="userName" class="link">${extendedPost.post.user.account}</a>
				</li>
				<li>Posts: <span>${extendedPost.post.user.forumNumberOfPost}</span></li>
				<li>Joined: <span>${extendedPost.post.user.forumJoinedDate}</span></li>
			</ul>
		</div>
		<div class="online visible"></div>
	</div>
    
    <a id="closePreview" href="#" class="buttonMedium button_medium">Close Preview</a>

    <script>
    	$j(document).ready(function() {
    		$j("#closePreview").click(function() {
    			$j("#postTablePreview").hide();
    			$j("#postTablePreview").html("");
    			$j(this).unbind();
    			return false;
    		});
    		
    		//delayed scrollTo preview
    		setTimeout(function() {
    			$j("#editPostContainer").scrollTo("#postTablePreviewMsg");
    		},200);
    		
    	});
    </script>