<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
<c:url var="EditForumPropertiesURL" value="/admin/EditForumProperties.do" />

<div id="EditForumPropertiesDiv" class="background">
	<div class="title">
		<h5>FORUM PROPERTIES</h5>
		<a id="EditGeneralProperties" class="editButton" href="${EditGeneralPropertiesURL}" title="Edit General Properties"></a><span id="loading"/>
	</div>
	<div class="list">
		<div class="row">
			<div class="item">Unique identifier for Document forum :</div> 
			<div class="value60">${getApplicationProperty:getApplicationProperty("forum.identifier.document");
})}</div> 
		</div>
        <div class="row">
			<div class="item">Unique identifier for Place forum :</div> 
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
return ""+(org.medici.bia.common.util.JSTLFunctions.:getApplicationProperty("forum.identifier.place");
})}</div> 
		</div>
        <div class="row">
			<div class="item">Unique identifier for People forum :</div> 
			<div class="value60">${public String _elExpression20728org.medici.bia.common.util.JSTLFunctions.:getApplicationProperty("forum.identifier.people")}</div> 
		</div>
        <div class="row">
			<div class="item">Unique identifier for Volume forum :</div> 
			<div class="value60">${20729public String _elExpressionorg.medici.bia.common.util.JSTLFunctions.:getApplicationProperty('forum.identifier.volume')}</div> 
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

		$j("#EditForumProperties").click(function(){
			$j(this).next().css('visibility', 'visible');
			$j("#EditForumPropertiesDiv").load($j(this).attr("href"));
			return false;
		});
	});
</script>

</security:authorize>