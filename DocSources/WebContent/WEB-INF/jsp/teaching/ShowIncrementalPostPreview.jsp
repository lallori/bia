<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="postTablePreviewMsg" class="postTable">
		<div class="postProfile">
			<div class="avatarContainer">
				<c:choose>
					<c:when test="${extendedPost.post.user.portrait}">
						<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
							<c:param name="account" value="${extendedPost.post.user.account}" />
							<c:param name="time" value="${time}" />
						</c:url>
						<img class="avatar" src="${ShowPortraitUserURL}"/>
					</c:when>
					<c:otherwise>
						<img class="avatar" src="<c:url value='/images/1024/img_user.png'/>" alt="User Portrait"/>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="titleContainer">
				<h2>${extendedPost.post.subject}</h2>
				<p>by <a href="#" class="link">${extendedPost.post.user.account}</a>&#xbb <span class="date"><fmt:formatDate value="${extendedPost.post.lastUpdate}" pattern="yy-MM-dd HH:mm:ss" /></span></p></td>
				<c:if test="${extendedPost.post.updater != null && extendedPost.post.user.account != extendedPost.post.updater.account}">
					<p class="administratorEdit" title='<fmt:message key="community.forum.topic.editedByAdministrator" />'>
						also by
						<a href="#" class="link" title='<fmt:message key="community.forum.topic.editedByAdministrator" />'>${extendedPost.post.updater.account}</a>
					</p>
				</c:if>
			</div>
			<div class="volumeContainer">
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
		<div class="post">
			<div class="transcriptionContainer">
				<div class="transcritpionCaption">Transcription:</div>
				<c:choose>
					<c:when test="${not empty extendedPost.transcription}">
						<div id="transcription_${extendedPost.post.postId}" class="transcription">${extendedPost.transcription}</div>
					</c:when>
					<c:otherwise>
						<div id="postTranscription_${extendedPost.post.postId}" class="transcription">{ Empty transcription }</div>
					</c:otherwise>
				</c:choose>
			</div>
			<c:if test="${not empty extendedPost.post.text}">
				<div class="postText">${extendedPost.post.text}</div>
			</c:if>
		</div>
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