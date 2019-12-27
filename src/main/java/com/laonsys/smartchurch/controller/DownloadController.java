package com.laonsys.smartchurch.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.laonsys.smartchurch.service.AttachService;

@Controller
@RequestMapping("/retriveResource")
public class DownloadController {

    @Autowired
    AttachService attachService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET/*, produces = "application/octet-stream"*/)
    public ResponseEntity<byte[]> download(@PathVariable int id) throws IOException {
        return attachService.retriveAttach(id);
    }
}
