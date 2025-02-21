package com.mdms.mdms.helpers;



import org.springframework.security.core.Authentication; //a core interface for representing the authentication state
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken; //used for OAuth2-based authentication
import org.springframework.security.oauth2.core.user.OAuth2User;   //epresents the user details from an OAuth2 provider.


public class HelperForUserName {


    public static String getEmailOfLoggedInUser(Authentication authentication){         //##Authentication

        if(authentication instanceof OAuth2AuthenticationToken) {  //indicating that if the user authenticated via an OAuth2 provider.

           var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;  // casting it to OAuth2AuthenticationToken

           String clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

           OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
           String username = "";

           if (clientId.equalsIgnoreCase("google")) {

               // sign with google
               System.out.println("Getting email from google");
               username = oauth2User.getAttribute("email").toString();

           } 

           return username;

       } else {
           System.out.println("Getting data from local database");
           return authentication.getName();
       }

   }
}
/*Authentication interface represents the authentication information for a user 
 * Authentication Contains:  
 *          1.Principal:-> an instance of a user details object (like UserDetails or OAuth2User) which contains user-specific information
 *          2.Credentials(like Password)  3.Authorities  4.Details  5.Authenticated Status
*/