<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
						<li><a href="#"><img src="<c:url value="/images/button_entrymenu2.png"/>" alt="Entry Menu" width="99" height="63" /></a>
							<ul>
								<li><a id="AddNewDocument" href="<c:url value="/de/docbase/CreateDocument.do"/>"><i>Add new Document</i></a></li>
								<li><a id="AddNewPerson" href="<c:url value="/de/peoplebase/CreatePerson.do"/>"><i>Add new Person</i></a></li>
								<li><a id="AddNewVolume" href="<c:url value="/de/volbase/CreateVolume.do"/>"><i>Add new Volume</i></a></li>
								<li><a id="AddNewPlace" href="<c:url value="/de/geobase/CreatePlace.do"/>"><i>Add new Place</i></a></li>
								<li><a id="DeleteThisItem" href="<c:url value="/de/volbase/DeleteThisItem.do"/>"><i>Delete this item</i></a></li>
							</ul> 
						</li>
						<li><a href="#"><img src="<c:url value="/images/button_vetmenu.png"/>" alt="Vet Menu" width="99" height="65"  /></a>
							<ul>
								<li><a href="#"><i>Set "Ready to ve vetted"</i></a></li>
								<li><a href="#"><i>Vet this document</i></a></li>
								<li><a href="#"><i>Un-vet this document</i></a></li>
							</ul>
						</li>
						<script type="text/javascript">
							$(document).ready(function() {
								$("#AddNewDocument").click(function(){$("#body_left").load($(this).attr("href"));return false;});
								$("#AddNewPerson").click(function(){$("#body_left").load($(this).attr("href"));return false;});
								$("#AddNewPlace").click(function(){$("#body_left").load($(this).attr("href"));return false;});
								$("#AddNewVolume").click(function(){$("#body_left").load($(this).attr("href"));return false;});
							});
						</script>
					</security:authorize>