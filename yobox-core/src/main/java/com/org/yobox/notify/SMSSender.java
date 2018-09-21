package com.org.yobox.notify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import org.smpp.Data;
import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.SmppObject;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindReceiver;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.PDU;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.Unbind;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.WrongLengthOfStringException;
import org.smpp.util.Queue;





public class SMSSender {
	
	private static Logger logger = (Logger) Logger.getInstance(SMSSender.class);
	
	
	private static SMSSender fSMSSender = null;

	/**
	 * This is the SMPP session used for communication with SMSC.
	 */
	static Session session = null;

	/**
	 * If the application is bound to the SMSC.
	 */
	boolean bound = false;

	/**
	 * Address of the SMSC.
	 */
	String ipAddress = "50.30.37.177";

	/**
	 * The port number to bind to on the SMSC server.
	 */
	int port = 2885;

	/**
	 * The name which identifies you to SMSC.
	 */
	String systemId = "fshtgmks";

	/**
	 * The password for authentication to SMSC.
	 */
	String password = "fshtg582";

	/**
	 * How you want to bind to the SMSC: transmitter (t), receiver (r) or
	 * transciever (tr). Transciever can both send messages and receive
	 * messages. Note, that if you bind as receiver you can still receive
	 * responses to you requests (submissions).
	 */
	String bindOption = "t";

	/**
	 * Indicates that the Session has to be asynchronous.
	 * Asynchronous Session means that when submitting a Request to the SMSC
	 * the Session does not wait for a response. Instead the Session is provided
	 * with an instance of implementation of ServerPDUListener from the smpp
	 * library which receives all PDUs received from the SMSC. It's
	 * application responsibility to match the received Response with sended Requests.
	 */
	boolean asynchronous = false;

	/**
	 * This is an instance of listener which obtains all PDUs received from the SMSC.
	 * Application doesn't have explicitly call Session's receive() function,
	 * all PDUs are passed to this application callback object.
	 * See documentation in Session, Receiver and ServerPDUEventListener classes
	 * form the SMPP library.
	 */
	SMPPTestPDUEventListener pduListener = null;

	/**
	 * The range of addresses the smpp session will serve.
	 */
	AddressRange addressRange = new AddressRange();

	/*
	 * for information about these variables have a look in SMPP 3.4
	 * specification
	 */
	String systemType = "";
	String serviceType = "";
	Address sourceAddress = new Address();
	Address destAddress = new Address();
	String scheduleDeliveryTime = "";
	String validityPeriod = "";
	String shortMessage = "";
	int numberOfDestination = 1;
	String messageId = "";
	byte esmClass = 0;
	byte protocolId = 0;
	byte priorityFlag = 0;
	byte registeredDelivery = 0;
	byte replaceIfPresentFlag = 0;
	byte dataCoding = 0;
	byte smDefaultMsgId = 0;

	/**
	 * If you attemt to receive message, how long will the application
	 * wait for data.
	 */
	long receiveTimeout = Data.RECEIVE_BLOCKING;

	private SMSSender() {

	}

	/**
	 * default mail host will be localhost
	 * 
	 * @return
	 */
	public static SMSSender getSoleInstance() {
		if (fSMSSender == null) {
			fSMSSender = new SMSSender();
		}
		return fSMSSender;
	}
	
	
	public void sendMessage(String toAddress, String content) {
		//bind, submit and unbind.
		bind();
		submit(toAddress,content);
		unbind();
	}
	
	
	
	/**
	 * The first method called to start communication
	 * betwen an ESME and a SMSC. A new instance of <code>TCPIPConnection</code>
	 * is created and the IP address and port obtained from user are passed
	 * to this instance. New <code>Session</code> is created which uses the created
	 * <code>TCPIPConnection</code>.
	 * All the parameters required for a bind are set to the <code>BindRequest</code>
	 * and this request is passed to the <code>Session</code>'s <code>bind</code>
	 * method. If the call is successful, the application should be bound to the SMSC.
	 * See "SMPP Protocol Specification 3.4, 4.1 BIND Operation."
	 * @see BindRequest
	 * @see BindResponse
	 * @see TCPIPConnection
	 * @see Session#bind(BindRequest)
	 * @see Session#bind(BindRequest,ServerPDUEventListener)
	 */
	private void bind() {
		
		
		logger.warn("22222222222   inside bind() method");
		try {
			if (bound) {
				return;
			}
			BindRequest request = null;
			BindResponse response = null;
			String syncMode;

			// type of the session
			syncMode = "sync";
			if (syncMode.compareToIgnoreCase("sync") == 0) {
				asynchronous = false;
			} else {
				asynchronous = true;
			}
			
			

			// input values
			//bindOption = "t";

			if (bindOption.compareToIgnoreCase("t") == 0) {
				request = new BindTransmitter();
			} else if (bindOption.compareToIgnoreCase("r") == 0) {
				request = new BindReceiver();
			} else if (bindOption.compareToIgnoreCase("tr") == 0) {
				request = new BindTransciever();
			} else {
				return;
			}
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
			connection.setReceiveTimeout(20*1000);
			session = new Session(connection);

			// set values
		
			request.setSystemId(systemId);
			request.setPassword(password);
			request.setSystemType(systemType);
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);

			// send the request
			if (asynchronous) {
				pduListener = new SMPPTestPDUEventListener(session);
				response = session.bind(request, pduListener);
			} else {
				response = session.bind(request);
				System.out.println("****************"+response.getCommandStatus());
				logger.warn("333   Not synchronous  response "+response.getCommandStatus());
			}
			
			if (response.getCommandStatus() == Data.ESME_ROK) {
				bound = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Creates a new instance of <code>SubmitSM</code> class, lets you set
	 * subset of fields of it. This PDU is used to send SMS message
	 * to a device.
	 *
	 * See "SMPP Protocol Specification 3.4, 4.4 SUBMIT_SM Operation."
	 * @see Session#submit(SubmitSM)
	 * @see SubmitSM
	 * @see SubmitSMResp
	 */
	private void submit(String toAddress, String content) {
		try {
			SubmitSM request = new SubmitSM();
			SubmitSMResp response;

			
			// input values
			serviceType = "";
			sourceAddress = getAddress("source",sourceAddress,null);
			destAddress = getAddress("destination",destAddress,toAddress);
//			replaceIfPresentFlag = getParam("replace if present flag", replaceIfPresentFlag);
			shortMessage = content;
//			scheduleDeliveryTime = getParam("Schedule delivery time", scheduleDeliveryTime);
//			validityPeriod = getParam("Validity period", validityPeriod);
//			esmClass = getParam("Esm class", esmClass);
//			protocolId = getParam("Protocol id", protocolId);
//			priorityFlag = getParam("Priority flag", priorityFlag);
//			registeredDelivery = getParam("Registered delivery", registeredDelivery);
//			dataCoding = getParam("Data encoding", dataCoding);
//			smDefaultMsgId = getParam("Sm default msg id", smDefaultMsgId);
			// set values
			request.setServiceType(serviceType);
			request.setSourceAddr(sourceAddress);
			request.setDestAddr(destAddress);
			request.setReplaceIfPresentFlag(replaceIfPresentFlag);
			request.setShortMessage(shortMessage,Data.ENC_ASCII);
			request.setScheduleDeliveryTime(scheduleDeliveryTime);
			request.setValidityPeriod(validityPeriod);
			request.setEsmClass(esmClass);
			request.setProtocolId(protocolId);
			request.setPriorityFlag(priorityFlag);
			request.setRegisteredDelivery(registeredDelivery);
			request.setDataCoding(dataCoding);
			request.setSmDefaultMsgId(smDefaultMsgId);

			// send the request

			int count = 1;
			System.out.println();
//			count = getParam("How many times to submit this message (load test)", count);
			for (int i = 0; i < count; i++) {
				request.assignSequenceNumber(true);
				if (asynchronous) {
					session.submit(request);
					System.out.println();
				} else {
					response = session.submit(request);
					System.out.println("DONE ########"+response.getCommandStatus());
					System.out.println("DONE ##%%%%%%#####"+response.getMessageId());
					
					messageId = response.getMessageId();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	/**
	 * Ubinds (logs out) from the SMSC and closes the connection.
	 *
	 * See "SMPP Protocol Specification 3.4, 4.2 UNBIND Operation."
	 * @see Session#unbind()
	 * @see Unbind
	 * @see UnbindResp
	 */
	private void unbind() {
		try {
			if (!bound) {
				return;
			}
			// send the request
			if (session.getReceiver().isReceiver()) {
				System.out.println("It can take a while to stop the receiver.");
			}
			UnbindResp response = session.unbind();
			System.out.println("Unbind response " + response.debugString());
			bound = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Gets the address value.
	 */
	private Address getAddress(String type, Address address, String toAddress)
							throws WrongLengthOfStringException {
		byte ton = Byte.parseByte("1");
		byte npi = Byte.parseByte("1");
		String addr;
		if (type.equalsIgnoreCase("source")) {
			addr = "javademocode";
		} else {
			/*addr="918606074321";*/
			addr = doDestAddressSanityCheck(toAddress);
			
			logger.warn("log becomes toAddress  222222"+addr);
		}
		address.setTon(ton);
		address.setNpi(npi);
		address.setAddress(addr, Data.SM_ADDR_LEN);
		return address;
	}
	
	
	/**
	 * Gets the address from the application and returns the corrected adderss or
	 * default address
	 */
	private String doDestAddressSanityCheck(String destAddressComingFromApplication) {
		// No value
		if (destAddressComingFromApplication == null) {
			
			logger.warn("log becomes toAddress  3333"+destAddressComingFromApplication);
			return "919999999999";
		}
		
		// Check if it is a numeric value 
		try {
			Long.parseLong(destAddressComingFromApplication);
		} catch (Exception e) {
			
			logger.warn("log becomes toAddress  4444444"+destAddressComingFromApplication);
			return "919999999999";
		}
		// Check if it is mobile number. Remember currently we are sending it to only mobile 
		// phones and only indian mobile phones.
		// First check the length. It has to be at least 10 or more digits.
		// 10 Digits means it is only a mobile number. So 91 (india country code) has to be added.
		// 11 Digits means it has 0 in front of it, which needs to be removed.
		// 12 or more digits means it has digits of country code + 10 digits of mobile number, 
		// Also check if it is 12 digits first two digits must be 91. 
		// For 11 digits first digit must be 0. Else it is a wrong number.
		// For the third digit must be 9 (as in 9198100012345).
		if (destAddressComingFromApplication.length() < 10) {
			logger.warn("log becomes toAddress  555555"+destAddressComingFromApplication);
			return "919999999999";
		} else {
			
			logger.warn("log becomes toAddress  66666");
			if (destAddressComingFromApplication.length() == 10) {
				
				logger.warn("log becomes toAddress  77777777");
				// make it 12 digits
				destAddressComingFromApplication = destAddressComingFromApplication;
			}
			if (destAddressComingFromApplication.length() == 11 && destAddressComingFromApplication.startsWith("09")) {
				logger.warn("log becomes toAddress  888888888");
				// Remove leading zero, make it 12 digits
				try {
					logger.warn("log becomes toAddress  999999999999");
					String tmpNumber = destAddressComingFromApplication.substring(1,destAddressComingFromApplication.length());
					destAddressComingFromApplication = tmpNumber;
				} catch (Exception e) {
					logger.warn("log becomes toAddress  000000000000"+e.getMessage());
					e.printStackTrace();
				}
			}
			if (destAddressComingFromApplication.length() == 12 && destAddressComingFromApplication.startsWith("91")) {
				{
					logger.warn("log becomes toAddress  ***********");
					// return this
					return destAddressComingFromApplication;
				}
			}
			
			logger.warn("log becomes toAddress  #############");
			return "919999999999";
		}
	}
	
	
	
	/**
	 * Implements simple PDU listener which handles PDUs received from SMSC.
	 * It puts the received requests into a queue and discards all received
	 * responses. Requests then can be fetched (should be) from the queue by
	 * calling to the method <code>getRequestEvent</code>.
	 * @see Queue
	 * @see ServerPDUEvent
	 * @see ServerPDUEventListener
	 * @see SmppObject
	 */
	private class SMPPTestPDUEventListener extends SmppObject implements ServerPDUEventListener {
		Session session;
		Queue requestEvents = new Queue();

		public SMPPTestPDUEventListener(Session session) {
			this.session = session;
		}

		public void handleEvent(ServerPDUEvent event) {
			PDU pdu = event.getPDU();
			if (pdu.isRequest()) {
				System.out.println("async request received, enqueuing " + pdu.debugString());
				synchronized (requestEvents) {
					requestEvents.enqueue(event);
					requestEvents.notify();
				}
			} else if (pdu.isResponse()) {
				System.out.println("async response received " + pdu.debugString());
			} else {
				System.out.println(
					"pdu of unknown class (not request nor " + "response) received, discarding " + pdu.debugString());
			}
		}

		/**
		 * Returns received pdu from the queue. If the queue is empty,
		 * the method blocks for the specified timeout.
		 */
		public ServerPDUEvent getRequestEvent(long timeout) {
			ServerPDUEvent pduEvent = null;
			synchronized (requestEvents) {
				if (requestEvents.isEmpty()) {
					try {
						requestEvents.wait(timeout);
					} catch (InterruptedException e) {
						// ignoring, actually this is what we're waiting for
					}
				}
				if (!requestEvents.isEmpty()) {
					pduEvent = (ServerPDUEvent) requestEvents.dequeue();
				}
			}
			return pduEvent;
		}
	}

}
