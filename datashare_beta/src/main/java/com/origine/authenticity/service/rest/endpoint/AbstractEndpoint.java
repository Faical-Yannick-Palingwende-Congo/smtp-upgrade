package com.origine.authenticity.service.rest.endpoint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.origine.authenticity.service.rest.entity.Account;
import com.origine.authenticity.service.rest.entity.HeadInbox;
import com.origine.authenticity.service.rest.entity.HeadOutbox;
import com.origine.authenticity.service.rest.entity.Inbox;
import com.origine.authenticity.service.rest.entity.Index;
import com.origine.authenticity.service.rest.entity.Outbox;
import com.origine.authenticity.service.rest.entity.Session;
import com.origine.authenticity.service.rest.utils.AES;
import com.origine.authenticity.service.rest.utils.Configuration;
import com.origine.authenticity.service.rest.utils.DBAdapter;
import com.origine.authenticity.service.rest.utils.Logger;
import com.origine.authenticity.service.rest.utils.exception.TechnicalUtilsException;

public abstract class AbstractEndpoint {
	public static final Logger LOGGER = new Logger();
	public static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
	public LinkedList<Account> accounts;
	public LinkedList<Index> indexes;
	public LinkedList<Session> sessions;
	public LinkedList<HeadInbox> headinboxes;
	public LinkedList<Inbox> inboxes;
	public LinkedList<HeadOutbox> headoutboxes;
	public LinkedList<Outbox> outboxes;
	public static String SERVICE_KEY_RUNTIME = "813ee4ca10d6617437be8a0d6b371a799cdbe2150faf904614d375eb2026504b";
	
	static Configuration confloader = null;
	static {
		confloader = new Configuration();
		try {
			confloader.loadProperties("internal.service.properties");
		} catch (TechnicalUtilsException e) {
			LOGGER.pushErrors(e, "Internal properties file not found.");
		}
	}
	final String DATABASE_NAME = confloader.getLoader().getProperty("database.name.value");
	final String LOGIN_NAME = confloader.getLoader().getProperty("user.login.value");
	final String LOGIN_PASSWORD = confloader.getLoader().getProperty("user.password.value");

	public DBAdapter adapter = new DBAdapter("jdbc:mysql://localhost/" + DATABASE_NAME, LOGIN_NAME, LOGIN_PASSWORD, LOGGER);
	
	public int email2Account(String email){
		int id = 0;
		for(Account account: this.accounts){
			if(account.getEmail().equals(email)){
				id = Integer.parseInt(account.getId());
				break;
			}
		}
		return id;
	}
	
	public LinkedList<HeadInbox> StringToHeadInboxes(String content) {
		String rows[] = content.split("\n");
		LinkedList<HeadInbox> contents = new LinkedList<HeadInbox>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			HeadInbox headinbox = new HeadInbox(fields[0], fields[1], fields[2], fields[3], fields[4]);
			contents.add(headinbox);
		}
		return contents;
	}
	
	public LinkedList<Inbox> StringToInboxes(String content) {
		String rows[] = content.split("\n");
		LinkedList<Inbox> contents = new LinkedList<Inbox>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Inbox inbox = new Inbox(fields[0], fields[1], fields[2], fields[3]);
			contents.add(inbox);
		}
		return contents;
	}
	
	public LinkedList<HeadOutbox> StringToHeadOutboxes(String content) {
		String rows[] = content.split("\n");
		LinkedList<HeadOutbox> contents = new LinkedList<HeadOutbox>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			HeadOutbox headoutbox = new HeadOutbox(fields[0], fields[1], fields[2], fields[3], fields[4]);
			contents.add(headoutbox);
		}
		return contents;
	}
	
	public LinkedList<Outbox> StringToOutboxes(String content) {
		String rows[] = content.split("\n");
		LinkedList<Outbox> contents = new LinkedList<Outbox>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Outbox friend = new Outbox(fields[0], fields[1], fields[2], fields[3]);
			contents.add(friend);
		}
		return contents;
	}
	
	public LinkedList<Account> StringToAccounts(String content) {
		String rows[] = content.split("\n");
		LinkedList<Account> contents = new LinkedList<Account>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Account account = new Account(fields[0], fields[1], fields[2], fields[3]);
			contents.add(account);
		}
		return contents;
	}

	
	public LinkedList<Index> StringToIndexes(String content) {
		String rows[] = content.split("\n");
		LinkedList<Index> contents = new LinkedList<Index>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Index index = new Index(fields[0], fields[1], fields[2]);
			contents.add(index);
		}
		return contents;
	}
	
	public LinkedList<Session> StringToSessions(String content) {
		String rows[] = content.split("\n");
		LinkedList<Session> contents = new LinkedList<Session>();
		for (String row : rows) {
			String fields[] = row.split(",");
			if (fields == null || row.equals(""))
				break;
			Session session = new Session(fields[0], fields[1], fields[2], fields[3], fields[4]);
			contents.add(session);
		}
		return contents;
	}
	
	public void sendMail(String type, String from, String to, String subject, String content) {
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			if (type.equals("html"))
				message.setHeader("Content-Type", "text/html");
			message.setFrom(new InternetAddress(from));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(from));
			message.setSubject(subject);
			if (type.equals("html"))
				message.setContent(content, "text/html; charset=ISO-8859-1");
			else
				message.setText(content);
			Transport.send(message);
			LOGGER.pushDebugs("Sent message successfully....");
		} catch (MessagingException mex) {
			LOGGER.pushWarnings(mex,"Email sending failure. Message problem." + mex.getMessage());
		}
	}
	
	//I have to check the sanity of the request after decryption
	//The package encryption will be AES 512
	//The package maximum payload will be 512 bits
	
	// Used for request encryption
	public String aes256Encrypt(String plaintext, String encryptionKey){
		byte[] cipher;
		String encrypted = "";
		try {
			cipher = AES.encrypt(packager(plaintext), encryptionKey);
			encrypted = byteToHex(cipher);
		} catch (Exception e) {
			LOGGER.pushErrors(e, "Encryption failed.");
		}
		return encrypted;
	}
	
	public String aes256Decrypt(String encrypted, String encryptionKey){
	    String decrypted = "";
		try {
			decrypted = AES.decrypt(HexTobyte(encrypted), encryptionKey);
		} catch (Exception e) {
			LOGGER.pushErrors(e, "Decryption failed.");
		}
		return decrypted.split("~")[0];
	}
	
	Index getIndex(String table){
		Index index = null;
		for(Index idx: this.indexes){
			if(idx.getTable().equals(table)){
				index = idx;
			}
		}
		return index;
	}
	
	public String packager(String data){
		  int complete = data.length() % 16;
		  String result = data;
		  if(complete >= 1) result+= "~";
		  for(int i = 0; i<(15-complete);i++){
			result+="0";
		  }
		  System.out.println(result);
		  return result;
	  }
	
	public String renewKey(String email, Date stamp, String state) {
		String provided = new String(dateFormat.format(stamp));
		String key = "";
		key = generateKey(email, provided);
		Account account = getAccountfromEmail(email);
		adapter.sync("session", "account", account.getId(), "sess_key,stamp,state",key+","+ provided+ ","+ state);
		return key;
	}
	
	public boolean isSessionExpired(String olddate, Date newer) {
		boolean expired = true;
		try {
			Date older = dateFormat.parse(olddate);
			long time = newer.getTime() - older.getTime();
			double mins10 = (double) time / 600000D;
			if (mins10 <= 1.0D)
				expired = false;
		} catch (ParseException e) {
			LOGGER.pushErrors(e, "Not able to convert the saved date into Date instance");
		}
		return expired;
	}
	
	public Account getAccountfromId(String id){
		for(Account account: accounts){
			if(account.getId().equals(id)) return account;
		}
		return null;
	}

	public Account getAccountfromEmail(String email) {
		for(Account account: accounts){
			if(account.getEmail().equals(email)) return account;
		}
		return null;
	}
	
	public Session account2Session(String account) {
		for (Session session : this.sessions) {
			if (session.getAccount().equals(account)) {
				return session;
			}
		}
		return null;
	}

	public Session key2Session(String key) {
		for (Session session : this.sessions) {
			if (session.getSessKey().equals(key)) {
				return session;
			}
		}
		return null;
	}
	
	public String generateKey(String pseudo, String date) {
		String newKey = sha256(pseudo + date, "AuthSession2014");
		return newKey;
	}

	public boolean authentificate(String email, String password) {
		boolean isit = false;
		for (Account acc : this.accounts) {
			if (acc.getEmail().equals(email) &&
				acc.getPassword().equals(sha256(password, "AuthPassword2014"))) {
				isit = true;
				break;
			}
		}

		return isit;
	}
	
	public String sha256(String clear, String sel) {
		String hash = clear;
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			hash = byteToHex(md.digest((clear + sel).getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hash;
	}

	public String byteToHex(byte[] bits) {
		if (bits == null) {
			return null;
		}
		StringBuffer hex = new StringBuffer(bits.length * 2); // encod(1_bit) =>
																// 2 digits
		for (int i = 0; i < bits.length; i++) {
			if (((int) bits[i] & 0xff) < 0x10) { // 0 < .. < 9
				hex.append("0");
			}
			hex.append(Integer.toString((int) bits[i] & 0xff, 16)); // [(bit+256)%256]^16
		}
		return hex.toString();
	}
	
	public byte[] HexTobyte (String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }
}
