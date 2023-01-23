package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class MinesweeperService {
	
	private MinesweeperRepository repository;
	
	@Inject
	public MinesweeperService(MinesweeperRepository repository) {
		super();
		this.repository = repository;
	}

	public Minesweeper newGame(ManesweeperConfigurationPOST request) {
		if(request.getMines() > request.getRows() * request.getColumns()) {
			throw CustomHttpExceptions.badRequest("Mines must be less than total of fields ");
		}
		int height = request.getRows();
		int width = request.getColumns();
		int mines = request.getMines();
		BoardVO vo = BoardVO.of(height, width, mines);
		Board board = Board.builder()
				.height(height)
				.width(width)
				.mines(mines)
				.boardFields(vo.toBoardFields())
				.boardMines(vo.toBoardMines())
				.build();
		Minesweeper newGame = Minesweeper.builder()
	            .status(TypeMinesweeperStatus.NEW)
	            .username(request.getUsername())
	            .board(board)
	            .build();
		return repository.save(newGame);
	}

	public Minesweeper get(String id) {
		return repository.findById(id)
				.orElseThrow(() -> CustomHttpExceptions.noEntityFound("Minesweeper id=" + id));
	}

	public List<Minesweeper> findByUsername(String username) {
		   return this.repository.findByUser(username);
	}

}