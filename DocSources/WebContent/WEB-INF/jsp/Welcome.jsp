<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a id="mapcourses" href="http://courses.medici.org/" target="_blank"></a>
	<div class="welcome_list">
		<h2>Welcome back <security:authentication property="principal.firstName"/>. <br /></h2>

	  <h5>ACTIVITY IN FORUMS</h5>
    
    <div id="topDiscussions">
        <h1>TOP DISCUSSIONS</h1>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref">Eleonora di Toledo</a> 
            <span>(5 replies)</span>
            <span>by <a href="#" id="userName" class="link">rpiccinelli</a><span class="date">2012-09-26</span></span>
        </div>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref">Calmady-Narborough, Elizabeth</a> 
            <span>(3 replies)</span>
            <span>by <a href="#" id="userName" class="link">marfaioli</a><span class="date">2012-09-18</span></span>
        </div>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref">When was Maria de' Medici born?</a> 
            <span>(3 replies)</span>
            <span>by <a href="#" id="userName" class="link">ebrizio</a><span class="date">2012-09-17</span></span>
        </div>
    </div>
    
    <div id="mostRecentDiscussions">
        <h1>MOST RECENT DISCUSSIONS</h1>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref" title="Schenckenschanz / Gelderland / Nederland / Europe">Schenckenschanz / Gelderland / N...</a> 
            <span>(1 reply)</span>
            <span>by <a href="#" id="userName" class="link">assonitis</a><span class="date">2012-10-01</span></span>
        </div>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref" title="Minute di Lettere e Registri / Minute: Cosimo I / Segretario: Concino">Minute di Lettere e Registri / Min...</a> 
            <span>(3 replies)</span>
            <span>by <a href="#" id="userName" class="link">marfaioli</a><span class="date">2012-09-30</span></span>
        </div>
        <div class="discussion">
            <img src="/DocSources/images/forum/img_forum.png" alt="entry" />
            <a href="#" class="forumHref">Vitelli, Gian Luigi (Chiappino)</a> 
            <span>(0 replies)</span>
            <span>by <a href="#" id="userName" class="link">ebrizio</a><span class="date">2012-09-29</span></span>
        </div>
    </div>
        
    
    <h5>ACTIVITY IN THE DATABASE</h5>
    
    <div id="lastLogOnDiv">
    	<h1>FROM YOUR LAST LOG ON</h1>
        <div>
        	<a href="#" class="databaseActivity">Document Transcriptions</a> 
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>0</span>
        </div>
    </div>
    
    <div id="thisWeekDiv">
    	<h1>THIS WEEK</h1>
        <div>
        	<a href="#" class="databaseActivity">Document Transcriptions</a> 
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>0</span>
        </div>
    </div>
    
    <div id="thisMonthDiv">
    	<h1>THIS MONTH</h1>
        <div>
        	<a href="#" class="databaseActivity">Document Transcriptions</a> 
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Document Synopsis</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Volumes</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">People</a>
            <span>0</span>
        </div>
        <div>
        	<a href="#" class="databaseActivity">Places</a>
            <span>0</span>
        </div>    
    </div>
