package io.hsjang.markers.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Document
@Data
@JsonInclude(Include.NON_NULL)
public class MarkerBoardDetail {
	
	@Id
	String boardId;
	String title;
	String contents;
	
	MarkerBoard board;
	List<MarkerComment> comments;
	
	public MarkerBoardDetail addBoardId(String boardId) {
		this.boardId = boardId;
		return this;
	}
	
	public MarkerBoardDetail addMarkerBoard(MarkerBoard board) {
		this.board = board;
		return this;
	}
	
	public MarkerBoardDetail addComments(List<MarkerComment> comments) {
		this.comments = comments;
		return this;
	}
	
}
