<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="commentButtons">
	<a href="#" class="addComment">Add comment</a>
</div>

<div id="multiOpenAccordion" style="margin-top:10px;">
	<h1><a>Comment Title</a></h1>
    <div>
        <div class="commentDiv">
            <p class="commentInfo"><b>Lisa Kaborycha</b>, <i>On-site Fellow</i>, says:<br />
            <font color="#000099">December 11, 2010 at 2:13 pm</font> <a href="/DocSources/cm/replyMessage.html" class="replyMessage">Reply</a> <a href="/DocSources/cm/editMessage.html" class="editMessage">Edit</a> <a href="#">Delete</a> <a href="/DocSources/de/volbase/ShowVolumeExplorerWithComments.html" class="showDigitizedDocument">Show digitized document</a></p>
            <p class="commentText">This doesn't quite belong in the other forums, so I'll ask here regarding the document's overabundance of punctuation, often in seemingly strange places within sentences. Was there a particular reason she used so many, or was it just idiosyncratic?</p>
        </div>
        
        <div class="replyCommentDiv">
            <p class="commentInfo"><b>Julia Vicioso</b>, <i>On-site Fellow</i>, says:<br />
            <font color="#000099">December 10, 2010 at 4:25 pm</font> <a href="/DocSources/cm/replyMessage.html" class="replyMessage">Reply</a> <a href="/DocSources/de/volbase/ShowVolumeExplorerWithComments.html" class="showDigitizedDocument">Show digitized document</a></p>
            <p class="commentText">In response to your point: it looks like Casandra tries to separate words and make sentences very clear. Although the use of punctuation is sometimes 'ridundant', it is very accurate. <br />Note, for instance, the modern use of brackets in '(quanto piace alla bontà divina)'.</p>
        </div>
	</div>

	<h1><a>Another Comment Title</a></h1>
    <div>    
        <div class="commentDiv">
            <p class="commentInfo"><b>Elena Brizio</b>, <i>On-site Fellow</i>, says:<br />
            <font color="#000099">December 11, 2010 at 2:13 pm</font> <a href="/DocSources/cm/replyMessage.html" class="replyMessage">Reply</a> <a href="/DocSources/de/volbase/ShowVolumeExplorerWithComments.html" class="showDigitizedDocument">Show digitized document</a></p>
            <p class="commentText">I had thought the transcriptions would be posted on Thursdays and new document on Fridays, and this "week" there was extra time to go through more information with the week ending Oct 21?</p>
        </div>
        
        <div class="replyCommentDiv">
            <p class="commentInfo"><b>Alessio Assonitis</b>, <i>Research Director</i>, says:<br />
            <font color="#000099">December 10, 2010 at 4:25 pm</font> <a href="/DocSources/cm/replyMessage.html" class="replyMessage">Reply</a> <a href="/DocSources/de/volbase/ShowVolumeExplorerWithComments.html" class="showDigitizedDocument">Show digitized document</a></p>
            <p class="commentText">I also feel that the round-robin work we are doing is helpful.</p>
        </div>
    </div>
</div>
    

<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>

<script type="text/javascript">
	$j(document).ready(function() {
		$j('#multiOpenAccordion').multiAccordion();
		$j(".showDigitizedDocument").click(function() {										
			window.open('/DocSources/de/volbase/ShowVolumeExplorerWithComments.html','VOLUME EXPLORER','width=1010,height=650,screenX=0,screenY=0,scrollbars=yes');
			return false;
		});
		$j(".replyMessage").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "REPLY", width: 500, height: 187});
			return false;
		});
		$j(".editMessage").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "EDIT", width: 500, height: 187});
			return false;
		});	
	});
</script>
