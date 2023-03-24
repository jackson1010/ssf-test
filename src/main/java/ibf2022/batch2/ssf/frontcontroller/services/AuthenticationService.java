package ibf2022.batch2.ssf.frontcontroller.services;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ibf2022.batch2.ssf.frontcontroller.Model.User;
import ibf2022.batch2.ssf.frontcontroller.respositories.AuthenticationRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;

@Service
public class AuthenticationService {

	@Autowired @Qualifier("redis")
	private RedisTemplate<String, String> template;

	@Autowired
	private AuthenticationRepository auRepo;

	public static final String url = "https://auth.chuklee.com/api/authenticate";
	int count_tries = 0;

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public void authenticate(String username, String password) throws IOException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		System.out.println(username);
		System.out.println(password);

		RequestEntity<String> req = RequestEntity.post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(user.toJSON().toString());

		ResponseEntity<String> resp;
		RestTemplate template = new RestTemplate();

		resp = template.exchange(req, String.class);
		this.template.opsForValue().set("authenticated", user.ForUsernameToJSON().toString());
		//String payload = resp.getBody();
		// JsonReader jsr = Json.createReader(new StringReader(payload));
		// JsonObject jso = jsr.readObject();
		// String authenticate = jso.getString("message");
		// if (authenticate.contains("Authenticated")) {
		// 	user.setAuthenticated(true);
		// }

	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		auRepo.disableUser(username);
	

	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		auRepo.getAuthenticate(username);
		return false;
	}

}
