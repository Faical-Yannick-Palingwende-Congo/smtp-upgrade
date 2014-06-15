package com.origine.authenticity.service.rest.endpoint;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.origine.authenticity.service.rest.entity.Index;
import com.origine.authenticity.service.rest.entity.Session;
import com.origine.authenticity.service.rest.envelop.AccountKey;
import com.origine.authenticity.service.rest.envelop.AccountLogin;
import com.origine.authenticity.service.rest.envelop.AccountRegister;
import com.origine.authenticity.service.rest.envelop.BasicResponse;
import com.origine.authenticity.service.rest.envelop.field.AccountKeyPayload;
import com.origine.authenticity.service.rest.envelop.field.AccountLoginPayload;
import com.origine.authenticity.service.rest.envelop.field.AccountRegisterPayload;
import com.origine.authenticity.service.rest.envelop.field.ResponseBasicPayload;

@Path("account")
public class Account extends AbstractEndpoint {
	
	@POST
	@Path("/register")
	@Consumes({ "application/xml", "application/json" })
	public Response register(AccountRegister request){
		
		BasicResponse response;
		AccountRegisterPayload RegisterObject = request.getPayload();
		Date stamp = new Date();
		if (RegisterObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			long account = email2Account(RegisterObject.getEmail());
			if(account != 0){
				envelop = new ResponseBasicPayload("200", "Registering failed, this email is already used. Please if you have registered use your default password or register. We recommand you to get a new password in the first case.");
			
			}else{
				Index index  = getIndex("account");
				account = Long.parseLong(index.getNext());
				this.adapter.write("account", "id,email,password,acc_key", account +","+RegisterObject.getEmail()+","+sha256(RegisterObject.getPassword(), "AuthPassword2014")+","+sha256(RegisterObject.getEmail(), "UserKey2014"));
				adapter.sync("indexer", "id", index.getId(), "next", ""+(account+1));
				index  = getIndex("session");
				long session = Long.parseLong(index.getNext());
				this.adapter.write("session", "id,account,sess_key,state,stamp",session +","+ account +","+generateKey(RegisterObject.getEmail(), dateFormat.format(stamp))+",REGISTERED,"+dateFormat.format(stamp));
				adapter.sync("indexer", "id", index.getId(), "next", ""+(session+1));
				envelop = new ResponseBasicPayload("100", "Your user account has been create successfully.");
		   }
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "Registering me failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@POST
	@Path("/login")
	@Consumes({ "application/xml", "application/json" })
	public Response login(AccountLogin request){
		
		BasicResponse response;
		AccountLoginPayload LoginObject = request.getPayload();
		Date stamp = new Date();
		if (LoginObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			if(authentificate(LoginObject.getEmail(), LoginObject.getPassword())){
				com.origine.authenticity.service.rest.entity.Account account = getAccountfromEmail(LoginObject.getEmail());
				Session session = account2Session(account.getId());

				if(session.getState().equals("LOGIN") || session.getState().equals("LOGOUT") || session.getState().equals("REGISTERED")){
					String key = generateKey(account.getEmail(), dateFormat.format(stamp));
					this.adapter.sync("session", "id", session.getId(),"sess_key,stamp,state",key+","+dateFormat.format(stamp)+",LOGIN");
					envelop = new ResponseBasicPayload("100", key);
				}else{
					envelop = new ResponseBasicPayload("700", "Login failed, your state is not consistent. Please contact us.");
				}
			}else{
				envelop = new ResponseBasicPayload("400", "Login failed. Bad email or password.");
			}
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "Login failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@POST
	@Path("/logout")
	@Consumes({ "application/xml", "application/json" })
	public Response logout(AccountKey request){
		
		BasicResponse response;
		AccountKeyPayload LogoutObject = request.getPayload();
		Date stamp = new Date();
		if (LogoutObject.sanity()) {
			accounts = StringToAccounts(adapter.read("account", "id,email,password,acc_key"));
			sessions = StringToSessions(adapter.read("session", "id,account,sess_key,state,stamp"));
			indexes = StringToIndexes(adapter.read("indexer", "id,table,next"));
			ResponseBasicPayload envelop;
			Session session = key2Session(LogoutObject.getKey());
			if(session == null){
				envelop = new ResponseBasicPayload("200", "Logout failed, you need to have an account.");
			}else{
				if(session.getState().equals("LOGIN")){
					this.adapter.sync("session", "id", session.getId(),"stamp,state",dateFormat.format(stamp)+",LOGOUT");
					envelop = new ResponseBasicPayload("100", "You have been logout.");
				}else{
					envelop = new ResponseBasicPayload("300", "Logout failed, you need to login before.");
				}
			}
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		} else {
			ResponseBasicPayload envelop = new ResponseBasicPayload("000", "Logout failed, malformed request.");
			response = new BasicResponse(AbstractEndpoint.dateFormat.format(stamp), envelop);
		}
		return Response.ok(response, MediaType.APPLICATION_JSON_TYPE).build();
	}
}
