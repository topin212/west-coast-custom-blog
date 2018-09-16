# Custom Blog

This project is a blog api made as a task for a company, which I will not name due to politeness reasons.

## TL DR;

```
systemctl start mariadb
#or
service start mariadb

git clone https://github.com/topin212/west-coast-custom-blog.git

cd west-coast-custom-blog

mvn clean package spring-boot:repackage

java -jar target/web-sboot-blog-1.3.jar
```

## In for the long part

### Prerequisites

You will need MySql or Mariadb in order for this to work (store users and posts).

This is very distribution dependent:
```
sudo apt-get install mariadb
```

### Installing

1. Install maven:
```
sudo apt-get install mvn
```

2. Install mysql/mariadb
```
sudo apt-get install mariadb
```

3. Make sure you have mysql running
```
systemctl status mariadb
#or
service mariadb status
```

4. Make sure nothing is running on port 8080
```
sudo netstat -tnlp | grep 8080
```

5. Now you are ready to clone the repo:
```
git clone https://github.com/topin212/west-coast-custom-blog.git
```

6. Cd there:
```
cd west-coast-custom-blog
```

7. Assemble the project:
```
mvn package
```

8. Run the project:
```
java -jar target/web-sboot-blog-1.3.jar
```

Now you are free to use the api.
For example:

1. Create a new user:
```
curl http://localhost:8080/register -d "login=Senya&password=test1"
```

This will register a new Publisher and auto-generate a token for him.

Response example:
```json
{"id":5,"name":"Sema","registrationDate":{"year":2018,"monthValue":9,"dayOfMonth":16,"hour":18,"minute":3,"second":2,"nano":592779000,"month":"SEPTEMBER","dayOfYear":259,"dayOfWeek":"SUNDAY","chronology":{"calendarType":"iso8601","id":"ISO"}},"roleId":1,"token":"Sema#ddb72edf-221d-4c7b-93b3-d547a95987fe"}
```

2. Write a post as that user:
```

```

3. View your posts:
```

```

4. View all posts:
```

```

5. Update your posts:
```

```

6. Cmon, who names a post 123? Delete it!
```

```

7. Choose a number and try to get a post with that id:
```

```

## Deployment

### THIS IS IMPORTANT

It's highly recommended to run this behind a reverse proxy, e.g. nginx to provide a secure connection, since authorisation type being used is HttpBasic, which is not even encoded, so use with extreme care on plainText connections!

### P.S.

It was probably intended for me to use some kind of token authorisation (e.g. JWT), that's why login page was required to be permitted for access by anybody.

But since the mechanism of token exchange is pretty much the same (except the client is able to generate a HttpBasic authorisation header), I chose to abandon this idea.

yeah, I disobeyed, I admit it. Ask me why :)