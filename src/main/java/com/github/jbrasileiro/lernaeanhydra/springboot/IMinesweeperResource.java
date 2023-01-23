package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Minesweeper API")
public interface IMinesweeperResource {

	@ApiOperation(value = "Create a new game")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created successfully") })
	ResponseEntity<Void> create(@Valid ManesweeperConfigurationPOST aRequest);

	@ApiOperation(value = "Find a game")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Game found") })
	ResponseEntity<Minesweeper> get(String id);

    @ApiOperation(value = "Return all the games by username", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retrieved successfully") })
	List<Minesweeper> findByUser(String username);

}
