package com.example.board.board.controller;

import java.util.List;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * BoardController
 */
@RestController
public class RestBoardApiController {

    public static final String REDIRECTION = "redirect:/board";

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/api/board", method = RequestMethod.GET)
    public List<BoardDto> openBoardList() throws Exception {
        return boardService.selectBoardList();
    }

    @RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
    public void insertBoard(@RequestBody BoardDto boardDto) throws Exception {
        boardService.insertBoard(boardDto, null);
    }

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
    public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        return boardService.selectBoardDetail(boardIdx);
    }

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
    public String updateBoard(@RequestBody BoardDto boardDto) throws Exception {
        boardService.updateBoard(boardDto);
        return REDIRECTION;
    }

    @RequestMapping(value = "/api/boarddelete/{boardIdx}", method = RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return REDIRECTION;
    }
}