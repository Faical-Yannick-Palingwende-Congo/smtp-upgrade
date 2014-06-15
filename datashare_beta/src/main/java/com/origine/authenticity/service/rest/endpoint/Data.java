package com.origine.authenticity.service.rest.endpoint;

import java.util.Date;
import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.origine.authenticity.service.rest.entity.HeadInbox;
import com.origine.authenticity.service.rest.entity.Inbox;
import com.origine.authenticity.service.rest.entity.Index;
import com.origine.authenticity.service.rest.entity.Session;
import com.origine.authenticity.service.rest.envelop.AccountKey;
import com.origine.authenticity.service.rest.envelop.BasicResponse;
import com.origine.authenticity.service.rest.envelop.DataHead;
import com.origine.authenticity.service.rest.envelop.DataReceive;
import com.origine.authenticity.service.rest.envelop.DataSendHack;
import com.origine.authenticity.service.rest.envelop.DataSendTrust;
import com.origine.authenticity.service.rest.envelop.PullResponse;
import com.origine.authenticity.service.rest.envelop.field.AccountKeyPayload;
import com.origine.authenticity.service.rest.envelop.field.DataField;
import com.origine.authenticity.service.rest.envelop.field.DataHeadField;
import com.origine.authenticity.service.rest.envelop.field.DataHeadPayload;
import com.origine.authenticity.service.rest.envelop.field.DataReceivePayload;
import com.origine.authenticity.service.rest.envelop.field.DataSendHackPayload;
import com.origine.authenticity.service.rest.envelop.field.DataSendTrustPayload;
import com.origine.authenticity.service.rest.envelop.field.PullResponsePayload;
import com.origine.authenticity.service.rest.envelop.field.ResponseBasicPayload;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

//The next big evolution is to change the payload to a json object and create a collector table that contains id table ids. It allows the recuperation of removed indexes
@Path("data")
public class Data extends AbstractEndpoint {
	
	@POST
	@Path("/push_trust")
	@Consumes({ "application/xml", "application/json" })
	public Response push_trust(DataSendTrust request){
		
		BasicResponse response;
		DataSendTrustPayload sendTrustObject = request.getPayload();
		Date stamp = new Date();
		if (sendTrustObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			Session session = key2Session(sendTrustObject.getToken());
			if(session == null){
				envelop = new ResponseBasicPayload("200", "push trust failed, you need to have an account.");
			}else{
				if(session.getState().equals("LOGIN")){
					com.origine.authenticity.service.rest.entity.Account account  = getAccountfromId(session.getAccount());
					Index index  = getIndex("head_outbox");
					long head_outbox = Long.parseLong(index.getNext());
					String send_stamp = dateFormat.format(stamp);
					String au_key = sha256(AbstractEndpoint.SERVICE_KEY_RUNTIME+send_stamp+account.getAccKey(), "AuthKey2014");
					this.adapter.write("head_outbox", "id,stamp,destinator,destination,au_key", head_outbox +","+send_stamp+","+sendTrustObject.getDestinator()+","+sendTrustObject.getDestination()+","+au_key);
					adapter.sync("indexer", "id", index.getId(), "next", ""+(head_outbox+1));
					index  = getIndex("outbox");
					long outbox = Long.parseLong(index.getNext());
					this.adapter.write("outbox", "id,head_outbox,account,content", outbox +","+head_outbox+","+account.getId()+","+sendTrustObject.getContent());
					adapter.sync("indexer", "id", index.getId(), "next", ""+(outbox+1));
					//Call the destination endpoint to send to the other service
					Client client  = Client.create();
					WebResource resource = client.resource(sendTrustObject.getDestination());
					Builder builder = resource.type(MediaType.APPLICATION_JSON);
				    builder.accept(MediaType.APPLICATION_JSON);
				    builder.post(DataReceive.class, new DataReceive(send_stamp, new DataReceivePayload(send_stamp, sendTrustObject.getSender(), sendTrustObject.getSource(), sendTrustObject.getDestinator(), sendTrustObject.getContent(), au_key)));
				    envelop = new ResponseBasicPayload("100", "Your data has been push trustfully.");
				}else{
					envelop = new ResponseBasicPayload("300", "push trust failed, you need to login before.");
				}
			}
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "push trust failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@POST
	@Path("/push_hack")
	@Consumes({ "application/xml", "application/json" })
	public Response push_hack(DataSendHack request){
		
		BasicResponse response;
		DataSendHackPayload sendHackObject = request.getPayload();
		Date stamp = new Date();
		if (sendHackObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			String send_stamp = dateFormat.format(stamp);
			//Call the destination endpoint to send to the other service directly
			Client client  = Client.create();
			WebResource resource = client.resource(sendHackObject.getDestination());
			Builder builder = resource.type(MediaType.APPLICATION_JSON);
		    builder.accept(MediaType.APPLICATION_JSON);
		    builder.post(DataReceive.class, new DataReceive(send_stamp, new DataReceivePayload(send_stamp, sendHackObject.getSender(), sendHackObject.getSource(), sendHackObject.getDestinator(), sendHackObject.getContent(), sendHackObject.getAuKey())));
		    envelop = new ResponseBasicPayload("100", "Your data has been push hackfully.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "push hack failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@POST
	@Path("/pull")
	@Consumes({ "application/xml", "application/json" })
	public Response pull(AccountKey request){
		
		AccountKeyPayload keyObject = request.getPayload();
		Date stamp = new Date();
		if (keyObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			inboxes = StringToInboxes(adapter.read("inbox", "id,head_inbox,account,content"));
			headinboxes = StringToHeadInboxes(adapter.read("head_inbox", "id,stamp,sender,source,au_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			Session session = key2Session(keyObject.getKey());
			if(session == null){
				ResponseBasicPayload envelop = new ResponseBasicPayload("200", "pull heads failed, you need to have an account.");
				BasicResponse response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
				return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
			}else{
				if(session.getState().equals("LOGIN")){
					com.origine.authenticity.service.rest.entity.Account account  = getAccountfromId(session.getAccount());
					LinkedList<DataField> datas = new LinkedList<DataField>();
					for(Inbox inbox : inboxes){
						if(inbox.getAccount().equals(account.getId())){
							for(HeadInbox headIn: headinboxes){
								if(headIn.getId().equals(inbox.getHeadInbox())){
									DataHeadField dataHead = new DataHeadField(headIn.getStamp(),headIn.getSender(),headIn.getSource(),headIn.getAuKey());
									datas.add(new DataField(dataHead, inbox.getAccount(), inbox.getContent()));
									break;
								}
							}
						}
					}
					PullResponsePayload envelop = new PullResponsePayload("100", datas);
					PullResponse response = new PullResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
					return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
				}else{
					ResponseBasicPayload envelop = new ResponseBasicPayload("300", "pull failed, you need to login before.");
					BasicResponse response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
					return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
				}
			}
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "pull failed, malformed request.");
			BasicResponse response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
			return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
		}
	}
	
	@POST
	@Path("/authentify")//We need to had a login level interservice to make sure this is not public if we want to implement it.
	@Consumes({ "application/xml", "application/json" })
	public Response authentify(DataHead request){
		
		BasicResponse response;
		DataHeadPayload HeadObject = request.getPayload();
		Date stamp = new Date();
		if (HeadObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			ResponseBasicPayload envelop;
			com.origine.authenticity.service.rest.entity.Account account = getAccountfromEmail(HeadObject.getSender());//A way to access the sender key on the serveur.
			if(account != null){
				String au_key = sha256(AbstractEndpoint.SERVICE_KEY_RUNTIME+HeadObject.getStamp()+account.getAccKey(), "AuthKey2014");
				if(au_key.equals(HeadObject.getAuKey())){
					envelop = new ResponseBasicPayload("100", "trusted data.");
				}else{
					envelop = new ResponseBasicPayload("200", "untrusted data.");
				}
			}else{
				envelop = new ResponseBasicPayload("300", "untrusted data.");
			}
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "cannot authentify, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@POST
	@Path("/receive")
	@Consumes({ "application/xml", "application/json" })
	public Response receive(DataReceive request){
		
		BasicResponse response;
		DataReceivePayload ReceiveObject = request.getPayload();
		Date stamp = new Date();
		if (ReceiveObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			com.origine.authenticity.service.rest.entity.Account account  = getAccountfromEmail(ReceiveObject.getDestinator());
			Index index  = getIndex("head_inbox");
			long head_inbox = Long.parseLong(index.getNext());
			this.adapter.write("head_inbox", "id,stamp,sender,source,au_key", head_inbox +","+ReceiveObject.getStamp()+","+ReceiveObject.getSender()+","+ReceiveObject.getSource()+","+ReceiveObject.getAuKey());
			adapter.sync("indexer", "id", index.getId(), "next", ""+(head_inbox+1));
			index  = getIndex("inbox");
			long inbox = Long.parseLong(index.getNext());
			this.adapter.write("inbox", "id,head_inbox,account,content", inbox +","+head_inbox+","+account.getId()+","+ReceiveObject.getContent());
			adapter.sync("indexer", "id", index.getId(), "next", ""+(inbox+1));
			envelop = new ResponseBasicPayload("100", "The data has been received by the server.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "data reception failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
}
