<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="IIPImageServer" value="/mview/IIPImageServer.do"/>
	<c:url var="ImagePrefixURL" value="/images/mview/"/>

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
	
		<script type="text/javascript">
			iip = new IIP( "targetframe", {
				server: '${IIPImageServer}',
				prefix: '${ImagePrefixURL}',
				image: '${documentExplorer.image}',
				<%-- credit: "${documentExplorer.image.imageType == 'C' ? 'folio: ' : ''} ${documentExplorer.image.imageType == 'R' ? 'Index of Names' : ''} ${documentExplorer.image.imageProgTypeNum} ${documentExplorer.image.imageRectoVerso == 'R' ? 'Recto' : 'Verso'}", --%>
				credit: '<span style=\'font-size:16px\'>' +
							"${documentExplorer.image.imageType == 'C' ? 'folio &nbsp;&nbsp;' : ''} ${documentExplorer.image.imageType == 'R' ? 'index of names &nbsp;' : ''}" +
							'<span style=\'font-size:22px\'>' +
							"${documentExplorer.image.imageProgTypeNum} ${documentExplorer.image.missedNumbering}" +
							'</span>' +
							"${documentExplorer.image.imageRectoVerso == 'R' ? 'recto' : 'verso'}" +
							'</span>',	
				navigation: true,
				showNavWindow: true,
				showNavImage: true, // this property hide navigation image
				showNavButtons: true,
				winResize: true,
				zoom: 3
			});
			
			$j(document).ready(function() {
				var $dialogExtract = $j('<div id="EditExtractDocumentDiv"></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
					width: 352,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'EDIT EXTRACT',
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
					autoOpen: false,
					width: 352,
					minWidth: 350,
					minHeight: 200,                                                                                                                                                         
					title: 'EDIT SYNOPSIS',
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
					resizable: false,
					width: 470,
					height: 110,
					minWidth: 470,
					minHeight: 110,                                                                                                                                                         
					title: 'PAGE TURNER',
					position: ['right','middle'],                                                                                                                                                       
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