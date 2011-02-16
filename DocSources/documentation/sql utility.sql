-- fix order column by imageName substring   
select "update tblImages set imageOrder = ", replace(FORMAT(substr(imageName,1, 4),0), ',', ''), " where imageId=", imageId, ";" from tblimages 
where volNum=5 and volLetExt is null