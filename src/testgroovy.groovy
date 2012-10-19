def urls =[
			"http://google.com",
			"http://google.co.in"
		   ];

urls.each {
	
	data "testcase.url", it;
	runTest "googletest1.tc.tsl"
	
}