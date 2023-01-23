package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document
public class Minesweeper {

	@Id
    private String id;

	@CreatedDate
    private LocalDateTime created;
	
	@LastModifiedDate
    private LocalDateTime updated;

    private String username;
    
    private TypeMinesweeperStatus status;

    private Board board;
    
}
