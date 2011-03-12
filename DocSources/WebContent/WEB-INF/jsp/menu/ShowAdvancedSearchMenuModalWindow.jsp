<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS,ROLE_ONSITE_FELLOWS,ROLE_DISTANT_FELLOWS">
						<li><a id="entryMenu" href="<c:url value="/de/ShowEntryMenu.do"/>"></a>
							<ul>
								<li><a id="AddNewDocument" href="<c:url value="/de/docbase/CreateDocument.do"/>"><i>Add new Document</i></a></li>
								<li><a id="AddNewPerson" href="<c:url value="/de/peoplebase/CreatePerson.do"/>"><i>Add new Person</i></a></li>
								<li><a id="AddNewVolume" href="<c:url value="/de/volbase/CreateVolume.do"/>"><i>Add new Volume</i></a></li>
								<li><a id="AddNewPlace" href="<c:url value="/de/geobase/CreatePlace.do"/>"><i>Add new Place</i></a></li>
								<li><a id="DeleteThisItem" href="<c:url value="/de/volbase/DeleteThisItem.do"/>"><i>Delete this item</i></a></li>
							</ul> 
						</li>
						<li><a id="vetMenu" href="#"></a>
							<ul>
								<li><a href="#"><i>Set "Ready to ve vetted"</i></a></li>
								<li><a href="#"><i>Vet this document</i></a></li>
								<li><a href="#"><i>Un-vet this document</i></a></li>
							</ul>
						</li>
						<script type="text/javascript">
							$j(document).ready(function() {
								$j("#AddNewDocument").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
								$j("#AddNewPerson").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
								$j("#AddNewPlace").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
								$j("#AddNewVolume").click(function(){$j("#body_left").load($j(this).attr("href"));return false;});
							});
							$j(document).ready(function() {
								$j("#entryMenu").click( function() {															
									Modalbox.show($j(this).attr("href"), {title: "ENTRY MENU", width: 750, height: 170});return false;
								});	
								$j("#vetMenu").click(function() {
									Modalbox.show($j(this).attr("href"), {title: "VET MENU", width: 750,  height: 510});return false;
								});	
								$j("#advsearchMenu").click(function() {															
									Modalbox.show($j(this).attr("href"), {title: "ADVANCED SEARCH", width: 750, height: 380});return false;
								});							   	
								$j("#chronologyMenu").click(function() {															
									Modalbox.show($j(this).attr("href"), {title: "CHRONOLOGY", width: 750, height: 650});return false;
								});	
								$j("#myprofileMenu").click(function() {
									Modalbox.show($j(this).attr("href"), {title: "MY PROFILE", width: 750, height: 400});return false;}																	
								);	
								$j("#messagesMenu").click(function() {															
									Modalbox.show($j(this).attr("href"), {title: "MESSAGES", width: 750, height: 350});return false;}
								);
							});
						</script>
					</security:authorize>