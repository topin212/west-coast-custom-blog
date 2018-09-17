# Custom Blog

This project is a blog api made as a task for a company, which I will not name for ethical reasons.

# TL DR;

```bash
service start mariadb

git clone https://github.com/topin212/west-coast-custom-blog.git

cd west-coast-custom-blog

mysql -u {username} -p < schema.sql

mvn clean package

java -jar target/web-sboot-blog-1.4.jar
```

# In for the long part

### Prerequisites

You will need MySql or Mariadb in order for this to work (store users and posts).

This is very distribution dependent:
```
sudo apt-get install mariadb
```

Also you will need maven to assemble this project:
```
sudo apt-get install mvn
```

### Installing

1. Make sure you have mysql running
```
systemctl status mariadb
#or
service mariadb status
```

Run it if it is not running yet:
```
systemctl start mariadb
#or
service mariadb start
```

2. Make sure nothing is running on port 8080
```
sudo netstat -tnlp | grep 8080
```

3. Now you are ready to clone the repo:
```
git clone https://github.com/topin212/west-coast-custom-blog.git
```

4. Cd there:
```
cd west-coast-custom-blog
```

5. Set database structure:
```
mysql -u {username} -p < schema.sql
```

6. Assemble the project:
```
mvn package
```

7. Run the project:
```
java -jar target/web-sboot-blog-1.3.jar
```

# Playing around with API

Now you are free to use the api.
For example:

1. Create a new user:

```bash
curl http://localhost:8080/register -d "login=newUser&password=pass"
```

This will register a new Publisher and auto-generate a token for him.

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"login",
   "status":"success",
   "result":{
      "id":35,
      "name":"newUser",
      "registrationDate":{
         "timestamp":1537146724,
         "iso":"2018-09-17T01:12:04.672531Z"
      },
      "roleId":1,
      "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
   }
}
```

</p>
</details>
<hr/>

2. Write a post as that user:

```bash
curl -X PUT http://localhost:8080/post -H "token:{token}" -H Content-Type:application/json -d '{"postTitle":"My new Post", "postText" : "Hello World!"}'
```

This will return an object, representing a post you have just created.
<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"postCreation",
   "status":"success",
   "result":{
      "id":10,
      "publisher":{
         "id":35,
         "name":"newUser",
         "registrationDate":{
            "timestamp":1537146724,
            "iso":"2018-09-17T04:12:04+03:00"
         },
         "roleId":1,
         "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
      },
      "postTitle":"My new Post",
      "postText":"Hello World!",
      "postDate":{
         "timestamp":1537147028,
         "iso":"2018-09-17T01:17:08.290416Z"
      },
      "thumbsUpCount":0,
      "valid":true,
      "active":true
   }
}
```
</p>
</details>

<hr/>
3. View your posts:

```bash
curl "http://localhost:8080/post/own?page=0&amp;limit=10" -H "token:{token}"
```

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"postFetching",
   "status":"success",
   "result":{
      "content":[
         {
            "id":10,
            "publisher":{
               "id":35,
               "name":"newUser",
               "registrationDate":{
                  "timestamp":1537146724,
                  "iso":"2018-09-17T04:12:04+03:00"
               },
               "roleId":1,
               "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
            },
            "postTitle":"My new Post",
            "postText":"Hello World!",
            "postDate":{
               "timestamp":1537147028,
               "iso":"2018-09-17T04:17:08+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         }
      ],
      "pageable":{
         "sort":{
            "sorted":false,
            "unsorted":true
         },
         "pageSize":10,
         "pageNumber":0,
         "offset":0,
         "paged":true,
         "unpaged":false
      },
      "last":true,
      "totalPages":1,
      "totalElements":1,
      "sort":{
         "sorted":false,
         "unsorted":true
      },
      "first":true,
      "number":0,
      "numberOfElements":1,
      "size":10
   }
}
```
</p>
</details>

<hr/>
4. View all posts:

```bash
curl "http://localhost:8080/post?page=0&amp;limit=10" -H "token:{token}"
```
> Output will be exactly the same if we assume a fresh installation with no database.
For this example a populated database was used.

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"postFetching",
   "status":"success",
   "result":{
      "content":[
         {
            "id":1,
            "publisher":{
               "id":1,
               "name":"Petya",
               "registrationDate":{
                  "timestamp":1536803502,
                  "iso":"2018-09-13T04:51:42+03:00"
               },
               "roleId":1,
               "token":"Petya#0cc5ef3a-ed3f-491d-96e2-38521a1b7826"
            },
            "postTitle":"456",
            "postText":"765",
            "postDate":{
               "timestamp":1536956083,
               "iso":"2018-09-14T23:14:43+03:00"
            },
            "thumbsUpCount":1,
            "valid":true,
            "active":true
         },
         {
            "id":2,
            "publisher":{
               "id":1,
               "name":"Petya",
               "registrationDate":{
                  "timestamp":1536803502,
                  "iso":"2018-09-13T04:51:42+03:00"
               },
               "roleId":1,
               "token":"Petya#0cc5ef3a-ed3f-491d-96e2-38521a1b7826"
            },
            "postTitle":"testTitle1",
            "postText":"testPost1",
            "postDate":{
               "timestamp":1536803523,
               "iso":"2018-09-13T04:52:03+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":3,
            "publisher":{
               "id":2,
               "name":"Vasya",
               "registrationDate":{
                  "timestamp":1536803502,
                  "iso":"2018-09-13T04:51:42+03:00"
               },
               "roleId":1,
               "token":"\"\""
            },
            "postTitle":"user2TestTitle",
            "postText":"user2TestPost",
            "postDate":{
               "timestamp":1536803523,
               "iso":"2018-09-13T04:52:03+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":4,
            "publisher":{
               "id":2,
               "name":"Vasya",
               "registrationDate":{
                  "timestamp":1536803502,
                  "iso":"2018-09-13T04:51:42+03:00"
               },
               "roleId":1,
               "token":"\"\""
            },
            "postTitle":"321",
            "postText":"123",
            "postDate":{
               "timestamp":1536841167,
               "iso":"2018-09-13T15:19:27+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":5,
            "publisher":{
               "id":27,
               "name":"SeregaQ",
               "registrationDate":{
                  "timestamp":1537124018,
                  "iso":"2018-09-16T21:53:38+03:00"
               },
               "roleId":1,
               "token":"SeregaQ#fa0808ad-e6a1-4e9d-957c-f0c5f6d1c8df"
            },
            "postTitle":"My new Post",
            "postText":"Hello World!",
            "postDate":{
               "timestamp":1537124895,
               "iso":"2018-09-16T22:08:15+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":6,
            "publisher":{
               "id":27,
               "name":"SeregaQ",
               "registrationDate":{
                  "timestamp":1537124018,
                  "iso":"2018-09-16T21:53:38+03:00"
               },
               "roleId":1,
               "token":"SeregaQ#fa0808ad-e6a1-4e9d-957c-f0c5f6d1c8df"
            },
            "postTitle":"123",
            "postText":"Hello World312!",
            "postDate":{
               "timestamp":1537125604,
               "iso":"2018-09-16T22:20:04+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":false
         },
         {
            "id":7,
            "publisher":{
               "id":27,
               "name":"SeregaQ",
               "registrationDate":{
                  "timestamp":1537124018,
                  "iso":"2018-09-16T21:53:38+03:00"
               },
               "roleId":1,
               "token":"SeregaQ#fa0808ad-e6a1-4e9d-957c-f0c5f6d1c8df"
            },
            "postTitle":"123",
            "postText":"Hello World312!",
            "postDate":{
               "timestamp":1537128198,
               "iso":"2018-09-16T23:03:18+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":8,
            "publisher":{
               "id":27,
               "name":"SeregaQ",
               "registrationDate":{
                  "timestamp":1537124018,
                  "iso":"2018-09-16T21:53:38+03:00"
               },
               "roleId":1,
               "token":"SeregaQ#fa0808ad-e6a1-4e9d-957c-f0c5f6d1c8df"
            },
            "postTitle":"123",
            "postText":"Hello World312!",
            "postDate":{
               "timestamp":1537129178,
               "iso":"2018-09-16T23:19:38+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":false
         },
         {
            "id":9,
            "publisher":{
               "id":29,
               "name":"testUser",
               "registrationDate":{
                  "timestamp":1537132020,
                  "iso":"2018-09-17T00:07:00+03:00"
               },
               "roleId":1,
               "token":"testUser#98b76b67-bc6b-412a-902a-4e368d129b99"
            },
            "postTitle":"My new Post",
            "postText":"Hello World!",
            "postDate":{
               "timestamp":1537132131,
               "iso":"2018-09-17T00:08:51+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         },
         {
            "id":10,
            "publisher":{
               "id":35,
               "name":"newUser",
               "registrationDate":{
                  "timestamp":1537146724,
                  "iso":"2018-09-17T04:12:04+03:00"
               },
               "roleId":1,
               "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
            },
            "postTitle":"My new Post",
            "postText":"Hello World!",
            "postDate":{
               "timestamp":1537147028,
               "iso":"2018-09-17T04:17:08+03:00"
            },
            "thumbsUpCount":0,
            "valid":true,
            "active":true
         }
      ],
      "pageable":{
         "sort":{
            "sorted":false,
            "unsorted":true
         },
         "pageSize":10,
         "pageNumber":0,
         "offset":0,
         "paged":true,
         "unpaged":false
      },
      "last":true,
      "totalPages":1,
      "totalElements":10,
      "sort":{
         "sorted":false,
         "unsorted":true
      },
      "first":true,
      "number":0,
      "numberOfElements":10,
      "size":10
   }
}
```
</p>
</details>
<hr/>
5. View a post by id:

```bash
curl http://localhost:8080/post/10 -H {token}
```

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"postFetching",
   "status":"success",
   "result":{
      "id":10,
      "publisher":{
         "id":35,
         "name":"newUser",
         "registrationDate":{
            "timestamp":1537146724,
            "iso":"2018-09-17T04:12:04+03:00"
         },
         "roleId":1,
         "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
      },
      "postTitle":"My new Post",
      "postText":"Hello World!",
      "postDate":{
         "timestamp":1537147028,
         "iso":"2018-09-17T04:17:08+03:00"
      },
      "thumbsUpCount":0,
      "valid":true,
      "active":true
   }
}
```
</p>
</details>
<hr/>
6. Update your post:


```bash
curl -X PUT http://localhost:8080/post/10 -H {token} -H Content-Type:application/json -d '{"postTitle":"Updated post title", "postText" : "Updated post Text"}'
```

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"postUpdating",
   "status":"success",
   "result":{
      "id":10,
      "publisher":{
         "id":35,
         "name":"newUser",
         "registrationDate":{
            "timestamp":1537146724,
            "iso":"2018-09-17T04:12:04+03:00"
         },
         "roleId":1,
         "token":"newUser#cedbc64f-4e2e-4e00-8db2-e4ec03e80c5a"
      },
      "postTitle":"Updated post title",
      "postText":"Updated post Text",
      "postDate":{
         "timestamp":1537158253,
         "iso":"2018-09-17T04:24:13.840308Z"
      },
      "thumbsUpCount":0,
      "valid":true,
      "active":true
   }
}
```

</p>
</details>
<hr/>
7. Delete your post:


```bash
curl -X DELETE http://localhost:8080/post/10 -H {token}
```

<details><summary>Response example:</summary>
<p>

```json
{
   "procedure":"orderDeletion",
   "status":"success",
   "result":"success"
}
```
</p>
</details>

Please, note that this WILL NOT delete your post, it will only mark it as an inactive post.

## Deployment

### THIS IS IMPORTANT

It's highly recommended to run this behind a reverse proxy, e.g. nginx to provide a secure connection. Use with extreme care on unprotected connections!
