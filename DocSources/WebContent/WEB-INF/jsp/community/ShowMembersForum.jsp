<%@page import="org.medici.docsources.common.util.ForumUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bia" uri="http://docsources.medici.org/jsp:jstl" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="findMember">
	<ul class="abc">
    	<li><a href="#">Find a member</a></li>
        <li><a href="#">All</a></li>
        <li><a href="#">A</a></li>
        <li><a href="#">B</a></li>
        <li><a href="#">C</a></li>
        <li><a href="#">D</a></li>
        <li><a href="#">E</a></li>
        <li><a href="#">F</a></li>
        <li><a href="#">G</a></li>
        <li><a href="#">H</a></li>
        <li><a href="#">I</a></li>
        <li><a href="#">J</a></li>
        <li><a href="#">K</a></li>
        <li><a href="#">L</a></li>
        <li><a href="#">M</a></li>
        <li><a href="#">N</a></li>
        <li><a href="#">O</a></li>
        <li><a href="#">P</a></li>
        <li><a href="#">Q</a></li>
        <li><a href="#">R</a></li>
        <li><a href="#">S</a></li>
        <li><a href="#">T</a></li>
        <li><a href="#">U</a></li>
        <li><a href="#">V</a></li>
        <li><a href="#">W</a></li>
        <li><a href="#">X</a></li>
        <li><a href="#">Y</a></li>
        <li><a href="#">Z</a></li>
        <li><a href="#">Other</a></li>
    </ul>
</div>
<div id="membersTable">
	<div class="list">
        <div class="rowFirst">
            <div class="one">USERNAME</div>
            <div class="two">POSTS</div>
            <div class="three">WEBSITE, LOCATION</div>
            <div class="four">JOINED</div>
            <div class="five">LAST ACTIVE</div>
        </div>
        <div class="row">
            <div class="one">
                <a class="username" href="/DocSources/forum/profile.html">Lisa Kaborycha</a>
            </div>
            <div class="two">1</div>
            <div class="three">www.lisakaborycha.com</div>
            <div class="four">Tue Mar 02, 2010 12:31 pm</div>
            <div class="five">Fri Mar 30, 2012 9:23 am </div>
        </div>
        <div class="rowLast"><!-- ultima righa sempre con questo class, anche se è una solta! -->
            <div class="one">
                <a class="username" href="/DocSources/forum/profile.html">Assonitis</a>
            </div>
            <div class="two">10</div>
            <div class="three"></div>
            <div class="four">Fri Apr 22, 2010 10:12 pm</div>
            <div class="five">Mon Dec 10, 2012 8:15 am</div>
        </div>
    </div>
</div>



<script>
	$j(document).ready(function() {
	});
</script>
