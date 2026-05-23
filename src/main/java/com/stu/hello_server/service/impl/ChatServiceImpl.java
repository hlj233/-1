package com.stu.hello_server.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.stu.hello_server.dto.ChatRequestDTO;
import com.stu.hello_server.service.ChatService;
import com.stu.hello_server.vo.ChatResponseVO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;
    private final StringRedisTemplate stringRedisTemplate;

    // 可配置的历史轮数，默认保留最近3轮
    private static final int MAX_HISTORY_ROUNDS = 3;

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder,
                           StringRedisTemplate stringRedisTemplate) {
        this.chatClient = chatClientBuilder
                .defaultSystem("你是一名专业、友好、简洁的中文智能助手，请结合历史对话上下文回答用户的问题。")
                .defaultOptions(
                        DashScopeChatOptions.builder()
                                .withTopP(0.7)
                                .build()
                )
                .build();
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ChatResponseVO chat(ChatRequestDTO requestDTO) {
        // 参数校验：sessionId 不能为空
        String sessionId = requestDTO.getSessionId();
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sessionId不能为空，请提供有效的会话编号");
        }

        String message = requestDTO.getMessage();
        String redisKey = "chat:session:" + sessionId;

        // 1. 读取历史消息
        List<String> records = stringRedisTemplate.opsForList().range(redisKey, 0, -1);

        // 2. 拼接历史上下文
        String historyText = buildHistoryText(records);

        // 3. 构建包含上下文的完整提示词
        String finalPrompt = buildFinalPrompt(historyText, message);

        // 4. 调用模型
        String answer;
        try {
            answer = chatClient.prompt(finalPrompt)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new RuntimeException("调用AI模型失败：" + e.getMessage(), e);
        }

        // 5. 保存本轮记录（用户问题 + 助手回答）
        String recordText = "用户：" + message + "\n助手：" + answer;
        stringRedisTemplate.opsForList().rightPush(redisKey, recordText);

        // 6. 控制历史记录轮数，只保留最近 MAX_HISTORY_ROUNDS 轮
        trimHistoryRecords(redisKey);

        return new ChatResponseVO(message, answer);
    }

    /**
     * 构建历史对话文本
     */
    private String buildHistoryText(List<String> records) {
        if (records == null || records.isEmpty()) {
            return "";
        }
        return String.join("\n", records);
    }

    /**
     * 构建包含历史上下文的完整提示词
     */
    private String buildFinalPrompt(String historyText, String currentMessage) {
        if (historyText == null || historyText.trim().isEmpty()) {
            return currentMessage;
        }
        return "以下是历史对话：\n" + historyText + "\n\n当前用户问题：" + currentMessage;
    }

    /**
     * 修剪历史记录，只保留最近 N 轮
     */
    private void trimHistoryRecords(String redisKey) {
        try {
            Long size = stringRedisTemplate.opsForList().size(redisKey);
            if (size != null && size > MAX_HISTORY_ROUNDS) {
                // 只保留最后 MAX_HISTORY_ROUNDS 条记录
                stringRedisTemplate.opsForList().trim(redisKey, size - MAX_HISTORY_ROUNDS, -1);
            }
        } catch (Exception e) {
            // 记录日志但不影响主流程
            System.err.println("修剪历史记录失败：" + e.getMessage());
        }
    }
}