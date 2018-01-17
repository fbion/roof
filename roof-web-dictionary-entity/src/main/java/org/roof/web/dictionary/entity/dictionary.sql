CREATE TABLE s_dictionary (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  type varchar(200) DEFAULT NULL,
  val varchar(200) DEFAULT NULL,
  text varchar(200) DEFAULT NULL,
  seq int(11) DEFAULT NULL,
  active char(1) DEFAULT NULL,
  description varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

