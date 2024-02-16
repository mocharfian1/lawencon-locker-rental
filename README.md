## Postman Link: 
### Test Collection
**Postman Collection:** [Lawencon Test Collection](https://www.postman.com/mocharfian1/workspace/lawencon/collection/6308048-a175af2d-0b1a-4ef3-aca8-e50a252e469a?action=share&source=collection_link&creator=6308048)

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
