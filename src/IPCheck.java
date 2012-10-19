import java.net.InetAddress;


public class IPCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		InetAddress addr = InetAddress.getByName("3.179.13.40");
		
		System.out.println(addr.getHostAddress()+" : "+addr.isLinkLocalAddress());
		
		InetAddress addr1 = InetAddress.getByName("10.10.131.84");
		System.out.println(addr1.getHostAddress()+" : "+addr1.isAnyLocalAddress());
		
		InetAddress local = InetAddress.getLocalHost();
		System.out.println(local.getHostAddress());
		
		 InetAddress[] all = InetAddress.getAllByName(local.getHostName());
		 for (int i=0; i<all.length; i++) {
		     System.out.println("  address = " + all[i].getHostAddress());
		 }
	}

	/** Converts a byte_array of octets into a string */
	public static String byteToStr( byte[] byte_arr )
	{
		StringBuffer internal_buffer = new StringBuffer();

		// Keep looping, and adding octets to the IP Address
		for (int index = 0; index < byte_arr.length -1; index++)
		{
			internal_buffer.append ( String.valueOf(byte_arr[index]) + ".");
		}
		
		// Add the final octet, but no trailing '.'
		internal_buffer.append ( String.valueOf (byte_arr.length) );

		return internal_buffer.toString();
	}
}
