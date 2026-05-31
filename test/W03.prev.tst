yyyy-mm-ddThh:mm:ss.sss+1000 ERROR Server returned HTTP response code: 500 for URL: http://localhost:8080/common/servlet?view=index&param1=null, parmURI="http://localhost:8080/common/servlet?view=index&param1=null", enc="UTF-8"

java.io.IOException: Server returned HTTP response code: 500 for URL: http://localhost:8080/common/servlet?view=index&param1=null
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream0(HttpURLConnection.java:1924) ~[?:?]
	at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1520) ~[?:?]
	at org.teherba.common.URIReader.<init>(URIReader.java:307) [common.jar:?]
	at org.teherba.common.URIReader.<init>(URIReader.java:210) [common.jar:?]
	at org.teherba.common.RegressionTester.runTests(RegressionTester.java:674) [common.jar:?]
	at org.teherba.common.RegressionTester.main(RegressionTester.java:751) [common.jar:?]
Server returned HTTP response code: 500 for URL: http://localhost:8080/common/servlet?view=index&param1=null
