package com.cwm.ecom.playwrite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.cwm.ecom.model.Address;
import com.cwm.ecom.model.User;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
@TestInstance(Lifecycle.PER_CLASS)
public class BaseClass {
	
	  protected RequestManager manager;
	  private Playwright  playwright;
	  private User user;
	  private APIRequestContext apiRequestContext;
	  
	  
	    @BeforeEach
	    public  void setupBase () {
	        manager = new RequestManager ();
	        manager.createPlaywright ();
	        final String baseUrl = "http://localhost:7171/api/user";
	        Map<String, String> headers = new HashMap<> ();
	        headers.put ("content-type", "application/json");
	        headers.put ("Accept", "application/json");
	        manager.setApiRequestContext (baseUrl, headers);
	        
	        
	        Set<String> roles = new HashSet<>();
			roles.add("USER");

			Address address = Address.builder().id(1L).state("Maharashtra").street("Karvenager").city("Pune")
					.country("INDIA").pin("11111").build();

			user = User.builder().id(1L).firstName("Mayur").lastName("Bhosale").email("mayur@test.com")
					.contact("1234567890").password("password").username("username").address(address).role(roles).build();
	    }

	    @AfterAll
	    public void tearDown () {
	        manager.disposeAPIRequestContext ();
	        manager.closePlaywright ();
	    }
	    
	
		
		public void setApiRequestContext (String baseUrl,Map<String, String> headers) {
		        apiRequestContext = playwright.request ()
		            .newContext (new APIRequest.NewContextOptions ()
		            .setBaseURL (baseUrl)
		            .setExtraHTTPHeaders (headers));
		  }
	    
	    @Test
	    public void createBookingTest() {
	        APIResponse response = manager.postRequest("/create", 
	          RequestOptions.create().setData(user));

	          assertEquals(response.status(), 401);

 }
	    
	   
}
