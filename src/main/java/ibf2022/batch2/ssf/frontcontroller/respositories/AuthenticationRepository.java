package ibf2022.batch2.ssf.frontcontroller.respositories;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.batch2.ssf.frontcontroller.Model.User;

@Repository
public class AuthenticationRepository {

	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	@Autowired
	@Qualifier("redis")
	private RedisTemplate<String, String> template;

	public void disableUser(String username) {
		User user = new User();
		user.setUsername(username);
		long timeout = 30;
		TimeUnit unit = TimeUnit.MINUTES;
		this.template.opsForValue().set("username", user.ForUsernameToJSON().toString(), timeout, unit);
	}

	public void getAuthenticate(String username) {
		User user = new User();
		user.setUsername(username);
		this.template.opsForValue().set("authenticated", user.ForUsernameToJSON().toString());
	}
}
