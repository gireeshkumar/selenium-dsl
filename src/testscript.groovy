def urls =[
			["http://google.com","IE"],
			["http://google.co.in","IE"]
		   ];

urls.each {
	println it[0]+" -> "+it[1];
}