## Database Design

### User Table

| Column Name  | Data Type     | Constraints  |
|--------------|---------------|--------------|
| id           | INT           | PRIMARY KEY  |
| phone_number | VARCHAR(255)  | NOT NULL     |
| ktp          | VARCHAR(255)  | NOT NULL     |
| email        | VARCHAR(255)  | NOT NULL     |
| deposit      | INT           |              |

### Locker Table

| Column Name       | Data Type     | Constraints  |
|-------------------|---------------|--------------|
| id                | INT           | PRIMARY KEY  |
| locker_number     | VARCHAR(255)  | NOT NULL     |
| user_phone_number | VARCHAR(255)  |              |
| booking_time      | TIMESTAMP     |              |
