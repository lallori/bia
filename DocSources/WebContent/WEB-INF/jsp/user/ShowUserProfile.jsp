<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="myProfile">
	<div id="userProfile">
		<h1>User Profile</h1>
		<h3>${userProfile.firstName} ${userProfile.lastName}</h3>
		
		<br/>
	
		<img src="/DocSources/images/default_user.jpg" alt="default image" id="imgProfile"/>
	
		<div id="EditGroupProfileDiv">
			<b>Group:</b> <security:authentication property="principal.significantRoleDescription"/>
		</div>
		
		<div id="EditMailProfileDiv">
			<b>Mail:</b> ${userProfile.mail} <a id="EditTitleProfile" href="/DocSources/EditTitleProfile.html">edit</a>
		</div>

		<div id="EditTitleProfileDiv">
			<b>Title:</b> ${userProfile.title} <a id="EditTitleProfile" href="/DocSources/EditTitleProfile.html">edit</a>
		</div>
		
		<div id="EditAddressProfileDiv">
			<b>Address:</b> ${userProfile.address} <a id="EditTitleProfile" href="/DocSources/EditTitleProfile.html">edit</a>
		</div>

		<div id="EditOrgProfileDiv">
			<b>Organization:</b> ${userProfile.organization} <a id="EditOrgProfile" href="/DocSources/EditOrgProfile.html">edit</a>
		</div>
		
		<div id="EditLocationProfileDiv">
			<b>Location:</b> Italy, Florence <a id="EditLocationProfile" href="/DocSources/EditLocationProfile.html">edit</a>
		</div>
		
		<div id="EditInterestsProfileDiv">
			<b>Interests:</b> ${userProfile.interests} <a id="EditInterestsProfile" href="/DocSources/EditInterestsProfile.html">edit</a>
		</div>
		
		<div id="EditResumeProfileDiv">
			<b>Resume:</b> no. <a id="EditResumeProfile" href="/DocSources/EditResumeProfile.html">add/modify resume</a>
		</div>
	</div>
	
	<div id="helpProfile">
		<h1>Help System</h1>

		<ul>
			<li><a href="#">User Manual (HTML version)</a></li>
			<li><a href="#">Download Manual (PDF version)</a></li>
			<li><a href="#">Help Videos</a></li>
		</ul>
	</div>
	
	<a href="http://courses.medici.org/" target="_blank"><img src="/DocSources/images/button_courses.jpg" alt="MAP courses" id="coursesProfile"/></a>

</div>

<div id="modalBoxCloseDivProfile"><input value="Close" class="modalBox-close" onClick="Modalbox.hide(); return false;" type="submit"><br /><span>(or click the overlay)</span></div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#EditTitleProfile").click(function(){$j("#EditTitleProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditOrgProfile").click(function(){$j("#EditOrgProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditLocationProfile").click(function(){$j("#EditLocationProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditInterestsProfile").click(function(){$j("#EditInterestsProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditResumeProfile").click(function(){$j("#EditResumeProfileDiv").load($j(this).attr("href"));return false;});
			});
		</script>
