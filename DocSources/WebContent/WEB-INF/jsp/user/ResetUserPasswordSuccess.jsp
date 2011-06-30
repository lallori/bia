<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<div id="reset">
			<h1>RESET PASSWORD</h1>
			<p>In 5 seconds you will be redirected to the home page.<br />If it won't <a id="HomeLink" href="<c:url value="/"/>">click here.</a></p>
		</div>
		<script type="text/javascript">
			$(window).load( function() { 
				$(window).delay(5000);
				$(location).attr('href', $("HomeLink").attr("href"));
			});
		</script>

