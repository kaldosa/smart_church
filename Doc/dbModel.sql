CREATE TABLE DEFAULT_SCHEMA.verse (
       verse_id INT NOT NULL AUTO_INCREMENT
     , book_index INT NOT NULL
     , chapter_index INT NOT NULL
     , verse_index INT NOT NULL
     , verse TEXT NOT NULL
     , PRIMARY KEY (verse_id)
);

CREATE TABLE DEFAULT_SCHEMA.language (
       lang_id INT NOT NULL AUTO_INCREMENT
     , lang VARCHAR(64) NOT NULL
     , PRIMARY KEY (lang_id)
);

CREATE TABLE DEFAULT_SCHEMA.version (
       version_id INT NOT NULL AUTO_INCREMENT
     , version_name CHAR(36) NOT NULL
     , lang_id INT NOT NULL
     , version_table_name VARCHAR(128)
     , PRIMARY KEY (version_id)
     , INDEX (lang_id)
     , CONSTRAINT FK_version_1 FOREIGN KEY (lang_id)
                  REFERENCES DEFAULT_SCHEMA.language (lang_id)
);

CREATE TABLE DEFAULT_SCHEMA.book_title (
       book_title_id INT NOT NULL AUTO_INCREMENT
     , book_index INT NOT NULL
     , book_title CHAR(128) NOT NULL
     , abbr CHAR(36) NOT NULL
     , lang_id INT NOT NULL
     , PRIMARY KEY (book_title_id)
     , INDEX (lang_id)
     , CONSTRAINT FK_book_title_1 FOREIGN KEY (lang_id)
                  REFERENCES DEFAULT_SCHEMA.language (lang_id)
);

