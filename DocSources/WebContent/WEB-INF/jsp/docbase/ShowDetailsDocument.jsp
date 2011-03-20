<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShareDocumentURL" value="/de/docbase/ShowDocumentShared.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>
	
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<c:url var="EditDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>

		<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="ShowDocumentExplorerURL" value="/src/docbase/ShowExplorerDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="volNum"   value="${document.volume.volNum}" />
		<c:param name="volLetExt"   value="${document.volume.volLetExt}" />
		<c:param name="folioNum"   value="${document.folioNum}" />
		<c:param name="folioMod"   value="${document.folioMod}" />
		<c:param name="imageType"   value="C" />
		<c:param name="imageProgTypeNum"   value="${document.folioNum}" />
		<c:param name="flashVersion"   value="true" />
	</c:url>

	<div id="EditDetailsDocumentDiv">
		<h5>DOCUMENT DETAILS </h5>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
		<a id="EditDetailsDocument" href="${EditDetailsDocumentURL}">edit</a><span id="loading"/>
	</security:authorize>
		<div id="CreatedSharePrintDiv">
			<div id="createdby">CREATED BY ${document.researcher} <fmt:formatDate pattern="MM/dd/yyyy" value="${document.dateCreated}" /></div>
			<a title="Print this record" href="#" id="buttonPrint"></a>
			<div id="buttonShareLink">
				<a href="#"><img src="/DocSources/images/1024/img_transparent.png"></a>
				<span>Use this to share this content / record / annotation across annotation clients and collections / applications such as: Zotero, Lore, Co-Annotea, Pliny, etc.</span>
			</div>
		</div>
		<hr id="lineSeparator"/>
		<div id="DocumentImageDiv">
			<c:if test="${not empty image}">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}">Show in manuscript viewer</a></p>
			</c:if>
			<c:if test="${empty image}">
			<img src="<c:url value="/images/image_document.png"/>" alt="document image" />
			</c:if>
		</div>
		
		<ul>
			<li><b>Doc ID:</b> ${document.entryId == 0 ? '' : document.entryId}</li>
			<li><b>Volume (MDP):</b> ${document.volume.volNum}</li>
			<li><b>Insert/Part:</b> ${document.insertNum} / ${document.insertLet}</li>
			<li><b>Document starts at folio :</b> ${document.folioNum} / ${document.folioMod}</li>
			<li><b>Paginated:</b> ${document.unpaged}</li>
			<li><b>Document Typology (other than letter):</b> ${document.docTypology}</li>
			<li><b>Modern Date:</b> ${document.yearModern}</li>
			<li><b>Recorded year:</b> ${document.docYear} ${document.docMonthNum} ${document.docDay}</li>
			<li><b>Date Notes:</b> ${document.dateNotes}</li>
		</ul>
	</div>
	
	<br />
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsOrPeopleDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptViewer").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditDetailsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			$j("#EditDetailsDocumentDiv").documentExplorer( {  
				showExplorerDocumentUrl     : "${ShowDocumentExplorerURL}",
				target                      : $j("#body_right")
			});  

			$j("#buttonShareLink").click(function() {
				window.open('/DocSources/de/peoplebase/CreatePerson.html','ADD NEW PERSON','width=490,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
			});

			$j("#buttonShareLink").hover(function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").attr({"style": 'display:inline'});
				$j(this).find("span").animate({opacity: 1, top: "-60"}, {queue:false, duration:400});
			}, function(){
				var iconName = $j(this).find("img").attr("src");
				var origen =  $j(this).find("img").attr("src");
				$j(this).find("img").attr("src");
				$j(this).find("span").animate({opacity: 0, top: "-50"}, {queue:false, duration:400, complete: function(){
					$j(this).attr({"style": 'display:none'});
				}});
			});

			$j("#ShowDocumentInManuscriptViewer").open({width: screen.width, height: screen.height, scrollbars: false});
		});
	</script>
