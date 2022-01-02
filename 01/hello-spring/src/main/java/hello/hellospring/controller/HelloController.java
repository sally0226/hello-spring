package hello.hellospring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello") // HTTP GET
    public String hello(Model model){
        model.addAttribute("data","hello~!~!"); //html에서 data변수를 attributeValue로 치환해줌
        return "hello"; // resource/templates 안의 hello.html을 return
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
        // 템플릿 엔진을 이용해서 html파일을 내보냄
    }

    @GetMapping("hello-string")
    @ResponseBody //res body에 데이터를 직접 넣겠다.
    public String helloString(@RequestParam("name") String name){
        return "우가우가 "+name;
        // 뷰 이런게 아니라 그냥 이 문자열 자체를 내보냄 이것이 api!
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello{
        private String name;

        //프롬퍼티 접근 방식? 자바 빈 방식?
        public String getName() {
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }
}
