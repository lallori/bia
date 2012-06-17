<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ForumCSSURL" value="/styles/forum/forum.css"/>

<c:url var="ShowPreviewForumPostURL" value="/community/ShowPreviewForumPost.do"/>
						
<h1 style="margin-bottom:20px;">POST A NEW TOPIC</h1>
	<form:form id="EditForumPost" method="post" class="edit">
		<div>
	        <label for="subject" id="subjectLabel">Subject</label>
	        <input id="subject" name="subject" class="input_25c" type="text" value=""/>
	    </div>
	    <div>
	    	<label for="topic" id="topicLabel">Topic</label>
			<textarea id="text" style="width:1000px; height:300px"></textarea>
	    </div>
	    
	    <input type="submit" value="Submit" class="buttonSmall" id="submit">
	    <a href="#" id="preview" class="buttonSmall">Preview</a>
	</form:form>

	<div id="postTable" style="display:none; margin-top:15px">
	</div>
<script type="text/javascript">
	$j(document).ready(function() {
		$j('#preview').click(function(){
 			$j.ajax({ type:"POST", url:"${ShowPreviewForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(html) {
 				$j("#postTable").html(html);
			}});

 			$j('#postTable').css('display','inherit');
			$j.scrollTo({top:'300px',left:'0px'}, 800 );
         });
	});	
</script>

<script type="text/javascript">
	new TINY.editor.edit('editor',{
		id:'text',
		width:920,
		height:250,
		cssclass:'te',
		controlclass:'tecontrol',
		rowclass:'teheader',
		dividerclass:'tedivider',
		controls:['bold','italic','underline','strikethrough','|','subscript','superscript','|',
				  'orderedlist','unorderedlist','|','outdent','indent','|','leftalign',
				  'centeralign','rightalign','blockjustify','|','unformat','|','undo','redo','n',
				  'font','size','style','|','image','hr','link','unlink','|','cut','copy','paste','print'],
		footer:false,
		fonts:['Georgia','Arial','Trebuchet MS'],
		xhtml:false,
		cssfile:'forum.css',
		bodyid:'editor',
		footerclass:'tefooter',
		toggle:{text:'source',activetext:'wysiwyg',cssclass:'toggle'},
		resize:{cssclass:'resize'}
	});
</script>

<script>
	$j(function() {
		$j('#submit').click(function(){
			$j('#messagePosted').dialog('open');
			return false;
		});
		
		$j( "#messagePosted" ).dialog({
			  autoOpen : false,
			  modal: true,
			  resizable: false,
			  width: 300,
			  height: 130, 
			  buttons: {
				  Ok: function() {
					  $j( this ).dialog( "close" );
				  }
			  }
		  });
		});
</script>