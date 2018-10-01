rest-api-doc
============


Simple library to document and test REST APIs.


Testing and Documenting REST API
--------------------------------

This library was created to facilitate the creation of living documentation for REST APIs
by capturing testing data and converting it to snapshots which
can be inserted in document templates.

Example of a typical case:

```
SpecificationFilter filter = new SpecificationFilter();
RestAssured.given()
	.filter(filter)
	.header("headerKey", "headerValue")
	.formParam("formParamKey", "formParamValue")
	.post("http://www.mocky.io/v2/5a08a8cb3200000a0a138011");
SpecificationParser parser = new SpecificationParser(filter);
System.out.println(
		"----- Request -----\n"
		+ parser.requestAsCurl()
		+ "\n\n----- Response -----\n" 
		+ parser.responseAsText());
```


```
----- Request -----
curl -v -X POST \
-H "headerKey: headerValue" \
-H "Content-Type: application/x-www-form-urlencoded; charset=ISO-8859-1" \
-F "formParamKey=formParamValue" \
http://www.mocky.io/v2/5a08a8cb3200000a0a138011

----- Response -----
HTTP/1.1 200 OK
-- Headers:
   Content-Type: application/json
{
    "bookId": 1,
    "title": "Documenting and testing APIs with Rest API Doc",
    "tags": [
        {"tagId": 1, "name": "Programming"},
        {"tagId": 2, "name": "REST"}
    ]
}
```

This library leverages [REST-assured](http://rest-assured.io) as testing framework;
hence, you can write tests and assert results.
Moreover, it provides a filter (see: `SpecificationFilter`)
and a parser (see: `SpecificationParser`) to export REST API calls as plain text, curl, or HTML.
Therefore you can create request/response snippets which can be used in your favorite documentation format.
