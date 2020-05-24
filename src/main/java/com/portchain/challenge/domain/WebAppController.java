package com.portchain.challenge.domain;

import com.portchain.challenge.domain.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAppController {
    private final TaskService taskService;

    public WebAppController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String challenge(Model model) {
        model.addAttribute("result", taskService.getTaskResult());
        return "index";
    }
}
