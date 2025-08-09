package com.example.demo.controller;

import com.example.demo.entity.Message;
import com.example.demo.mapper.MessageMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "*")
public class MessageController {
    @Resource
    MessageMapper messageMapper;

    @GetMapping("/mylist")
    public List<Message> getMessages(@RequestParam Integer toid) {return messageMapper.findByToid(toid);}

    @GetMapping("/myaddlist")
    public List<Message> getaddMessages(@RequestParam Integer fromid) {return messageMapper.findByFromid(fromid);}

    @PostMapping("/add")
    public void addMessage(@RequestBody Message message) {messageMapper.insert(message);}

    @GetMapping("/delete")
    public void deleteMessage(@RequestParam Integer id) {messageMapper.deleteById(id);}

}
