<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<style type="text/css">
		#personTitle {
			margin:10px 0 20px 5px;
		}
    </style>
	
	<script type="text/javascript" src="<c:url value="/scripts/mview/jquery-ui-1.8.9.custom.min.js"/>"></script>
	
    <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="true" />
	</security:authorize>
	
    <security:authorize ifNotGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS, ROLE_FORMER_FELLOWS, ROLE_DIGITIZATION_TECHNICIANS, ROLE_COMMUNITY_USERS">
		<c:set var="logged" value="false" />
	</security:authorize>
	
	<c:url var="ShowPersonURL" value="/src/peoplebase/ShowPerson.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	<c:url var="HomeURL" value="/Home.do">
		<c:param name="personId" value="${person.personId}" />
	</c:url>
	<c:url var="ShowLoginFirstDialogURL" value="/menu/ShowLoginFirstModalWindow.do" />
	
    <div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_EN/all.js#xfbml=1";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>
	
	<a href="${ShowPersonURL}" id="moreInfoButton" class="button_medium" title="Browse The Medici Archive Project Database" target="_blank">More info</a>
	
	<ul id="network">
       <div class="fb-like" data-send="false" data-layout="button_count" data-width="500" data-show-faces="false" style="display:inline;"></div>
	   <a href="https://twitter.com/share" class="twitter-share-button" data-text=" "><fmt:message key=“people.sharePerson.tweet”/></a>
	   <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
	   <div class="g-plusone" data-size="medium" style="display:inline"></div>
	   <script type="text/javascript">
  	   	(function() {
    		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    		po.src = 'https://apis.google.com/js/plusone.js';
    		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  		})();
		</script>
	</ul>
	
	<div id="personDiv">
		<div id="personTitle">
			<div id="text">
				<h3>${person.mapNameLf}</h3>
				<c:forEach items="${person.poLink}" var="currentPoLink">
					<c:if test="${currentPoLink.preferredRole}">
						<h4>${currentPoLink.titleOccList.titleOcc}</h4>
					</c:if>
				</c:forEach>			
				<c:if test="${person.activeStart != null}">
					<h7><fmt:message key=“people.sharePerson.activeStart”/> <span class="h7"> ${person.activeStart}</span></h7>
				</c:if>
				<c:if test="${person.activeStart == null}">
					<h7><fmt:message key=“people.sharePerson.birth”/> <span class="h7">${person.bornYear} ${person.bornMonth} ${person.bornDay}</span></h7>
				</c:if>		
				<c:if test="${person.activeEnd != null}">
					<h7><fmt:message key=“people.sharePerson.activeEnd”/><span class="h7"> ${person.activeEnd}</span></h7>
				</c:if>
				<c:if test="${person.activeEnd == null}">
					<h7><fmt:message key=“people.sharePerson.death”/> <span class="h7">${person.deathYear} ${person.deathMonth} ${person.deathDay}</span></h7>
				</c:if>
				<c:if test="${docsRelated != 0 && docsRelated != 1}">
					<p><fmt:message key=“people.sharePerson.documentsRelatedToThisPersonEntry”/> <span class="num_docs">${docsRelated}</span></p>
				</c:if>
				<c:if test="${docsRelated == 1}">
					<p><fmt:message key=“people.sharePerson.documentsRelatedToThisPersonEntry”/> <span class="num_docs">${docsRelated}</span></p>
				</c:if>
			</div>
			<div id="EditPortraitPersonDiv">
				<c:url var="ShowPortraitPersonURL" value="/src/peoplebase/ShowPortraitPerson.do">
					<c:param name="personId" value="${person.personId}" />
					<c:param name="time" value="${time}" />
				</c:url>
				<div id="imgPortraitPerson">
					<c:if test="${person.portraitAuthor != null && person.portraitSubject != null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitAuthor} - ${person.portraitSubject}"/>
					</c:if>
					<c:if test="${person.portraitAuthor != null && person.portraitSubject == null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitAuthor}"/>
					</c:if>
					<c:if test="${person.portraitAuthor == null && person.portraitSubject != null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145" title="${person.portraitSubject}"/>
					</c:if>
					<c:if test="${person.portraitAuthor == null && person.portraitSubject == null}">
						<img src="${ShowPortraitPersonURL}" width="111" height="145"/>
					</c:if>
				</div>
			</div>
		</div>
		
		<div id="EditDetailsPersonDiv" class="background">
			<div class="title">
				<h5><fmt:message key=“people.sharePerson.personDetails”/></h5>
			</div>
			
			<div class="listDetails">
				<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.gender”/></div> <div class="value">${person.gender}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.dateOfBirth”/></div> <div class="value">${person.bornYear} ${person.bornMonth} ${person.bornDay}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.birthPlace”/></div>
						<div class="value">${person.bornPlace.placeNameFull}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.activeStart”/></div> <div class="value">${person.activeStart}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.dateOfDeath”/></div> <div class="value">${person.deathYear} ${person.deathMonth} ${person.deathDay}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.deathPlace”/></div>
						<div class="value">${person.deathPlace.placeNameFull}</div>
					</div>
					<div class="row">
						<div class="item"><fmt:message key=“people.sharePerson.activeEnd”/></div> <div class="value">${person.activeEnd}</div>
					</div>
			</div>
			
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditNamesPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.names”/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${person.altName}" var="currentName">
				<div class="row">
					<div class="item">${currentName.nameType}</div>
					<div class="value">${currentName.namePrefix} ${currentName.altName}</div> 
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditTitlesOccupationsPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.titlesOccupations”/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${person.poLink}" var="currentPoLink">
				<div class="row">
					<c:if test="${currentPoLink.preferredRole}">
						<div class="value5" title="Preferred Role" id="preferredRoleIcon"></div>
					</c:if>
					<c:if test="${!currentPoLink.preferredRole}">
						<div class="value5"></div>
					</c:if>
					<div class="value60"><b>${currentPoLink.titleOccList.titleOcc}</b><br>
					${currentPoLink.titleOccList.roleCat.roleCatMinor}</div> 
					<div class="info"><fmt:message key=“people.sharePerson.start”/> ${currentPoLink.startDate} | <fmt:message key=“people.sharePerson.end”/> ${currentPoLink.endDate}</div>
				</div>
			</c:forEach>
		</div>	
	</div>
	
	<br />
	<br />
	
	<div id="EditParentsPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.parents”/></h5>
			<br />
		</div>
		
		<div class="list">
			<div class="row">
				<div class="item"><fmt:message key=“people.sharePerson.father”/></div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:if test="${currentParent.parent.gender == 'M'}">
				<div class="value">${currentParent.parent}</div> 
				<div class="info"><fmt:message key=“people.sharePerson.born”/> ${currentParent.parent.bornYear} | <fmt:message key=“people.sharePerson.death”/> ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
			<div class="row">
				<div class="item"><fmt:message key=“people.sharePerson.mother”/></div> 
		<c:forEach items="${person.parents}" var="currentParent">
			<c:if test="${currentParent.parent.gender == 'F'}">
				<div class="value">${currentParent.parent}</div> 
				<div class="info"><fmt:message key=“people.sharePerson.born”/> ${currentParent.parent.bornYear} | <fmt:message key=“people.sharePerson.death”/> ${currentParent.parent.deathYear}</div>
			</c:if>				
		</c:forEach>
			</div>
		</div>
	</div>
	
	<br />
	
	<div id="EditChildrenPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.children”/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${children}" var="currentChild">
				<div class="row">
					<div class="value">${currentChild.child}</div> 
					<div class="info"><fmt:message key=“people.sharePerson.born”/> ${currentChild.child.bornYear} | <fmt:message key=“people.sharePerson.death”/> ${currentChild.child.deathYear}</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div id="EditSpousesPersonDiv"class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.spouses”/></h5>
		</div>
		
		<div class="list">
			<c:forEach items="${marriages}" var="currentMarriage">
				<div class="row">
					<c:if test="${person.personId == currentMarriage.husband.personId}">
						<div class="value">${currentMarriage.wife}</div> 
						<div class="info"><fmt:message key=“people.sharePerson.marriage”/> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <fmt:message key=“people.sharePerson.death”/> ${currentMarriage.wife.deathYear}</div>
					</c:if>
					<c:if test="${person.personId == currentMarriage.wife.personId}">
						<div class="value">${currentMarriage.husband}</div> 
						<div class="info"><fmt:message key=“people.sharePerson.marriage”/> ${currentMarriage.startYear} - ${currentMarriage.endYear} | <fmt:message key=“people.sharePerson.death”/> ${currentMarriage.husband.deathYear}</div>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	<br />
	
	<div id="EditResearchNotesPersonDiv" class="background">
		<div class="title">
			<h5><fmt:message key=“people.sharePerson.researchNotes”/></h5>
		</div>
		
		<div class="list">
			<div class="row">
				<div class="value">
					${person.bioNotes}
				</div>
			</div>		
		</div>		
	</div>
	
	<script type="text/javascript">
		$j(document).ready(function() {
			
			var showDialogLoginFirst = $j('<div id="DialogLoginFirst"></div>').dialog({
				resizable: false,
				width: 300,
				height: 150, 
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				open: function(event, ui) { 
            		$j(this).load('${ShowLoginFirstDialogURL}');
           		},
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				title: "LOG IN FIRST"
			});
			
			$j("#moreInfoButton").die();
			$j("#moreInfoButton").live('click', function(e){
				if (!${logged}) {
					showDialogLoginFirst.dialog('open');
					return false;
				}
				e.preventDefault();
				if (window.opener != null) {
					if (window.opener.$j("#body_left").length == 1) {
						window.opener.$j("#body_left").load($j(this).attr('href'));
						window.opener.alert('<fmt:message key="home.showRecordAlertMessage"/>');
					} else {
						// Parent window is not yet opened
						window.open("${HomeURL}","_self");
					}
				} else {
					// If there isn't BIA window
					window.open("${HomeURL}","_self");
				}
				return false;
			});
		});
	</script>