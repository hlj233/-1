package com.stu.hello_server.controller;

import com.stu.hello_server.common.Result;
import com.stu.hello_server.dto.ChatRequestDTO;
import com.stu.hello_server.service.ChatService;
import com.stu.hello_server.vo.ChatResponseVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Result<ChatResponseVO> chat(@RequestBody ChatRequestDTO requestDTO) {
        try {
            // 参数校验
            if (requestDTO.getSessionId() == null || requestDTO.getSessionId().trim().isEmpty()) {
                return Result.error(com.stu.hello_server.common.ResultCode.ERROR);
            }
            if (requestDTO.getMessage() == null || requestDTO.getMessage().trim().isEmpty()) {
                return Result.error(com.stu.hello_server.common.ResultCode.ERROR);
            }

            ChatResponseVO responseVO = chatService.chat(requestDTO);
            return Result.success(responseVO);
        } catch (IllegalArgumentException e) {
            return Result.error(com.stu.hello_server.common.ResultCode.ERROR);
        } catch (Exception e) {
            return Result.error(com.stu.hello_server.common.ResultCode.ERROR);
        }
    }
}