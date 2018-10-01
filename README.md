rest-api-doc
============


Simple library to document and test REST APIs.


Testing and Documenting REST API
--------------------------------

This library was created to facilitate the creation of living documentation for REST APIs by writing tests and snapshots which are inserted in templates.

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
   Server: Cowboy
   Connection: keep-alive
   Date: Mon, 01 Oct 2018 00:03:30 GMT
   Content-Type: application/json
   Content-Length: 187
   Via: 1.1 vegur
{
    "bookId": 1,
    "title": "Documenting and testing APIs with Rest Doc Maker",
    "tags": [
        {"tagId": 1, "name": "Programming"},
        {"tagId": 2, "name": "REST"}
    ]
}
```

This library leverages [REST-assured](http://rest-assured.io) as testing framework; hence, you can write tests and assert results. Moreover, it provides a filter (see: `SpecificationFilter`) and a parser (see: `SpecificationParser`) to export REST API calls as text, curl, or HTML; so, you can create request/response snippets which can be used in your favorite documentation format.