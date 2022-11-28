CREATE TABLE keywords
(
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    value VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE books
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    author_name VARCHAR(255) NOT NULL
);

CREATE TABLE books_keywords
(
    book_id    UUID REFERENCES books (id),
    keyword_id UUID REFERENCES keywords (id),
    PRIMARY KEY (book_id, keyword_id)
);

-- put some default values

INSERT INTO keywords (id, value)
VALUES ('1979e754-1b35-4091-b188-8a24e1d66ee4', 'keyword1'),
       ('2979e754-1b35-4091-b188-8a24e1d66ee1', 'keyword2'),
       ('3979e754-1b35-4091-b188-8a24e1d66ee7', 'keyword3');

INSERT INTO books (id, name, author_name)
VALUES ('21df6376-772c-44af-b89c-021ef5a70626', 'book1', 'author1'),
       ('31df6376-772c-44af-b89c-021ef5a70627', 'book2', 'author2'),
       ('41df6376-772c-44af-b89c-021ef5a70622', 'book3', 'author2'),
       ('51df6376-772c-44af-b89c-021ef5a70621', 'book4', 'author3');

INSERT INTO books_keywords (book_id, keyword_id)
VALUES ('21df6376-772c-44af-b89c-021ef5a70626', '1979e754-1b35-4091-b188-8a24e1d66ee4'),
       ('31df6376-772c-44af-b89c-021ef5a70627', '1979e754-1b35-4091-b188-8a24e1d66ee4'),
       ('41df6376-772c-44af-b89c-021ef5a70622', '1979e754-1b35-4091-b188-8a24e1d66ee4'),
       ('21df6376-772c-44af-b89c-021ef5a70626', '2979e754-1b35-4091-b188-8a24e1d66ee1'),
       ('41df6376-772c-44af-b89c-021ef5a70622', '3979e754-1b35-4091-b188-8a24e1d66ee7');