
CREATE TABLE `s_fileinfo` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(250) NOT NULL,
  displayName varchar(250) DEFAULT NULL,
  fileSize bigint(20) DEFAULT NULL,
  realPath varchar(250) DEFAULT NULL,
  webPath varchar(250) DEFAULT NULL,
  type varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UNIQUE_NAME (name)
);