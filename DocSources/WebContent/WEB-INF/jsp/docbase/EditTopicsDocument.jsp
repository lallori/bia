<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<form:form id="EditTopicsDocumentForm" method="post" cssClass="edit">
		<fieldset>
		<legend><b>TOPICS</b></legend>
			<div>
				<input id="firstTopic" name="firstTopic" class="input_28c_disabled" type="text" value="SCULPTURE - City of London / England" disabled="disabled" />
				<a href="#"><img src="/DocSources/images/button_cancel_form13.gif" alt="Cancel value" title="Delete this entry"/></a>
				<a id="editValue" href="/DocSources/de/docbase/EditTopicDocument.html">edit value</a>
			</div>
			
			<div>
				<input id="firstTopic" name="firstTopic" class="input_28c_disabled" type="text" value="LUXURY TEXTILES - Firenze / Toscana / Italia" disabled="disabled" />
				<a href="#"><img src="/DocSources/images/button_cancel_form13.gif" alt="Cancel value" title="Delete this entry"/></a>
				<a id="editValue" href="/DocSources/de/docbase/EditTopicDocument.html">edit value</a>
			</div>
			
				
			<input id="summaryId" name="summaryId" type="hidden" value="0"/>
			<input id="resIdNo" name="resIdNo" type="hidden" value=""/>
			<input id="seriesRefNum" name="seriesRefNum" type="hidden" value=""/>
			<input id="dateCreated" name="dateCreated" type="hidden" value="11/03/2010 11:51:57"/>
			
			<div>
			<input id="close" type="submit" value="Close" title="do not save changes" class="button" />
			<a id="AddTopicDocument" href="/DocSources/de/docbase/AddTopicsDocument.html">Add new Topic</a>
			</div>
			
		</fieldset>	
	</form>

	<div id="AddTopicDocumentDiv"></div>

<script type="text/javascript">
							$(document).ready(function() {
								$("#AddTopicDocument").click(function(){$("#AddTopicDocumentDiv").load($(this).attr("href"));return false;});
								$("#editValue").click(function(){$("#EditTopicsDocumentDiv").load($(this).attr("href"));return false;});
							});
</script>

<script type="text/javascript"> 
    $(document).ready(function() { 
		$('#close').click(function() { 
            $('#EditDetailsVolumeDiv').block({ 
                message: '<h1>Discard changes and close window?</h1>', 
                css: { border: '3px solid #a00' } 
            }); 
        }); 
	s});					  
</script>
	</form:form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#EditTopicsDocumentForm").submit(function (){
				$.ajax({ type:"POST", url:$(this).attr("action"), data:$(this).serialize(), async:false, success:function(html) { 
						if(html.match(/inputerrors/g)){
							$("#EditTopicsDocumentDiv").html(html);
						} else {
							$("#body_left").html(html);
						}
					} 
				});
				return false;
			});
		});
	</script>
