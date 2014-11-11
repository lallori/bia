<%@page import="org.medici.bia.common.util.ForumUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowMembersForumURL" value="/community/ShowMembersForum.do"/>

<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
	<c:param name="forumId" value="1"/>
</c:url>

<div id="findMember">
	<ul class="abc">
    	<li><a href="#" id="findMember"><fmt:message key="community.showMembersForum.findAMember"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=All"><fmt:message key="community.showMembersForum.all"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=A"><fmt:message key="community.showMembersForum.a"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=B"><fmt:message key="community.showMembersForum.b"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=C"><fmt:message key="community.showMembersForum.c"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=D"><fmt:message key="community.showMembersForum.d"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=E"><fmt:message key="community.showMembersForum.e"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=F"><fmt:message key="community.showMembersForum.f"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=G"><fmt:message key="community.showMembersForum.g"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=H"><fmt:message key="community.showMembersForum.h"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=I"><fmt:message key="community.showMembersForum.i"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=J"><fmt:message key="community.showMembersForum.j"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=K"><fmt:message key="community.showMembersForum.k"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=L"><fmt:message key="community.showMembersForum.l"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=M"><fmt:message key="community.showMembersForum.m"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=N"><fmt:message key="community.showMembersForum.n"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=O"><fmt:message key="community.showMembersForum.o"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=P"><fmt:message key="community.showMembersForum.p"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Q"><fmt:message key="community.showMembersForum.q"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=R"><fmt:message key="community.showMembersForum.r"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=S"><fmt:message key="community.showMembersForum.s"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=T"><fmt:message key="community.showMembersForum.t"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=U"><fmt:message key="community.showMembersForum.u"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=V"><fmt:message key="community.showMembersForum.v"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=W"><fmt:message key="community.showMembersForum.w"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=X"><fmt:message key="community.showMembersForum.x"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Y"><fmt:message key="community.showMembersForum.y"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Z"><fmt:message key="community.showMembersForum.z"/></a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Other"><fmt:message key="community.showMembersForum.other"/></a></li>
    </ul>
</div>
<div id="membersTable">
	<div class="list">
        <div class="rowFirst">
            <div class="one"><fmt:message key="community.showMembersForum.uSername"/></div>
            <div class="two"><fmt:message key="community.showMembersForum.pOsts"/></div>
            <div class="three"><fmt:message key="community.showMembersForum.wEbsite"/></div>
            <div class="four"><fmt:message key="community.showMembersForum.jOined"/></div>
            <div class="five"><fmt:message key="community.showMembersForum.lAstActive"/></div>
        </div>
        <c:forEach items="${membersPage.list}" var="currentMember" varStatus="status">
        <div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">
        	<div class="one">
        		<a class="username" href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${currentMember.account}">${currentMember.account} - ${currentMemebr.firstName} ${currentMember.lastName}</a>
        	</div>
        	<div class="two">${currentMember.forumNumberOfPost}</div>
        	<div class="three"></div>
        	<div class="four">${currentMember.forumJoinedDate}</div>
        	<div class="five">${currentMember.lastLoginDate}</div>
        </div>
        </c:forEach>
    </div>
</div>

<div id="findMemberModal" title="Find member" style="display:none"> 
	<form id="findMemberForm" method="post">
		<label for="member" id="memberLabel"><fmt:message key="community.showMembersForum.findMember"/></label>
		<input id="member" name="member" class="input_24c" type="text" value=""/>	
	</form>	
</div>



<script>
	$j(document).ready(function() {
		$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
			$j("#chronologyDiv").html(json.chronology);
			$j("#selectForum").append(json.selectChronology);
			$j("#chronologyDiv .arrowForum").css('display','');
			$j("#chronologyDiv .forum").css('display','');
			if($j("#prevUrl").val() != "")
				$j("#chronologyDiv").append("<span class='arrowForum'>&rarr; <a href='" + $j("#prevUrl").val() + "' class='forum'>Go Back</a></span>");
			else
				$j("#goBackTo").css("display","none");
			return false;
		}});
		
		$j(".abc > li a.letter").click(function(){
			$j("#main").load($j(this).attr('href'));
			return false;
		});
		
		$j(".username").die();
		$j(".username").live('click', function(){
			$j("#main").load($j(this).attr('href'));
			return false;
		});
		
// 		$j("#findMemberForm").submit(function(){
// 			$j("#findMemberModal").dialog('close');
// 			var formSubmitUrl = '${ShowMembersForumURL}' + '?' + $j(this).serialize();
// 			$j("#main").load(formSubmitUrl);
// 			return false;
// 		});
		
		$j("#findMemberModal").dialog({
			  autoOpen : false,
			  modal: true,
			  resizable: false,
			  scrollable: false,
			  width: 310,
			  height: 130,
			  buttons: {
				  Find: function() {
					  $j(this).dialog("close");
					  var formSubmitUrl = '${ShowMembersForumURL}' + '?' + $j("#findMemberForm").serialize();
					  $j("#main").load(formSubmitUrl);
					  return false;
				  }
			  }
		  });
		
		$j("#findMember").click(function(){
			$j("#findMemberModal").dialog('open');
			return false;
		});
		
		$j("#findMemberForm").submit(function(){
			$j("#findMemberModal").dialog("close");
			var formSubmitUrl = '${ShowMembersForumURL}' + '?' + $j("#findMemberForm").serialize();
			$j("#main").load(formSubmitUrl);
			return false;
		});
		
		$j('.forum').die();
		$j('.forum').live('click', function() {
			$j("#main").load($j(this).attr("href"));
			return false;
		});
	});
</script>
