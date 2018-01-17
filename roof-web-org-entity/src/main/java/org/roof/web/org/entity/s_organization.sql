
CREATE TABLE s_organization (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  lvl int(11) DEFAULT NULL,
  seq int(11) DEFAULT NULL,
  parent_id bigint(20) DEFAULT NULL,
  leaf char(1) DEFAULT NULL,
  usable char(1) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK3F85E97FF7FF0B5A (parent_id),
  CONSTRAINT FK3F85E97FF7FF0B5A FOREIGN KEY (parent_id) REFERENCES s_organization (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
