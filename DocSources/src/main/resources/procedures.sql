DELIMITER //
CREATE PROCEDURE InsertLessonImage(
	IN iName VARCHAR(45),
	IN iTitle VARCHAR(300),
	IN pOrder INT(5),
	IN sPath INT(11))
BEGIN
	DECLARE iOrder INT(5) DEFAULT 0;
	
	SELECT 
		COUNT(*) 
		INTO iOrder
	FROM 
		tblImages 
	WHERE 
		volNum = 0 AND volLetExt IS NULL;
	
	INSERT INTO 
		tblImages  (`volNum`, `imageName`, `imageType`, `imageRectoVerso`, `imageOrder`, `imageProgTypeNum`, `storagePath`, `dateCreated`, `imageTitle`) 
	VALUES (0,  iName, 'O', 'N', iOrder + 1, iOrder + 1, sPath, NOW(), iTitle);
END //
DELIMITER ;
