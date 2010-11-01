<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    </head>
    <body>
        <div id="nonFooter">
            <tiles:insertAttribute name="header"/>
            <div id="content">
                <div id="content_main">
                    <tiles:insertAttribute name="main"/>
                </div>
            </div>
        </div>
        <tiles:insertAttribute name="footer"/>
    </body>
</html>
