INSERT INTO topics (name, creation_time) values
  ('First topic', '2015-09-17 18:47:52.690'),
  ('Second topic', '2015-10-18 18:47:52.690'),
  ('Another topic', '2015-12-01 10:40:52.690');

INSERT INTO messages (topic_id, content, creation_time) values
  (1, 'Message for first topic #1', '2015-09-17 18:47:52.690'),
  (1, 'Message for first topic #2', '2015-10-18 18:47:52.690'),
  (2, 'Message for second topic #1', '2015-12-01 10:40:52.690');