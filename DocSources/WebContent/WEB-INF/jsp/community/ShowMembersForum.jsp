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
    	<li><a href="#" id="findMember">Find a member</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=All">All</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=A">A</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=B">B</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=C">C</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=D">D</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=E">E</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=F">F</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=G">G</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=H">H</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=I">I</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=J">J</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=K">K</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=L">L</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=M">M</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=N">N</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=O">O</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=P">P</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Q">Q</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=R">R</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=S">S</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=T">T</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=U">U</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=V">V</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=W">W</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=X">X</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Y">Y</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Z">Z</a></li>
        <li><a class="letter" href="${ShowMembersForumURL}?letter=Other">Other</a></li>
    </ul>
</div>
<div id="membersTable">
	<div class="list">
        <div class="rowFirst">
            <div class="one">USERNAME</div>
            <div class="two">POSTS</div>
            <div class="three">WEBSITE, LOCATION</div>
            <div class="four">JOINED</div>
            <div class="five">LAST ACTIVE</div>
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
		<label for="member" id="memberLabel">Find member:</label>
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
	});
</script>
