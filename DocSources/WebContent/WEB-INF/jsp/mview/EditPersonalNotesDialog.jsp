<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="editPersonalNotesForm" value="/src/mview/EditPersonalNotesDialog.do?"/>

	<%-- Loading div when saving the form --%>
	<div id="loadingDiv"></div>
	<form:form id="EditPersonalNotesForm" action="${editPersonalNotesForm}" method="post" cssClass="edit">
		<form:textarea id="personalNotes" path="personalNotes" rows="16" style="width: 98%; height: 90%;"/>
		<input type="submit" value="Save Notes"  id="saveNotes">
		<a id="cleanNotes">Clear notes</a>
	</form:form>
	
	<div id="clearNotes" title="ALERT" style="display:none"> 
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are your sure you want to clear the Personal Notes window?</p> 
	</div>
	
	<div id="saveNotesSuccess" title="Alert" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Your personal notes has been saved!</p>
	</div>

	<script type="text/javascript">
		$j(document).ready(function() {
			var close = $j("#ui-dialog-title-DialogPersonalNotesDiv").parent();
			$j(close).find(".ui-dialog-titlebar-close").css("display", "inline");
			
			$j("#saveNotes").click(function (){
				$j("#loadingDiv").css('height', $j("#loadingDiv").parent().height());
				$j("#loadingDiv").css('width', $j("#loadingDiv").parent().width());
	        	$j("#loadingDiv").css('visibility', 'visible');
				$j.ajax({ type:"POST", url:$j("#EditPersonalNotesForm").attr("action"), data:$j("#EditPersonalNotesForm").serialize(), async:false, success:function(html) { 
						$j("#DialogPersonalNotes").html(html);
						personalNotesChanged=false;
						$j("#saveNotesSuccess").dialog("open");
					} 
				});
				return false;
			});
			
			$j("#cleanNotes").click(function (){
				$j('#clearNotes').dialog('open');
				return false;
			});

			$j("#personalNotes").change(function(){
				personalNotesChanged=true;
			});
			
			$j("#clearNotes").dialog({
				resizable: false,
				height:140,
				modal: true,
				autoOpen : false,
				zIndex: 3999,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				buttons: {
					YES : function() {
						$j("#personalNotes").text("");
						$j(this).dialog('close');
					},
					NO: function() {
						$j(this).dialog('close');
					}
				}
			});
			
			$j("#saveNotesSuccess").dialog({
				resizable: false,
				height:150,
				modal: true,
				autoOpen : false,
				title: 'PERSONAL NOTES SAVED',
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				buttons: {
					OK : function() {
						$j(this).dialog('close');
						$j("#loadingDiv").css('visibility', 'hidden');
					}
				}
			});
		});
	</script>

