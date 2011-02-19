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
		<c:param name="entryId" value="${documentImage.entryId}" />
		<c:param name="volNum" value="${documentImage.volNum}" />
		<c:param name="volLetExt" value="${documentImage.volLetExt}" />
		<c:param name="imageOrder" value="${documentImage.image.imageOrder}" />
		<c:param name="total" value="${documentImage.total}" />
		<c:param name="totalRubricario" value="${documentImage.totalRubricario}" />
		<c:param name="totalCarta" value="${documentImage.totalCarta}" />
		<c:param name="totalAppendix" value="${documentImage.totalAppendix}" />
		<c:param name="totalOther" value="${documentImage.totalOther}" />
		<c:param name="totalG" value="${documentImage.totalG}" />
	</c:url>
		<script type="text/javascript">
			var $j = jQuery.noConflict();
			$j(document).ready(function() {
				$j.ajaxSetup ({
					// Disable caching of AJAX responses */
					cache: false
				});

				var $dialogExtract = $j('<div></div>')
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

				var $dialogSynopsis = $j('<div></div>')
				.dialog({                                                                                                                                                                   
					autoOpen: true,
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
				});
				
				
				var $dialogExplorer = $j('<div></div>')
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
				});
			
	
			});

			iip = new IIP( "targetframe", {
				server: '/DocSources/mview/ProxyIIPImage.do',
				image: '${documentImage.image}',
				credit: '&copy; copyright or information message', 
				zoom: 1,
				showNavButtons: true,
				render: 'random'
			});

		</script>