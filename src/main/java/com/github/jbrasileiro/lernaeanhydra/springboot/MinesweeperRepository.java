package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinesweeperRepository extends CrudRepository<Minesweeper, String> {

	@Query("{'username': ?0}")
	List<Minesweeper> findByUser(String username);

}
