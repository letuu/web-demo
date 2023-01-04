select * from book;
select * from book where id > 2;
select * from book where name like '%4444%';
select * from book where name like '%44%' or author like '%той%';
select * from book where name like '%мир%' or author is not null;
select * from book where name like '%мир%' or author is null;

select * from reader;

select *
from book,
     reader
where book.reader_id = reader.id
  and reader.personal_number = 8465468;

select book.id, book.name, book.author
from book,
     reader
where book.reader_id = reader.id
  and reader.personal_number = 8465468;

select b.id, b.name, b.author
from book as b,
     reader as r
where b.reader_id = r.id
  and r.personal_number = 8465468;

select b.*
from book as b,
     reader as r
where b.reader_id = r.id
  and r.personal_number = 8465468;



select * from reader_books;

select b.*
from book as b,
     reader as r,
     reader_books as rb
where b.id = rb.books_id
  and r.id = rb.reader_id
  and r.first_name = 'Иван'
  and b.id > 5;
