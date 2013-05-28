<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


	<c:url var="checkDocumentDigitizedURL" value="/src/docbase/CheckDocumentDigitized.json">
		<c:param name="entryId"   value="${document.entryId}" />
		<c:param name="folioNum"   value="${document.folioNum}" />
		<c:param name="folioMod"   value="${document.folioMod}" />
		<c:param name="volNum"   value="${document.volume.volNum}" />
		<c:param name="volLetExt"   value="${document.volume.volLetExt}" />
	</c:url>

	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
		<c:url var="EditDetailsDocumentURL" value="/de/docbase/EditDetailsDocument.do">
			<c:param name="entryId"   value="${document.entryId}" />
		</c:url>

		<c:url var="ShowDocumentInManuscriptViewerURL" value="/src/mview/ShowDocumentInManuscriptViewer.do">
			<c:param name="entryId"   value="${document.entryId}" />
			<c:param name="flashVersion"   value="false" />
		</c:url>
	</security:authorize>
	
	<c:url var="CompareVolumeURL" value="/src/volbase/CompareVolume.do">
		<c:param name="summaryId"   value="${document.volume.summaryId}" />
	</c:url>
	
	
		<div id="EditDetailsDocumentDiv" class="background">
			<div class="title">
				<h5><fmt:message key="docbase.showDetailsDocument.title"/> </h5>
			<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_ONSITE_FELLOWS, ROLE_FELLOWS">
				<a id="EditDetailsDocument" href="${EditDetailsDocumentURL}" class="editButton"></a><span id="loading"/>
			</security:authorize>
			</div>
			
			
			<div class="list">
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.docId"/></div> <div class="value">${document.entryId == 0 ? '' : document.entryId}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.volume"/></div> <div class="value"><a href="${CompareVolumeURL}" class="linkVolume" title="View Volume n.${document.volume.volNum}${document.volume.volLetExt} file">${document.volume.volNum}${document.volume.volLetExt}</a></div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.insertPart"/></div> <div class="value">${document.insertNum} / ${document.insertLet}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.documentStartsAtFolio"/> </div> <div class="value">${document.folioNum} / ${document.folioMod}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.notpaginated"/></div> <div class="value">${document.unpaged ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.nonconsecutive"/></div> <div class="value">${document.contDisc ? 'Yes' : 'No'}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.documentTypology"/></div> <div class="value">${document.docTypology}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.modernYear"/></div> <div class="valueHilight">${document.yearModern}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.dateAsWritten"/></div> <div class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.dateUncertain"/></div> <div class="value">${document.dateUns ? 'Yes' : 'NO'}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.undated"/></div> <div class="value">${document.undated ? 'Yes' : 'NO'}</div>
				</div>
				<div class="row">
					<div class="item37"><fmt:message key="docbase.showDetailsDocument.dateNotes"/></div> <div class="value">${document.dateNotes}</div>
				</div>
			</div>
		</div>
	
	<br />
	
	<script type="text/javascript">
		$j(document).ready(function() {
	        $j("#EditCorrespondentsDocument").css('visibility', 'visible');
	        $j("#EditDetailsDocument").css('visibility', 'visible'); 
	        $j("#EditExtractOrSynopsisDocument").css('visibility', 'visible');
	        $j("#EditFactCheckDocument").css('visibility', 'visible');
	        $j("#EditDocumentInManuscriptTranscriber").css('visibility', 'visible');
	        $j("#EditDocumentInModal").css('visibility', 'visible');
	        $j("#EditTopicsDocument").css('visibility', 'visible');

	        $j("#EditDetailsDocument").click(function(){
				$j(this).next().css('visibility', 'visible');
				$j("#EditDetailsDocumentDiv").load($j(this).attr("href"));
				return false;
			});

			/*
			$j("#EditDetailsDocumentDiv").documentExplorer( {
				checkDocumentDigitizedUrl	: "${checkDocumentDigitizedURL}",
				showExplorerDocumentUrl     : "${ShowDocumentExplorerURL}"
			});
			*/  

			/*$j("#buttonShareLink").click(function() {
				window.open('${ShareDocumentURL}','ADD NEW PERSON','width=490,height=700,screenX=0,screenY=0,scrollbars=yes');return false;
			});*/

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
		});
	</script>
