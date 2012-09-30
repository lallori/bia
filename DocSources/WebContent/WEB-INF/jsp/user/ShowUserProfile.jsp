<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="EditUserProfileURL" value="/user/EditUserProfile.do" />

<c:url var="EditPasswordUserURL" value="/user/EditPasswordUser.do" />

<c:url var="ShowPersonalNotesUserURL" value="/user/ShowPersonalNotesUser.do" />

<div id="myProfile">
	<div id="userProfile">
		<h3>${userProfile.firstName} ${userProfile.lastName}</h3>
		
		<div id="bgImgUserProfile">
			<div id="imgUserProfile"></div>
			<a id="ChangePassword" href="${EditPasswordUserURL}">Change Password</a>
			<a id="PersonalNotesButton" href="${ShowPersonalNotesUserURL}">Personal Notes</a>
			<a id="EditUserProfile" href="${EditUserProfileURL}">Edit Profile</a>
			<a id="CloseUserProfile" href="#">Close Profile</a>
		</div>
		
		<div id="EditUserProfileDiv">
			<div class="list">
				<div class="row">
					<div class="firstItem">Email</div>
					<div class="firstValue">${userProfile.mail}</div>
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
					<div class="value">${userProfile.city}</div>
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
	</div>
</div>

</div>
	
		<script type="text/javascript">
			$j(document).ready(function() {
				$j("#CloseUserProfile").click(function(){
					Modalbox.hide();
					return false;
				});

				$j("#ChangePassword").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "CHANGE PASSWORD", width: 440, height: 207});
					return false;
				});

				$j("#PersonalNotesButton").click(function(){
					Modalbox.show($j(this).attr("href"), {title: "PERSONAL NOTES", width: 750, height: 415});
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
