<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditGeneralPropertiesURL" value="/admin/EditGeneralProperties.do" />

<div id="EditGeneralPropertiesDiv" class="background">
	<div class="title">
		<h5>GENERAL PROPERTIES</h5>
		<a id="EditGeneralProperties" class="editButton" href="${EditGeneralPropertiesURL}" title="Edit General Properties"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Website domain</div> 
			<div class="value60">${org.medici.bia.common.util.JSTLFunctions.:getApplicationProperty("website.domain");
})}</div> 
		</div>
        <div class="row">
			<div class="item">Images Path</div> 
			<div class="value60">${()
		throws java.io.IOException, javax.servlet.ServletException, javax.servlet.jsp.JspException {
javax.servlet.jsp.PageContext pageContext = null;
java.util.Map<String, String> param = null;
java.util.Map<String, String[]> paramValues = null;
java.util.Map<String, String> header = null;
java.util.Map<String, String[]> headerValues = null;
java.util.Map<String, javax.servlet.http.Cookie> cookie = null;
java.util.Map<String, String> initParam = null;
java.util.Map<String, Object> pageScope = null;
java.util.Map<String, Object> requestScope = null;
java.util.Map<String, Object> sessionScope = null;
java.util.Map<String, Object> applicationScope = null;
return ""+(public String _elExpressionorg.medici.bia.common.util.JSTLFunctions.:getApplicationProperty('iipimage.image.path')}</div> 
		</div>
	</div>
</div>
<br />

<script type="text/javascript">
	$j(document).ready(function(){
		$j("#EditEmailSystemProperties").css('visibility', 'visible');
		$j("#EditForumProperties").css('visibility', 'visible');
		$j("#EditGeneralProperties").css('visibility', 'visible');
		$j("#EditRecaptchaProperties").css('visibility', 'visible');
		$j("#EditIipImageProperties").css('visibility', 'visible');
		$j("#EditSchedoneProperties").css('visibility', 'visible');
		$j("#EditUserProperties").css('visibility', 'visible');

		$j("#EditGeneralProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditGeneralPropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	});
</script>

</security:authorize>