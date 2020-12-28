package com.example.board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;
import com.example.board.board.service.BoardService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * BoardController
 */
@Controller
public class RestBoardController {

    public static final String REDIRECTION = "redirect:/board";

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "/board", method = RequestMethod.GET)
    public ModelAndView openBoardList() throws Exception {

        ModelAndView modelAndView = new ModelAndView("/board/restBoardList");

        List<BoardDto> list = boardService.selectBoardList();
        modelAndView.addObject("list", list);

        return modelAndView;
    }

    @RequestMapping(value = "/board/write", method = RequestMethod.GET)
    public String openBoardWrite() throws Exception {
        return "/board/restBoardWrite";
    }

    @RequestMapping(value = "/board/write", method = RequestMethod.POST)
    public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest)
            throws Exception {
        boardService.insertBoard(boardDto, multipartHttpServletRequest);
        return REDIRECTION;
    }

    @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/board/restBoardDetail");

        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
        modelAndView.addObject("board", boardDto);

        return modelAndView;
    }

    @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.POST)
    //원본 " @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.PUT)
    public String updateBoard(BoardDto boardDto) throws Exception {
        boardService.updateBoard(boardDto);
        return REDIRECTION;
    }

    @RequestMapping(value = "/boarddelete/{boardIdx}", method = RequestMethod.POST)
    //원본 : @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return REDIRECTION;
    }

    @RequestMapping(value = "/board/file", method = RequestMethod.GET)
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx,
            HttpServletResponse httpServletResponse) throws Exception {
        BoardFileDto boardFileDto = boardService.selectBoardFileInformation(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileDto) == false) {
            String fileName = boardFileDto.getOriginalFileName();

            byte[] files = FileUtils.readFileToByteArray(new File(boardFileDto.getStoredFilePath()));

            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setContentLength(files.length);
            httpServletResponse.setHeader("Content-Disposition",
                    "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
            httpServletResponse.setHeader("Content-Transfer-Encoding", "binary");

            httpServletResponse.getOutputStream().write(files);
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        }
    }
}