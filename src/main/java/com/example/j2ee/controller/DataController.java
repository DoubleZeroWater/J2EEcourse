package com.example.j2ee.controller;

//
//@RestController
//public class DataController {
//
//    @Autowired
//    private DataBaseService DataBaseService;
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/loginError")
//    public String loginError() {
//        return "loginError";
//    }
//
//    @GetMapping("/loginDBError")
//    public String loginDBError() {
//        return "loginDBError";
//    }
//
//    @PostMapping("/submit")
//    public String submit(String username, String password,String code, HttpSession session) {
//
//        //请求转发，会话管理
//        try{
//            session.setAttribute("user",DataBaseService.setUser(username,password));
//        }catch (RuntimeException e){
//            return "Login failed";
//        }
//        return "Login successfully";
//
//    }
//
//
//    @PostMapping("/loginVerify")
//    public String loginVerify(String username, String password, HttpSession session) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//
//        boolean verify = LoginService.verifyLogin(user);
//        try {
//            if (verify) {
//                session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
//                return "View/index";
//            } else {
//                return "redirect:/loginError";
//            }
//        } catch (Exception ex) {
//            return "redirect:/loginDBError";
//        }
//    }
//
//    @GetMapping("/Select")
//    public String Select(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String account, Model model) {
//        return "View/Select";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
//        return "redirect:/login";
//    }
//}
//}
