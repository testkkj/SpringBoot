package com.example.board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;
import com.example.board.board.service.BoardService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * BoardController
 */
@Controller
public class BoardController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String REDIRECTION = "redirect:/board/openBoardList.do";

    @Autowired
    private BoardService boardService;

    @RequestMapping("/board/openBoardList.do")
    public ModelAndView openBoardList() throws Exception {
        log.debug("openBoardList");

        ModelAndView modelAndView = new ModelAndView("/board/boardList");

        List<BoardDto> list = boardService.selectBoardList();
        modelAndView.addObject("list", list);

        return modelAndView;
    }

    @RequestMapping("/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/board/boardDetail");

        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
        modelAndView.addObject("board", boardDto);

        return modelAndView;
    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardService.insertBoard(boardDto, multipartHttpServletRequest);
        return REDIRECTION;
    }

    @RequestMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto boardDto) throws Exception {
        boardService.updateBoard(boardDto);
        return REDIRECTION;
    }

    @RequestMapping("/board/deleteBoard.do")
    public String deleteBoard(int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return REDIRECTION;
    }

    @RequestMapping("/board/downloadBoardFile.do")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse httpServletResponse) throws Exception {
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