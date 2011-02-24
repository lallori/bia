<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditExtractDialogUrl" value="/de/mview/EditExtractDocumentDialog.do" >
		<c:param name="entryId" value="${requestCommand.entryId}" />
	</c:url>
	<c:url var="EditSynopsisDialogUrl" value="/de/mview/EditSynopsisDocumentDialog.do" >
		<c:param name="entryId" value="${requestCommand.entryId}" />
	</c:url>
	
	<c:url var="PageTurnerDialogUrl" value="/de/mview/PageTurnerDialog.do" >
		<c:param name="entryId" value="${documentExplorer.entryId}" />
		<c:param name="volNum" value="${documentExplorer.volNum}" />
		<c:param name="volLetExt" value="${documentExplorer.volLetExt}" />
		<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
		<c:param name="total" value="${documentExplorer.total}" />
		<c:param name="totalRubricario" value="${documentExplorer.totalRubricario}" />
		<c:param name="totalCarta" value="${documentExplorer.totalCarta}" />
		<c:param name="totalAppendix" value="${documentExplorer.totalAppendix}" />
		<c:param name="totalOther" value="${documentExplorer.totalOther}" />
		<c:param name="totalGuardia" value="${documentExplorer.totalGuardia}" />
		<c:param name="modeEdit" value="true" />
	</c:url>
	
	<c:set var="autoOpenSynopsisDialog" value="${document.synExtract != null ? 'true' : 'false'}"/> 
		
		<script type="text/javascript">
			iip = new IIP( "targetframe", {
				server: '/DocSources/mview/ProxyIIPImage.do',
				image: '${documentExplorer.image}',
				credit: '&copy; copyright or information message', 
				zoom: 1,
				showNavButtons: true,
				render: 'random'
			});
			
			$j(document).ready(function() {
				var $dialogExtract = $j('<div id="EditExtractDocumentDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
					width: 352,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'Edit Extract',
					position: ['left','top'],                                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
						$(this).load('${EditExtractDialogUrl}');
					},
					//drag: function(event, ui) {$j(this).append(ui.position.left);},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);}, 
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});                                                                                                                                                                         

				var $dialogSynopsis = $j('<div id="EditSynopsisDocumentDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: ${autoOpenSynopsisDialog},
					width: 352,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'Edit Synopsis',
					position: ['left','bottom'],                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
						$(this).load('${EditSynopsisDialogUrl}');
					},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});;
				
				
				var $pageTurner = $j('<div id="PageTurnerDiv"></div>')
				.dialog({
					autoOpen: true,
					width: 575,
					height: 110,
					minWidth: 575,
					minHeight: 110,                                                                                                                                                         
					title: 'Page Turner',
					position: ['right','bottom'],                                                                                                                                                       
					closeOnEscape: false,
					maximized:false,
					
					open: function(event, ui) { 
						$j(".ui-dialog-titlebar-close").hide(); 
                		$(this).load('${PageTurnerDialogUrl}');
               		},
					dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
					dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
				}).dialogExtend({"minimize" : true});
			
	
			});

		</script>