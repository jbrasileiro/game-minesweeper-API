package com.github.jbrasileiro.lernaeanhydra.springboot;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonPropertyOrder(value = {
		"username",
		"columns",
		"rows",
		"mines",
})
public class ManesweeperConfigurationPOST {

	@ApiModelProperty(example = "any")
	@NotEmpty
	private String username;
	
	@ApiModelProperty(example = "2")
	@Range(min = 2, max = 9)
	private Integer columns;
	
	@ApiModelProperty(example = "2")
	@Range(min = 2, max = 9)
	private Integer rows;
	
	@ApiModelProperty(example = "1")
	@NotNull
	@Min(value = 1)
	private Integer mines;
	
}
