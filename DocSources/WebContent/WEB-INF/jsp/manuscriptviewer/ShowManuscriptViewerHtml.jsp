<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >

 <head>
  <meta name="author" content="Ruven Pillay &lt;ruven@users.sourceforge.netm&gt;"/>
  <meta name="keywords" content="IIPImage Ajax Internet Imaging Protocol IIP Zooming Streaming High Resolution Mootools"/>
  <meta name="description" content="IIPImage: High Resolution Remote Image Streaming Viewing"/>
  <meta name="copyright" content="&copy; 2003-2008 Ruven Pillay"/>

  <link rel="stylesheet" type="text/css" media="all" href="/DocSources/styles/iip.compressed.css" />
  <link rel="stylesheet" type="text/css" media="all" href="/DocSources/styles/ext-all.css" />
  <link rel="shortcut icon" href="images/iip-favicon.png" />
  <title>IIPMooViewer 1.2 :: IIPImage High Resolution Ajax Image Streaming Viewer</title>

  <script type="text/javascript" src="/DocSources/scripts/mootools-core-1.3-full-compat-compressed.js"></script>
  <script type="text/javascript" src="/DocSources/scripts/mootools-more-1.3-full-compat-compressed.js"></script>
  <script type="text/javascript" src="/DocSources/scripts/iipmooviewer-1.1-compressed.js"></script>
  <script type="text/javascript" src="/DocSources/scripts/ext-base.js"></script>
  <script type="text/javascript" src="/DocSources/scripts/ext-all.js"></script>
  <!-- <script type="text/javascript" src="/DocSources/scripts/anchoring.js"></script> -->
  
  <script type="text/javascript">
	  iip = new IIP( "targetframe", {
			server: '/DocSources/mview/ProxyIIPImage.do',
			//server: 'http://192.168.1.200/fcgi-bin/iipsrv.fcgi',
			//server: '/DocSources/fcgi-bin/iipsrv.fcgi',
			image: '${image}',
			credit: '&copy; copyright or information message', 
			zoom: 1,
			render: 'random',
	      showNavButtons: true
	  });
  </script>

 </head>

 <body>
   <div style="width:99%;height:99%;margin-left:auto;margin-right:auto" id="targetframe"></div>

 </body>

</html>

