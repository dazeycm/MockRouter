package edu.miamioh.cse283.lab6;

import java.util.ArrayList;


/**
 * Software router template for CSE283 Lab 6, FS2014.
 * 
 * @author dk
 */
public class SoftwareRouter {
	
	// data structure to store entries for forwarding table
	// inner class? to hold (link, network_address, subnetmask)
	// calculate longest prefix match:
	//		1) does a network_address match a packet's address
	//		   do the subnet_mask # of bits of a network's address == the subnet mask # of bits of a packet's address
	//		2) calculate maximum prefix == greatest subnet mask of matched networks
	// this will give link to send packet on
	
	// addLink: adds an entry to the forwarding table (link, networkaddress, subnetmask)
	// removeLink: removes a link from the table
	// receive packet: calculate longest prefix match; send packet on that link

	protected ArrayList<tableEntry> fwdTable = new ArrayList<tableEntry>();	//contains IP range and link
	
	public class tableEntry	{

		public Link link;
		public Address network_address;
		public int subnet_mask;
		
		public tableEntry(Link link, Address network_address, int subnet_mask) {
			this.link = link;
			this.network_address = network_address;
			this.subnet_mask = subnet_mask;
		}
	}
	
	/**
	 * Adds the given link and [start,stop] address range to this router's routing table.
	 * 
	 * @param link is the link that packets in the [start,stop] address range should be forwarded to.
	 * @param network_address is the network address for this link.
	 * @param subnet_mask is the number of bits for the network prefix.
	 */
	public void addLink(Link link, Address network_address, int subnet_mask) {
		fwdTable.add(new tableEntry(link, network_address, subnet_mask));
	}
	
	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {
		for ( tableEntry i : fwdTable)	{
			if (i.link == link)	{
				fwdTable.remove(i);
			}
		}
	}
	
	/**
	 * Packets that are received in this method are to be forwarded to the correct outgoing link.
	 * 
	 * @param pkt is the packet that needs to be forwarded.
	 */
	public void receivePacket(Packet pkt) {
		//once the correct outgoing link has been identified, call link.send(pkt, this); 
		//row by row go through fwdtable and see if first (subnet_mask) # of bits = first number of bits of destination
		//if it find a match, send packet out on that link
		//can't match longer than subnet mask
		//need to look at bits up to subnetmask length in fwdtable and match to destination
		
		//need to change this to only look up until subnetmask number of bits
		for (tableEntry i : fwdTable)	{
			matchingBits(i.network_address.ip, pkt.src.ip); 
		}
		
		System.out.println("Packet sent");
	}
	
	//might need to change this
	public static int matchingBits(int a, int b)	{
		int result = a ^ b;
		int count = 0;
		while (result != 0)	{
			result = result >> 1;
			count++;
		}
		return 32 - count;
		
	}
}
