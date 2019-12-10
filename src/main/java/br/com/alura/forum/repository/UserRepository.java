package br.com.alura.forum.repository;

import java.util.Optional;

import br.com.alura.forum.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findById(Long userId);
	User save(User user);
	
}
