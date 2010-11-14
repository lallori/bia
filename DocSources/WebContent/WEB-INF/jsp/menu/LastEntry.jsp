<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="lastentry">
					<ul id="menulastentry">
						<li id="last_doc"><a id="ShowLastEntryDocument" href="<c:url value="/src/docbase/ShowLastEntryDocument.do"/>"><img src="<c:url value="/images/Ldoc_entry.png"/>" alt="Last Document Entry" width="100" height="85"  /></a></li>
						<li id="last_vol"><a id="ShowLastEntryVolume" href="<c:url value="/src/volbase/ShowLastEntryVolume.do"/>"><img src="<c:url value="/images/Lvol_entry.png"/>" alt="Last Volume Entry" width="100" height="85" /></a></li>
						<li id="last_pers"><a id="ShowLastEntryPerson" href="<c:url value="/src/peoplebase/ShowLastEntryPerson.do"/>"><img src="<c:url value="/images/Lpers_entry.png"/>" alt="Last Person Entry" width="100" height="85" /></a></li>
						<li id="last_pla"><a id="ShowLastEntryPlace" href="<c:url value="/src/geobase/ShowLastEntryPlace.do"/>"><img src="<c:url value="/images/Lpla_entry.png"/>" alt="Last Place Entry" width="100" height="85" /></a></li>
					</ul>
				</div>
				<script type="text/javascript">
					$(document).ready(function() {
						$("#ShowLastEntryDocument").click(function(){$("#body_left").load($(this).attr("href"));return false;});
						$("#ShowLastEntryVolume").click(function(){$("#body_left").load($(this).attr("href"));return false;});
						$("#ShowLastEntryPerson").click(function(){$("#body_left").load($(this).attr("href"));return false;});
						$("#ShowLastEntryPlace").click(function(){$("#body_left").load($(this).attr("href"));return false;});
					});
				</script>
		