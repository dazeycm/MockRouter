package edu.miamioh.cse283.lab6;

import java.util.ArrayList;


/**
 * Software router template for CSE283 Lab 6, FS2014.
 * 
 * @author dk
 */
public class SoftwareRouter1 {

	/*
	 * Things we will need:
	 * 
	 * data structure to store entries for the forwarding table
	 * 		possibly use an inner class? to hold (link, network address, subnet mask)
	 * some way to calculate the longest prefix match:
	 * 		1) does a network address match a packet's address?
	 * 			do the subnet mask number of bits of a network's address == the subnet mask number of bits of a packet's address?
	 * 		2) calculate the maximum prefix == greatest subnet mask of matched networks
	 * add link: add an entry into the forwarding table (link, network, subnet mask)
	 * remove link: removes a link from the forwarding table
	 * receive packet: calculate the longest prefix match, then send a packet on that link
	 */
	
	public class TableEntry{
		private Link link;
		private Address address;
		private int subnetMask;
		private int subnetBits;
		
		public TableEntry (Link l, Address a, int sm){
			this.link = l;
			this.address = a;
			this.subnetMask = 0;
			this.subnetBits = sm;
			
			for (int count = 0; count < sm; count++){
				this.subnetMask = (this.subnetMask >> 1) | 0x10000000;
			}
		}
		
		public Link getLink(){
			return this.link;
		}
		
		public Address getAddress(){
			return this.address;
		}
		
		public int getSubnetMask(){
			return this.subnetMask;
		}
		
		public int getSubnetBits(){
			return this.subnetBits;
		}
	}
	
	protected ArrayList<TableEntry> fwdTable = new ArrayList<TableEntry>();
	
	/**
	 * Adds the given link and (network, subnet mask) to this router's routing table.
	 * 
	 * @param link is the link that packets should be forwarded to.
	 * @param network_address is the network address for this link.
	 * @param subnet_mask is the number of bits for the network prefix. 
	 */
	public void addLink(Link link, Address network_address, int subnet_mask) {
		fwdTable.add(new TableEntry(link, network_address, subnet_mask));
	}
	
	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {
		for (int count = 0; count < fwdTable.size(); count++){
			if (fwdTable.get(count).getLink() == link){
				fwdTable.remove(count);
				break;
			}
		}
	}
	
	/**
	 * Packets that are received in this method are to be forwarded to the correct outgoing link.
	 * 
	 * @param pkt is the packet that needs to be forwarded.
	 */
	public void receivePacket(Packet pkt) {
		// once the correct outgoing link has been identified, call link.send(pkt, this);
		int pktAddress = pkt.getDestination().getIP();
		int indexNum = -1;
		int longestNum = -1;
		int temp = -1;
		
		for(int count = 0; count < fwdTable.size(); count++){
			temp = fwdTable.get(count).getSubnetMask() & pktAddress;
			if (temp == fwdTable.get(count).getAddress().getIP() && fwdTable.get(count).getSubnetBits() > longestNum){
				longestNum = fwdTable.get(count).getSubnetBits();
				indexNum = count;
			}
		}
		if (longestNum > -1 && indexNum > -1){
			fwdTable.get(indexNum).getLink().send(pkt);
		}
	}
}
