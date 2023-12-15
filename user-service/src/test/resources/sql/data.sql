INSERT INTO app.users (uuid, mail, mobile_phone, status, created_at, updated_at
)
VALUES
    ('ad16c450-386f-427b-a5e5-763577425e5d', 'testuaser1@test.com', '12345678', '1', '2019-01-21T05:47:29.886Z', '2019-01-21T05:47:29.886Z'),
    ('eb9ce6ab-716f-4da6-9b9d-44329e64ab0c', 'testuaser2@test.com', '87654321', '1', '2019-01-21T05:47:29.886Z', '2019-01-21T05:47:29.886Z'),
    ('edabdd93-5871-4243-b118-df575c1a1b8c', 'testuaser3@test.com', '54321987', '1', '2019-01-21T05:47:29.886Z', '2019-01-21T05:47:29.886Z');

INSERT INTO app.verification (mail, code)
VALUES ('testuaser1@test.com', 'ad16c459-386f-427b-a5e5-763577425e5d')