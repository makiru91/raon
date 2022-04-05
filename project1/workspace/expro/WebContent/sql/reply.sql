create table ex_reply(
  reply_no number primary key,
  board_no number references ex_board(board_no),
  id varchar2(20),
  reply_date date,
  reply_content varchar2(1000)
);

create SEQUENCE seq_reply_no
START WITH 1
INCREMENT BY 1;

select * from ex_reply;