<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="EditUserProfileURL" value="/user/EditUserProfile.do" />

<div id="myProfile">
	<div id="userProfile">
		<h3>${userProfile.firstName} ${userProfile.lastName}</h3>
		
		<div id="bgImgUserProfile">
			<div id="imgUserProfile"></div>
			<!--  <img src="/DocSources/images/default_user.jpg" alt="default image" id="imgProfile"/>-->
			<a id="PersonalNotesButton" href="#">Personal Notes</a>
			<a id="EditUserProfile" href="${EditUserProfileURL}">Edit Profile</a>
			<a id="CloseUserProfile" href="#">Close Profile</a>
		</div>
		
		
		
		<div id="EditUserProfileDiv">
			<div class="list">
				<div class="row">
					<div class="item">Email</div>
					<div class="value">${userProfile.mail}</div>
				</div>
				<div class="row">
					<div class="item">Address</div>
					<div class="value">${userProfile.address}</div>
				</div>
				<div class="row">
					<div class="item">Country</div>
					<div class="value">${userProfile.country}</div>
				</div>
				<div class="row">
					<div class="item">Group</div>
					<div class="value"><security:authentication property="principal.significantRoleDescription"/></div>
				</div>
				<div class="row">
					<div class="item">Title</div>
					<div class="value">${userProfile.title}</div>
				</div>
				<div class="row">
					<div class="item">Organization</div>
					<div class="value">${userProfile.organization}</div>
				</div>
				<div class="row">
					<div class="item">Location</div>
					<div class="value">Italy, Florence</div>
				</div>
				<div class="row">
					<div class="item">Interests</div>
					<div class="value">${userProfile.interests}</div>
				</div>
				<div class="row">
					<div class="item">Resume</div>
					<div class="value">no.</div>					
				</div>
			</div>
			
		<div id="helpProfile">
			<h1>HELP SYSTEM</h1>
			<a href="#">User Manual (HTML version)</a><br />
			<a href="#">Download Manual (PDF version)</a><br />
			<a href="#">Help Videos</a>
		</div>
	
		<div id="aboutmapProfile">
			<h1>ABOUT MAP</h1>
			<a href="#">News from MAP</a><br />
			<a href="#">MAP website</a><br />
			<a href="#">MAP online courses</a><br />
			<a href="#">MAP Forums</a>
		</div>
	</div>
</div>
	
	
	
		
	
	
	
	<!--  <a href="http://courses.medici.org/" target="_blank"><img src="/DocSources/images/button_courses.jpg" alt="MAP courses" id="coursesProfile"/></a>-->

</div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#CloseUserProfile").click(function(){
					Modalbox.hide();
					return false;
				});

				$j("#EditUserProfile").click(function(){
					$j("#EditUserProfileDiv").load($j(this).attr("href"));
					return false;
				});

				$j("#EditTitleProfile").click(function(){$j("#EditTitleProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditOrgProfile").click(function(){$j("#EditOrgProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditLocationProfile").click(function(){$j("#EditLocationProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditInterestsProfile").click(function(){$j("#EditInterestsProfileDiv").load($j(this).attr("href"));return false;});
				$j("#EditResumeProfile").click(function(){$j("#EditResumeProfileDiv").load($j(this).attr("href"));return false;});
			});
		</script>
