package com.example.board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.board.board.entity.BoardEntity;
import com.example.board.board.entity.BoardFileEntity;
import com.example.board.board.service.JpaBoardService;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class JpaBoardController {

    public static final String REDIRECTION = "redirect:/jpa/board";

    @Autowired
    private JpaBoardService jpaBoardService;

    @RequestMapping(value = "/jpa/board", method = RequestMethod.GET)
    public ModelAndView openBoardList(ModelMap modelMap) throws Exception {

        ModelAndView modelAndView = new ModelAndView("/board/jpaBoardList");

        List<BoardEntity> list = jpaBoardService.selectBoardList();
        modelAndView.addObject("list", list);

        return modelAndView;
    }

    @RequestMapping(value = "/jpa/board/write", method = RequestMethod.GET)
    public String openBoardWrite() throws Exception {
        return "/board/japBoardWrite";
    }

    @RequestMapping(value = "/jpa/board/write", method = RequestMethod.POST)
    public String writeBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest)
            throws Exception {
        jpaBoardService.saveBoard(boardEntity, multipartHttpServletRequest);

        return REDIRECTION;
    }

    @RequestMapping(value = "/jpa/board/{boardIdx}", method = RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/board/japBoardDetail");

        BoardEntity boardEntity = jpaBoardService.selectBoardDetail(boardIdx);
        modelAndView.addObject("board", boardEntity);

        return modelAndView;
    }

    @RequestMapping(value = "/jpa/board/{boardIdx}", method = RequestMethod.POST)
    // 원본 " @RequestMapping(value = "/jpa/board/{boardIdx}", method =
    // RequestMethod.PUT)
    public String updateBoard(BoardEntity boardEntity) throws Exception {
        jpaBoardService.saveBoard(boardEntity, null);
        return REDIRECTION;
    }

    @RequestMapping(value = "/jpa/boarddelete/{boardIdx}", method = RequestMethod.POST)
    // 원본 : @RequestMapping(value = "/board/{boardIdx}", method =
    // RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        jpaBoardService.deleteBoard(boardIdx);
        return REDIRECTION;
    }

    @RequestMapping(value = "/jpa/board/file", method = RequestMethod.GET)
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx,
            HttpServletResponse httpServletResponse) throws Exception {
        BoardFileEntity boardFileEntity = jpaBoardService.selectBoardFileInformation(boardIdx, idx);
        if (ObjectUtils.isEmpty(boardFileEntity) == false) {
            String fileName = boardFileEntity.getOriginalFileName();

            byte[] files = FileUtils.readFileToByteArray(new File(boardFileEntity.getStoredFilePath()));

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