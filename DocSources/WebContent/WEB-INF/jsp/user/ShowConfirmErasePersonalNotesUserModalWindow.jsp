<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ErasePersonalNotesUserURL" value="/user/ErasePersonalNotesUser.do"/>

	<c:url var="ShowPersonalNotesUserURL" value="/user/ShowPersonalNotesUser.do" />

	<div id="erasePersonalNotesWindow"> 
		<p style="text-align:center">Are your sure you want to erase your Personal Notes?</p>
	    <div class="yesNoButtons">
	        <a class="yes" href="${ErasePersonalNotesUserURL}" title="Yes, erase my Personal Notes">Yes</a>
	        <a class="no" href="${ShowPersonalNotesUserURL}" title="No, go back to my Personal Notes">No</a>
	    </div>
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			$j(".yes").click(function() {
				Modalbox.show($j(this).attr("href"), {title: "ERASE PERSONAL NOTES", width: 300, height: 80});
				return false;
			});
			$j(".no").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "PERSONAL NOTES", width: 750, height: 415});
				return false;
			});
		});
	</script>