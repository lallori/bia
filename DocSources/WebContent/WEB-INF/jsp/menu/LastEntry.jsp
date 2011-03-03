<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

				<div id="lastentry">
					<ul id="menulastentry">
						<li id="last_doc"><a id="ShowLastEntryDocument" href="<c:url value="/src/docbase/ShowLastEntryDocument.do"/>"></a></li>
						<li id="last_vol"><a id="ShowLastEntryVolume" href="<c:url value="/src/volbase/ShowLastEntryVolume.do"/>"></a></li>
						<li id="last_pers"><a id="ShowLastEntryPerson" href="<c:url value="/src/peoplebase/ShowLastEntryPerson.do"/>"></a></li>
						<li id="last_pla"><a id="ShowLastEntryPlace" href="<c:url value="/src/geobase/ShowLastEntryPlace.do"/>"></a></li>
					</ul>
				</div>
				<script type="text/javascript">
					$j(document).ready(function() {
						$j("#ShowLastEntryDocument").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
						$j("#ShowLastEntryVolume").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
						$j("#ShowLastEntryPerson").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
						$j("#ShowLastEntryPlace").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
					});
				</script>
		