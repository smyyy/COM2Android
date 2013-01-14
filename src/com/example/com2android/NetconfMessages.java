package com.example.com2android;

public class NetconfMessages {
	
	private String spinnerText;
	private String value;
	
	public NetconfMessages(String spinnerText, String value) {
		this.spinnerText = spinnerText;
		this.value = value;			
	}

    public String getSpinnerText() {
        return spinnerText;
    }

    public String getValue() {
        return value;
    }
    
    public String toString() {
        return spinnerText;
    }
	
		
	
	public final static String NC_HELLO = 
			"<hello xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">"
					+ 	"<capabilities>"
					+ 		"<capability>urn:ietf:params:netconf:base:1.0</capability>"
					+ 	"</capabilities>"
					+ "</hello>"
					+"]]>]]>";


	public final static String NC_GETCONFIG = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+ "<rpc xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\" message-id=\"1\">"
					+ 	"<get-config>"
					+ 		"<source>"
					+ 			"<running />"
					+ 		"</source>"
					+ 	"</get-config>"
					+ "</rpc>"
					+ "]]>]]>";
}

