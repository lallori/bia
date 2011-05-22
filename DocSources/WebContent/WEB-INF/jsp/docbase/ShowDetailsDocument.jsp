<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShareDocumentURL" value="/src/docbase/ShowDocument.do">
		<c:param name="entryId"   value="${document.entryId}" />
	</c:url>

	<c:url var="checkDocumentDigitizedURL" value="/src/docbase/CheckDocumentDigitized.json">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="folioNum"   value="${document.folioNum}" />
		<c:param name="folioMod"   value="${document.folioMod}" />
		<c:param name="volNum"   value="${document.volume.volNum}" />
		<c:param name="volLetExt"   value="${document.volume.volLetExt}" />
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

	<div id="EditDetailsDocumentDiv" class="background">
		<div class="title">
			<h5>DOCUMENT DETAILS </h5>
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_DISTANT_FELLOWS">
			<a id="EditDetailsDocument" href="${EditDetailsDocumentURL}" class="editButton"></a><span id="loading"/>
		</security:authorize>
		</div>

		<div id="DocumentImageDiv">
			<c:if test="${not empty image}">
			<img src="<c:url value="/mview/ReverseProxyIIPImageThumbnail.do?imageName=${image}"/>">
			<p><a id="ShowDocumentInManuscriptViewer" href="${ShowDocumentInManuscriptViewerURL}">Show in manuscript viewer</a></p>
			</c:if>
			<c:if test="${empty image}">
			<img src="<c:url value="/images/1024/img_document.png"/>" alt="document image" />
			</c:if>
		</div>
		
		<div class="listDetails">
			<div class="row">
				<div class="item">Doc ID</div> <div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
			</div>
			<div class="row">
				<div class="item">Volume (MDP)</div> <div class="value">${document.volume.volNum}${document.volume.volLetExt}</div>
			</div>
			<div class="row">
				<div class="item">Insert/Part</div> <div class="value">${document.insertNum} / ${document.insertLet}</div>
			</div>
			<div class="row">
				<div class="item">Document starts at folio </div> <div class="value">${document.folioNum} / ${document.folioMod}</div>
			</div>
			<div class="row">
				<div class="item">Paginated</div> <div class="value">${document.unpaged}</div>
			</div>
			<div class="row">
				<div class="item">Document Typology (other than letter)</div> <div class="value">${document.docTypology}</div>
			</div>
			<div class="row">
				<div class="item">Modern Date</div> <div class="value">${document.yearModern}</div>
			</div>
			<div class="row">
				<div class="item">Recorded year</div> <div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
			</div>
			<div class="row">
				<div class="item">Date uncertain or approximate</div> <div class="value">${document.dateUns}</div>
			</div>
			<div class="row">
				<div class="item">Undated</div> <div class="value">${document.undated}</div>
			</div>
			<div class="row">
				<div class="item">Date Notes</div> <div class="value">${document.dateNotes}</div>
			</div>
		</div>
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
				checkDocumentDigitizedUrl	: "${checkDocumentDigitizedURL}",
				showExplorerDocumentUrl     : "${ShowDocumentExplorerURL}",
			});  

			$j("#buttonShareLink").click(function() {
				window.open('${ShareDocumentURL}','ADD NEW PERSON','width=490,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
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
