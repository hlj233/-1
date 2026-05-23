package com.stu.hello_server.service;

import com.stu.hello_server.dto.ChatRequestDTO;
import com.stu.hello_server.vo.ChatResponseVO;

public interface ChatService {
    ChatResponseVO chat(ChatRequestDTO requestDTO);
}