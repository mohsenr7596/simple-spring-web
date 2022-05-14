# Simple Spring Web Application

This is a simple app to show code Assessment.
Show some topics like:

* Clean code
* Multi-tier architecture(such as MVC)
* Unit and integration testing
* Logging
* Exception handling
* Documentation

, etc...



------------


The database of use in this project does not have **Data Migration** like flyway to simplify of project.

App has swagger Api _(use spring-doc)_ to generate API Doc for this project.

![img.png](readme/swagger.png)

This project uses an MVC design pattern.

![img.png](readme/mvc.png)

Some library and technology used in this project:
* Java 17
* Spring Web
* Spring Data
* Spring Batch
* Spring AOP
* H2 Database
* Hibernate ORM
* JUnit
* Lombok
* ...



------------


after the application started, the spring batch used spring rest-client query to another API service and get data from it and
inside local memory database.

now time we can use spring rest controller to crud of data in mem database.


------------


Some of rest point of this project is:

##### Posts API’s:
| Method |      Context       | Detail |
| :------------: |:------------------:| :------------: |
| GET |                    | Get all posts(with pagination) |
| GET | /posts/1 | Get post by post id | 
| GET | /posts/1/comments | Get comments of specific post by post id | 
| GET | /posts?title=eos | Get all posts that have the “eos” keyword in their title | 
| POST |  | Create a post | 
| PATCH | /posts/1 | Update a post by post id | 
| DELETE | /posts/1 | Delete a post by post id |

##### Comments API’s:
| Method |      Context       |                  Detail                  |
| :------------: |:------------------:|:----------------------------------------:|
| GET |                    |    Get all comments(with pagination)     |
| GET | /comments?postId=1 | Get comments of specific post by post id | 
| POST |  |            Create a comments             | 
| PATCH | /comments/1 |    Update a comment by postcomment id    | 
| DELETE | /comments/1 |  Delete a comment by comment id  |

##### ToDo’s API’s:
| Method | Context | Detail |
| :------------: | :------------: | :------------: |
| GET |   | Get all to-do’s |
| GET | /todos?userId=1&completed=true | Get to-do’s of specific user by user id and completed field | 
