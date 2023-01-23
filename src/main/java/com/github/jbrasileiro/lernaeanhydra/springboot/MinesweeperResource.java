package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/minesweeper")
public class MinesweeperResource implements IMinesweeperResource {

	private final MinesweeperService service;

	@Inject
	public MinesweeperResource(MinesweeperService service) {
		super();
		this.service = service;
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Minesweeper> get(@PathVariable String id) {
		return ResponseEntity.ok(service.get(id));
	}

	@PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public ResponseEntity<Void> create(@RequestBody final ManesweeperConfigurationPOST request) {
		Minesweeper minesweeper = service.newGame(request);
		return CustomResponseEntity.create(MinesweeperResponse.class, minesweeper.getId());
	}

	@GetMapping(value = "/load/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public List<Minesweeper> findByUser(@PathVariable final String username) {
		return this.service.findByUsername(username);
	}

}
