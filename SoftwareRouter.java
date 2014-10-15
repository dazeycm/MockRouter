package edu.miamioh.cse283.lab6;


/**
 * Software router template for CSE283 Lab 6, FS2014.
 * 
 * @author dk
 */
public class SoftwareRouter {

	/**
	 * Adds the given link and [start,stop] address range to this router's routing table.
	 * 
	 * @param link is the link that packets in the [start,stop] address range should be forwarded to.
	 * @param start is the lower-bound of addresses for this link.
	 * @param stop is the upper-bound of addresses for this link.
	 */
	public void addLink(Link link, Address start, Address stop) {
	}
	
	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {
	}
	
	/**
	 * Packets that are received in this method are to be forwarded to the correct outgoing link.
	 * 
	 * @param pkt is the packet that needs to be forwarded.
	 */
	public void receivePacket(Packet pkt) {
		// once the correct outgoing link has been identified, call link.send(pkt, this); 
	}
}
