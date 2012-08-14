<%@ taglib prefix="bia" uri="http://docsources.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReplyForumPostURL" value="/community/ReplyForumPost.do">
		<c:param name="postId" value="0"/>
		<c:param name="forumId" value="${topic.forum.forumId}"/>
		<c:param name="topicId" value="${topic.topicId}"/>
	</c:url>
	
	<h2>Advanced Search</h2>
	<form id="advancedSearchForm" method="post">
		<div id="searchQueryDiv">
			<h1>SEARCH QUERY</h1>
			<p><b>Search for keywords:</b><br />
    		Place + in front of a word which must be found and - in front of a word which must not be found. Put a list of words separated by | into brackets if only one of the words must be found. Use * as a wildcard for partial matches.</p>
    	
    		<input id="text" name="text" class="input_50c" type="text" value="" />
    		<label for="allTerms" id="allTermsLabel"><input type="radio" name="allTerms" id="allTerms" value="1">Search for all terms or use query as entered</label>
        	<label for="anyTerms" id="anyTermsLabel"><input type="radio" name="allTerms" id="anyTerms" value="0">Search for any terms</label>
        	<p><b>Search for author:</b><br />
    		Use * as a wildcard for partial matches.</p>
    		<input id="textAuthor" name="textAuthor" class="input_50c" type="text" value=""/>
		</div>
		
		<div id="searchOptionsDiv">
			<h1>SEARCH OPTIONS</h1>
			<p><b>Search in forums:</b><br />
   			Select the forum or forums you wish to search in. Subforums are searched automatically if you do not disable "search subforums" below.</p>
   			<div>
   				<select name="searchForum" id="searchForum" multiple="multiple" size="${subForums.size()}" title="Search in forums">
   				<!-- MD: In this point we insert all the forum with subforums (Document > 2206 ; Person > Cosimo I, Cosimo II...) or only the main forums (Documents, People, Volumes, Places) ? -->
   				<c:forEach items="${subForums}" var="currentForum">
					<option value="${currentForum.forumId}">${currentForum.title}</option>
				</c:forEach>
   				</select>
   			</div>
   			<div class="listForm">
		        <div class="row">
		             <div class="item">
		                <label for="searchSubforums" id="searchSubforumsLabel">Search subforums:</label>
		             </div>
		             <div class="value">
		                <label for="ssYes"><input type="radio" name="searchSubforums" id="ssYes" value="Yes" checked="checked"> Yes</label> 
		                <label for="ssNo"><input type="radio" name="searchSubforums" id="ssNo" value="No"> No</label> 
		             </div>
		        </div> 
		        
		        <div class="row">
		            <div class="item">
		            	<label for="searchWithin" id="searchWithinLabel">Search within:</label>
		            </div>
		            <div class="value">
		                <ul>
		                    <li><label for="searchWithin"><input type="radio" name="searchWithin" id="sw_1" value="Post subjects and message text" checked="checked"> Post subjects and message text</label></li>
		                    <li><label for="searchWithin"><input type="radio" name="searchWithin" id="swMessage" value="Message text only"> Message text only</label></li>
		                    <li><label for="searchWithin"><input type="radio" name="searchWithin" id="swTopicTitles" value="Topic titles only"> Topic titles only</label></li>
		                    <li><label for="searchWithin"><input type="radio" name="searchWithin" id="swFirstPost" value="First post of topics only"> First post of topics only</label></li>
		                </ul>
		            </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="displayResults" id="displayResultsLabel">Display results as:</label>
		             </div>
		             <div class="value">
		                <label for="drPosts"><input type="radio" name="displayResults" id="drPosts" value="Posts" checked="checked"> Posts</label> 
		                <label for="drTopics"><input type="radio" name="displayResults" id="drTopics" value="Topics"> Topics</label> 
		             </div>
				</div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="sortResults" id="sortResultsLabel">Sort results by:</label>	
		             </div>
		             <div class="value">
		                <select name="sortResults" id="sortResults">
		                    <option value="author">Author</option>
		                    <option value="post time" selected="selected">Post time</option>
		                    <option value="forum">Forum</option>
		                    <option value="topic title">Topic title</option>
		                    <option value="post subject">Post subject</option>
		                </select>
		                <label for="ascending"><input type="radio" name="ascending" id="ascending" value="Ascending" checked="checked"> Ascending</label> 
		                <label for="descending"><input type="radio" name="descending" id="descending" value="Descending"> Descending</label> 
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="limitResults" id="limitResultsLabel">Limit results to previous:</label>	
		             </div>
		             <div class="value">
		                <select name="limitResults" id="limitResults">
		                    <option value="0" selected="selected">All results</option>
		                    <option value="1">1 day</option>
		                    <option value="7">7 days</option>
		                    <option value="14">2 weeks</option>
		                    <option value="30">1 month</option>
		                    <option value="90">3 months</option>
		                    <option value="180">6 months</option>
		                    <option value="365">1 year</option>
		                 </select>
		             </div>
		        </div>
		        
		        <div class="row">
		        	<div class="item">
		                <label for="returnFirst" id="returnFirstLabel">Return first:</label>	
		             </div>
		             <div class="value">
		                <select name="returnFirst" title="Return first">
		                    <option value="-1">All available</option>
		                    <option value="0">0</option>
		                    <option value="25">25</option>
		                    <option value="50">50</option>
		                    <option value="100">100</option>
		                    <option value="200">200</option>
		                    <option value="300" selected="selected">300</option>
		                    <option value="400">400</option>
		                    <option value="500">500</option>
		                    <option value="600">600</option>
		                    <option value="700">700</option>
		                    <option value="800">800</option>
		                    <option value="900">900</option>
		                    <option value="1000">1000</option>
		                </select>
		                characters of posts
		             </div>
		        </div>
		     </div>
		</div>
		
		<input type="submit" value="Search" class="buttonSmall" id="submitSearch">
    	<a href="/DocSources/forum/viewThreads.html" id="cancel" class="buttonSmall">Reset</a>
	</form>



	<script type="text/javascript">
		$j(document).ready(function() {
			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
   				$j("#chronologyDiv").html(json.chronology);
				$j(".arrowForum").css('visibility','visible');
				$j(".forum").css('visibility','visible');
   			}});

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#mainContent").load($j(this).attr("href"));
				return false;
			});
			
			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.boardIndex').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});

			$j('.quotePost').die();
			$j('.quotePost').click(function (){
				$j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
				return false;
			});

			$j('#postReply').click(function (){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			//MD: This code highlight the word inside the posts
			var test = [];
			test = $j('.search').text().split(" ");
			
			$j(".textPost").each(function(){
			 	var newText = $j(this).text().split(" ").join("</span> <span class='toRemove'>");
			  	newText = "<span class='toRemove'>" + newText + "</span>";
			  	for(var i = 0; i < test.length; i++){
			  		$j(this).html(newText).find('span').end().find(":contains('" + test[i] + "')").wrap("<span class='highlighted' />");
			  		newText = $j(this).html();
			  	}
				$j(".toRemove").contents().unwrap();

			});
				
// 			$j(".textPost:contains('${yourSearch}')").append($j('<span class="highlighted"></span>'));

		});
	</script>
