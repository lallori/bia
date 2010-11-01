<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

		<base href="${pageContext.request.contextPath}"></base>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>the MEDICI ARCHIVE PROJECT</title>
		<link rel="shortcut icon" href="<c:url value="images/favicon_medici.jpg"/>" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/admmenu.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mainmenu.css"/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/style.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/style_template.css"/>"/>

		<!--[if lte IE 7]>
		<style type="text/css">
		html .jquerycssmenu{height: 1%;} /*Holly Hack for IE7 and below*/
		</style>
		<![endif]-->
		
		<script type="text/javascript" src="<c:url value="/scripts/jquery-1.4.2.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquery.form-2.47.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquerycssmenuADM.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/scripts/jquerycssmenuMAIN.js"/>"></script>